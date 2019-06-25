package fh.swei;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import com.drew.imaging.ImageProcessingException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser;

public class PrimaryController {


    public ImageView prev1;
    public ImageView prev2;
    public ImageView prev3;
    public Text photographer;
    public Text filename;
    public ListView EXIFMeta;
    public MenuItem addpic;
    public AnchorPane ap;
    public ListView IPTCMeta;
    public Button searchbutton;
    public TextField searchbar;
    private List<String> result=new ArrayList<>();
    private int picn;
    public ImageView mainImg;
    private Picture pic;

    private List<Picture> pics=new ArrayList<>();


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
            SecondaryController secondary= fxmlLoader.<SecondaryController>getController();
            secondary.setPicnum(pics.get(picn).getID());
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

    @FXML
    private void addPic() throws SQLException {
        String Filename=null;
        Desktop desktop = Desktop.getDesktop();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));

        Stage stage = (Stage) ap.getScene().getWindow();
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
          Filename =file.getName();
        }
        if (Filename != null && !Filename.isEmpty()) {
            PicDataAccess.addPicture(Filename);
            getPics();
        }
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
        prev2.setImage(new Image(new FileInputStream(result.get(prevn))));
        prev3.setImage(new Image(new FileInputStream(result.get(i3))));
    }

    //update the picture and metadata
    private void update(int picn) throws IOException, SQLException, ImageProcessingException {
        EXIFMeta.getItems().clear();
        IPTCMeta.getItems().clear();
        picn = (picn % result.size());
        Image img = new Image(new FileInputStream(result.get(picn)));
        mainImg.setImage(img);


        pics.get(picn).setIptcMetadata(IPTC.getIPTC(pics.get(picn).getID()));
        pics.get(picn).setExifMetadata(MetadataExtractor.Metadata(new File(result.get(picn)),true));
        EXIFMeta.getItems().addAll(pics.get(picn).getExifMetadata());
        IPTCMeta.getItems().addAll(pics.get(picn).getIptcMetadata());

        Picture pic = PicDataAccess.getPicture(result.get(picn));


            filename.setText(pic.getFilename());
            photographer.setText(pic.getPhotographer());
       // MetadataExtractor.comparePics();
    }


    @FXML
    private void openFolder() throws IOException {
        Desktop.getDesktop().open(new File("pictures"));
    }

    @FXML
    public void editIPTC() throws SQLException, IOException, ImageProcessingException {
        String value=null;
        if(IPTCMeta.getSelectionModel().getSelectedItems().isEmpty()){
            Alert a=new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("Select item first!");
            a.show();
            return;
        }

       String label= IPTCMeta.getSelectionModel().getSelectedItem().toString();

       label=label.split(":")[0];

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Change Info");
        dialog.setContentText("Please enter Information");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
             value=result.get();
        }

        IPTC.setIPTC(pics.get(picn).getID(),label,value);
        update(picn);

    }

    @FXML
    public void search() throws ImageProcessingException, SQLException, IOException {
        String search=searchbar.getText();
        List<String> res=new ArrayList<>();
        List<Integer> nums=new ArrayList<>();
        int i=0;
        for(Picture pic: pics){
            if(pic.getFilename().contains(search)){
             //   System.out.println("Name: "+pic.getFilename());
                res.add("Name: "+pic.getFilename());

                nums.add(i);
        }
            else if(pic.getPhotographer()!=null&&pic.getPhotographer().contains(search)){
                res.add("Photographer: "+pic.getPhotographer()+" Pic: "+pic.getFilename());
                nums.add(i);
                //System.out.println("Photographer: "+pic.getPhotographer() );
            }

            i++;
        }
        final int[] sel = new int[1];
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        ListView<String> result=new ListView<>();
        Button conf=new Button("Confirm");
        result.getItems().addAll(res);
        VBox dialogVbox = new VBox(20);


        dialogVbox.getChildren().add(result);
        dialogVbox.getChildren().add(conf);
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
        conf.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                sel[0] =result.getSelectionModel().getSelectedIndex();
               int get=nums.get(sel[0]);


                Stage stage= (Stage) conf.getScene().getWindow();
                stage.close();
                picn=get;
                try {
                    update(get);
                } catch (IOException | SQLException | ImageProcessingException e) {
                    e.printStackTrace();
                }
                try {
                    updatePrev(get);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
    }




    public void singleReport() throws IOException {
        Reporting.singleReport(pics.get(picn));
    }

    public void about() {
        Alert a=new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText("This application is bad and i should feel bad.");
        a.show();
    }
}
