package ui.panels;

import controller.UsersController;
import model.User;
import model.exceptions.CredentialsNotSetException;
import model.exceptions.GetAllDataException;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import ui.frames.InputWantedPlayerFrame;
import ui.frames.PlayerInfoFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class InputWantedPlayerPanel extends JPanel {
    private JLabel usernameWantedLabel;
    private JComboBox<String> userWantedBox;
    private JButton okButton;
    private JButton cancelButton;
    private InputWantedPlayerFrame parent;
    private ArrayList<User> users;

    public InputWantedPlayerPanel(InputWantedPlayerFrame parent) {
        UsersController controller = new UsersController();
        this.parent = parent;

        try {
            users = controller.getAllUsers();
            setLayout(new MigLayout());

            usernameWantedLabel = new JLabel("Joueur souhaité : ");
            add(usernameWantedLabel, "");

            userWantedBox = new JComboBox<>();
            userWantedBox.addItem("");
            for (User user : users) {
                userWantedBox.addItem(user.toString());
            }
            AutoCompleteDecorator.decorate(userWantedBox);
            add(userWantedBox, "span 2, wrap");

            okButton = new JButton("  OK  ");
            okButton.addActionListener(new InputWantedPlayerPanel.OKButtonListener());
            okButton.setMnemonic(KeyEvent.VK_O);
            add(okButton, "cell 1 2");

            cancelButton = new JButton("Annuler");
            cancelButton.addActionListener(new InputWantedPlayerPanel.CancelButtonListener());
            add(cancelButton);
        } catch (GetAllDataException | CredentialsNotSetException exception) {
            JOptionPane.showMessageDialog(parent, exception.getMessage(), "Erreur lors de la récupération des joueurs", JOptionPane.ERROR_MESSAGE);
            parent.dispose();
        }

    }

    private class OKButtonListener implements ActionListener {
        private OKButtonListener() {
        }

        public void actionPerformed(ActionEvent e) {
            if (userWantedBox.getSelectedIndex() != 0) {
                new PlayerInfoFrame(parent.getParentWindow(), users.get(userWantedBox.getSelectedIndex() - 1));
                parent.dispose();
            } else {
                JOptionPane.showMessageDialog(parent, "Vous ne pouvez pas ne pas choisir de pseudo.", "Attention", JOptionPane.WARNING_MESSAGE);
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
