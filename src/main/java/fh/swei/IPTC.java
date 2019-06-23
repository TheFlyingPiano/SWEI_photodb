package fh.swei;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class IPTC {
    static List<String> getIPTC(int picID) throws SQLException {
        List<String> iptcList = new ArrayList<>();
        String sql="SELECT * FROM photodb.iptc WHERE pic_ID ="+ picID+";";
        ResultSet rs = DatabaseConnection.getData(sql);
        ResultSetMetaData rsmd=rs.getMetaData();
        int columnCount = rsmd.getColumnCount();

        while(rs.next()) {
           for (int i = 1; i <= columnCount; i++) {
                iptcList.add(rsmd.getColumnLabel(i) + ": "+ rs.getString(i));

            }
        }
        iptcList.remove(0);
        return iptcList;
    }

    static void setIPTC(int picID, String label, String value) throws SQLException {
        String sql="UPDATE photodb.iptc SET "+label+" = '"+value+"' WHERE pic_ID = "+picID+";";
        System.out.println(sql);
        DatabaseConnection.updateData(sql);


    }
}
