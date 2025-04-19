package com.github.blaxk3.compressor.ui.windows.optionpane;

public class AlertMessage extends javax.swing.JOptionPane {

    public static void fileNotFound(boolean file, boolean empty) {
        if (empty) {
            showMessageDialog(null, "Please select or enter your file/folder path", "Error", ERROR_MESSAGE);
        }
        else {
            if (file) {
                showMessageDialog(null, "File not found", "Error", ERROR_MESSAGE);
            }
            else {
                showMessageDialog(null, "Folder not found", "Error", ERROR_MESSAGE);
            }
        }
    }

}
