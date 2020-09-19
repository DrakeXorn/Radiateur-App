package ui.thread;

import controller.KsosController;
import model.exceptions.FileException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class KsosPanel extends JPanel {
    private double rotation;
    private Image ksosImg;

    public KsosPanel() {
        KsosController ksosController = new KsosController();

        try {
            KsosMovingThread thread = new KsosMovingThread(this);
            ksosImg = ksosController.getKsosImg();
            rotation = 0;
            thread.start();
            addMouseListener(new MouseOverImageListener());
        } catch (FileException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Erreur lors du chargement de l'image", JOptionPane.WARNING_MESSAGE);
        }

    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;

        g2d.rotate(Math.toRadians(rotation), (double)getParent().getWidth() / 2., (double)getParent().getHeight() / 2.0);
        g2d.drawImage(ksosImg, getParent().getWidth() / 2 - ksosImg.getWidth(this) / 2, getParent().getHeight() / 2 - ksosImg.getHeight(this) / 2 - 20, this);
    }

    public void rotate() {
        rotation = rotation < 360 ? rotation + 1.5 : 0;
        repaint();
    }

    private class MouseOverImageListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getX() >= getParent().getWidth() / 2 - ksosImg.getWidth(KsosPanel.this) / 2 &&
                    e.getX() <= getParent().getWidth() / 2 + ksosImg.getWidth(KsosPanel.this) / 2 &&
                    e.getY() >= getParent().getHeight() / 2 - ksosImg.getHeight(KsosPanel.this) / 2 - 20 &&
                    e.getY() <= getParent().getHeight() / 2 + ksosImg.getHeight(KsosPanel.this) / 2 - 20) {
                try {
                    KsosController controller = new KsosController();
                    controller.getKsosClip((int)(Math.random() * 8) + 1).start();
                } catch (FileException exception) {
                    JOptionPane.showMessageDialog(KsosPanel.this, exception.getMessage(), "Erreur", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
}