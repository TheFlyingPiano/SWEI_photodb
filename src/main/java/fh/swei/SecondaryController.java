package fh.swei;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;

public class SecondaryController {

    public ListView<String> photogrList;
    public Button addphot;

    @FXML
    public void initialize() throws SQLException {
        listPhotographers();

    }
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    private void listPhotographers() throws SQLException {

       ResultSet rs= Photographer.getPhotogrs();

        while (rs.next()) {
            photogrList.getItems().add(     rs.getString("name") + " " + rs.getString("surname"));
        }
    }

    @FXML
    private void addphotogr() throws SQLException {
        TextInputDialog dialog = new TextInputDialog("New Photographer");
        dialog.setTitle("Add Photographer");
        //dialog.setHeaderText("Please enter ");
        dialog.setContentText("Please enter Photographer name:");
     //   dialog.setContentText("Please enter your surname:");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
           // System.out.println("Your name: " + result.get());
            Photographer.addPhotographer(result.get());
        }
        listPhotographers();
    }


}