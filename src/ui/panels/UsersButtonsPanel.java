package ui.panels;

import controller.UsersController;
import model.User;
import model.exceptions.AddDataException;
import model.exceptions.CredentialsNotSetException;
import model.exceptions.MinecraftDataRetrieverException;
import model.exceptions.UpdateDataException;
import ui.frames.InputWantedPlayerFrame;
import ui.frames.MainWindow;
import ui.frames.PlayerInfoFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class UsersButtonsPanel extends JPanel {
    private JButton searchButton;
    private JButton loadButton;
    private JButton addButton;
    private JButton addToWhitelistButton;
    private JButton removeFromWhitelistButton;
    private JButton seeInfoButton;
    private MainWindow parent;

    public UsersButtonsPanel(MainWindow parent) {
        this.parent = parent;
        setLayout(new GridLayout(1, 8));

        add(new JLabel(""));
        searchButton = new JButton("Rechercher");
        searchButton.addActionListener(new SearchUserListener());
        searchButton.setMnemonic(KeyEvent.VK_R);
        add(searchButton);

        loadButton = new JButton("Afficher tous les joueurs");
        loadButton.addActionListener(new LoadButtonListener());
        loadButton.setMnemonic(KeyEvent.VK_A);
        add(loadButton);

        addButton = new JButton("Ajouter un joueur");
        addButton.setEnabled(false);
        addButton.setMnemonic(KeyEvent.VK_J);
        addButton.addActionListener(new AddToWhiteistButtonListener());
        add(addButton);

        addToWhitelistButton = new JButton("Whitelister la sélection");
        addToWhitelistButton.setMnemonic(KeyEvent.VK_W);
        addToWhitelistButton.setEnabled(false);
        addToWhitelistButton.addActionListener(new AddToWhitelistButtonListener());
        add(addToWhitelistButton);

        removeFromWhitelistButton = new JButton("Déwhitelister la sélection");
        removeFromWhitelistButton.setMnemonic(KeyEvent.VK_D);
        removeFromWhitelistButton.setEnabled(false);
        removeFromWhitelistButton.addActionListener(new RemoveFromWhitelistButtonListener());
        add(removeFromWhitelistButton);

        seeInfoButton = new JButton("Voir les informations");
        seeInfoButton.addActionListener(new SeeInfoButtonListener());
        seeInfoButton.setEnabled(false);
        seeInfoButton.setMnemonic(KeyEvent.VK_I);
        add(seeInfoButton);

        add(new JLabel(""));
    }

    public JButton getSeeInfoButton() {
        return seeInfoButton;
    }

    public JButton getAddToWhitelistButton() {
        return addToWhitelistButton;
    }

    public JButton getRemoveFromWhitelistButton() {
        return removeFromWhitelistButton;
    }

    private void updateUsersStatus(ArrayList<User> users, boolean whitelistStatus) {
        for (User user : users) {
            if (user.isWhitelisted() != whitelistStatus) {
                user.setIsWhitelisted(whitelistStatus);

                try {
                    UsersController controller = new UsersController();
                    controller.updateUser(user);
                } catch (UpdateDataException | CredentialsNotSetException exception) {
                    JOptionPane.showMessageDialog(parent, exception.getMessage(), "Erreur lors de la mise à jour de " + user, JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        parent.setUsersTable();
    }

    private class SearchUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new InputWantedPlayerFrame(parent);
        }
    }

    private class LoadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            parent.setUsersTable();
            addButton.setEnabled(true);
        }
    }

    private class AddToWhiteistButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String usernameToAdd = JOptionPane.showInputDialog(parent, "Quel est le pseudo à ajouter ?");

            if (!usernameToAdd.isEmpty()) {
                if (parent.getAllUsers().stream().noneMatch(user -> user.getUsername().equalsIgnoreCase(usernameToAdd))) {
                    UsersController controller = new UsersController();

                    try {
                        controller.addUser(usernameToAdd);
                    } catch (AddDataException | CredentialsNotSetException | MinecraftDataRetrieverException exception) {
                        JOptionPane.showMessageDialog(parent, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }

                    parent.setUsersTable();
                } else JOptionPane.showMessageDialog(parent, "Vous ne pouvez pas ajouter un utilisateur déjà présent dans la liste !", "Attention", JOptionPane.WARNING_MESSAGE);
            }
        }
    }


    private class AddToWhitelistButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateUsersStatus(parent.getSelectedItems(), true);
        }
    }

    private class RemoveFromWhitelistButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateUsersStatus(parent.getSelectedItems(), false);
        }
    }

    private class SeeInfoButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new PlayerInfoFrame(parent, parent.getSelectedItem());
        }
    }
}
