/*
 *
 * Every ui swing controls of CompressPanel & DecompressPanel class
 *
 */

package com.github.blaxk3.compressor.ui.controls;

import com.github.blaxk3.compressor.ui.process.CompressPanelProcess;
import com.github.blaxk3.compressor.ui.windows.chooser.FileChooser;
import com.github.blaxk3.compressor.ui.windows.dialog.SelectOption;
import com.github.blaxk3.compressor.ui.windows.dialog.SettingDialog;

import java.awt.Dimension;
import java.awt.Font;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CompressPanelControls {

    private JButton[] button;
    private JButton buttonConfirm;
    private JLabel label;
    private static JTextField textFieldPath;

    public static Dimension setControlsSize(int width, int height) {
        return new Dimension(width, height);
    }

    public static String getTextFieldPath() {
        return textFieldPath.getText();
    }

    public JLabel labelMode() {
        this.label = new JLabel();
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setText("Compress Mode");
        return label;
    }

    public JTextField textFieldPath() {
        textFieldPath = new JTextField();
        textFieldPath.setFont(new Font("Arial", Font.PLAIN, 15));
        textFieldPath.setPreferredSize(setControlsSize(350, 35));
        return textFieldPath;
    }

    public JButton[] buttonSelect() {
        this.button = new JButton[] {
                new JButton("Select"),
                new JButton("Select File"),
                new JButton("Select Folder"),
                new JButton("Setting")
        };
        Arrays.stream(button).forEach(b -> b.setPreferredSize(setControlsSize(110, 35)));
        button[0].addActionListener(e -> new SelectOption());
        button[1].addActionListener(e -> new FileChooser("file", false));
        button[2].addActionListener(e -> new FileChooser("folder", false));
        button[3].addActionListener(e -> new SettingDialog());
        return button;
    }

    public JButton buttonConfirm() {
        this.buttonConfirm = new JButton("Compress");
        buttonConfirm.setPreferredSize(setControlsSize(130, 35));
        buttonConfirm.addActionListener(e -> CompressPanelProcess.checkInputFile(true));
        return buttonConfirm;
    }
}