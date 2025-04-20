package com.github.blaxk3.compressor.ui.process;

import com.github.blaxk3.compressor.ui.controls.CompressPanelControls;
import com.github.blaxk3.compressor.ui.controls.SettingsDialogControls;
import com.github.blaxk3.compressor.ui.windows.optionpane.AlertMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

public class CompressPanelProcess {

    private static final Logger logger = LoggerFactory.getLogger(CompressPanelProcess.class);

    static File[] filesInFolder;

    public static void checkInputFile(String path) {
        if (path == null || path.isEmpty()) {
            AlertMessage.fileNotFound(true, true);
            return;
        }

        File pathInput = new File(path);
        if (!pathInput.exists()) {
            AlertMessage.fileNotFound(false, false);
            return;
        }

        if (!FilenameUtils.getExtension(path).isEmpty()) {
            CompressPanelControls.setTextFieldPath(pathInput.getAbsolutePath());
        }

        else {
            filesInFolder = pathInput.listFiles(File::isFile);
            if (filesInFolder != null && filesInFolder.length > 0) {
                CompressPanelControls.setTextFieldPath(filesInFolder[0].getParent());

            } else {
                AlertMessage.fileNotFound(true, false);
            }
        }
    }

    public static void checkInputDict(String path) {
        if (path == null || path.isEmpty()) {
            return;
        }
        File pathInput = new File(path);

        if (!pathInput.exists()) {
            AlertMessage.fileNotFound(true, false);
        }
        else {
            SettingsDialogControls.setTextFieldDictPath(pathInput.getAbsolutePath());
        }
    }
}
