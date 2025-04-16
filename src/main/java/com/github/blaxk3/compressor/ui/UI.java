package com.github.blaxk3.compressor.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;


public class UI extends javax.swing.JFrame {

    private static final Logger logger = LoggerFactory.getLogger(UI.class);

    private CardLayout cardLayout = new CardLayout();
    private JButton[] button;
    private JButton[] buttonCPDP;
    private JDialog dialogSelectOption;
    private JFileChooser fileChooser;
    private JLabel label;
    private JMenu[] menu;
    private JMenuBar menuBar;
    private JMenuItem[] menuItemMode;
    private JMenuItem[] menuItemTools;
    private JPanel compressPanel;
    private JPanel decompressPanel;
    private JPanel framePanel;
    private JPanel scrollPanePanel;
    private JScrollPane scrollPane;
    private JSpinner spinnerCompressLevel;
    private JTextField textFieldPath;
    private JTextField textFieldDictPath;


    public UI() {
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
        menuItemMode[0].addActionListener(e -> cardLayout.show(framePanel, "Compress Panel"));
        menuItemMode[1].addActionListener(e -> cardLayout.show(framePanel, "Decompress Panel"));

        framePanel = new JPanel(cardLayout);
        framePanel.add(compressPanel(), "Compress Panel");
        framePanel.add(decompressPanel(), "Decompress Panel");

        add(framePanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public JLabel labelMode(String mode) {
        label = new JLabel();
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setText(mode + " Mode");
        return label;
    }

    public JScrollPane scrollPaneAllFile() {
        scrollPanePanel = new JPanel();
        scrollPanePanel.setLayout(new BoxLayout(scrollPanePanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(scrollPanePanel);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        return scrollPane;
    }

    public JTextField textFieldPath(int width, int height) {
        textFieldPath = new JTextField();
        textFieldPath.setFont(new Font("Arial", Font.PLAIN, 15));
        textFieldPath.setPreferredSize(new Dimension(width, height));
        return textFieldPath;
    }

    public JTextField textFieldDictPath(int width, int height) {
        textFieldDictPath = new JTextField();
        textFieldDictPath.setFont(new Font("Arial", Font.PLAIN, 15));
        textFieldDictPath.setPreferredSize(new Dimension(width, height));
        return textFieldDictPath;
    }

    public JButton[] buttonSelect() {
        button = new JButton[] {
                new JButton("Select"),
                new JButton("Select File"),
                new JButton("Select Folder")
        };
        Arrays.stream(button).forEach(b -> b.setPreferredSize(new Dimension(110, 35)));
        button[0].addActionListener(e -> dialogSelectOption());
        button[1].addActionListener(e -> fileChooser());

        return button;
    }

    public JButton[] buttonCPDP() {
        buttonCPDP = new JButton[] {
                new JButton("Compress File"),
                new JButton("Decompress File")
        };
        Arrays.stream(buttonCPDP).forEach(b -> b.setPreferredSize(new Dimension(120, 40)));
        return buttonCPDP;
    }

    public void dialogSelectOption() {
        dialogSelectOption = new JDialog();
        dialogSelectOption.setSize(200, 150);
        dialogSelectOption.setModal(true);
        dialogSelectOption.setLayout(new FlowLayout());
        dialogSelectOption.setResizable(false);
        dialogSelectOption.setLocationRelativeTo(null);
        dialogSelectOption.add(buttonSelect()[1]);
        dialogSelectOption.add(buttonSelect()[2]);
        dialogSelectOption.setVisible(true);
    }

    public void fileChooser() {

        fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            System.out.println("SELECT " + file.getAbsolutePath());
        } else {
            System.out.println("NOT SELECT");
        }
    }

    public JPanel compressPanel() {
        compressPanel = new JPanel();
        compressPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        compressPanel.setSize(500,500);
        compressPanel.add(labelMode("Compress"));
        compressPanel.add(scrollPaneAllFile());
        compressPanel.add(textFieldPath(350, 35));
        compressPanel.add(buttonSelect()[0]);

        fileChooser = new JFileChooser();

        compressPanel.add(buttonCPDP()[0]);

        return compressPanel;
    }

    public JPanel decompressPanel() {
        decompressPanel = new JPanel();
        decompressPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        decompressPanel.setSize(500,500);

        label = new JLabel();
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setText("Decompress Mode");

        decompressPanel.add(label);
        decompressPanel.add(scrollPaneAllFile());
        decompressPanel.add(textFieldPath(350, 35));

        button = new JButton[] {
                new JButton("Select"),
                new JButton("Select File"),
                new JButton("Select Folder")
        };

        Arrays.stream(button).forEach(b -> b.setPreferredSize(new Dimension(120, 35)));

        decompressPanel.add(button[0]);

        setJMenuBar(menuBar);

        return decompressPanel;
    }


}
