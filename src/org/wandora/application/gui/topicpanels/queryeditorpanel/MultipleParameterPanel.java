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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javax.swing.JButton;
import org.wandora.application.Wandora;
import org.wandora.query2.DirectiveUIHints.Parameter;

/**
 *
 * @author olli
 */


public class MultipleParameterPanel extends AbstractTypePanel {

    protected Class<? extends AbstractTypePanel> typeCls;
    
    protected final ArrayList<Row> rows=new ArrayList<Row>();
    
    protected static class Row {
        public AbstractTypePanel panel;
        public JButton removeButton;
        public Row(AbstractTypePanel panel, JButton removeButton) {
            this.panel = panel;
            this.removeButton = removeButton;
        }
        public Row(){}
    }
    
    /**
     * Creates new form MultipleParameterPanel
     */
    public MultipleParameterPanel(Parameter parameter,Class<? extends AbstractTypePanel> typeCls,DirectivePanel panel) {
        super(parameter,panel);
        initComponents();
        this.typeCls=typeCls;
    }

    @Override
    public synchronized void setValue(Object o){
        for(Row row : rows){
            row.panel.disconnect();
        }
        parametersPanel.removeAll();
        rows.clear();
        
        Object[] a=(Object[])o;
        for (Object ao : a) {
            addParameter();
            Row row=rows.get(rows.size()-1);
            row.panel.setValue(ao);
        }
    }    
    
    @Override
    public Object getValue() {
        Object[] ret=new Object[rows.size()];
        for(int i=0;i<rows.size();i++){
            Row row=rows.get(i);
            ret[i]=row.panel.getValue();
        }        
        return ret;
    }

    @Override
    public String getValueScript() {
        StringBuilder sb=new StringBuilder();
        sb.append("new ");
        sb.append(parameter.getType().getSimpleName());
        sb.append("[]{");
        boolean first=true;
        for(Row row : rows){
            if(!first) sb.append(", ");
            else first=false;
            sb.append(row.panel.getValueScript());
        }
        sb.append("}");
        return sb.toString();
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
        addButton = new javax.swing.JButton();
        parametersPanel = new javax.swing.JPanel();

        setBorder(null);
        setLayout(new java.awt.GridBagLayout());

        parameterLabel.setText("Label");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        add(parameterLabel, gridBagConstraints);

        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(addButton, gridBagConstraints);

        parametersPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        parametersPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        add(parametersPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        addParameter();
    }//GEN-LAST:event_addButtonActionPerformed

    public synchronized void addParameter(){
        final AbstractTypePanel paramPanel;
        try{
            Constructor c=typeCls.getConstructor(Parameter.class,DirectivePanel.class);
            paramPanel=(AbstractTypePanel)c.newInstance(this.parameter,this.directivePanel);
        }catch(IllegalAccessException | InstantiationException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException | SecurityException e){
            Wandora.getWandora().handleError(e);
            return;
        }
        

        GridBagConstraints gbc=new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=rows.size();
        gbc.insets=new Insets(5, 5, 0, 5);
        
        JButton removeButton=new JButton();
        removeButton.setText("Remove");
        removeButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                removeParameter(paramPanel);
            }
        });
        parametersPanel.add(removeButton,gbc);
        
        gbc.gridx=1;
        gbc.weightx=1.0;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        paramPanel.setLabel("-");
        parametersPanel.add(paramPanel,gbc);
        if(this.orderingHint!=null) {
            paramPanel.setOrderingHint(orderingHint+rows.size());
        }
        
        this.revalidate();
        parametersPanel.repaint();
        
        rows.add(new Row(paramPanel, removeButton));
    }
    
    public synchronized void removeParameter(int index){
        GridBagLayout layout=(GridBagLayout)parametersPanel.getLayout();
        Row row=rows.get(index);
        row.panel.disconnect();
        
        parametersPanel.remove(row.panel);
        parametersPanel.remove(row.removeButton);
        rows.remove(index);
        for(int i=index;i<rows.size();i++){
            row=rows.get(i);
            GridBagConstraints gbc=layout.getConstraints(row.panel);
            gbc.gridy--;
            layout.setConstraints(row.panel, gbc);
            gbc=layout.getConstraints(row.removeButton);
            gbc.gridy--;
            layout.setConstraints(row.removeButton, gbc);
            if(this.orderingHint!=null) row.panel.setOrderingHint(this.orderingHint+i);
        }
        
        this.revalidate();
        parametersPanel.repaint();
    }

    @Override
    public void setOrderingHint(String orderingHint) {
        super.setOrderingHint(orderingHint);
        for(int i=0;i<rows.size();i++){
            Row row=rows.get(i);
            if(this.orderingHint!=null) row.panel.setOrderingHint(this.orderingHint+i);
            else row.panel.setOrderingHint(null);
        }
    }

    
    
    public synchronized void removeParameter(AbstractTypePanel panel){
        for(int i=0;i<rows.size();i++){
            if(rows.get(i).panel==panel) {
                removeParameter(i);
                break;
            }
        }
    }
    
    @Override
    public void setLabel(String label){
        parameterLabel.setText(label);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JLabel parameterLabel;
    private javax.swing.JPanel parametersPanel;
    // End of variables declaration//GEN-END:variables
}
