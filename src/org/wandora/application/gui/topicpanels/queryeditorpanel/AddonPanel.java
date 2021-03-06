/*
 * WANDORA
 * Knowledge Extraction, Management, and Publishing Application
 * http://wandora.org
 * 
 * Copyright (C) 2004-2016 Wandora Team
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
 * 
 */
package org.wandora.application.gui.topicpanels.queryeditorpanel;

import org.wandora.query2.DirectiveUIHints.Addon;
import org.wandora.query2.DirectiveUIHints.Parameter;

/**
 *
 * @author olli
 */


public class AddonPanel extends javax.swing.JPanel {

    protected Addon addon;
    
    protected DirectiveEditor parentPanel;
    protected AbstractTypePanel[] parameterPanels;
    
    /**
     * Creates new form AddonPanel
     */
    public AddonPanel() {
        initComponents();
    }
    
    public AddonPanel(DirectiveEditor parent,Addon addon){
        this();
        this.parentPanel=parent;
        setAddon(addon);
    }
    
    public Addon getAddon(){
        return addon;
    }
    
    public AbstractTypePanel[] getParameterPanels(){
        return parameterPanels;
    }
    
    public void populateParametersPanel(){
        Parameter[] parameters=addon.getParameters();
        this.parameterPanels=DirectiveEditor.populateParametersPanel(parametersPanel, parameters, this.parameterPanels, parentPanel.getDirectivePanel());
        this.revalidate();
        parametersPanel.repaint();        
    }

    public void setAddon(Addon addon) {
        this.addon = addon;
        this.addonLabel.setText(addon.getLabel());
        populateParametersPanel();
    }
    
    public void disconnect(){
        if(this.parameterPanels!=null){
            for(AbstractTypePanel pp : this.parameterPanels){
                pp.disconnect();
            }
        }
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

        addonLabel = new javax.swing.JLabel();
        deleteButton = new javax.swing.JButton();
        parametersPanel = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        setLayout(new java.awt.GridBagLayout());

        addonLabel.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        addonLabel.setText("Addon label");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(addonLabel, gridBagConstraints);

        deleteButton.setText("Remove addon");
        deleteButton.setToolTipText("");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
        add(deleteButton, gridBagConstraints);

        parametersPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        parametersPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        add(parametersPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        parentPanel.removeAddon(this);
    }//GEN-LAST:event_deleteButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addonLabel;
    private javax.swing.JButton deleteButton;
    private javax.swing.JPanel parametersPanel;
    // End of variables declaration//GEN-END:variables
}
