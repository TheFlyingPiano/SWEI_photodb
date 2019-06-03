package fh.swei;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import java.io.File;
import java.io.IOException;
import java.util.Date;


public class picMetaData {




    //reads all metadata
     static void imageData(File imagePath) throws ImageProcessingException, IOException {
        Metadata metadata = ImageMetadataReader.readMetadata(imagePath);
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
          //      System.out.println(tag);
            }
        }
    }

    //reads the date
    static Date imageDate(File imagePath) throws ImageProcessingException, IOException {
        Metadata metadata = ImageMetadataReader.readMetadata(imagePath);
        ExifSubIFDDirectory directory
                = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

        return directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);

    }


}

