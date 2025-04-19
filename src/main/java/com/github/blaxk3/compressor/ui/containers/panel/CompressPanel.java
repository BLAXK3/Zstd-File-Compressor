package com.github.blaxk3.compressor.ui.containers.panel;

import com.github.blaxk3.compressor.ui.controls.CompressPanelControls;
import com.github.blaxk3.compressor.ui.containers.scrollpane.FileScrollPane;

import java.awt.FlowLayout;

public class CompressPanel extends javax.swing.JPanel {
    
    public CompressPanel() {
        CompressPanelControls cpc = new CompressPanelControls();
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setSize(500,500);
        add(cpc.labelMode());
        add(new FileScrollPane(null));
        add(cpc.textFieldPath());
        add(cpc.buttonSelect()[0]);
        add(cpc.buttonConfirm());
        add(cpc.buttonSelect()[3]);

    }
}
