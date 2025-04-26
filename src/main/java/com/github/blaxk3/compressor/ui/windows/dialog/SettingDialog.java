package com.github.blaxk3.compressor.ui.windows.dialog;

import com.github.blaxk3.compressor.ui.frame.MainFrame;
import com.github.blaxk3.compressor.ui.process.CheckInputFile;
import com.github.blaxk3.compressor.ui.windows.chooser.FileChooser;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class SettingDialog extends javax.swing.JDialog {

    private JButton buttonSelectDict;
    private JButton buttonOK;
    private JButton buttonSave;
    private JButton buttonCancel;
    private JCheckBox checkBoxTrainDict;
    private static JComboBox<String> comboBoxCompressLevel;
    private JLabel labelSource;
    private JPanel buttonPanePanel;
    private JPanel dictSourcePanePanel;
    private JPanel optionPanePanel;
    private JPanel mainDesignPanel;
    private static JTextField textFieldDictPath = new JTextField();

    private static boolean usingDict;

    public static boolean isUsingDict() {
        return usingDict;
    }

    public void setUsingDict(boolean usingDict) {
        SettingDialog.usingDict = usingDict;
    }

    public static String getTextFieldDictPath() {
        return SettingDialog.textFieldDictPath.getText();
    }

    public static void setTextFieldDictPath(String path) {
        SettingDialog.textFieldDictPath.setText(path);
    }

    public static int getComboBoxCompressLevel() {
        if (usingDict) {
            Matcher matcher = Pattern.compile("level\\s*(\\d{1,2})").matcher(String.valueOf(SettingDialog.comboBoxCompressLevel.getSelectedItem()).toLowerCase());
            if (matcher.find()) {
                int level = Integer.parseInt(matcher.group(1));
                if (level >= 1 && level <= 22) {
                    return level;
                }
            }
        }
        return 11;
    }

    public void setDisableUI() {
        SettingDialog.comboBoxCompressLevel.setEnabled(false);
        this.checkBoxTrainDict.setEnabled(false);
    }

    public void setLabelSource(String mode) {
        this.labelSource.setText(mode);
    }

    public SettingDialog() {
        setTitle("Settings");
        setSize(500,250);
        setResizable(false);
        setModal(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        add(designSettingDialogPanePanel());
        if (MainFrame.getCurrentMode().equals("Decompress")) {
            setDisableUI();
        }
        if (MainFrame.getCurrentMode().equals("Dictionary")) {
            setLabelSource("Output file : ");
            setSize(500, 200);
        }
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
        if (!MainFrame.getCurrentMode().equals("Dictionary")) {
            gbc.gridy++;
            mainDesignPanel.add(optionPanePanel(), gbc);
        }
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
        labelSource = new JLabel("Dictionary : ");
        dictSourcePanePanel.add(labelSource, gbc);

        gbc.gridy++;
        dictSourcePanePanel.add(new JLabel("Compression level : "), gbc);

        gbc.gridx++;
        gbc.gridy--;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        textFieldDictPath.setFont(new Font("Arial", Font.PLAIN, 15));
        textFieldDictPath.setPreferredSize(new Dimension(0, 25));
        dictSourcePanePanel.add(textFieldDictPath, gbc);

        gbc.gridx++;
        gbc.weightx = 0;
        buttonSelectDict = new JButton("...");
        buttonSelectDict.setPreferredSize(new Dimension(50, 25));
        buttonSelectDict.addActionListener(e -> new FileChooser(true, true));
        dictSourcePanePanel.add(buttonSelectDict, gbc);

        gbc.gridx--;
        gbc.gridy++;
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.fill = GridBagConstraints.NONE;
        comboBoxCompressLevel = new JComboBox<>();
        comboBoxCompressLevel.setPreferredSize(new Dimension(150, 25));
        new SettingDialog.CompressLevelLoader(comboBoxCompressLevel).execute();
        dictSourcePanePanel.add(comboBoxCompressLevel, gbc);
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
        checkBoxTrainDict = new JCheckBox();
        checkBoxTrainDict.setPreferredSize(new Dimension(20, 20));
        checkBoxTrainDict.setEnabled(true);
        oppPanel.add(checkBoxTrainDict, gbc);

        gbc.gridx++;
        gbc.weightx = 1;
        oppPanel.add(new JLabel("Create dictionary from file (Auto) (Compress Mode)"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 4, 4, 4);
        optionPanePanel.add(oppPanel, gbc);
        return optionPanePanel;
    }

    public JPanel buttonPanePanel() {
        buttonOK = new JButton("OK");
        buttonOK.setPreferredSize(new Dimension(130, 40));
        buttonOK.addActionListener(e -> {
            if (!MainFrame.getCurrentMode().equals("Dictionary")) {
                if (CheckInputFile.checkInputDict(getTextFieldDictPath(), false)) {
                    setUsingDict(true);
                    dispose();
                }
            }
            else {
                if (CheckInputFile.checkInputDict(getTextFieldDictPath(), true)) {
                    setUsingDict(true);
                    dispose();
                }
            }

        });

        buttonSave = new JButton("Save");
        buttonSave.setPreferredSize(new Dimension(130, 40));
        buttonSave.addActionListener(e -> {
            if (CheckInputFile.checkInputDict(getTextFieldDictPath(), false)) {
                dispose();
            }
        });

        buttonCancel = new JButton("Cancel");
        buttonCancel.setPreferredSize(new Dimension(130, 40));
        buttonCancel.addActionListener(e -> {
            setUsingDict(false);
            setTextFieldDictPath("");
            dispose();
        });

        buttonPanePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanePanel.add(buttonOK);
        buttonPanePanel.add(buttonSave);
        buttonPanePanel.add(buttonCancel);
        return buttonPanePanel;
    }

    public void loadSaveSettings() {

    }

    private static class CompressLevelLoader extends SwingWorker<Void, String> {

        private final JComboBox<String> comboBox;

        public CompressLevelLoader(JComboBox<String> comboBox) {
            this.comboBox = comboBox;
        }

        @Override
        protected Void doInBackground() {
            for (int i = 1; i <= 22; i++) {
                String label = switch (i) {
                    case 1 -> "Level " + i + " (Store)";
                    case 3 -> "Level " + i + " (Fastest)";
                    case 5 -> "Level " + i + " (Fast)";
                    case 11 -> "Level " + i + " (Normal)";
                    case 17 -> "Level " + i + " (Maximum)";
                    case 22 -> "Level " + i + " (Ultra)";
                    default -> "Level " + i;
                };
                publish(label);
            }
            return null;
        }

        @Override
        protected void process(List<String> chunks) {
            for (String level : chunks) {
                comboBox.addItem(level);
            }
        }
    }
}
