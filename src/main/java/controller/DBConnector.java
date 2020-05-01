package controller;

import java.sql.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tess
 */
public class DBConnector {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "test";
    private static final String CONN_STRING = "jdbc:mysql://localhost/turnering?serverTimezone=UTC";
    public Connection mysql;

    public DBConnector() {
    }

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
        System.out.println("Running query ");
        ArrayList<String[]> data = new ArrayList<String[]>();
        try{
            Statement stmt = mysql.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(sql);
            if(rs != null){
             data = processData(rs);
            }
        } catch (SQLException ex) {
            System.out.println("SQL error: "+ex.getLocalizedMessage());
        }
        return data;
    }


    public void runInsertQuery(String sql){
        try{
            PreparedStatement stmt = mysql.prepareStatement(sql, Statement. RETURN_GENERATED_KEYS );
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
        } catch (SQLException ex) {
            System.out.println("SQL error: "+ex.getLocalizedMessage());
        }
    }




    public  LinkedHashMap<Integer, int[][]> runMatchQuery(String sql,String oneEntity, String manyEntity1, String manyEntity2 ){
        LinkedHashMap<Integer, int[][]> data = new LinkedHashMap<>();
        int values[][];
        try{
            Statement stmt = mysql.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                int key;
                int index;

                if(data.containsKey(rs.getInt(oneEntity))){//Tjekker om entitieten (fx. Golden Eagles) allerede er i HashMap
                    values = data.get(rs.getInt(oneEntity));
                    index = 1;
                }else{
                    values = new int[2][2];
                    index = 0;
                }

                key = rs.getInt(oneEntity);
                values[index][0]= rs.getInt(manyEntity1);
                values[index][1]= rs.getInt(manyEntity2);
                data.put(key, values);
                //Bemærk HashMap magic: Hvis key ikke allerede findes, bliver tilføjet, hvis den gør bliver den kun brugt som nøgle - hashmapnøgler er unikke
            }


        } catch (SQLException ex) {
            System.out.println("SQL error: "+ex.getLocalizedMessage());
        }
        return data;
    }


/**
 *  Fra join tabel/sammenføjingstabel til HashMap
 *  I en en-mange tabel har vi flere forekomster af EN entiteten  (som i hold->spillere)
 *  Vi laver datastrukturen om, så det bliver til éen forekomst af EN entiteten.
 *  <Golden Eagles <jacob, gustav, ali>>
 * @param oneEntity korrepnonds to a table that is referenced by
 * @param manyEntity

 */

    public LinkedHashMap<String, ArrayList<String>> runTeamsQuery(String sql,String oneEntity, String manyEntity ){
        LinkedHashMap<String, ArrayList<String>> data = new LinkedHashMap<String, ArrayList<String>>();
        ArrayList<String> values;
        try{
            Statement stmt = mysql.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(sql);
            //data = addToMap(rs, oneEntity, manyEntity);
            while(rs.next()){

                String key;
                if(data.containsKey(rs.getString(oneEntity))){//Tjekker om entitieten (fx. Golden Eagles) allerede er i HashMap
                    values = data.get(rs.getString(oneEntity));
                }else{
                    values = new ArrayList();
                }
                key = rs.getString(oneEntity);
                values.add(rs.getString(manyEntity));
                data.put(key, values);
                //Bemærk HashMap magic: Hvis key ikke allerede findes, bliver tilføjet, hvis den gør bliver den kun brugt som nøgle - hashmapnøgler er unikke

            }
        } catch (SQLException ex) {
            System.out.println("SQL error: "+ex.getLocalizedMessage());
        }
        return data;
    }



private LinkedHashMap addToMap(ResultSet rs, String oneEntity, String manyEntity) throws SQLException{
    LinkedHashMap<String, ArrayList<String>> data = new LinkedHashMap<String, ArrayList<String>>();
    ArrayList<String> values;
    while(rs.next()){

        String key;
        if(data.containsKey(rs.getString(oneEntity))){//Tjekker om entitieten (fx. Golden Eagles) allerede er i HashMap
            values = data.get(rs.getString(oneEntity));
        }else{
            values = new ArrayList();
        }
        key = rs.getString(oneEntity);
        values.add(rs.getString(manyEntity));
        data.put(key, values);
        //Bemærk HashMap magic: Hvis key ikke allerede findes, bliver tilføjet, hvis den gør bliver den kun brugt som nøgle - hashmapnøgler er unikke

    }
    return data;
}
 private  ArrayList<String[]> processData(ResultSet rs){
            ArrayList<String[]> data = new  ArrayList<String[]>();
  
            try{
                ResultSetMetaData rsmd = rs.getMetaData();
                String table= rsmd.getTableName(2);

                int col_num = rsmd.getColumnCount();
                String[] row = null;
            
                int x = 1;
                while ( rs.next()) {

                    int type =rsmd.getColumnType(x);
                    row = new String[col_num];
                    switch (table){

                        case "matches":

                                row[0] = rs.getString("matchType");
                                row[1] = Integer.toString(rs.getInt("tournamentID"));
                                row[2] = rs.getDate("date").toString();
                                row[3] = rs.getTime("time").toString();
                                break;

                        case "teams":
                                 row[0] = Integer.toString(rs.getInt("id"));
                                 row[1] = rs.getString("name");
                         break;
                    }
                        data.add(row);
                        x++;



                }
            } catch (SQLException ex) {
            System.out.println("SQL error"+ex.getLocalizedMessage());
            }
            return data;
        }

    private String getValue(String label, ResultSet rs, int type) throws SQLException {
        String value="";
                switch(type){
           case 1:  value = Integer.toString(rs.getInt(label));
           break;
           case 2:  value = rs.getString(label);
               break;
           case 3:  value = rs.getDate(label).toString();
               break;
                }
               return value;
    }

}

