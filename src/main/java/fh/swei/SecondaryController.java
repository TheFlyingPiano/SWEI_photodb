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

       ResultSet rs= PhotographerDataAccess.getPhotogrs();

        while (rs.next()) {
            photogrList.getItems().add(     rs.getString("name") + " " + rs.getString("surname"));
        }
    }

    @FXML
    private void addphotogr() throws SQLException {
        TextInputDialog dialog = new TextInputDialog("New PhotographerDataAccess");
        dialog.setTitle("Add PhotographerDataAccess");
        //dialog.setHeaderText("Please enter ");
        dialog.setContentText("Please enter PhotographerDataAccess name:");
     //   dialog.setContentText("Please enter your surname:");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
           // System.out.println("Your name: " + result.get());
            PhotographerDataAccess.addPhotographer(result.get());
        }
        listPhotographers();
    }


}