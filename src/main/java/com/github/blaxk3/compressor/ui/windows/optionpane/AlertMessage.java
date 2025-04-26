package com.github.blaxk3.compressor.ui.windows.optionpane;

public class AlertMessage extends javax.swing.JOptionPane {

    public static void fileNotFound(boolean isFile, boolean isEmpty) {
        if (isEmpty) {
            showMessageDialog(null, "Please select or enter your file/folder path", "Error", ERROR_MESSAGE);
        }
        else {
            if (isFile) {
                showMessageDialog(null, "File not found", "Error", ERROR_MESSAGE);
            }
            else {
                showMessageDialog(null, "Folder not found", "Error", ERROR_MESSAGE);
            }
        }
    }

    public static void complete(String mode) {
        showMessageDialog(null, "File " + mode + " successfully", "Complete", INFORMATION_MESSAGE);
    }

    public static void failed(String mode, String file) {
        showMessageDialog(null, "Unable to" + mode + " " + file, "Failed", ERROR_MESSAGE);
    }

    public static void failedTraining() {
        showMessageDialog(null, "Failed to train dictionary", "Failed", ERROR_MESSAGE);
    }
}
