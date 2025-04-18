package com.github.blaxk3.compressor.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.Arrays;
import java.util.Objects;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainFrame extends javax.swing.JFrame {

    private JMenu[] menu;
    private JMenuBar menuBar;
    private JMenuItem[] menuItemMode;
    private JMenuItem menuItemHelp;
    private JMenuItem menuItemTools;
    private JPanel framePanel;

    public MainFrame() {
        setIconImage(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/icon/image/icon.png"))).getImage());
        setTitle("Zstd File Compressor");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

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

        menuItemTools = new JMenuItem("Setting");
        menu[1].add(menuItemTools);

        menuItemHelp = new JMenuItem("Github");
        menu[2].add(menuItemHelp);

        CardLayout cardLayout = new CardLayout();

        menuItemMode[0].addActionListener(e -> cardLayout.show(framePanel, "Compress Panel"));
        menuItemMode[1].addActionListener(e -> cardLayout.show(framePanel, "Decompress Panel"));
        setJMenuBar(menuBar);

        framePanel = new JPanel(cardLayout);
        framePanel.add(new CompressPanel("Compress"), "Compress Panel");
        framePanel.add(new DecompressPanel(), "Decompress Panel");

        add(framePanel, BorderLayout.CENTER);
        setVisible(true);
    }
}
