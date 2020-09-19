package ui.panels;

import model.Punishment;
import net.miginfocom.swing.MigLayout;
import ui.frames.NewPunishmentFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public class NewPunishmentPanel extends JPanel {
    private JLabel punishmentTypeLabel;
    private JLabel timeRequiredLabel;
    private JLabel reasonLabel;
    private JComboBox<String> punishmentTypeBox;
    private JComboBox<String> timeRequiredBox;
    private JTextArea reasonArea;
    private JButton confirmButton;
    private JButton cancelButton;
    private NewPunishmentFrame parent;
    private static final int[] NUMERIC_REPRESENTATIONS = new int[]{15, 30, 60, 120, 180, 240, 1440, 4320, 10080, 20160};
    private static final String[] LABELS = new String[]{"15 minutes", "30 minutes", "1 heure", "2 heures", "3 heures", "4 heures", "1 jour", "3 jours", "1 semaine", "2 semaines"};

    public NewPunishmentPanel(NewPunishmentFrame parent) {
        setLayout(new MigLayout());

        punishmentTypeLabel = new JLabel("Type de punition :");
        punishmentTypeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(punishmentTypeLabel, "span 2");

        punishmentTypeBox = new JComboBox<>();
        punishmentTypeBox.addItem("BAN");
        punishmentTypeBox.addItem("TEMP_BAN");
        punishmentTypeBox.addItem("KICK");
        punishmentTypeBox.addItem("MUTE");
        punishmentTypeBox.addItem("TEMP_MUTE");
        punishmentTypeBox.addItem("JAIL");
        punishmentTypeBox.addActionListener(new PunishmentTypeBoxListener());
        add(punishmentTypeBox, "width 120!");

        timeRequiredLabel = new JLabel("Durée :");
        timeRequiredLabel.setHorizontalAlignment(4);
        add(timeRequiredLabel, "gapleft 75px");

        timeRequiredBox = new JComboBox<>(LABELS);
        timeRequiredBox.setEnabled(false);
        add(timeRequiredBox, "width 120!, wrap");

        reasonLabel = new JLabel("Raison :");
        add(reasonLabel, "span, wrap");

        reasonArea = new JTextArea();
        reasonArea.setLineWrap(true);
        add(reasonArea, "width 460px!, span, height 165px!, wrap");

        confirmButton = new JButton("Confirmer");
        confirmButton.addActionListener(new ConfirmButtonListener());
        add(confirmButton, "cell 3 4");

        cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(new CancelButtonListener());
        add(cancelButton);
        this.parent = parent;
    }

    private boolean isPunishmentTemporary() {
        return ((String) Objects.requireNonNull(punishmentTypeBox.getSelectedItem())).startsWith("TEMP");
    }

    private class PunishmentTypeBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            timeRequiredBox.setEnabled(isPunishmentTemporary());
            timeRequiredBox.setSelectedIndex(0);
        }
    }

    private class ConfirmButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Punishment punishment = new Punishment((String)punishmentTypeBox.getSelectedItem(), reasonArea.getText(), new Date(), new Date(-1L));
            punishment.setIsNewlyCreated();
            if (isPunishmentTemporary()) {
                LocalDateTime punishmentEndDate = LocalDateTime.now().plusMinutes(NewPunishmentPanel.NUMERIC_REPRESENTATIONS[timeRequiredBox.getSelectedIndex()]);
                punishment.setPunishmentEndTime(Date.from(punishmentEndDate.atZone(ZoneId.systemDefault()).toInstant()));
            }

            parent.getParent().getPlayer().addPunishment(punishment);
            parent.getParent().addPunishment(punishment);
            parent.getParent().repaint();
            parent.dispose();
        }
    }

    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            parent.dispose();
        }
    }
}
