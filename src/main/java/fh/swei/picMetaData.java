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
import java.sql.*;

class picMetaData {

    static Connection conn = null;

    static Connection connectDB() {

        if (conn != null) {
            return conn;
        }
        try {

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/photodb?" +
                    "useTimezone=true&serverTimezone=UTC", "FH_SWEI", "swei");

        } catch (SQLException ex) {

            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return conn;
    }

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

    static String getPhotogr(int phoID) throws SQLException {
        String Phot = null;
        Statement stmt = null;
        ResultSet rs = null;

        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM photographer WHERE idphotographer = " + phoID);


        while (rs.next()) {
            Phot = rs.getString("name") + " " + rs.getString("surname");
        }
        return Phot;

    }

    static ResultSet getPicture(String file) throws SQLException {
        conn = connectDB();
        file = file.split("\\\\")[1];
        Statement stmt = null;
        ResultSet rs = null;

        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM photodb.pictures WHERE filename = \"" + file + "\"");

        return rs;
    }


}

