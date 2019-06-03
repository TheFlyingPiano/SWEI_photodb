package fh.swei;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.drew.imaging.ImageProcessingException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class PrimaryController {


    public Text date;
    private List<String> result;
    private int picn;
    public ImageView mainImg;

    public ImageView getMainImg() {
        return mainImg;
    }



    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    public void initialize(){
        getPics();
    }

    //get all pictures in a List and display the first
    private void getPics(){

        try (Stream<Path> walk = Files.walk(Paths.get("pictures"))) {

            result = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());


            update(picn);

        } catch (IOException | ImageProcessingException e) {
            e.printStackTrace();
        }
    }

    //show the next picture
    @FXML
    private void nextPic() throws IOException, ImageProcessingException {
        picn++;
        update(picn);
        return;
    }


    //show the previous picture
    @FXML
    private void prevPic() throws IOException, ImageProcessingException {
        picn--;
        if(picn<0){
            picn=picn+result.size();

        }
        update(picn);

    }

    //update the picture and metadata
    private void update(int picn) throws ImageProcessingException, IOException {
        picn=(picn %result.size());
        Image img=new Image(new FileInputStream(result.get(picn)));
        mainImg.setImage(img);
        picMetaData.imageData(new File(result.get(picn)));
        picMetaData.imageDate(new File(result.get(picn)));
        date.setText(picMetaData.imageDate(new File(result.get(picn))).toString());
    }

    @FXML
    private void openFolder() throws IOException {
        Desktop.getDesktop().open(new File("pictures"));
    }



}
