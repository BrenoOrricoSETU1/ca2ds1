package org.example;

public class AuctionLot {
    private String lotId;
    private String title;
    private String description;
    private String type;
    private String year;
    private double price;
    private String imageUrl;
    private boolean sold;
    private String saleDate;
    private double finalPrice;

    public AuctionLot(String lotId, String title, String description, String type, String year, double price, String imageUrl, boolean sold, String saleDate, double finalPrice) {
        this.lotId = lotId;
        this.title = title;
        this.description = description;
        this.type = type;
        this.year = year;
        this.price = price;
        this.imageUrl = imageUrl;
        this.sold = sold;
        this.saleDate = saleDate;
        this.finalPrice = finalPrice;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
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

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    @Override
    public String toString() {

    }
}
