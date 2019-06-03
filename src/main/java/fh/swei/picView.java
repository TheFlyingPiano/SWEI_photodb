package fh.swei;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class picView {

    File imagePath = new File("E:/Dokumente/FH/SWEI_SEM4/photodb/pictures/1.jpg");



    private void imageData() throws ImageProcessingException, IOException {

        Metadata metadata = ImageMetadataReader.readMetadata(imagePath);
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                System.out.println(tag);
            }
        }
    }


}

