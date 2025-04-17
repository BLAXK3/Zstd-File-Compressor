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


    int resultSelected;

    private int getResultSelected() {
        return this.resultSelected = fileChooser.showOpenDialog(null);
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
        scrollPane.setPreferredSize(new Dimension(400, 200));
        return scrollPane;
    }

    public JTextField textFieldPath(int width, int height) {
        textFieldPath = new JTextField();
        textFieldPath.setFont(new Font("Arial", Font.PLAIN, 15));
        textFieldPath.setPreferredSize(new Dimension(width, height));
        return textFieldPath;
    }

    public JButton[] buttonSelect() {
        button = new JButton[] {
                new JButton("Select"),
                new JButton("Select File"),
                new JButton("Select Folder"),
                new JButton("Setting")
        };
        Arrays.stream(button).forEach(b -> b.setPreferredSize(new Dimension(110, 35)));
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
        Arrays.stream(buttonCPDP).forEach(b -> b.setPreferredSize(new Dimension(130, 40)));
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
            if (getResultSelected() == JFileChooser.APPROVE_OPTION) {
                System.out.println(selectedFiles);
            }
        /*
        * Path of File
        */
        } else {
            fileChooser.setMultiSelectionEnabled(true);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            File[] selectedFiles = fileChooser.getSelectedFiles();
            if (getResultSelected() == JFileChooser.APPROVE_OPTION) {
                Arrays.stream(selectedFiles).forEach(file -> System.out.println(file.getName()));
            }
        }
    }

    public void folderChooser() {
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (getResultSelected() == JFileChooser.APPROVE_OPTION) {
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

class SettingsDialogControls {

    private JComboBox<String> comboBoxCompressLevel;
    private JCheckBox checkBoxTrainDict;
    private JCheckBox checkBoxDeleteFile;
    private JLabel labelDict;
    private JLabel labelCompressLevel;
    private JTextField textFieldDictPath;

    public JLabel labelDict() {
        return new JLabel("Dictionary : ");
    }

    public JCheckBox checkBoxTrainDict() {
        checkBoxTrainDict = new JCheckBox("Backup File");
        checkBoxTrainDict.setPreferredSize(new Dimension(100, 30));
        return checkBoxTrainDict;
    }

    public JCheckBox getCheckBoxDeleteFile() {
        checkBoxTrainDict = new JCheckBox("Delete files after compression");
        checkBoxTrainDict.setPreferredSize(new Dimension(100, 30));
        return checkBoxTrainDict;
    }

    public JTextField textFieldDictPath(int width, int height) {
        textFieldDictPath = new JTextField();
        textFieldDictPath.setFont(new Font("Arial", Font.PLAIN, 15));
        textFieldDictPath.setPreferredSize(new Dimension(width, height));
        return textFieldDictPath;
    }

    public JComboBox<String> getComboBoxCompressLevel() {
        comboBoxCompressLevel = new JComboBox<>();

        return  comboBoxCompressLevel;
    }
}
