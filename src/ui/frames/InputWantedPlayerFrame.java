package ui.frames;

import ui.panels.InputWantedPlayerPanel;

import javax.swing.*;
import java.awt.*;

public class InputWantedPlayerFrame extends JFrame {
    private InputWantedPlayerPanel panel;
    private MainWindow parent;

    public InputWantedPlayerFrame(MainWindow parent) {
        super("Infos joueur");

        this.parent = parent;
        panel = new InputWantedPlayerPanel(this);

        setBounds((int)parent.getSize().getWidth() / 2 + 200, (int)parent.getSize().getHeight() / 2, 260, 110);
        setVisible(true);
        setLayout(new BorderLayout());
        setResizable(false);
        add(panel, BorderLayout.CENTER);
        setIconImage(parent.getIconImage());
    }

    public MainWindow getParentWindow() {
        return parent;
    }
}
