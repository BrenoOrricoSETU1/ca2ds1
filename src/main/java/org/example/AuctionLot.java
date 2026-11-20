package org.example;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuctionLot implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private String lotId;
    private String title;
    private String description;
    private String type;
    private int year;
    private double price;
    private String imageUrl;
    private boolean sold;
    private LocalDateTime saleDate;
    private double finalPrice;
    private final CustomLinkedList<Bid> bids = new CustomLinkedList<>();

    public AuctionLot(String lotId, String title, String description, String type, int year, double price, String imageUrl) {
        this.lotId = lotId;
        this.title = title;
        this.description = description;
        this.type = type;
        this.year = year;
        this.price = price;
        this.imageUrl = imageUrl;
        this.sold = false;
        this.saleDate = null;
        this.finalPrice = 0;
    }

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isSold() {
        return sold;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public CustomLinkedList<Bid> getBids() {
        return bids;
    }

    public Bid getHighestBid() {
        Bid highest = null;
        for (Bid bid : bids) {
            if (highest == null || bid.getAmount() > highest.getAmount()) {
                highest = bid;
            }
        }
        return highest;
    }

    public boolean canAcceptBid(double amount) {
        if (sold || amount <= 0) {
            return false;
        }
        Bid highest = getHighestBid();
        return highest == null || amount > highest.getAmount();
    }

    public boolean addBid(Bid bid) {
        if (!canAcceptBid(bid.getAmount())) {
            return false;
        }
        bids.add(bid);
        return true;
    }

    public boolean removeBid(Bid bid) {
        if (bid.isAccepted()) {
            return false;
        }
        return bids.remove(bid);
    }

    public Bid sell(LocalDateTime when) {
        if (sold) {
            return null;
        }
        Bid winning = getHighestBid();
        if (winning == null) {
            return null;
        }
        winning.setAccepted(true);
        sold = true;
        saleDate = when;
        finalPrice = winning.getAmount();
        return winning;
    }

    public String getFormattedSaleDate() {
        return saleDate == null ? "" : saleDate.format(DISPLAY_FORMAT);
    }

    @Override
    public String toString() {
        String status = sold ? String.format("SOLD €%.2f", finalPrice) : "Available";
        return String.format("%s - %s (%s, %d) Guide €%.2f [%s]", lotId, title, type, year, price, status);
    }
}
