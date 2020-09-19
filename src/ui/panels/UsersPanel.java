package ui.panels;

import controller.UsersController;
import model.User;
import model.exceptions.CredentialsNotSetException;
import model.exceptions.GetAllDataException;
import ui.frames.MainWindow;
import ui.tableModels.UserTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class UsersPanel extends JPanel {
    private JScrollPane scrollPane;
    private JTable usersTable;
    private ListSelectionModel listSelect;
    private MainWindow parent;
    private UserTableModel model;

    public UsersPanel(MainWindow parent) {
        UsersController controller = new UsersController();
        this.parent = parent;
        setLayout(new BorderLayout());

        try {
            setUsers(controller.getAllUsers());
        } catch (GetAllDataException | CredentialsNotSetException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void setUsers(ArrayList<User> users) {
        removeAll();
        model = new UserTableModel(users);

        usersTable = new JTable(model);
        usersTable.setAutoCreateRowSorter(true);
        usersTable.setUpdateSelectionOnSort(true);
        usersTable.getTableHeader().setReorderingAllowed(false);
        usersTable.setDefaultRenderer(Integer.class, new BodyRenderer(usersTable));
        usersTable.getTableHeader().setDefaultRenderer(new HeaderRenderer(usersTable));
        usersTable.getTableHeader().setFont(new Font(usersTable.getTableHeader().getFont().getName(), Font.BOLD, usersTable.getTableHeader().getFont().getSize()));
        usersTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        listSelect = usersTable.getSelectionModel();
        listSelect.addListSelectionListener(new TableRowSelectListener());
        scrollPane = new JScrollPane(usersTable);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
        parent.repaint();
        parent.setVisible(true);
        parent.getButtons().repaint();
    }

    public ArrayList<User> getSelectedItems() {
        ArrayList<User> users = new ArrayList<>();

        for (int index : listSelect.getSelectedIndices())
            users.add(model.getRow(usersTable.getRowSorter().convertRowIndexToView(index)));

        return users;
    }

    public ArrayList<User> getAllUsers() {
        return model.getContents();
    }

    private static class BodyRenderer implements TableCellRenderer {
        DefaultTableCellRenderer renderer;

        public BodyRenderer(JTable table) {
            renderer = (DefaultTableCellRenderer)table.getDefaultRenderer(Integer.class);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            renderer.setHorizontalAlignment(SwingConstants.CENTER);
            return renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
        }
    }

    private static class HeaderRenderer implements TableCellRenderer {
        DefaultTableCellRenderer renderer;

        public HeaderRenderer(JTable table) {
            renderer = (DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer();
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            renderer.setHorizontalAlignment(table.getColumn(value).getHeaderValue().equals("Est whitelisté ?") || table.getColumn(value).getHeaderValue().equals("Nombre de punitions") || table.getColumn(value).getHeaderValue().equals("Est banni ?") ? SwingConstants.CENTER : SwingConstants.LEFT);

            return renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
        }
    }

    private class TableRowSelectListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            parent.getSeeInfoButton().setEnabled(usersTable.getSelectedRowCount() == 1);
            parent.getAddToWhitelistButton().setEnabled(true);
            parent.getRemoveFromWhitelistButton().setEnabled(true);
        }
    }
}
