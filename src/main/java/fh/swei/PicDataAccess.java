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



    //reads the date
    static Date imageDate(File imagePath) throws ImageProcessingException, IOException {
        Metadata metadata = ImageMetadataReader.readMetadata(imagePath);
        if (metadata.containsDirectoryOfType(ExifSubIFDDirectory.class)) {
            ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

            return directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
        }
        return new Date();
    }

    static void deletePicture(int id){
        String sql="DELETE FROM `photodb`.`pictures` WHERE idpictures="+id+";";
    }

    static void addPicture(String Filename) throws SQLException {
        String sql="INSERT INTO `photodb`.`pictures` (`filename`) VALUES(\""+Filename+"\");";
        //System.out.println(sql);
        DatabaseConnection.updateData(sql);

    }

    static Picture getPicture(String file) throws SQLException {
        file = file.split("\\\\")[1];
        String sql = "SELECT * FROM photodb.pictures WHERE filename = \"" + file + "\"";


        ResultSet rs=DatabaseConnection.getData(sql);
        Picture tmp=new Picture();

        while(rs.next()){
            tmp.setFilename(rs.getString("filename"));
            tmp.setPhotographer(PhotographerDataAccess.getPhotogr(rs.getInt("photographer_ID")));
        }


        return tmp;
    }

    static void updatePicPhotographer(int sel, int pic) throws SQLException {

            String sql="UPDATE `photodb`.`pictures` SET `photographer_ID` = "+sel+" WHERE idpictures = "+ pic+";";

        DatabaseConnection.updateData(sql);
    }

    static List<Picture> getPictures() throws SQLException {
        if(Pictures.isEmpty()) {
            String sql = "SELECT * FROM photodb.pictures";

            ResultSet rs = DatabaseConnection.getData(sql);


            while (rs.next()) {
                Picture tmp = new Picture();
                tmp.setFilename(rs.getString("filename"));
                tmp.setPhotographer(PhotographerDataAccess.getPhotogr(rs.getInt("photographer_ID")));
                tmp.setID(rs.getInt("idpictures"));
                Pictures.add(tmp);
            }
            return Pictures;
        }
        else
        return Pictures;
    }


    public static void setPictures(List<Picture> pictures) {
        Pictures = pictures;
    }
}

