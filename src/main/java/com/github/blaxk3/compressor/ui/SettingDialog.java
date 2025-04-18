package com.github.blaxk3.compressor.ui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class SettingDialog extends javax.swing.JDialog {

    private final SettingsDialogControls settingsDialogControls = new SettingsDialogControls();
    private JPanel buttonPanePanel;
    private JPanel dictSourcePanePanel;
    private JPanel optionPanePanel;
    private JPanel mainDesignPanel;

    public SettingDialog() {
        setTitle("Settings");
        setSize(500,300);
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(null);
        add(designSettingDialogPanePanel());
        setVisible(true);
    }

    private JPanel designSettingDialogPanePanel() {
        mainDesignPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(2, 4, 2, 4);
        mainDesignPanel.add(dictSourcePanePanel(), gbc);

        gbc.gridy++;
        mainDesignPanel.add(optionPanePanel(), gbc);

        gbc.gridy++;
        mainDesignPanel.add(buttonPanePanel(), gbc);
        return mainDesignPanel;
    }

    public JPanel dictSourcePanePanel() {
        dictSourcePanePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        dictSourcePanePanel.add(settingsDialogControls.labelDict(), gbc);

        gbc.gridy++;
        dictSourcePanePanel.add(settingsDialogControls.labelCompressLevel(), gbc);

        gbc.gridx++;
        gbc.gridy--;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        dictSourcePanePanel.add(settingsDialogControls.textFieldDictPath(), gbc);

        gbc.gridx++;
        gbc.weightx = 0;
        dictSourcePanePanel.add(settingsDialogControls.buttonSelectDict(), gbc);

        gbc.gridx--;
        gbc.gridy++;
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.fill = GridBagConstraints.NONE;
        dictSourcePanePanel.add(settingsDialogControls.getComboBoxCompressLevel(), gbc);

        return dictSourcePanePanel;
    }

    public JPanel optionPanePanel() {
        optionPanePanel = new JPanel(new GridBagLayout());
        optionPanePanel.setBorder(new CompoundBorder(new TitledBorder("Options"), new EmptyBorder(12, 0, 0, 0)));

        JPanel oppPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        oppPanel.add(settingsDialogControls.checkBoxTrainDict(), gbc);

        gbc.gridx++;
        oppPanel.add(settingsDialogControls.labelCheckBoxTrainDict(), gbc);

        gbc.gridx--;
        gbc.gridy++;
        oppPanel.add(settingsDialogControls.checkBoxDeleteFile(), gbc);

        gbc.gridx++;
        gbc.weightx = 1;
        oppPanel.add(settingsDialogControls.labelCheckBoxDeleteFile(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 4, 4, 4);
        optionPanePanel.add(oppPanel, gbc);
        return optionPanePanel;
    }

    public JPanel buttonPanePanel() {
        buttonPanePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanePanel.add(settingsDialogControls.buttonOK());
        buttonPanePanel.add(settingsDialogControls.buttonSave());
        buttonPanePanel.add(settingsDialogControls.buttonCancel());
        return buttonPanePanel;
    }
}
