package ui.panels;

import controller.CredentialsController;
import model.exceptions.FileException;
import ui.frames.CredentialsInputFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class CredentialsInputPanel extends JPanel {
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton acceptButton;
    private JButton cancelButton;
    private CredentialsInputFrame parent;

    public CredentialsInputPanel(CredentialsInputFrame inputFrame) {
        setLayout(new GridLayout(3, 2));

        usernameLabel = new JLabel("Nom d'utilisateur");
        usernameLabel.setVerticalAlignment(0);
        usernameLabel.setHorizontalAlignment(0);
        add(usernameLabel);

        usernameField = new JTextField();
        add(usernameField);

        passwordLabel = new JLabel("Mot de passe");
        passwordLabel.setVerticalAlignment(0);
        passwordLabel.setHorizontalAlignment(0);
        add(passwordLabel);

        passwordField = new JPasswordField();
        add(passwordField);

        acceptButton = new JButton("OK");
        acceptButton.setMnemonic(KeyEvent.VK_O);
        acceptButton.addActionListener(new CredentialsInputPanel.AcceptButtonListener());
        add(acceptButton);

        cancelButton = new JButton("Annuler");
        cancelButton.setMnemonic(KeyEvent.VK_A);
        cancelButton.addActionListener(new CredentialsInputPanel.CancelButtonListener());
        add(cancelButton);

        parent = inputFrame;
    }

    private class AcceptButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            CredentialsController controller = new CredentialsController();

            try {
                controller.setCredentials(usernameField.getText(), passwordField.getPassword());
                parent.dispose();
                JOptionPane.showMessageDialog(parent, "Les identifiants ont bien été ajoutés !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (FileException exception) {
                JOptionPane.showMessageDialog(parent, exception.getMessage(), "Erreur !", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            parent.dispose();
        }
    }
}
