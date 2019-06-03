package fh.swei;

import java.io.File;
import java.io.IOException;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import javafx.fxml.FXML;

public class PrimaryController {

    File imagePath = new File("E:/Dokumente/FH/SWEI_SEM4/photodb/pictures/1.jpg");

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
        try {
            imageViwer();
        } catch (ImageProcessingException e) {
            e.printStackTrace();
        }
    }


    private void imageViwer() throws ImageProcessingException, IOException {

        Metadata metadata = ImageMetadataReader.readMetadata(imagePath);
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                System.out.println(tag);
            }
        }
    }


}
