package com.github.blaxk3.compressor.ui.windows.dialog;

import com.github.blaxk3.compressor.ui.controls.CompressPanelControls;

import java.awt.FlowLayout;

public class SelectOption extends javax.swing.JDialog{

    CompressPanelControls cpp = new CompressPanelControls();

    public SelectOption() {
        setSize(200, 150);
        setModal(true);
        setLayout(new FlowLayout());
        setResizable(false);
        setLocationRelativeTo(null);
        add(cpp.buttonSelect()[1]);
        add(cpp.buttonSelect()[2]);
        setVisible(true);
    }
}
