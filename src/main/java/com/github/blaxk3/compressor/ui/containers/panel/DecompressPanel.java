package com.github.blaxk3.compressor.ui.containers.panel;

import com.github.blaxk3.compressor.ui.controls.CompressPanelControls;
import com.github.blaxk3.compressor.ui.controls.DecompressPanelControls;

import java.awt.FlowLayout;

public class DecompressPanel extends javax.swing.JPanel {

    public DecompressPanel() {
        CompressPanelControls dpc = new DecompressPanelControls();
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setSize(500,500);
        add(dpc.labelMode());
        add(dpc.textFieldPath());
        add(dpc.buttonSelect()[0]);
        add(dpc.buttonConfirm());
        add(dpc.buttonSelect()[3]);
    }
}
