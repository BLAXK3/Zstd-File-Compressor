package com.github.blaxk3.compressor.ui.frame;

import com.github.blaxk3.compressor.ui.containers.panel.MainPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Desktop;
import java.io.InputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainFrame extends javax.swing.JFrame {

    private static final Logger logger = LoggerFactory.getLogger(MainFrame.class);

    private JMenu[] menu;
    private JMenuBar menuBar;
    private JMenuItem[] menuItemMode;
    private JMenuItem menuItemHelp;
    private JMenuItem menuItemTools;

    public static String currentMode;
    public static boolean outputStatus;

    public static String getCurrentMode() {
        return MainFrame.currentMode;
    }

    public void setCurrentMode(String mode) {
        MainFrame.currentMode = mode;
    }

    public static boolean getOutputStatus() {
        return MainFrame.outputStatus;
    }

    public void setOutputStatus(boolean status) {
        MainFrame.outputStatus = status;
    }

    public MainFrame() {
        setIconImage(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/icon/image/icon.png"))).getImage());
        setTitle("Zstd File Compressor");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setCurrentMode("Compress");
        setOutputStatus(false);

        menuBar = new JMenuBar();

        menu = new JMenu[] {
                new JMenu("Mode"),
                new JMenu("Tools"),
                new JMenu("Help")
        };
        Arrays.stream(menu).forEach(menuBar::add);

        menuItemMode = new JMenuItem[] {
                new JMenuItem("Compress"),
                new JMenuItem("Decompress")
        };
        Arrays.stream(menuItemMode).forEach(menu[0]::add);

        menuItemTools = new JMenuItem("Dictionary Trainer");
        menu[1].add(menuItemTools);

        menuItemHelp = new JMenuItem("Github");
        menu[2].add(menuItemHelp);

        menuItemHelp.addActionListener(e -> help());

        setJMenuBar(menuBar);

        add(new MainPanel());

        menuItemMode[0].addActionListener(e -> {
            MainPanel.setLabelMode("Compress Mode");
            MainPanel.setButtonMode("Compress");
            setCurrentMode("Compress");
            setOutputStatus(false);
        });
        menuItemMode[1].addActionListener(e -> {
            MainPanel.setLabelMode("Decompress Mode");
            MainPanel.setButtonMode("Decompress");
            setCurrentMode("Decompress");
            setOutputStatus(false);
        });

        menuItemTools.addActionListener(e -> {
            MainPanel.setLabelMode("Dictionary Trainer");
            MainPanel.setButtonMode("Create");
            setCurrentMode("Dictionary");
            setOutputStatus(true);
        });

        setVisible(true);
    }

    private void help() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config/github.properties")) {
            if (input == null) {
                logger.error("Unable to find github.properties");
            }
            Properties properties = new Properties();
            properties.load(input);
            String url = properties.getProperty("URL");
            if (url != null && !url.isEmpty()) {
                Desktop.getDesktop().browse(new URI(url));
            }
        } catch (Exception e) {
            logger.error("Failed to open URL", e);
        }
    }
}
