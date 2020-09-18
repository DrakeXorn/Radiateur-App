package ui.tableModels;

import model.User;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class UserTableModel extends AbstractTableModel {
    private ArrayList<String> columnNames;
    private ArrayList<User> contents;

    public UserTableModel(ArrayList<User> users) {
        columnNames = new ArrayList<>();
        columnNames.add("Nom d'utilisateur");
        columnNames.add("UUID");
        columnNames.add("Nombre de punitions");
        columnNames.add("Est whitelisté ?");
        columnNames.add("Est banni ?");
        contents = users;
    }

    public User getRow(int row) {
        return contents.get(row);
    }

    public Object getValueAt(int row, int column) {
        User user = getRow(row);

        return switch(column) {
            case 0 -> user.getUsername();
            case 1 -> user.getUUID();
            case 2 -> user.getPunishments().size();
            case 3 -> user.isWhitelisted();
            case 4 -> user.isBanned();
            default -> null;
        };
    }

    public Class<?> getColumnClass(int column) {
        return switch(column) {
            case 2 -> Integer.class;
            case 3, 4 -> Boolean.class;
            default -> String.class;
        };
    }

    public int getRowCount() {
        return contents.size();
    }

    public int getColumnCount() {
        return columnNames.size();
    }

    public String getColumnName(int column) {
        return columnNames.get(column);
    }
}
