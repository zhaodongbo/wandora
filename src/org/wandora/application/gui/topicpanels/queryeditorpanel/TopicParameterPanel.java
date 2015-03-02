/*
 * WANDORA
 * Knowledge Extraction, Management, and Publishing Application
 * http://wandora.org
 * 
 * Copyright (C) 2004-2015 Wandora Team
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.wandora.application.gui.topicpanels.queryeditorpanel;

import org.wandora.application.Wandora;
import org.wandora.application.gui.GetTopicButton;
import org.wandora.query2.Directive;
import org.wandora.query2.DirectiveUIHints.Parameter;
import org.wandora.topicmap.Topic;
import org.wandora.topicmap.TopicMapException;

/**
 *
 * @author olli
 */


public class TopicParameterPanel extends AbstractTypePanel {

    /**
     * Creates new form TopicParameterPanel
     */
    public TopicParameterPanel(Parameter parameter,DirectivePanel panel) {
        super(parameter,panel);
        initComponents();
    }

    @Override
    public void setLabel(String label){
        parameterLabel.setText(label);
    }
    
    @Override
    public void setValue(Object o){
        try{
            if(o instanceof Topic)
                ((GetTopicButton)getTopicButton).setTopic((Topic)o);
            else if(o instanceof String)
                ((GetTopicButton)getTopicButton).setTopic((String)o);
            else throw new RuntimeException("Topic value must be a topic or a string");
        }catch(TopicMapException tme){
            Wandora.getWandora().handleError(tme);
        }
    }    
    
    @Override
    public Object getValue(){
        return ((GetTopicButton)getTopicButton).getTopic();
    }
    @Override
    public String getValueScript(){
        Object o=getValue();
        if(o==null) return "null";
        
        if(o instanceof Topic){
            try{
                return "\""+((Topic)o).getOneSubjectIdentifier().toExternalForm()+"\"";
            }catch(TopicMapException tme){
                Wandora.getWandora().handleError(tme);
                return null;
            }
        }
        else if(o instanceof String){
            return "\""+o+"\"";
        }
        else throw new RuntimeException("Topic value must be a topic or a string");
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        parameterLabel = new javax.swing.JLabel();
        try{
            getTopicButton = new GetTopicButton();
        }catch(TopicMapException tme){Wandora.getWandora().handleError(tme);}

        setLayout(new java.awt.GridBagLayout());

        parameterLabel.setText("Label");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        add(parameterLabel, gridBagConstraints);

        getTopicButton.setText("Topic");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.weightx = 1.0;
        add(getTopicButton, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton getTopicButton;
    private javax.swing.JLabel parameterLabel;
    // End of variables declaration//GEN-END:variables
}
