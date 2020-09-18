package ui.frames;

import controller.AppIconController;
import controller.UsersController;
import model.User;
import model.exceptions.CredentialsNotSetException;
import model.exceptions.GetAllDataException;
import ui.panels.AToZButtonsPanel;
import ui.panels.UsersButtonsPanel;
import ui.panels.UsersPanel;
import ui.thread.KsosPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class MainWindow extends JFrame {
    private JMenuBar menuBar;
    private JMenu infoMenu;
    private JMenuItem setDBCredentialsItem;
    private Container container;
    private UsersPanel usersPanel;
    private KsosPanel ksosPanel;
    private UsersButtonsPanel buttonsPanel;
    private AToZButtonsPanel aToZButtonsPanel;

    public MainWindow() {
        super("Radiateur App");

        AppIconController iconController = new AppIconController();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setBounds(screenSize.width / 4 - 150, screenSize.height / 4 - 100, (int)Math.ceil((double)screenSize.width / 1.5D), (int)Math.ceil((double)screenSize.height / 1.5D));
        setLayout(new BorderLayout());

        container = getContentPane();
        buttonsPanel = new UsersButtonsPanel(this);
        ksosPanel = new KsosPanel();
        container.add(ksosPanel, BorderLayout.CENTER);
        container.add(buttonsPanel, BorderLayout.SOUTH);

        menuBar = new JMenuBar();
        infoMenu = new JMenu("?");

        setDBCredentialsItem = new JMenuItem("Identifiants DB");
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(infoMenu);
        setDBCredentialsItem.addActionListener(new MainWindow.SetCredentialsListener());
        infoMenu.add(setDBCredentialsItem);
        setJMenuBar(menuBar);

        setIconImage(iconController.getAppIcon());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }

    public void setUsersTable() {
        if (aToZButtonsPanel == null) {
            aToZButtonsPanel = new AToZButtonsPanel(this);
            container.add(aToZButtonsPanel, BorderLayout.NORTH);
        }

        if (ksosPanel != null)
            container.remove(ksosPanel);

        if (usersPanel != null)
            container.remove(usersPanel);

        usersPanel = new UsersPanel(this);
        container.add(this.usersPanel);
        repaint();
        setVisible(true);
    }

    public void listPlayers(String regex) throws CredentialsNotSetException, GetAllDataException {
        UsersController controller = new UsersController();
        ArrayList<User> users = controller.getAllUsers();
        ArrayList<User> filteredUsers = new ArrayList<>();

        for (User user : users)
            if (Pattern.matches(regex, user.getUsername()))
                filteredUsers.add(user);

        usersPanel.setUsers(filteredUsers);
    }

    public JButton getSeeInfoButton() {
        return buttonsPanel.getSeeInfoButton();
    }

    public ArrayList<User> getSelectedItems() {
        return usersPanel.getSelectedItems();
    }

    public User getSelectedItem() {
        return getSelectedItems().get(0);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement des paramètres par défaut de l'interface : " + exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        new MainWindow();
    }

    public JButton getAddToWhitelistButton() {
        return buttonsPanel.getAddToWhitelistButton();
    }

    public JButton getRemoveFromWhitelistButton() {
        return buttonsPanel.getRemoveFromWhitelistButton();
    }

    private class SetCredentialsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new CredentialsInputFrame(MainWindow.this);
        }
    }
}