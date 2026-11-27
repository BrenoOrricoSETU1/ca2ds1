package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class EditBidderController {

    @FXML
    private ComboBox <String> selectBidderComboBox;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneField;

    @FXML
    private Label feedbackLabel;

    @FXML
    private Button backButton;

    @FXML
    private void initialize() {
        for(Bidder bidder : App.auctionSystem.getAllBidders()) {
            selectBidderComboBox.getItems().add(bidder.getBidderId() + " - " + bidder.getName());
        }
    }

    @FXML
    private void selectBidder(){
        if(selectBidderComboBox.getValue() == null){
            return;
        }

        //get id of selected bidder
        String bidderId =  selectBidderComboBox.getValue().split(" - ")[0];

        //find bidder using hash
        Bidder bidder = App.auctionSystem.getBidder(bidderId);
        if(bidder == null){
            return;
        }

        //display the bidder information on the fields
        nameField.setText(bidder.getName());
        emailField.setText(bidder.getEmail());
        addressField.setText(bidder.getAddress());
        phoneField.setText(bidder.getPhone());
        //not show anything on feedback label yet
        feedbackLabel.setText("");
    }

    @FXML
    private void editBidder(){
        if(selectBidderComboBox.getValue() == null){
            feedbackLabel.setText("Please select a Bidder");
            return;
        }
        String bidderId = selectBidderComboBox.getValue().split(" - ")[0];
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String address = addressField.getText().trim();
        String phone = phoneField.getText().trim();

        //check if all fields are filled
        if(name.isEmpty() || email.isEmpty() || address.isEmpty() || phone.isEmpty()){
            feedbackLabel.setText("Please fill all the fields");
            return;
        }

        //validate phone number
        if(!phone.matches("[+]?[0-9]{7,15}")){
            feedbackLabel.setText("Invalid phone number");
            return;
        }

        //validate email using regular expressions
        if(!email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")){
            feedbackLabel.setText("Invalid Email. Correct format: example@example.com");
            return;
        }

        if(App.auctionSystem.editBidder(bidderId, name, address, phone, email)){
            int index = selectBidderComboBox.getSelectionModel().getSelectedIndex();

            selectBidderComboBox.getItems().set(index, bidderId + " - " + name);
            selectBidderComboBox.setValue(bidderId + " - " + name);

            feedbackLabel.setText("Successfully Edited Bidder");
        } else{
            feedbackLabel.setText("Unable to Edit Bidder");
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
}
