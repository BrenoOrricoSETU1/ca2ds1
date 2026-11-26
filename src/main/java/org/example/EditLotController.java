package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class EditLotController {

    @FXML
    private ComboBox<String> selectLotComboBox;

    @FXML
    private TextField titleField;

    @FXML
    private TextField priceField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> selectTypeComboBox;

    @FXML
    private TextArea descriptionField;

    @FXML
    private ImageView lotImageView;

    @FXML
    private Label feedbackLabel;

    private String imagePath;

    @FXML
    private void initialize(){
        for(AuctionLot lot : App.auctionSystem.getAllLots()){
            selectLotComboBox.getItems().add(lot.getLotId() + " - " + lot.getTitle());
        }
        selectLotComboBox.getItems().addAll("Ceramics", "Furniture", "Art", "Jewellery", "Collectables", "Other");
    }

    //display details of the selected lot
    @FXML
    private void selectLot(){
        if(selectLotComboBox.getValue() == null){
            return;
        }

        //get id of the selected lot
        String lotId = selectLotComboBox.getValue().split(" - ")[0];

        //find lot using hashing method
        AuctionLot lot = App.auctionSystem.getLot(lotId);
        if(lot == null){
            return;
        }

        //display existing details of the lot in the fields
        titleField.setText(lot.getTitle());
        selectTypeComboBox.setValue(lot.getTitle());
        descriptionField.setText(lot.getDescription());
        priceField.setText(String.valueOf(lot.getPrice()));

        //concert stored year into date
        datePicker.setValue(LocalDate.of(lot.getYear(),1,1));

        //keep and display image of lot on right side
        imagePath = lot.getImageUrl();
        if(imagePath == null && !imagePath.isEmpty()){
            lotImageView.setImage(new Image(imagePath));
        } else{
            lotImageView.setImage(null);
        }
        feedbackLabel.setText("");
    }

    //allow user to change image
    @FXML
    private void chooseImage(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose new image");

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        File file = fileChooser.showOpenDialog(lotImageView.getScene().getWindow());
        if(file != null){
            imagePath = file.toURI().toString();
            lotImageView.setImage(new Image(imagePath));
        }
    }

    //check new details and edit lot
    @FXML
    private void editLot(){
        if(selectLotComboBox.getValue() == null){
            feedbackLabel.setText("Select a lot");
            return;
        }
        String lotId = selectLotComboBox.getValue().split(" - ")[0];
        String title = titleField.getText().trim();
        String type = selectTypeComboBox.getValue();
        LocalDate date = datePicker.getValue();
        String description = descriptionField.getText().trim();

        //check if all fields are filled
        if(title.isEmpty() ||
                type == null ||
                date == null ||
                description.isEmpty() ||
                priceField.getText().isEmpty()){
            feedbackLabel.setText("Please, fill all the fields");
            return;
        }
        try{
            //convert price into double
            double price = Double.parseDouble(priceField.getText());
            if(price <= 0){
                feedbackLabel.setText("Please, enter a valid price");
                return;
            }

            //get the year from date picker
            int year = date.getYear();

            //update existing lot
            if(App.auctionSystem.editLot(lotId, title, description, type, year, price, imagePath)){
                int index = selectLotComboBox.getItems().indexOf(lotId);
                selectLotComboBox.getItems().set(index, lotId + " - " + title);
                selectLotComboBox.setValue(lotId + " - " + title);
                feedbackLabel.setText("Successfully edited lot");
            } else{
                feedbackLabel.setText("Unable to edit lot");
            }
        } catch(NumberFormatException e){
            feedbackLabel.setText("Please, enter a valid price");
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

    @FXML
    public void saveChanges() {
    }
}
