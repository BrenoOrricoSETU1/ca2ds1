package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class HomePageController {
    @FXML
    private BorderPane mainPane;

    // Load a new page into the centre of the main window
    private void loadPage(String filename) {
        try {
            Parent page = FXMLLoader.load(getClass().getResource(filename));

            mainPane.setCenter(page);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Open add auction lot screen
    @FXML
    private void openAddAuctionLot(){
        loadPage("addAuctionLot.fxml");
    }

    //Open search lot screen and so on...
    @FXML
    private void openSearchAuctionLot(){
        loadPage("SearchLot.fxml");
    }

    @FXML
    private void openViewAuctionLot(){
        loadPage("auctionLotDetails.fxml");
    }

    @FXML
    private void openAddBidder(){
        loadPage("addBidder.fxml");
    }

    @FXML
    private void openSearchBidder(){
        loadPage("searchBidder.fxml");
    }

    @FXML
    private void openViewBidders(){
        loadPage("bidderDetails.fxml");
    }

    @FXML
    private void openMakeABid(){
        loadPage("addBid.fxml");
    }

    @FXML
    private void openLotDetails(){
        loadPage("auctionLotDetails.fxml");
    }

    @FXML
    private void exit(){
        System.exit(0);
    }

    @FXML
    private void save(){

    }

    @FXML
    private void load(){

    }
}
