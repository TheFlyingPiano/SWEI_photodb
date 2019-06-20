package fh.swei;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import com.drew.imaging.ImageProcessingException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PrimaryController {


    public Text date;
    public ImageView prev1;
    public ImageView prev2;
    public ImageView prev3;
    public Text photographer;
    public Text filename;
    public ListView metadatalist;
    private List<String> result=new ArrayList<>();
    private int picn;
    public ImageView mainImg;

    public List<Picture> pics=new ArrayList<>();


    @FXML
    private void switchToSecondary() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("secondary.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Photographers");
            stage.setScene(new Scene(root1));
            SecondaryController secondary=fxmlLoader.<SecondaryController>getController();
            secondary.setPicnum(picn);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // App.setRoot("secondary");
    }

    @FXML
    public void initialize() {
        getPics();
    }

    //get all pictures in a List and display the first
    private void getPics() {


        try{
        pics =PicDataAccess.getPictures();
            for(Picture picture : pics){
            result.add("pictures\\"+picture.getFilename());
            }

            update(picn);
            updatePrev(picn);

        } catch (IOException | SQLException | ImageProcessingException e) {
            e.printStackTrace();
        }
    }

    //show the next picture
    @FXML
    private void nextPic() throws IOException, SQLException, ImageProcessingException {
        picn++;
        update(picn);
        updatePrev(picn);
    }


    //show the previous picture
    @FXML
    private void prevPic() throws IOException, SQLException, ImageProcessingException {
        picn--;
        if (picn < 0) {
            picn = picn + result.size();

        }
        update(picn);
        updatePrev(picn);

    }

    //update the preview
    private void updatePrev(int prevn) throws FileNotFoundException {
        prevn = prevn % result.size();
        int i1 = prevn;
        int i3 = prevn;
        i1--;
        i3++;
        i1 = Math.floorMod(i1, result.size());
        i3 = (i3 % result.size());
        prev1.setImage(new Image(new FileInputStream(result.get(i1))));
        //prev1.setImage(new Image(new FileInputStream(PicDataAccess.getPictures().get(i1).getFilename())));
        prev2.setImage(new Image(new FileInputStream(result.get(prevn))));
        prev3.setImage(new Image(new FileInputStream(result.get(i3))));
    }

    //update the picture and metadata
    private void update(int picn) throws IOException, SQLException, ImageProcessingException {
        picn = (picn % result.size());
        Image img = new Image(new FileInputStream(result.get(picn)));
        mainImg.setImage(img);

        PicDataAccess.imageDate(new File(result.get(picn)));
        date.setText(PicDataAccess.imageDate(new File(result.get(picn))).toString());
        // filename.setText(result.get(picn));
        metadatalist.getItems().addAll(MetadataExtractor.imageData(new File(result.get(picn))));
        Picture pic = PicDataAccess.getPicture(result.get(picn));


            filename.setText(pic.getFilename());
            photographer.setText(pic.getPhotographer());
       // MetadataExtractor.comparePics();
    }


    @FXML
    private void openFolder() throws IOException {
        Desktop.getDesktop().open(new File("pictures"));
    }


}
