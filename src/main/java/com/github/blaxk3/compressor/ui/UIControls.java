package com.github.blaxk3.compressor.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.io.File;
import java.util.Arrays;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/*
 *
 * Every ui swing controls of CompressPanel & DecompressPanel class
 *
 */

public class UIControls {

    private JButton[] button;
    private JButton[] buttonCPDP;
    private JDialog dialogSelectOption;
    private JFileChooser fileChooser = new JFileChooser();;
    private JLabel label;
    private JPanel scrollPanePanel;
    private JScrollPane scrollPane;
    private JTextField textFieldPath;

    private int resultSelected() {
        return fileChooser.showOpenDialog(null);
    }

    public static Dimension setControlsSize(int width, int height) {
        return new Dimension(width, height);
    }

    public JLabel labelMode(String mode) {
        label = new JLabel();
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setText(mode + " Mode");
        return label;
    }

    public JScrollPane scrollPaneAllFile() {
        scrollPanePanel = new JPanel();
        scrollPanePanel.setLayout(new BoxLayout(scrollPanePanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(scrollPanePanel);
        scrollPane.setPreferredSize(setControlsSize(400, 200));
        return scrollPane;
    }

    public JTextField textFieldPath() {
        textFieldPath = new JTextField();
        textFieldPath.setFont(new Font("Arial", Font.PLAIN, 15));
        textFieldPath.setPreferredSize(setControlsSize(350, 35));
        return textFieldPath;
    }

    public JButton[] buttonSelect() {
        button = new JButton[] {
                new JButton("Select"),
                new JButton("Select File"),
                new JButton("Select Folder"),
                new JButton("Setting")
        };
        Arrays.stream(button).forEach(b -> b.setPreferredSize(setControlsSize(110, 35)));
        /*
        *
        * Test ui interaction
        * */
        button[0].addActionListener(e -> dialogSelectOption());
        button[1].addActionListener(e -> fileChooser(false));
        button[2].addActionListener(e -> folderChooser());
        button[3].addActionListener(e -> new SettingDialog());
        return button;
    }

    public JButton[] buttonCPDP() {
        buttonCPDP = new JButton[] {
                new JButton("Compress File"),
                new JButton("Decompress File")
        };
        Arrays.stream(buttonCPDP).forEach(b -> b.setPreferredSize(setControlsSize(130, 35)));
        return buttonCPDP;
    }

    public void dialogSelectOption() {
        dialogSelectOption = new JDialog();
        dialogSelectOption.setSize(200, 150);
        dialogSelectOption.setModal(true);
        dialogSelectOption.setLayout(new FlowLayout());
        dialogSelectOption.setResizable(false);
        dialogSelectOption.setLocationRelativeTo(null);
        dialogSelectOption.add(buttonSelect()[1]);
        dialogSelectOption.add(buttonSelect()[2]);
        dialogSelectOption.setVisible(true);
    }

    public void fileChooser(boolean dict) {
        /*
         * Path of Dictionary
         */
        if (dict) {
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            File selectedFiles = fileChooser.getSelectedFile();
            if (resultSelected() == JFileChooser.APPROVE_OPTION) {
                System.out.println(selectedFiles);
            }
        /*
        * Path of File
        */
        } else {
            fileChooser.setMultiSelectionEnabled(true);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            File[] selectedFiles = fileChooser.getSelectedFiles();
            if (resultSelected() == JFileChooser.APPROVE_OPTION) {
                Arrays.stream(selectedFiles).forEach(file -> System.out.println(file.getName()));
            }
        }
    }

    public void folderChooser() {
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (resultSelected() == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = fileChooser.getSelectedFile();
            System.out.println("Folder select" + selectedFolder.getAbsolutePath());

            File[] files = selectedFolder.listFiles();

            if (files != null) {
                Arrays.stream(files)
                        .filter(File::isFile)
                        .forEach(file -> System.out.println(file.getName()));
            } else {
                System.out.println("File Not found in folder");
            }
        } else {
            System.out.println("Folder not select");
        }
    }
}

/*
 *
 * Every ui swing controls of SettingDialog class
 *
 */

class SettingsDialogControls extends JPanel {

    private JButton buttonSelectDict;
    private JButton buttonOK;
    private JButton buttonSave;
    private JButton buttonCancel;
    private JCheckBox checkBoxTrainDict;
    private JCheckBox checkBoxDeleteFile;
    private JComboBox<String> comboBoxCompressLevel;
    private JTextField textFieldDictPath;


    public JLabel labelDict() {
        return new JLabel("Dictionary : ");
    }

    public JLabel labelCompressLevel() {
        return new JLabel("Compress Level : ");
    }

    public JLabel labelCheckBoxDeleteFile() {
        return new JLabel("Delete file after compression");
    }

    public JLabel labelCheckBoxTrainDict() {
        return new JLabel("Create dictionary from file (Auto) (Compress Mode)");
    }

    public JButton buttonOK() {
        buttonOK = new JButton("OK");
        buttonOK.setPreferredSize(UIControls.setControlsSize(130, 40));
        return buttonOK;
    }

    public JButton buttonSave() {
        buttonSave = new JButton("Save");
        buttonSave.setPreferredSize(UIControls.setControlsSize(130, 40));
        return buttonSave;
    }

    public JButton buttonCancel() {
        buttonCancel = new JButton("Cancel");
        buttonCancel.setPreferredSize(UIControls.setControlsSize(130, 40));
        return buttonCancel;
    }

    public JCheckBox checkBoxTrainDict() {
        checkBoxTrainDict = new JCheckBox();
        checkBoxTrainDict.setPreferredSize(UIControls.setControlsSize(20, 20));
        return checkBoxTrainDict;
    }

    public JCheckBox checkBoxDeleteFile() {
        checkBoxDeleteFile = new JCheckBox();
        checkBoxDeleteFile.setPreferredSize(UIControls.setControlsSize(20, 20));
        return checkBoxDeleteFile;
    }

    public JTextField textFieldDictPath() {
        textFieldDictPath = new JTextField();
        textFieldDictPath.setFont(new Font("Arial", Font.PLAIN, 15));
        textFieldDictPath.setPreferredSize(UIControls.setControlsSize(0, 25));
        return textFieldDictPath;
    }

    public JButton buttonSelectDict() {
        buttonSelectDict = new JButton("...");
        buttonSelectDict.setPreferredSize(UIControls.setControlsSize(50, 25));
        return buttonSelectDict;
    }

    public JComboBox<String> getComboBoxCompressLevel() {
        comboBoxCompressLevel = new JComboBox<>();
        comboBoxCompressLevel.setPreferredSize(UIControls.setControlsSize(120, 25));
        return comboBoxCompressLevel;
    }
}
