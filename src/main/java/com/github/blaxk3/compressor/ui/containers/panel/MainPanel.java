package com.github.blaxk3.compressor.ui.containers.panel;

import com.github.blaxk3.compressor.api.zstd.mode.compress.ZstdProcess;
import com.github.blaxk3.compressor.ui.frame.MainFrame;
import com.github.blaxk3.compressor.ui.process.CheckInputFile;
import com.github.blaxk3.compressor.ui.windows.chooser.FileChooser;
import com.github.blaxk3.compressor.ui.windows.dialog.SettingDialog;
import org.apache.commons.io.FilenameUtils;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainPanel extends JPanel {

    private JButton buttonSelect;
    private JButton buttonSelectFile;
    private JButton buttonSelectFolder;
    private JButton buttonSettings;
    private static JButton buttonMode;
    private static JDialog dialogSelectOption;
    private static JLabel labelMode;
    private JPanel scrollPanePanel;
    private JScrollPane scrollPaneFile;
    private static JTextArea textAreaFileName;
    private static JTextField textFieldPath;

    public static void setButtonMode(String mode) {
        buttonMode.setText(mode);
    }

    public static void setLabelMode(String mode) {
        labelMode.setText(mode);
    }

    public String getTextFieldPath() {
        return textFieldPath.getText();
    }

    public static void setTextFieldPath(String path) {
        textFieldPath.setText(path);
    }

    public static void closeDialogSelectOption() {
        if (dialogSelectOption != null) {
            dialogSelectOption.dispose();
        }
    }

    public MainPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setPreferredSize(new Dimension(500, 500));

        labelMode = new JLabel("Compress Mode");
        labelMode.setFont(new Font("Arial", Font.BOLD, 30));

        scrollPanePanel = new JPanel();
        scrollPanePanel.setLayout(new BoxLayout(scrollPanePanel, BoxLayout.Y_AXIS));

        scrollPaneFile = new JScrollPane(scrollPanePanel);
        scrollPaneFile.setPreferredSize(new Dimension(400, 200));

        textAreaFileName = new JTextArea();
        textAreaFileName.setFont(new Font("Arial", Font.PLAIN, 15));
        textAreaFileName.setEditable(false);
        scrollPanePanel.add(textAreaFileName);

        textFieldPath = new JTextField();
        textFieldPath.setFont(new Font("Arial", Font.PLAIN, 15));
        textFieldPath.setPreferredSize(new Dimension(350, 35));

        buttonSelect = new JButton("Select");
        buttonSelect.setPreferredSize(new Dimension(110, 35));
        buttonSelect.addActionListener(e -> selectOption());

        buttonMode = new JButton("Compress");
        buttonMode.setPreferredSize(new Dimension(130, 40));
        buttonMode.addActionListener(e -> {
            System.out.println(SettingDialog.getTextFieldDictPath());

            if (CheckInputFile.checkInputFile(getTextFieldPath())) {
                displayFile(new File(getTextFieldPath()));
                new ZstdProcess(new File(getTextFieldPath()), SettingDialog.getComboBoxCompressLevel(), new File(SettingDialog.getTextFieldDictPath()), MainFrame.getCurrentMode());
            }
        });

        buttonSettings = new JButton("Settings");
        buttonSettings.setPreferredSize(new Dimension(130, 40));
        buttonSettings.addActionListener(e -> new SettingDialog().setVisible(true));

        add(labelMode);
        add(scrollPaneFile);
        add(textFieldPath);
        add(buttonSelect);
        add(buttonMode);
        add(buttonSettings);
    }

    public void selectOption() {
        dialogSelectOption = new JDialog();
        dialogSelectOption.setLayout(new FlowLayout(FlowLayout.CENTER));
        dialogSelectOption.setSize(200, 150);
        dialogSelectOption.setModal(true);
        dialogSelectOption.setResizable(false);
        dialogSelectOption.setLocationRelativeTo(this);

        buttonSelectFile = new JButton("File");
        buttonSelectFile.setPreferredSize(new Dimension(110, 40));
        buttonSelectFile.addActionListener(e -> new FileChooser(true, false));

        buttonSelectFolder = new JButton("Folder");
        buttonSelectFolder.setPreferredSize(new Dimension(110, 40));
        buttonSelectFolder.addActionListener(e -> new FileChooser(false, false));

        dialogSelectOption.add(buttonSelectFile);
        dialogSelectOption.add(buttonSelectFolder);
        dialogSelectOption.setVisible(true);
    }

    public static void displayFile(File path) {
        textAreaFileName.setText("");
        if (FilenameUtils.getExtension(String.valueOf(path)).isEmpty()) {
            File[] files = path.listFiles(File::isFile);

            assert files != null;
            for (File filesName : files) {
                textAreaFileName.append(filesName.getName() + "\n");
            }
            return;
        }
        textAreaFileName.setText(path.getName());
    }
}
