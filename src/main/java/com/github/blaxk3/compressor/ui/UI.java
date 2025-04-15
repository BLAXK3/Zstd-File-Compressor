package com.github.blaxk3.compressor.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Objects;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class UI extends javax.swing.JFrame {

    private static final Logger logger = LoggerFactory.getLogger(UI.class);

    private CardLayout cardLayout;
    private JButton[] button;
    private JFileChooser fileChooser;
    private JLabel label;
    private JMenu[] menu;
    private JMenuBar menuBar;
    private JPanel compressPanel;
    private JPanel decompressPanel;
    private JPanel framePanel;
    private JPanel scrollPanePanel;
    private JScrollPane scrollPane;
    private JTextField textFieldPath;


    public UI() {
        setIconImage(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/icon/image/icon.png"))).getImage());
        setTitle("File Compressor");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
//        getContentPane().setLayout(null);

        menuBar = new JMenuBar();


        menu = new JMenu[] {
                new JMenu("Compress"),
                new JMenu("Decompress"),
                new JMenu("Setting"),
                new JMenu("Help")
        };
        Arrays.stream(menu).forEach(menuBar::add);

        cardLayout = new CardLayout();
        framePanel = new JPanel(cardLayout);
        framePanel.add(compressPanel());
        framePanel.add(decompressPanel());

        JButton switchButton = new JButton("Switch framePanel");
        switchButton.addActionListener((ActionEvent e) -> {
            cardLayout.next(framePanel);
        });

        add(framePanel, BorderLayout.CENTER);
        add(switchButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    public Component compressPanel() {
        compressPanel = new JPanel();
        compressPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        compressPanel.setSize(500,500);

        label = new JLabel();
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setText("Compress Mode");

        compressPanel.add(label);

        scrollPanePanel = new JPanel();
        scrollPanePanel.setLayout(new BoxLayout(scrollPanePanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(scrollPanePanel);
        scrollPane.setPreferredSize(new Dimension(400, 200));

        compressPanel.add(scrollPane);

        textFieldPath = new JTextField();
        textFieldPath.setPreferredSize(new Dimension(300, 35));

        compressPanel.add(textFieldPath);

        button = new JButton[] {
                new JButton("Select"),
                new JButton("Select File"),
                new JButton("Select Folder")
        };

        Arrays.stream(button).forEach(b -> b.setPreferredSize(new Dimension(120, 35)));

        compressPanel.add(button[0]);

        fileChooser = new JFileChooser();

        setJMenuBar(menuBar);

        return compressPanel;
    }


    public Component decompressPanel() {
        decompressPanel = new JPanel();
        decompressPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        decompressPanel.setSize(500,500);

        label = new JLabel();
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setText("Decompress Mode");

        decompressPanel.add(label);

        scrollPanePanel = new JPanel();
        scrollPanePanel.setLayout(new BoxLayout(scrollPanePanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(scrollPanePanel);
        scrollPane.setPreferredSize(new Dimension(400, 200));

        decompressPanel.add(scrollPane);

        textFieldPath = new JTextField();
        textFieldPath.setPreferredSize(new Dimension(300, 35));

        decompressPanel.add(textFieldPath);

        button = new JButton[] {
                new JButton("Select"),
                new JButton("Select File"),
                new JButton("Select Folder")
        };

        Arrays.stream(button).forEach(b -> b.setPreferredSize(new Dimension(120, 35)));

        decompressPanel.add(button[0]);

        fileChooser = new JFileChooser();

        setJMenuBar(menuBar);

        return decompressPanel;
    }

//    public Component modeLabel()

}
