package com.alquiler;

import javax.swing.SwingUtilities;
import com.alquiler.gui.AlquilerGUI;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AlquilerGUI ventana = new AlquilerGUI();
            ventana.setVisible(true);
        });
    }
}
