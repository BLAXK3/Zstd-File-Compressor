package com.github.blaxk3.compressor.ui.windows.chooser;

import com.github.blaxk3.compressor.ui.frame.MainFrame;
import com.github.blaxk3.compressor.ui.process.CheckInputFile;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class FileChooser extends javax.swing.JFileChooser {

    public FileChooser(boolean isFile, boolean isDict) {
        applyExtensionFilter();
        setAcceptAllFileFilterUsed(false);

        if (isFile) {
            selectFile(isDict);
        } else {
            selectFolder();
        }
    }

    private void applyExtensionFilter() {
        setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory() || (file.getName().contains(".") && !file.getName().endsWith("."));
            }

            @Override
            public String getDescription() {
                return "All Files (*.*)";
            }
        });
    }

    private void selectFile(boolean isDict) {
        setFileSelectionMode(FILES_ONLY);
        if (showOpenDialog(null) == APPROVE_OPTION) {
            File selectedFile = getSelectedFile();

            if (isDict) {
                CheckInputFile.checkInputDict(selectedFile.getAbsolutePath(), MainFrame.getOutputStatus());
            } else {
                CheckInputFile.checkInputFile(selectedFile.getAbsolutePath());
            }
        }
    }

    private void selectFolder() {
        setFileSelectionMode(DIRECTORIES_ONLY);
        if (showOpenDialog(null) == APPROVE_OPTION) {
            File selectedFolder = getSelectedFile();
            CheckInputFile.checkInputFile(selectedFolder.getAbsolutePath());
        }
    }
}