package fh.swei;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PhotographerDataAccess {

    static ResultSet getPhotogrs() throws SQLException {
        String sql="SELECT * FROM photographer";
        return DatabaseConnection.getData(sql);
    }

    static String getPhotogr(int phoID) throws SQLException {
        String Phot = null;


        ResultSet rs=DatabaseConnection.getData("SELECT * FROM photographer WHERE idphotographer = " + phoID);

        while (rs.next()) {
            Phot = rs.getString("name") + " " + rs.getString("surname");
        }
        return Phot;

    }


    static void addPhotographer(String FName) throws SQLException {
        String Name=FName.split(" ")[0];
        String SName=FName.split(" ")[1];
        String sql="INSERT INTO photographer (name,surname) VALUES ('"+Name+"','"+SName+"')";
        DatabaseConnection.updateData(sql);

    }
}
