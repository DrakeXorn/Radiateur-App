package ui.panels;

import model.exceptions.CredentialsNotSetException;
import model.exceptions.GetAllDataException;
import ui.frames.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AToZButtonsPanel extends JPanel {
    private MainWindow parent;

    public AToZButtonsPanel(MainWindow parent) {
        this.parent = parent;
        setLayout(new GridLayout(1, 6));

        for (int iButton = 0; iButton < 6; iButton++) {
            JButton button = new JButton(switch(iButton) {
                case 1 -> "F-J";
                case 2 -> "K-P";
                case 3 -> "Q-U";
                case 4 -> "V-Z";
                case 5 -> "Caractères spéciaux et chiffres";
                default -> "A-E";
            });

            button.addActionListener(new LetterButtonListener());
            this.add(button);
            this.setVisible(true);
        }

    }

    private class LetterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                parent.listPlayers("(?i)^(" +
                        (!((JButton)e.getSource()).getText().equals("Caractères spéciaux et chiffres") ?
                                "[" + ((JButton)e.getSource()).getText() + "]" :
                                "\\d|\\_|\\||\\(|\\)|\\+|\\-|\\°|\\$|\\&|\\*|\\/|\\'|\\{|\\}|\\@|\\#|\\§") +
                        ").+");
            } catch (GetAllDataException | CredentialsNotSetException exception) {
                JOptionPane.showMessageDialog(parent, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
