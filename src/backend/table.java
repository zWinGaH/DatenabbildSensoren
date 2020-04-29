/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;
import backend.db;
import static backend.db.conn;
import frontend.LoginInterface;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Peaq PNB P2015
 */
public class table {
    
    public String tablename;
    public static int rowCount;
    public int columnCount;
    public String rowNames[][];
    public String columnNames[];
    
    db db = LoginInterface.db;
    Connection conn = db.conn;
    
    public table(String tablename) throws SQLException{
        this.tablename = tablename;
        this.rowCount = rowCount();
        this.columnCount = columnCount();
        this.columnNames = columnNames();
        this.rowNames = rowNames();
    }
    
    public int rowCount() throws SQLException{
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from " + this.tablename);
        int count = 0;
        while (rs.next()) {
            count++;
            //System.out.println(rs.getString("name"));
        }
        return count;
    }
    
    private int columnCount() throws SQLException{
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + this.tablename);
        int col = rs.getMetaData().getColumnCount();
        return col;
    }
    
    private String[] columnNames() throws SQLException{
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + this.tablename);
        String colNames[] = new String[this.columnCount];
        for (int i = 1; i <= this.columnCount; i++){
            String col_name = rs.getMetaData().getColumnName(i);
            colNames[i-1] = col_name;
        }
        return colNames;
    }
    
    private String[][] rowNames() throws SQLException{
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + this.tablename);
        String rowNames[][] = new String[this.rowCount][this.columnCount+1];
        int i = 0;
        while (rs.next()) {
            for (int j = 0; j < this.columnCount; j++){
                rowNames[i][j] = rs.getString(this.columnNames[j]);
            }
            rowNames[i][this.columnCount] = getstate(rowNames[i][0]);
            //System.out.println(rs.getString("name"));
            i++;
        }
        return rowNames;
    }
    
    public String getstate(String i) throws SQLException{
        int j = Integer.parseInt(i);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT statename FROM states WHERE idresource = " + j);
        String state = null;
        while (rs.next())
            state = rs.getString("statename");
        return state;
    }
    
   
    public static void main(String[] args) throws SQLException {
        //testing
        table hosttable = new table("user");
        System.out.println("Rows: " + hosttable.rowCount);
        System.out.println("Columns: " + hosttable.columnCount);
        System.out.println(hosttable.columnNames[0]);
        System.out.println(hosttable.rowNames[0][0]);
    }
}
