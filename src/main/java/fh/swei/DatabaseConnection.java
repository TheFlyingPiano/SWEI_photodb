package fh.swei;

import java.sql.*;

class DatabaseConnection {

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


    static ResultSet getData(String sql) throws SQLException {

        Statement stmt;
        ResultSet rs;

        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);

        return rs;

    }

}
