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
    public ImageView prev1;
    public ImageView prev2;
    public ImageView prev3;
    public Text photographer;
    public Text filename;
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
        picMetaData.connectDB();
    }

    //get all pictures in a List and display the first
    private void getPics(){

        try (Stream<Path> walk = Files.walk(Paths.get("pictures"))) {

            result = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());


            update(picn);
            updatePrev(picn);

        } catch (IOException | ImageProcessingException | SQLException e) {
            e.printStackTrace();
        }
    }

    //show the next picture
    @FXML
    private void nextPic() throws IOException, ImageProcessingException, SQLException {
        picn++;
        update(picn);
        updatePrev(picn);
    }


    //show the previous picture
    @FXML
    private void prevPic() throws IOException, ImageProcessingException, SQLException {
        picn--;
        if(picn<0){
            picn=picn+result.size();

        }
        update(picn);
        updatePrev(picn);

    }

    //update the preview
    private void updatePrev(int prevn) throws FileNotFoundException {
        prevn=prevn%result.size();
        int i1=prevn;
        int i3=prevn;
        i1--;
        i3++;
        i1= Math.floorMod(i1,result.size());
        i3=(i3 %result.size());
        prev1.setImage(new Image(new FileInputStream(result.get(i1))));
        prev2.setImage(new Image(new FileInputStream(result.get(prevn))));
        prev3.setImage(new Image(new FileInputStream(result.get(i3))));
    }

    //update the picture and metadata
    private void update(int picn) throws ImageProcessingException, IOException, SQLException {
        picn=(picn %result.size());
        Image img=new Image(new FileInputStream(result.get(picn)));
        mainImg.setImage(img);

        picMetaData.imageDate(new File(result.get(picn)));
        date.setText(picMetaData.imageDate(new File(result.get(picn))).toString());
       // filename.setText(result.get(picn));

        ResultSet picdata=picMetaData.getPicture(result.get(picn));
        while (picdata.next()) {
            filename.setText(picdata.getString("filename"));
            String phot=picMetaData.getPhotogr(picdata.getInt("photographer_ID"));
            photographer.setText(phot);
        }
    }

    @FXML
    private void openFolder() throws IOException {
        Desktop.getDesktop().open(new File("pictures"));
    }



}
