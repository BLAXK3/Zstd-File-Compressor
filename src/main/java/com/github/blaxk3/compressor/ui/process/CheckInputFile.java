package com.github.blaxk3.compressor.ui.process;

import com.github.blaxk3.compressor.ui.containers.panel.MainPanel;
import com.github.blaxk3.compressor.ui.windows.dialog.SettingDialog;
import com.github.blaxk3.compressor.ui.windows.optionpane.AlertMessage;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

public class CheckInputFile {

    public static boolean checkInputFile(String path) {
        if (path == null || path.isEmpty()) {
            AlertMessage.fileNotFound(false, true);
            return false;
        }

        File pathInput = new File(path);

        if (!FilenameUtils.getExtension(path).isEmpty()) {
            if (pathInput.exists()) {
                MainPanel.setTextFieldPath(pathInput.getAbsolutePath());
                MainPanel.closeDialogSelectOption();
                MainPanel.displayFile(pathInput);
                return true;
            }
            AlertMessage.fileNotFound(true, false);
            return false;
        }
        else {
            if (pathInput.exists()) {
                File[] filesInFolder = pathInput.listFiles(File::isFile);
                if (filesInFolder != null && filesInFolder.length > 0) {
                    MainPanel.setTextFieldPath(filesInFolder[0].getParent());
                    MainPanel.closeDialogSelectOption();
                    MainPanel.displayFile(pathInput);
                    return true;
                } else {
                    AlertMessage.fileNotFound(true, false);
                    return false;
                }
            }
            AlertMessage.fileNotFound(false, false);
            return false;
        }
    }

    public static boolean checkInputDict(String path, boolean output) {
        if (path == null || path.isEmpty()) {
            return true;
        }
        File pathInput = new File(path);

        if (output && !pathInput.exists()) {
            AlertMessage.fileNotFound(false, false);
            return false;
        }
        else {
            if (!output && !pathInput.exists()) {
                AlertMessage.fileNotFound(true, false);
                return false;
            }
            else {
                SettingDialog.setTextFieldDictPath(pathInput.getAbsolutePath());
                return true;
            }
        }
    }
}
