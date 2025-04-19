package com.github.blaxk3.compressor.ui.windows.chooser;

import com.github.blaxk3.compressor.ui.windows.optionpane.AlertMessage;

import java.io.File;
import java.util.Arrays;

public class FileChooser extends javax.swing.JFileChooser {

    public FileChooser(String opt, boolean dict) {
        if (opt.equals("file")) {
            /*
            * Path of Dictionary
            * */
            if (dict) {
                setFileSelectionMode(FILES_ONLY);
                File selectedFiles = getSelectedFile();
                if (showOpenDialog(null) == APPROVE_OPTION) {
                    System.out.println(selectedFiles);
                }
            }
            /*
            * Path of file
            * */
            else {
                setMultiSelectionEnabled(true);
                setFileSelectionMode(FILES_ONLY);
                if (showOpenDialog(null) == APPROVE_OPTION) {
                    File[] selectedFiles = getSelectedFiles();
                    Arrays.stream(selectedFiles).forEach(file -> System.out.println(file.getName()));
                }
            }
        }
        /*
         * Path of Folder
         * */
        else {
            setFileSelectionMode(DIRECTORIES_ONLY);
            if (showOpenDialog(null) == APPROVE_OPTION) {
                File selectedFolder = getSelectedFile();
                System.out.println("Folder select" + selectedFolder.getAbsolutePath());

                File[] files = selectedFolder.listFiles();

                if (files != null) {
                    Arrays.stream(files)
                            .filter(File::isFile)
                            .forEach(file -> System.out.println(file.getName()));
                } else {
                    AlertMessage.fileNotFound(true, false);
                }
            }
        }
    }
}
