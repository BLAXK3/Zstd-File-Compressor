package com.github.blaxk3.compressor.ui;

import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class SettingDialog extends javax.swing.JDialog {

    private SettingsDialogControls settingsDialogControls = new SettingsDialogControls();

    public SettingDialog() {

        add(designSettingDialog());
        setTitle("Settings");
        setSize(500,500);
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel designSettingDialog() {
        /*
        *
        * Test design GridBagLayout
        *
        * */
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new CompoundBorder(new TitledBorder("Options"), new EmptyBorder(12, 0, 0, 0)));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 0, 4);
        return panel;
    }
}
