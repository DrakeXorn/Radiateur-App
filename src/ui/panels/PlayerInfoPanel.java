package ui.panels;

import controller.UsersController;
import model.Punishment;
import model.User;
import model.exceptions.CredentialsNotSetException;
import model.exceptions.UpdateDataException;
import net.miginfocom.swing.MigLayout;
import ui.frames.NewPunishmentFrame;
import ui.frames.PlayerInfoFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerInfoPanel extends JPanel {
    private JTextField uuidField;
    private JLabel usernameLabel;
    private JLabel uuidLabel;
    private JLabel punishmentsLabel;
    private JCheckBox isWhitelistedBox;
    private JCheckBox isBannedBox;
    private JLabel userHead;
    private JButton addPunishmentButton;
    private JButton okButton;
    private JButton cancelButton;
    private PlayerInfoFrame parent;
    private DefaultListModel<Punishment> punishmentsListModel;
    private JList<Punishment> punishmentsList;
    private User player;

    public PlayerInfoPanel(PlayerInfoFrame parent, Image playerHead, User player) {
        setLayout(new MigLayout());

        this.parent = parent;
        this.player = player;

        userHead = new JLabel();
        userHead.setIcon(new ImageIcon(playerHead.getScaledInstance(60, 60, 8)));
        add(userHead, "gaptop 5, gapleft 5, span 1 2");

        usernameLabel = new JLabel(player.getUsername());
        usernameLabel.setFont(new Font(usernameLabel.getFont().getName(), Font.BOLD, 20));
        usernameLabel.setHorizontalAlignment(0);
        add(usernameLabel, "gaptop 5, span, wrap");

        uuidLabel = new JLabel("UUID :");
        add(uuidLabel, "w 40, split 2, span");

        uuidField = new JTextField(player.getUUID());
        uuidField.setEditable(false);
        add(uuidField, "width 215px!, wrap");

        isWhitelistedBox = new JCheckBox("Est whitelisté", player.isWhitelisted());
        add(isWhitelistedBox, "cell 1 3");

        isBannedBox = new JCheckBox("Est banni", player.isBanned());
        isBannedBox.setEnabled(false);
        add(isBannedBox, "wrap");

        punishmentsLabel = new JLabel("Punitions attribuées :");
        punishmentsLabel.setFont(new Font(usernameLabel.getFont().getName(), Font.PLAIN, 14));
        add(punishmentsLabel, "span, wrap");

        punishmentsListModel = new DefaultListModel<>();
        punishmentsList = new JList<>();
        punishmentsList.setModel(punishmentsListModel);

        for (Punishment punishment : player.getPunishments())
            addPunishment(punishment);

        add(new JScrollPane(punishmentsList), "width 345px!, span, wrap");

        addPunishmentButton = new JButton("Ajouter une punition");
        addPunishmentButton.addActionListener(new PlayerInfoPanel.AddPunishmentListener());
        add(addPunishmentButton, "span, wrap");

        okButton = new JButton("  OK  ");
        okButton.addActionListener(new PlayerInfoPanel.OKButtonListener());
        add(okButton, "cell 2 8");

        cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(new PlayerInfoPanel.CloseWindowListener());
        add(cancelButton);

        setVisible(true);
    }

    public void addPunishment(Punishment punishment) {
        punishmentsListModel.addElement(punishment);

        if (punishment.getPunishmentType().equals("BAN"))
            isBannedBox.setSelected(true);
    }

    public User getPlayer() {
        return player;
    }

    private class AddPunishmentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new NewPunishmentFrame(PlayerInfoPanel.this, parent.getIconImage());
        }
    }

    private class OKButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            UsersController controller = new UsersController();
            if (player.isWhitelisted() != isWhitelistedBox.isSelected())
                player.setIsWhitelisted(isWhitelistedBox.isSelected());

            try {
                controller.updateUser(player);
            } catch (CredentialsNotSetException | UpdateDataException exception) {
                JOptionPane.showMessageDialog(parent, exception.getMessage(), "Erreur lors de la mise à jour du joueur", JOptionPane.ERROR_MESSAGE);
            }

            parent.getParent().setUsersTable();
            parent.dispose();
        }
    }

    private class CloseWindowListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            parent.dispose();
        }
    }
}