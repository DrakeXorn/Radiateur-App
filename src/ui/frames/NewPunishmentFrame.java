package ui.frames;

import ui.panels.NewPunishmentPanel;
import ui.panels.PlayerInfoPanel;

import javax.swing.*;
import java.awt.*;

public class NewPunishmentFrame extends JFrame {
    private NewPunishmentPanel panel;
    private PlayerInfoPanel parent;

    public NewPunishmentFrame(PlayerInfoPanel parent, Image playerHead) {
        super("Ajout d'une punition à l'encontre de " + parent.getPlayer());

        setBounds(parent.getBounds());
        setSize(490, 300);
        setLayout(new BorderLayout());
        setIconImage(playerHead);
        setResizable(false);

        panel = new NewPunishmentPanel(this);
        add(this.panel, "Center");

        this.parent = parent;

        setVisible(true);
    }

    public PlayerInfoPanel getParent() {
        return parent;
    }
}
