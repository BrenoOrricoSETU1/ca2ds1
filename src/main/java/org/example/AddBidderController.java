package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class AddBidderController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private Label feedbackLabel;

    @FXML
    private void addBidder(){
        //Updating the fields and trimming them to remove spaces in the end
        String name = nameField.getText().trim();
        String address = addressField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        if(name.isEmpty() ||
                address.isEmpty() ||
                email.isEmpty() ||
                phone.isEmpty() ||
                email.isEmpty()){

            feedbackLabel.setText("Please fill all the fields");
            return;
        }

        //Adding regular expressions for phone number
        if(!phone.matches("[+]?[0-9]{7,15}")){
            feedbackLabel.setText("Please, enter a valid phone number");
            return;
        }

        //Same for email
        if(!email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")){
            feedbackLabel.setText("Please, enter a valid email address");
            return;
        }

        //Adding Bidder to auction system
        Bidder bidder = App.auctionSystem.addBidder(name,address,email,phone);
        feedbackLabel.setText("Successfully added bidder. ID: " + bidder.getBidderId() + " - Name: " + bidder.getName());

        //Clear the fields after adding the bidder
        nameField.clear();
        addressField.clear();
        emailField.clear();
        phoneField.clear();

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
