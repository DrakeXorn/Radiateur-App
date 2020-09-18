package model;

import java.time.Duration;
import java.util.Date;

public class Punishment {
    private String punishmentType;
    private String reason;
    private String operator;
    private Date punishmentStartTime;
    private Date punishmentEndTime;
    private boolean isNewlyCreated;
    private boolean stillInPunishmentState;

    public Punishment(String punishmentType, String reason, String operator, Date punishmentStartTime, Date punishmentEndTime) {
        this.punishmentType = punishmentType;
        this.reason = reason;
        this.operator = operator;
        this.punishmentStartTime = punishmentStartTime;
        this.punishmentEndTime = punishmentEndTime;
        isNewlyCreated = false;
    }

    public Punishment(String punishmentType, String reason, Date punishmentStartTime, Date punishmentEndTime) {
        this(punishmentType, reason, "vous", punishmentStartTime, punishmentEndTime);
    }

    public void setStillInPunishmentState(boolean stillInPunishmentState) {
        this.stillInPunishmentState = stillInPunishmentState;
    }

    public void setIsNewlyCreated() {
        this.isNewlyCreated = true;
    }

    public void setPunishmentEndTime(Date punishmentEndTime) {
        this.punishmentEndTime = punishmentEndTime;
    }

    public String getPunishmentType() {
        return punishmentType;
    }

    public String getReason() {
        return reason;
    }

    public Date getPunishmentStartTime() {
        return punishmentStartTime;
    }

    public Date getPunishmentEndTime() {
        return punishmentEndTime;
    }

    public boolean isCurrentlyBanned() {
        return punishmentType.equals("BAN") && stillInPunishmentState || !punishmentType.equals("BAN") && punishmentEndTime.after(new Date());
    }

    public boolean isNewlyCreated() {
        return this.isNewlyCreated;
    }

    public String toString() {
        return punishmentType + " par " + operator + (punishmentEndTime.getTime() == -1L ? " de manière permanente" : " pendant " + Duration.between(punishmentStartTime.toInstant(), punishmentEndTime.toInstant()).toMinutes()) + " minutes. Raison : " + this.reason + ".";
    }
}