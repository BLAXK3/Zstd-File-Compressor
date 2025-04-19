package com.github.blaxk3.compressor.ui.containers.scrollpane;

import static com.github.blaxk3.compressor.ui.controls.CompressPanelControls.setControlsSize;

import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class FileScrollPane extends java.awt.ScrollPane {

    public FileScrollPane(String fileName) {
        JPanel scrollPanePanel = new JPanel();
        scrollPanePanel.setLayout(new BoxLayout(scrollPanePanel, BoxLayout.Y_AXIS));

        JTextArea text = new JTextArea();
        text.setFont(new Font("Arial", Font.PLAIN, 15));
        text.setEditable(false);
//        for (int i = 1; i <= 10; i++) {
//            text.append("File_" + i + ".txt\n");
//        }
//        scrollPanePanel.add(text);
        add(scrollPanePanel);
        setPreferredSize(setControlsSize(400, 200));
    }
}
