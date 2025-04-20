package com.github.blaxk3.compressor.ui.controls;

import com.github.blaxk3.compressor.ui.frame.MainFrame;
import com.github.blaxk3.compressor.ui.windows.chooser.FileChooser;
import static com.github.blaxk3.compressor.ui.controls.CompressPanelControls.setControlsSize;

import java.awt.Font;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

public class SettingsDialogControls {

    private JButton buttonSelectDict;
    private JButton buttonOK;
    private JButton buttonSave;
    private JButton buttonCancel;
    private JCheckBox checkBoxTrainDict;
    private JCheckBox checkBoxDeleteFile;
    private JComboBox<String> comboBoxCompressLevel;
    private static JTextField textFieldDictPath;

    public static void setTextFieldDictPath(String path) {
        SettingsDialogControls.textFieldDictPath.setText(path);
    }

    public static String getTextFieldDictPath() {
        return textFieldDictPath.getText();
    }

    public JButton getButtonOK() {
        return buttonOK;
    }

    public JButton getButtonSave() {
        return buttonSave;
    }

    public JButton getButtonCancel() {
        return buttonCancel;
    }

    public JLabel labelDict() {
        return new JLabel("Dictionary : ");
    }

    public JLabel labelCompressLevel() {
        return new JLabel("Compression level : ");
    }

    public JLabel labelCheckBoxDeleteFile() {
        return new JLabel("Delete file after compression/decompression");
    }

    public JLabel labelCheckBoxTrainDict() {
        return new JLabel("Create dictionary from file (Auto) (Compress Mode)");
    }

    public JButton buttonOK() {
        this.buttonOK = new JButton("OK");
        buttonOK.setPreferredSize(setControlsSize(130, 40));
        return buttonOK;
    }

    public JButton buttonSave() {
        this.buttonSave = new JButton("Save");
        buttonSave.setPreferredSize(setControlsSize(130, 40));
        return buttonSave;
    }

    public JButton buttonCancel() {
        this.buttonCancel = new JButton("Cancel");
        buttonCancel.setPreferredSize(setControlsSize(130, 40));
        return buttonCancel;
    }

    public JButton buttonSelectDict() {
        this.buttonSelectDict = new JButton("...");
        buttonSelectDict.setPreferredSize(setControlsSize(50, 25));
        buttonSelectDict.addActionListener(e -> new FileChooser(true, true));
        return buttonSelectDict;
    }

    public JCheckBox checkBoxTrainDict() {
        this.checkBoxTrainDict = new JCheckBox();
        checkBoxTrainDict.setPreferredSize(setControlsSize(20, 20));
        if(MainFrame.getCurrentPanel().equals("Decompress")) {
            checkBoxTrainDict.setEnabled(false);
            return checkBoxTrainDict;
        }
        return checkBoxTrainDict;
    }

    public JCheckBox checkBoxDeleteFile() {
        this.checkBoxDeleteFile = new JCheckBox();
        checkBoxDeleteFile.setPreferredSize(setControlsSize(20, 20));
        return checkBoxDeleteFile;
    }

    public JTextField textFieldDictPath() {
        textFieldDictPath = new JTextField();
        textFieldDictPath.setFont(new Font("Arial", Font.PLAIN, 15));
        textFieldDictPath.setPreferredSize(setControlsSize(0, 25));
        textFieldDictPath.setEditable(false);
        return textFieldDictPath;
    }

    public JComboBox<String> getComboBoxCompressLevel() {
        this.comboBoxCompressLevel = new JComboBox<>();
        comboBoxCompressLevel.setPreferredSize(setControlsSize(150, 25));
        if (MainFrame.getCurrentPanel().equals("Decompress")) {
            comboBoxCompressLevel.setEnabled(false);
            return comboBoxCompressLevel;
        }
        new CompressLevelLoader(comboBoxCompressLevel).execute();
        return comboBoxCompressLevel;
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
