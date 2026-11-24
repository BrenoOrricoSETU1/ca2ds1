package org.example;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AuctionSystem implements Serializable {

    private HashTable<AuctionLot> lots = new HashTable<>();
    private HashTable<Bidder> bidders = new HashTable<>();
    private HashTable<Bid> bids = new HashTable<>();
    private HashTable<CustomLinkedList<AuctionLot>> lotsByType = new HashTable<>();

    private int nextLotNumber = 1;
    private int nextBidderNumber = 1;
    private int nextBidNumber = 1;

    public AuctionLot addLot(String title, String description, String type, int year, double price, String imageUrl) {
        String lotId = "LOT" + nextLotNumber;
        nextLotNumber++;

        AuctionLot lot = new AuctionLot(lotId, title, description, type, year, price, imageUrl);
        lots.put(lotId, lot);
        addToTypeTable(lot);
        return lot;
    }

    public Bidder addBidder(String name, String address, String phone, String email) {
        String bidderId = "BIDDER" + nextBidderNumber;
        nextBidderNumber++;

        Bidder bidder = new Bidder(bidderId, name, address, phone, email);
        bidders.put(bidderId, bidder);
        return bidder;
    }

    public Bid addBid(String lotId, String bidderId, double amount) {
        AuctionLot lot = getLot(lotId);
        Bidder bidder = getBidder(bidderId);

        if (lot == null || bidder == null) {
            return null;
        }
        if (!lot.canAcceptBid(amount)) {
            return null;
        }

        String bidId = "BID" + nextBidNumber;
        nextBidNumber++;

        Bid bid = new Bid(bidId, lotId, bidderId, amount, LocalDateTime.now());
        lot.addBid(bid);
        bidder.addBid(bid);
        bids.put(bidId, bid);
        return bid;
    }

    public AuctionLot getLot(String lotId) {
        if (lotId == null) {
            return null;
        }
        return lots.get(lotId);
    }

    public Bidder getBidder(String bidderId) {
        if (bidderId == null) {
            return null;
        }
        return bidders.get(bidderId);
    }

    public Bid getBid(String bidId) {
        if (bidId == null) {
            return null;
        }
        return bids.get(bidId);
    }

    public AuctionLot getLotForBid(Bid bid) {
        return getLot(bid.getLotId());
    }

    public Bidder getBidderForBid(Bid bid) {
        return getBidder(bid.getBidderId());
    }

    public CustomLinkedList<AuctionLot> getLotsByType(String type) {
        if (type == null) {
            return new CustomLinkedList<>();
        }
        CustomLinkedList<AuctionLot> list = lotsByType.get(typeKey(type));
        if (list == null) {
            return new CustomLinkedList<>();
        }
        return list;
    }

    public CustomLinkedList<AuctionLot> getAllLots() {
        return lots.values();
    }

    public CustomLinkedList<Bidder> getAllBidders() {
        return bidders.values();
    }

    public boolean sellLot(String lotId) {
        AuctionLot lot = getLot(lotId);
        if (lot == null) {
            return false;
        }
        return lot.sell(LocalDateTime.now()) != null;
    }

    public boolean withdrawLot(String lotId) {
        AuctionLot lot = getLot(lotId);
        if (lot == null) {
            return false;
        }

        for (Bid bid : lot.getBids()) {
            Bidder bidder = getBidder(bid.getBidderId());
            if (bidder != null) {
                bidder.removeBid(bid);
            }
            bids.remove(bid.getBidId());
        }

        removeFromTypeTable(lot);
        lots.remove(lotId);
        return true;
    }

    public boolean withdrawBid(String bidId) {
        Bid bid = getBid(bidId);
        if (bid == null) {
            return false;
        }

        AuctionLot lot = getLot(bid.getLotId());
        if (lot != null && !lot.removeBid(bid)) {
            return false;
        }

        Bidder bidder = getBidder(bid.getBidderId());
        if (bidder != null) {
            bidder.removeBid(bid);
        }

        bids.remove(bidId);
        return true;
    }

    public boolean editLot(String lotId, String title, String description, String type, int year, double price, String imageUrl) {
        AuctionLot lot = getLot(lotId);
        if (lot == null) {
            return false;
        }

        removeFromTypeTable(lot);
        lot.setTitle(title);
        lot.setDescription(description);
        lot.setType(type);
        lot.setYear(year);
        lot.setPrice(price);
        lot.setImageUrl(imageUrl);
        addToTypeTable(lot);
        return true;
    }

    public boolean editBidder(String bidderId, String name, String address, String phone, String email) {
        Bidder bidder = getBidder(bidderId);
        if (bidder == null) {
            return false;
        }

        bidder.setName(name);
        bidder.setAddress(address);
        bidder.setPhone(phone);
        bidder.setEmail(email);
        return true;
    }

    public int getNumberOfLots() {
        return lots.size();
    }

    public int getNumberOfBidders() {
        return bidders.size();
    }

    public int getNumberOfBids() {
        return bids.size();
    }

    private void addToTypeTable(AuctionLot lot) {
        String key = typeKey(lot.getType());
        CustomLinkedList<AuctionLot> list = lotsByType.get(key);
        if (list == null) {
            list = new CustomLinkedList<>();
            lotsByType.put(key, list);
        }
        list.add(lot);
    }

    private void removeFromTypeTable(AuctionLot lot) {
        String key = typeKey(lot.getType());
        CustomLinkedList<AuctionLot> list = lotsByType.get(key);
        if (list == null) {
            return;
        }
        list.remove(lot);
        if (list.isEmpty()) {
            lotsByType.remove(key);
        }
    }

    private String typeKey(String type) {
        if (type == null) {
            return "";
        }
        return type.trim().toLowerCase();
    }
}
