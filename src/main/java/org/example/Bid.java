package org.example;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bid implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private String bidId;
    private String lotId;
    private String bidderId;
    private double amount;
    private LocalDateTime dateTime;
    private boolean accepted;

    public Bid(String bidId, String lotId, String bidderId, double amount, LocalDateTime dateTime) {
        this.bidId = bidId;
        this.lotId = lotId;
        this.bidderId = bidderId;
        this.amount = amount;
        this.dateTime = dateTime;
        this.accepted = false;
    }

    public String getBidId() {
        return bidId;
    }

    public void setBidId(String bidId) {
        this.bidId = bidId;
    }

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public String getBidderId() {
        return bidderId;
    }

    public void setBidderId(String bidderId) {
        this.bidderId = bidderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getFormattedDateTime() {
        return dateTime == null ? "" : dateTime.format(DISPLAY_FORMAT);
    }

    @Override
    public String toString() {
        return String.format("%s  €%.2f  %s%s", bidId, amount, getFormattedDateTime(), accepted ? "  [WINNING BID]" : "");
    }
}
