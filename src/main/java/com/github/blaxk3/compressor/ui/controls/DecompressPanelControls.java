package com.github.blaxk3.compressor.ui.controls;

import com.github.blaxk3.compressor.ui.process.CompressPanelProcess;
import com.github.blaxk3.compressor.ui.windows.chooser.FileChooser;
import com.github.blaxk3.compressor.ui.windows.dialog.SelectOption;
import com.github.blaxk3.compressor.ui.windows.dialog.SettingDialog;

import java.awt.Font;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class DecompressPanelControls extends CompressPanelControls{

    private JButton[] button;
    private JButton buttonConfirm;
    private JLabel label;
    private static JTextField textFieldPath;

    public static String getTextFieldPath() {
        return DecompressPanelControls.textFieldPath.getText();
    }

    public static void setTextFieldPath(String path) {
        DecompressPanelControls.textFieldPath.setText(path);
    }

    @Override
    public JLabel labelMode() {
        this.label = new JLabel();
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setText("Decompress");
        return label;
    }

    @Override
    public JTextField textFieldPath() {
        textFieldPath = new JTextField();
        textFieldPath.setFont(new Font("Arial", Font.PLAIN, 15));
        textFieldPath.setPreferredSize(setControlsSize(350, 35));
        return textFieldPath;
    }


    @Override
    public JButton[] buttonSelect() {
        this.button = new JButton[] {
                new JButton("Select"),
                new JButton("Select File"),
                new JButton("Select Folder"),
                new JButton("Setting")
        };
        Arrays.stream(button).forEach(b -> b.setPreferredSize(setControlsSize(110, 35)));
        button[0].addActionListener(e -> new SelectOption());
        button[1].addActionListener(e -> new FileChooser(true, false));
        button[2].addActionListener(e -> new FileChooser(false, false));
        button[3].addActionListener(e -> new SettingDialog());
        return button;
    }

    @Override
    public JButton buttonConfirm() {
        this.buttonConfirm = new JButton("Decompress");
        buttonConfirm.setPreferredSize(setControlsSize(130, 35));
        buttonConfirm.addActionListener(e -> CompressPanelProcess.checkInputFile(getTextFieldPath()));
        return buttonConfirm;
    }
}
