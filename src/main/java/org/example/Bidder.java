package org.example;

public class Bidder {
    private String bidderId;
    private String name;
    private String address;
    private String phone;
    private String email;

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

    @Override
    public String toString() {

    }
}
