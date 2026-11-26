package org.example;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDateTime;

public class BidderDetailsController {

    @FXML
    private Button backButton;

    @FXML
    private Button editButton;

    @FXML
    private ComboBox<String> selectedBidderComboBox;

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private TableView <Bid> bidHistoryTable;

    @FXML
    private TableColumn<Bid, String> lotIdColumn;

    @FXML
    private TableColumn<Bid, String> lotTitleColumn;

    @FXML
    private TableColumn<Bid, Double> amountColumn;

    @FXML
    private TableColumn<Bid, LocalDateTime> dateTimeColumn;

    @FXML
    private TableColumn<Bid, String> resultColumn;

    @FXML
    private void initialize() {
        for(Bidder bidder : App.auctionSystem.getAllBidders()){
            selectedBidderComboBox.getItems().add(bidder.getName() + " - ID: " + bidder.getBidderId());
        }

        lotIdColumn.setCellValueFactory(new PropertyValueFactory<>("lotId"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("formattedDateTime"));
        //find the title of lot using its id
        lotTitleColumn.setCellValueFactory(cellData -> {
            Bid bid = cellData.getValue();
            AuctionLot lot = App.auctionSystem.getLot(bid.getLotId());
            if(lot == null){
                return new SimpleStringProperty("Lot not found");
            }
            return new SimpleStringProperty(lot.getTitle());
        });

        //display if bid was accepted
        resultColumn.setCellValueFactory(cellData ->{
            Bid bid = cellData.getValue();
            if(bid.isAccepted()){
                return new SimpleStringProperty("Winning Bid!");
            }
            return new SimpleStringProperty("");
        });
    }

    @FXML
    private void selectBidder(){
        if(selectedBidderComboBox.getItems() == null){
            return;
        }

        //get id of selected bidder
        String bidderId = selectedBidderComboBox.getValue().split(" - ")[0];

        //find the bidder using auction system hashing
        Bidder bidder = App.auctionSystem.getBidder(bidderId);
        if(bidder == null){
            return;
        }

        nameField.setText(bidder.getName());
        addressField.setText(bidder.getAddress());
        phoneField.setText(bidder.getPhone());
        emailField.setText(bidder.getEmail());

        //clear previous bidder's history
        bidHistoryTable.getItems().clear();

        //display all bids made by the selected bidder
        for(Bid bid : bidder.getBids()){
            bidHistoryTable.getItems().add(bid);
        }
    }



    @FXML
    private void backToMain() {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("homePage.fxml")
            );

            Scene scene = new Scene(root);

            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void editBidder() {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("editBidder.fxml")
            );

            Scene scene = new Scene(root);

            Stage stage = (Stage) editButton.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
