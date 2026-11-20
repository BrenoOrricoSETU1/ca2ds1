package org.example;

import java.io.Serializable;

public class Bidder implements Serializable {
    private static final long serialVersionUID = 1L;

    private String bidderId;
    private String name;
    private String address;
    private String phone;
    private String email;
    private final CustomLinkedList<Bid> bids = new CustomLinkedList<>();

    public Bidder(String bidderId, String name, String address, String phone, String email) {
        this.bidderId = bidderId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public String getBidderId() {
        return bidderId;
    }

    public void setBidderId(String bidderId) {
        this.bidderId = bidderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CustomLinkedList<Bid> getBids() {
        return bids;
    }

    public void addBid(Bid bid) {
        bids.add(bid);
    }

    public boolean removeBid(Bid bid) {
        return bids.remove(bid);
    }

    @Override
    public String toString() {
        return name + " (" + bidderId + ") - " + address;
    }
}
