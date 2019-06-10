package fh.swei;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Photographer {

    static ResultSet getPhotogrs() throws SQLException {
        Connection conn=picMetaData.connectDB();
        String Phot = null;
        Statement stmt = null;
        ResultSet rs = null;

        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM photographer");
        return rs;
    }



    static void addPhotographer(String FName) throws SQLException {
        String Name=FName.split(" ")[0];
        String SName=FName.split(" ")[1];
        Connection conn=picMetaData.connectDB();
        Statement stmt = null;
        stmt = conn.createStatement();
        String sql="INSERT INTO photographer (name,surname) VALUES ('"+Name+"','"+SName+"')";
        stmt.executeUpdate(sql);

    }
}
