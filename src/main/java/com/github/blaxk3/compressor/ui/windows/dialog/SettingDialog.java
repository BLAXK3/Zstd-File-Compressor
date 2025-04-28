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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class SettingDialog extends javax.swing.JDialog {

    private JButton buttonSelectDict;
    private JButton buttonOK;
    private JButton buttonCancel;
    private static JComboBox<String> comboBoxCompressLevel;
    private JLabel labelSource;
    private JPanel buttonPanePanel;
    private JPanel dictSourcePanePanel;
    private JPanel mainDesignPanel;
    private static JTextField textFieldDictPath = new JTextField();
    private static JTextField textFieldDictSize = new JTextField();

    private static boolean usingDict;

    public void setUsingDict(boolean usingDict) {
        SettingDialog.usingDict = usingDict;
    }

    public static String getTextFieldDictPath() {
        return SettingDialog.textFieldDictPath.getText();
    }

    public static int getTextFieldDictSize() {
        return Integer.parseInt(SettingDialog.textFieldDictSize.getText());
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

    public void setLabelSource(String mode) {
        this.labelSource.setText(mode);
    }

    public SettingDialog() {
        setTitle("Settings");
        setSize(500,200);
        setResizable(false);
        setModal(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        add(designSettingDialogPanePanel());
        if (MainFrame.getCurrentMode().equals("Dictionary")) {
            setLabelSource("Output file (Optional) : ");
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
        labelSource = new JLabel("Dictionary (Optional) : ");
        dictSourcePanePanel.add(labelSource, gbc);

        if (!MainFrame.getCurrentMode().equals("Decompress")) {
            gbc.gridy++;
            dictSourcePanePanel.add(new JLabel("Compression level : "), gbc);
        }

        if (MainFrame.getCurrentMode().equals("Dictionary")) {
            gbc.gridy++;
            dictSourcePanePanel.add(new JLabel("Dictionary Size (KB) : "), gbc);
        }

        gbc.gridx++;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        textFieldDictPath.setFont(new Font("Arial", Font.PLAIN, 12));
        textFieldDictPath.setPreferredSize(new Dimension(0, 25));
        dictSourcePanePanel.add(textFieldDictPath, gbc);

        gbc.gridx++;
        gbc.weightx = 0;
        buttonSelectDict = new JButton("...");
        buttonSelectDict.setPreferredSize(new Dimension(50, 25));
        if (MainFrame.getCurrentMode().equals("Dictionary")) {
            buttonSelectDict.addActionListener(e -> new FileChooser(true, false));
        }
        else {
            buttonSelectDict.addActionListener(e -> new FileChooser(true, true));
        }
        dictSourcePanePanel.add(buttonSelectDict, gbc);

        if (!MainFrame.getCurrentMode().equals("Decompress")) {
            gbc.gridx--;
            gbc.gridy++;
            gbc.insets = new Insets(5, 0, 5, 0);
            gbc.fill = GridBagConstraints.NONE;
            comboBoxCompressLevel = new JComboBox<>();
            comboBoxCompressLevel.setPreferredSize(new Dimension(150, 25));
            new SettingDialog.CompressLevelLoader(comboBoxCompressLevel).execute();
            dictSourcePanePanel.add(comboBoxCompressLevel, gbc);
        }

        if (MainFrame.getCurrentMode().equals("Dictionary")) {
            gbc.gridy++;
            textFieldDictSize.setFont(new Font("Arial", Font.PLAIN, 12));
            textFieldDictSize.setPreferredSize(new Dimension(100, 25));
            ((AbstractDocument) textFieldDictSize.getDocument()).setDocumentFilter(new NumericFilter());
            dictSourcePanePanel.add(textFieldDictSize, gbc);
        }
        return dictSourcePanePanel;
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

        buttonCancel = new JButton("Cancel");
        buttonCancel.setPreferredSize(new Dimension(130, 40));
        buttonCancel.addActionListener(e -> {
            setUsingDict(false);
            setTextFieldDictPath("");
            dispose();
        });

        buttonPanePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanePanel.add(buttonOK);
        buttonPanePanel.add(buttonCancel);
        return buttonPanePanel;
    }


    private class CompressLevelLoader extends SwingWorker<Void, String> {
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

    private class NumericFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string != null && string.matches("\\d+")) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text != null && text.matches("\\d+")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
        }
    }
}
