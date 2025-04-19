package com.github.blaxk3.compressor.ui.process;

import com.github.blaxk3.compressor.ui.controls.CompressPanelControls;
import com.github.blaxk3.compressor.ui.controls.DecompressPanelControls;
import com.github.blaxk3.compressor.ui.windows.optionpane.AlertMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class CompressPanelProcess {

    private static final Logger logger = LoggerFactory.getLogger(CompressPanelProcess.class);

    public static void checkInputFile(boolean compress) {

        if (compress) {
            if(CompressPanelControls.getTextFieldPath().isEmpty()) {
                logger.info("test empty compress");
                AlertMessage.fileNotFound(true, true);
            }
            else {
                File file = new File(CompressPanelControls.getTextFieldPath());
                if (file.exists() && file.isDirectory()) {
                    logger.info("test exists file compress");
                } else {

                    logger.info("test out of file compress");
                }
            }
        }
        else {
            if(DecompressPanelControls.getTextFieldPath().isEmpty()) {
                logger.info("test empty decompress");
                AlertMessage.fileNotFound(true, true);
            }
            else {
                File file = new File(DecompressPanelControls.getTextFieldPath());
                if (file.exists() && file.isDirectory()) {
                    logger.info("test exists file decompress");
                } else {

                    logger.info("test out of file decompress");
                }
            }
        }
    }

}
