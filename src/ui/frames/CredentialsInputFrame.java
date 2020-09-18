package ui.frames;

import business.AppIconManager;
import ui.panels.CredentialsInputPanel;

import javax.swing.*;
import java.awt.*;

public class CredentialsInputFrame extends JFrame {
    private CredentialsInputPanel inputPanel;

    public CredentialsInputFrame(MainWindow parent) {
        super("Identifiants de la base de données");
        AppIconManager manager = new AppIconManager();

        setBounds(parent.getSize().width / 2 + 100, parent.getSize().height / 2 + 100, 450, 100);
        setLayout(new BorderLayout());

        inputPanel = new CredentialsInputPanel(this);
        add(inputPanel, BorderLayout.CENTER);
        setIconImage(manager.getAppIcon());
        setVisible(true);
    }
}
