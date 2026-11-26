package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;

public class AddBidController {

    @FXML
    private ComboBox <String> selectLotComboBox;

    @FXML
    private ComboBox <String> selectBidderComboBox;

    @FXML
    private TextField newBidField;

    @FXML
    private Label currentBidLabel;

    @FXML
    private ImageView lotImage;

    @FXML
    private Label statusLabel;

    @FXML
    private Label feedbackLabel;

    @FXML
    private Label lotTitleLabel;

    @FXML
    private void initialize() {
        for(AuctionLot lot : App.auctionSystem.getAllLots()){
            selectLotComboBox.getItems().add(lot.getLotId());
        }
        for(Bidder bidder : App.auctionSystem.getAllBidders()){
            selectBidderComboBox.getItems().add(bidder.getBidderId() + " - " + bidder.getName());
        }
    }

    @FXML
    private void selectLot() {
        if(selectLotComboBox.getValue() == null){
            return;
        }

        //Get the ID of the selected lot
        String lotId = selectLotComboBox.getValue().split(" - ")[0];

        //Find the lot using hashing
        AuctionLot lot = App.auctionSystem.getLot(lotId);
        if(lot == null){
            return;
        }

        //Display the title of the lot
        lotTitleLabel.setText(lot.getTitle());
        //Display image of the lot
        if(lot.getImageUrl() != null && !lot.getImageUrl().isEmpty()){
            lotImage.setImage(new Image(lot.getImageUrl()));
        } else{
            lotImage.setImage(null);
        }

        //Get and display the highest bid
        Bid highestBid = lot.getHighestBid();
        if(highestBid == null){
            currentBidLabel.setText("No bids yet");
        } else{
            currentBidLabel.setText("€ " + highestBid.getAmount());
        }

        //Display lot status
        if(lot.isSold()){
            statusLabel.setText("Sold");
        } else{
            statusLabel.setText("Available");
        }
    }

    @FXML
    private void addBid(){
        //check if all fields are filled
        if(selectBidderComboBox.getValue() == null ||
                selectLotComboBox.getValue() == null ||
                selectBidderComboBox.getValue().isEmpty()){
                    feedbackLabel.setText("Please fill out all fields");
                    return;
        }

        //Get ID of lot and bidder
        String lotId = selectLotComboBox.getValue().split(" - ")[0];
        String bidderId = selectBidderComboBox.getValue().split(" - ")[0];

        try{
            //convert bid amount into a number
            double amount = Double.parseDouble(newBidField.getText());

            //check if amount is a valid number
            if(amount <= 0 || !Double.isFinite(amount)){
                feedbackLabel.setText("Invalid amount. Please enter a valid bid amount");
                return;
            }

            //Add bid to auction system
            Bid bid = App.auctionSystem.addBid(lotId, bidderId, amount);

            //Check if bid was added
            if(bid == null){
                feedbackLabel.setText("Bid rejected. Please, check Current Bid and Lot Status");
            } else{
                feedbackLabel.setText("Bid added successfully");

                //Update the current bid
                selectLot();
                newBidField.clear();
            }
        } catch(NumberFormatException e){
            feedbackLabel.setText("Invalid bid amount. Please, enter a amount greater than 0");
        }
    }

    @FXML
    private Button backButton;

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
}
