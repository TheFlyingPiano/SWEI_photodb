package fh.swei;

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
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PrimaryController {

    private static List<String> result;
    private int picn;
    public ImageView mainImg;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    public void initialize(){
        getPics();
    }


    public  void getPics(){

        try (Stream<Path> walk = Files.walk(Paths.get("pictures"))) {

            result = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());


            Image img=new Image(new FileInputStream(result.get(picn)));
            System.out.println(result.get(picn));
            //result.forEach(System.out::println);
            mainImg.setImage(img);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void nextPic() throws FileNotFoundException {
        picn++;
        picn=(picn %result.size());
        Image img=new Image(new FileInputStream(result.get(picn)));
        mainImg.setImage(img);
    }

    @FXML
    private void prevPic() throws FileNotFoundException {
        picn--;
        if(picn<0){
            picn=picn+result.size();

        }
        picn=(picn %result.size());

        Image img=new Image(new FileInputStream(result.get(picn)));
        mainImg.setImage(img);

    }




}
