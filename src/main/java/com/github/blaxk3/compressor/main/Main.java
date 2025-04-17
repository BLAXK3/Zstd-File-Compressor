package com.github.blaxk3.compressor.main;

import com.github.blaxk3.compressor.ui.MainFrame;

import javax.swing.SwingUtilities;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
