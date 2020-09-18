package ui.thread;

import javax.swing.*;

public class KsosMovingThread extends Thread {
    private KsosPanel panel;

    public KsosMovingThread(KsosPanel panel) {
        this.panel = panel;
    }

    public void run() {
        while(true) {
            try {
                panel.rotate();
                sleep(10L);
            } catch (InterruptedException exception) {
                JOptionPane.showMessageDialog(panel, "Erreur lors de l'animation de l'image du Ksos", "Problème", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
