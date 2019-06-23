package fh.swei;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SecondaryController {

    public ListView<String> photogrList;
    public Button addphot;
    public Button closeButton;
    public Button choosephot;

    public void setPicnum(int picnum) {
        this.picnum = picnum;
    }

    public int picnum;


    @FXML
    public void initialize() throws SQLException {
        listPhotographers();

    }
    @FXML
    private void switchToPrimary() throws IOException {
        Stage stage= (Stage) closeButton.getScene().getWindow();
        stage.close();

    }

    private void listPhotographers() throws SQLException {
        photogrList.getItems().clear();
       ResultSet rs= PhotographerDataAccess.getPhotogrs();

        while (rs.next()) {
            photogrList.getItems().add(rs.getString("name") + " " + rs.getString("surname"));
        }
    }

    @FXML
    private void addphotogr() throws SQLException {
        TextInputDialog dialog = new TextInputDialog("New Photographer");
        dialog.setTitle("Add Photographer");
        //dialog.setHeaderText("Please enter ");
        dialog.setContentText("Please enter Photographer name:");


// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
           // System.out.println("Your name: " + result.get());
            PhotographerDataAccess.addPhotographer(result.get());
        }
        listPhotographers();
    }

    @FXML
    public void choosePhot() throws SQLException {

        int sel= photogrList.getSelectionModel().getSelectedIndex();
        PicDataAccess.updatePicPhotographer(sel+1, picnum);
        Alert a=new Alert(Alert.AlertType.CONFIRMATION);
        a.setHeaderText("Photographer changed!!");
        a.show();
    }
}