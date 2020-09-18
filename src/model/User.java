package model;

import java.util.ArrayList;

public class User {
    private String uuid;
    private String username;
    private Boolean isWhitelisted;
    private ArrayList<Punishment> punishments;

    public User(String uuid, String username, Boolean isWhitelisted) {
        this.uuid = uuid;
        this.username = username;
        this.isWhitelisted = isWhitelisted;
        punishments = new ArrayList<>();
    }

    public Boolean isBanned() {
        boolean isBanned = !punishments.isEmpty();

        for (Punishment punishment : punishments) {
            isBanned = isBanned && punishment.isCurrentlyBanned();
        }

        return isBanned;
    }

    public Boolean isWhitelisted() {
        return isWhitelisted;
    }

    public String getUsername() {
        return username;
    }

    public String getUUID() {
        return uuid;
    }

    public void setIsWhitelisted(boolean isWhitelisted) {
        this.isWhitelisted = isWhitelisted;
    }

    public ArrayList<Punishment> getPunishments() {
        return punishments;
    }

    public void addPunishment(Punishment punishment) {
        punishments.add(punishment);
    }

    public String toString() {
        return getUsername();
    }
}