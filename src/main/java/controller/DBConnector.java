package controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnector {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "test";
    private static final String CONN_STRING = "jdbc:mysql://localhost/turnering?serverTimezone=UTC";

    public Connection mysql;
    public Boolean connect(){

        mysql = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            mysql = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
            System.out.println("connection established!");

        } catch (SQLException ex) {
            System.err.println(ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mysql != null;

    }


    public ArrayList<String[]> runQuery(String sql){

        ArrayList<String[]> data = new ArrayList<String []>();
        try{

            Statement stmt = mysql.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(sql);

            if(rs != null){
                data = processData(rs);

            }
        }catch (SQLException e){

            System.out.println("fejl i sql");
        }
        return data;
    }

    private ArrayList<String[]> processData(ResultSet rs) {
        ArrayList<String[]> data = new ArrayList<String[]>();

        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            String table = rsmd.getTableName(2);

            int col_num = rsmd.getColumnCount();
            String[] row = null;

            int x = 1;
            while (rs.next()) {
                row = new String[col_num];
                row[1] = rs.getString("matchType");
                row[2] = Integer.toString(rs.getInt("tournamentID"));
                row[3] = rs.getDate("date").toString();
                row[4] = rs.getTime("time").toString();
                row[5] = Integer.toString(rs.getInt("team1ID"));
                row[6] = Integer.toString(rs.getInt("team2ID"));
                data.add(row);
                x++;
            }
        } catch (SQLException ex) {
            System.out.println("SQL error" + ex.getLocalizedMessage());
        }
        return data;
    }


}
