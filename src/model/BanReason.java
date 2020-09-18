package model;

import java.time.LocalDateTime;

public class BanReason {
    private LocalDateTime banDate;
    private String banReason;

    public BanReason(String banReason, LocalDateTime banDate) {
        this.banDate = banDate;
        this.banReason = banReason;
    }

    public BanReason(String banReason) {
        this(banReason, LocalDateTime.now());
    }

    public String getReason() {
        return banReason;
    }

    public LocalDateTime getBanDate() {
        return banDate;
    }

    public boolean isEmpty() {
        return banReason.isEmpty();
    }

    public void setReason(String reason) {
        this.banReason = reason;
    }

    public void updateDate() {
        banDate = LocalDateTime.now();
    }
}
