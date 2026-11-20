package org.example;

public class Bid {
    private String bidId;
    private String lotId;
    private String bidderId;
    private double amount;
    private String dateTime;
    private boolean accepted;

    public Bid(String bidId, String lotId, String bidderId, double amount, String dateTime, boolean accepted) {
        this.bidId = bidId;
        this.lotId = lotId;
        this.bidderId = bidderId;
        this.amount = amount;
        this.dateTime = dateTime;
        this.accepted = accepted;
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public String toString() {

    }
}
