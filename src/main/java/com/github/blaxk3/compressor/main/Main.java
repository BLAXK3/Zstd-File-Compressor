package com.github.blaxk3.compressor.main;

import com.github.blaxk3.compressor.ui.frame.MainFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        setSystemLookAndFeel();

        SwingUtilities.invokeLater(MainFrame::new);
    }

    private static void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.err.println("Failed to set LookAndFeel: " + e.getMessage());
            logger.error("Failed to set LookAndFeels", e);
        }
    }
}
