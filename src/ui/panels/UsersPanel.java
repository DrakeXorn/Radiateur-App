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
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class UsersPanel extends JPanel {
    private JScrollPane scrollPane;
    private JTable usersTable;
    private ListSelectionModel listSelect;
    private MainWindow parent;
    private UserTableModel model;

    public UsersPanel(MainWindow parent) {
        UsersController controller = new UsersController();
        this.parent = parent;
        this.setLayout(new BorderLayout());

        try {
            this.setUsers(controller.getAllUsers());
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
        usersTable.setSelectionMode(2);

        listSelect = usersTable.getSelectionModel();
        listSelect.addListSelectionListener(new TableRowSelectListener());
        scrollPane = new JScrollPane(usersTable);
        add(scrollPane, BorderLayout.CENTER);

        parent.repaint();
        parent.setVisible(true);
    }

    public ArrayList<User> getSelectedItems() {
        ArrayList<User> res = new ArrayList<>();

        for (int iRow = 0; iRow < usersTable.getSelectedRows().length; iRow++) {
            res.add(model.getRow(usersTable.getSelectedRows()[iRow]));
        }

        return res;
    }

    private static class BodyRenderer implements TableCellRenderer {
        DefaultTableCellRenderer renderer;

        public BodyRenderer(JTable table) {
            this.renderer = (DefaultTableCellRenderer)table.getDefaultRenderer(Integer.class);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            this.renderer.setHorizontalAlignment(2);
            return this.renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
        }
    }

    private static class HeaderRenderer implements TableCellRenderer {
        DefaultTableCellRenderer renderer;

        public HeaderRenderer(JTable table) {
            renderer = (DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer();
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            renderer.setHorizontalAlignment(!table.getColumn(value).getHeaderValue().equals("Est whitelisté ?") && !table.getColumn(value).getHeaderValue().equals("Est banni ?") ? 2 : 0);
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
