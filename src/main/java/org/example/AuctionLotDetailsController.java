package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;
import java.io.IOException;
import java.time.LocalDateTime;

public class AuctionLotDetailsController {

    @FXML
    private Button backButton;

    @FXML
    private Button editButton;

    @FXML
    private ComboBox <String> selectLotComboBox;

    @FXML
    private ImageView imageViewLot;

    @FXML
    private Label titleLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label feedbackLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label currentBidLabel;

    @FXML
    private Label yearLabel;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TableView <Bid> bidHistoryTable;

    @FXML
    private TableColumn <Bid, String> bidderIdColumn;

    @FXML
    private TableColumn <Bid, Double> amountColumn;

    @FXML
    private TableColumn <Bid, LocalDateTime> dateColumn;

    @FXML
    private void initialize() {
        for(AuctionLot lot : App.auctionSystem.getAllLots()){
            selectLotComboBox.getItems().add(lot.getLotId() + " - " + lot.getTitle());
        }
        bidderIdColumn.setCellValueFactory(new PropertyValueFactory<>("bidderId"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
    }

    @FXML
    private void selectLot(){
        if(selectLotComboBox.getValue() == null){
            return;
        }
        //Get Id of selected lot
        String lotId = selectLotComboBox.getValue().split(" - ")[0];
        //find the lost using hashing
        AuctionLot lot = App.auctionSystem.getLot(lotId);
        if(lot == null){
            return;
        }

        //Display the lot details
        titleLabel.setText(lot.getTitle());
        typeLabel.setText(lot.getType());
        yearLabel.setText(String.valueOf(lot.getYear()));
        priceLabel.setText(String.valueOf(lot.getPrice()));
        descriptionTextArea.setText(lot.getDescription());

        //Display image
        if(lot.getImageUrl() != null && !lot.getImageUrl().isEmpty()){
            imageViewLot.setImage(new Image(lot.getImageUrl()));
        } else{
            imageViewLot.setImage(null);
        }

        //show current bid
        Bid highestBid = lot.getHighestBid();
        if(highestBid == null){
            currentBidLabel.setText("No bids yet");
        } else{
            currentBidLabel.setText("€ " + highestBid.getAmount());
        }

        //show status
        if(lot.isSold()){
            statusLabel.setText("Sold for € " + lot.getFinalPrice() + " on " + lot.getFormattedSaleDate());
        } else{
            statusLabel.setText("Available");
        }

        bidHistoryTable.getItems().clear();
        for(Bid bid : lot.getBids()){
            bidHistoryTable.getItems().add(bid);
        }
    }

    //Sell the selected lot
    @FXML
    private void sellLot(){
        if(selectLotComboBox.getValue() == null){
            feedbackLabel.setText("Please select lot");
            return;
        }
        String lotId = selectLotComboBox.getValue().split(" - ")[0];
        if(App.auctionSystem.sellLot(lotId)){
            feedbackLabel.setText("Lot sold");
            //update lot details after selling
            selectLot();
        } else{
            feedbackLabel.setText("Unable to sell");
        }
    }

    //Withdraw lot
    @FXML
    private void withdrawLot(){
        if(selectLotComboBox.getValue() == null){
            feedbackLabel.setText("Please select lot");
            return;
        }

        String selectedLot = selectLotComboBox.getValue();
        String lotId = selectLotComboBox.getValue().split(" - ")[0];

        AuctionLot lot = App.auctionSystem.getLot(lotId);

        //Avoid sold lots to be withdrawn
        if(lot == null || lot.isSold()){
            feedbackLabel.setText("Unable to withdraw this lot");
            return;
        }
        if(App.auctionSystem.withdrawLot(lotId)){
            //remove the withdrawn lot from the select lot option
            selectLotComboBox.getItems().remove(selectedLot);
            clearDetails();
            feedbackLabel.setText("Lot withdrawn");
        } else{
            feedbackLabel.setText("Unable to withdraw");
        }
    }

    @FXML
    private void withdrawBid(){
        Bid bid = bidHistoryTable.getSelectionModel().getSelectedItem();
        if(bid == null){
            feedbackLabel.setText("Please, select a bid to withdraw");
            return;
        }
        //withdraw bid from the system
        if(App.auctionSystem.withdrawBid(bid.getBidId())){
            feedbackLabel.setText("Bid withdrawn");
            //update the bid history and current bid
            selectLot();
        } else{
            feedbackLabel.setText("Unable to withdraw the bid");
        }
    }

    private void clearDetails(){
        titleLabel.setText("");
        typeLabel.setText("");
        statusLabel.setText("");
        yearLabel.setText("");
        priceLabel.setText("");
        currentBidLabel.setText("");
        descriptionTextArea.clear();
        imageViewLot.setImage(null);
        bidHistoryTable.getItems().clear();
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
    private void openEditLot() {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("editLot.fxml")
            );

            Scene scene = new Scene(root);
            Stage stage = (Stage) editButton.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
