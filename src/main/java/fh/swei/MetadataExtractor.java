package fh.swei;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MetadataExtractor {



    static void comparePics() throws SQLException {
        List<String> LocalPics=new ArrayList<>();
        List<Picture> DBPics=PicDataAccess.getPictures();
        List<String> FoundPics=new ArrayList<>();

        try (Stream<Path> walk = Files.walk(Paths.get("pictures"))) {

            LocalPics = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }



            for (String localPic : LocalPics) {
               for (Picture dbPic : DBPics) {
                String file = localPic.split("\\\\")[1];
                if (!dbPic.getFilename().contains(file)) {
                    System.out.println("DB FILENAME: " + dbPic.getFilename());
                    System.out.println("Local FILENAME: " + file);
                }

                System.out.println("PIC FOUND "+dbPic.getFilename());
            }
        }


    }


    static void imageData(File imagePath) throws ImageProcessingException, IOException {
        Metadata metadata = ImageMetadataReader.readMetadata(imagePath);

        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                //      System.out.println(tag);
            }
        }

        if (metadata.containsDirectoryOfType(ExifSubIFDDirectory.class)) {
            ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

            Date date= directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
        }


    }
}
