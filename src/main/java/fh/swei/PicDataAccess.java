package fh.swei;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.sql.*;
import java.util.List;

class PicDataAccess {

    private static List<Picture> Pictures=new ArrayList<>();

    static Connection conn = null;


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
        if (metadata.containsDirectoryOfType(ExifSubIFDDirectory.class)) {
            ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

            return directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
        }
        return new Date();
    }



    static Picture getPicture(String file) throws SQLException {
        file = file.split("\\\\")[1];
        String sql = "SELECT * FROM photodb.pictures WHERE filename = \"" + file + "\"";

       // if(Pictures.contains()){}



        ResultSet rs=DatabaseConnection.getData(sql);
        Picture tmp=new Picture();

        while(rs.next()){
            tmp.setFilename(rs.getString("filename"));
            tmp.setPhotographer(PhotographerDataAccess.getPhotogr(rs.getInt("photographer_ID")));
        }
        Pictures.add(tmp);
        return tmp;
    }


    public static List<Picture> getPictures() {
        return Pictures;
    }

    public static void setPictures(List<Picture> pictures) {
        Pictures = pictures;
    }
}

