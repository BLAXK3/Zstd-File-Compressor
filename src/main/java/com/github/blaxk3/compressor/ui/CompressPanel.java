package com.github.blaxk3.compressor.ui;

import java.awt.FlowLayout;

public class CompressPanel extends javax.swing.JPanel {

    public CompressPanel(String mode) {
        UIControls uiControls = new UIControls();
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setSize(500,500);
        add(uiControls.labelMode(mode));
        add(uiControls.scrollPaneAllFile());
        add(uiControls.textFieldPath());
        add(uiControls.buttonSelect()[0]);

        switch (mode) {
            case "Compress" :
                add(uiControls.buttonCPDP()[0]);
                break;
            case "Decompress" :
                add(uiControls.buttonCPDP()[1]);
                break;
        }
        add(uiControls.buttonSelect()[3]);
    }
}
