package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;

public class AddAuctionLotController {

    @FXML
    private TextField titleField;

    @FXML
    private ComboBox<String> selectTypeChoiceBox;

    @FXML
    private DatePicker selectDatePicker;

    @FXML
    private TextField priceField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private ImageView lotImageView;

    @FXML
    private Label feedbackLabel;

    private String imagePath;

    @FXML
    private Button backButton;

    //Adding type to auction type box
    @FXML
    private void initialize() {
        selectTypeChoiceBox.getItems().addAll("Ceramics", "Furniture", "Art", "Jewellery", "Collectables", "Other");
    }

    //Select an image from device instead of copy and past URL, more user-friendly
    @FXML
    private void chooseImage(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an Image");

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        File file = fileChooser.showOpenDialog(lotImageView.getScene().getWindow());
        if(file != null){
            imagePath = file.toURI().toString();
            Image image = new Image(imagePath);
            lotImageView.setImage(image);
        }
    }

    //Validate the details entered by user and adding the lot
    @FXML
    public void addLot(ActionEvent actionEvent) {
        String title = titleField.getText();
        String type = selectTypeChoiceBox.getValue();
        LocalDate date = selectDatePicker.getValue();
        String description = descriptionField.getText();
        //Check that all fields are filled if not won't add
        if(title.isEmpty() ||
            type == null ||
            date == null ||
            description.isEmpty() ||
            priceField.getText().isEmpty() ||
            imagePath == null){
                feedbackLabel.setText("Please fill all the fields");
                return;
        }
        //Adding Lot
        try{
            //Convert the price entered into a number
            double price = Double.parseDouble(priceField.getText());

            //Check if price is valid
            if(price <= 0){
                feedbackLabel.setText("Invalid price. Price must be greater than zero.");
                return;
                }

            //Get the year from the selected date
            int year = date.getYear();

            App.auctionSystem.addLot(title, description, type, year, price, imagePath);

            feedbackLabel.setText("Lot added successfully");

        }catch (NumberFormatException e){
            feedbackLabel.setText("Please enter a valid price");
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
