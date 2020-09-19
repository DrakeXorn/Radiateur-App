package ui.frames;

import controller.PlayerHeadController;
import model.User;
import model.exceptions.SkinRetrieverException;
import ui.panels.PlayerInfoPanel;

import javax.swing.*;
import java.awt.*;

public class PlayerInfoFrame extends JFrame {
    private Image headIcon;
    private Container container;
    private MainWindow parent;

    public PlayerInfoFrame(MainWindow parent, User player) {
        super("Informations sur " + player.getUsername());

        PlayerHeadController iconController = new PlayerHeadController();

        try {
            headIcon = iconController.getSkinHeadViaUsername(player.getUsername());
            setIconImage(headIcon);
        } catch (SkinRetrieverException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Problème de récupération d'icône", JOptionPane.WARNING_MESSAGE);
        }

        setLayout(new BorderLayout());
        container = getContentPane();
        container.add(new PlayerInfoPanel(this, headIcon, player), BorderLayout.CENTER);

        this.parent = parent;

        setResizable(false);
        setSize(375, 400);
        setVisible(true);
    }

    public MainWindow getParent() {
        return parent;
    }
}