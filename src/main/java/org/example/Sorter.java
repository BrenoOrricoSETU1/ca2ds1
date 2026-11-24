package org.example;
//SORRY THIS IS A MESS
public class Sorter {

    public static CustomLinkedList<AuctionLot> sortLots(CustomLinkedList<AuctionLot> list, String sortBy, boolean ascending) {
        AuctionLot[] array = new AuctionLot[list.size()];
        int count = 0;
        for (AuctionLot lot : list) {
            array[count] = lot;
            count++;
        }

        for (int i = 0; i < array.length - 1; i++) {
            int chosen = i;
            for (int j = i + 1; j < array.length; j++) {
                int result = compareLots(array[j], array[chosen], sortBy);
                if (!ascending) {
                    result = -result;
                }
                if (result < 0) {
                    chosen = j;
                }
            }
            AuctionLot temp = array[i];
            array[i] = array[chosen];
            array[chosen] = temp;
        }

        CustomLinkedList<AuctionLot> sorted = new CustomLinkedList<>();
        for (int i = 0; i < array.length; i++) {
            sorted.add(array[i]);
        }
        return sorted;
    }

    private static int compareLots(AuctionLot a, AuctionLot b, String sortBy) {
        if (sortBy.equals("year")) {
            return a.getYear() - b.getYear();
        } else if (sortBy.equals("type")) {
            return a.getType().compareToIgnoreCase(b.getType());
        } else if (sortBy.equals("price")) {
            if (a.getPrice() < b.getPrice()) {
                return -1;
            } else if (a.getPrice() > b.getPrice()) {
                return 1;
            }
            return 0;
        } else {
            return a.getTitle().compareToIgnoreCase(b.getTitle());
        }
    }

    public static CustomLinkedList<Bidder> sortBidders(CustomLinkedList<Bidder> list, String sortBy, boolean ascending) {
        Bidder[] array = new Bidder[list.size()];
        int count = 0;
        for (Bidder bidder : list) {
            array[count] = bidder;
            count++;
        }

        for (int i = 0; i < array.length - 1; i++) {
            int chosen = i;
            for (int j = i + 1; j < array.length; j++) {
                int result = compareBidders(array[j], array[chosen], sortBy);
                if (!ascending) {
                    result = -result;
                }
                if (result < 0) {
                    chosen = j;
                }
            }
            Bidder temp = array[i];
            array[i] = array[chosen];
            array[chosen] = temp;
        }

        CustomLinkedList<Bidder> sorted = new CustomLinkedList<>();
        for (int i = 0; i < array.length; i++) {
            sorted.add(array[i]);
        }
        return sorted;
    }

    private static int compareBidders(Bidder a, Bidder b, String sortBy) {
        if (sortBy.equals("address")) {
            return a.getAddress().compareToIgnoreCase(b.getAddress());
        } else {
            return a.getName().compareToIgnoreCase(b.getName());
        }
    }

    public static CustomLinkedList<Bid> sortBidsByAmountDescending(CustomLinkedList<Bid> list) {
        Bid[] array = new Bid[list.size()];
        int count = 0;
        for (Bid bid : list) {
            array[count] = bid;
            count++;
        }

        for (int i = 1; i < array.length; i++) {
            Bid current = array[i];
            int j = i - 1;
            while (j >= 0 && array[j].getAmount() < current.getAmount()) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = current;
        }

        CustomLinkedList<Bid> sorted = new CustomLinkedList<>();
        for (int i = 0; i < array.length; i++) {
            sorted.add(array[i]);
        }
        return sorted;
    }

    public static CustomLinkedList<Bid> sortBidsByDateAscending(CustomLinkedList<Bid> list) {
        Bid[] array = new Bid[list.size()];
        int count = 0;
        for (Bid bid : list) {
            array[count] = bid;
            count++;
        }

        for (int i = 1; i < array.length; i++) {
            Bid current = array[i];
            int j = i - 1;
            while (j >= 0 && array[j].getDateTime().isAfter(current.getDateTime())) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = current;
        }

        CustomLinkedList<Bid> sorted = new CustomLinkedList<>();
        for (int i = 0; i < array.length; i++) {
            sorted.add(array[i]);
        }
        return sorted;
    }
}
