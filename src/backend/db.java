package backend;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
//import frontend.RessourcenInterface;

public class db {
    static Connection conn = null;
    public db(String dbname) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("loaded driver ...");
        } catch (Exception ex) {
            System.out.println("Kann Treiber nicht laden!");
            System.out.println("Exception: " + ex.getMessage());
            System.out.println("ExceptionLocal: " + ex.toString());
        }
        try {
            conn =
            DriverManager.getConnection("jdbc:mysql://localhost/" + dbname + "?" +
                    "user=root&password=1234&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"); 
            
            System.out.println("connected to database ...");
            
        } catch (SQLException ex) {
            // handle any errors
            System.err.println("Can't connect to server!");
            System.exit(0);
            //System.out.println("SQLException: " + ex.getMessage());
            //System.out.println("SQLState: " + ex.getSQLState());
            //System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
    
    public static void insertUser(user user) throws SQLException {
	    Statement stmt = conn.createStatement();
	    String query = "INSERT INTO user(username, email, password) VALUES('" +
	    user.username + "','" + user.email + "','" + user.password + "')";
	    int count = stmt.executeUpdate(query);
	    System.out.println("Anzahl eingefuegter Datensätze: " + count); 
    }
    /*
	 private static final String extendStringTo14( String s )
	  {
	    if( null == s ) s = "";
	    final String sFillStrWithWantLen = "              ";
	    final int iWantLen = sFillStrWithWantLen.length();
	    final int iActLen  = s.length();
	    if( iActLen < iWantLen )
	      return (s + sFillStrWithWantLen).substring( 0, iWantLen );
	    if( iActLen > 2 * iWantLen )
	      return s.substring( 0, 2 * iWantLen );
	    return s;
	  }
        
	private void showTable(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
		  int i, n = rsmd.getColumnCount();
		  // Print table content:
		  for( i=0; i<n; i++ )
		    System.out.print( "+---------------" );
		  System.out.println( "+" );
		  for( i=1; i<=n; i++ )    // Attention: first column with 1 instead of 0
		    System.out.print( "| " + extendStringTo14( rsmd.getColumnName( i ) ) );
		  System.out.println( "|" );
		  for( i=0; i<n; i++ )
		    System.out.print( "+---------------" );
		  System.out.println( "+" );
		  while( rs.next() ) {
		    for( i=1; i<=n; i++ )  // Attention: first column with 1 instead of 0
		      System.out.print( "| " + extendStringTo14( rs.getString( i ) ) );
		    System.out.println( "|" );
		  }
	}
        
	public void insertHost(Host host) throws SQLException {
	    Statement stmt = conn.createStatement();
	    String befehl = "INSERT INTO hosts(ipAdresse, name,macAdresse) VALUES('" +
	    host.ipAdresse + "','" + host.name + "','" + host.macAdresse+ "')";
	    System.out.println(befehl);
	    int count = stmt.executeUpdate(befehl);
	    System.out.println("Anzahl eingefuegter Datens�tze: " + count); 
	}
        */
        public int countActives() throws SQLException{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM states WHERE statename = 'active'");
            int count = 0;
            while (rs.next()) {
                count++;
            }
            return count;
        }
        
        public resource[] getActiveArray() throws SQLException{
            resource[] activeArray = new resource[countActives()];
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM resources WHERE idresources IN (SELECT idresource FROM states WHERE statename = \"active\")");
            int i = 0;
            while (rs.next()) {
                activeArray[i] = new resource(rs.getInt("idresources"), rs.getString("name"), rs.getString("protocol"), rs.getString("adress"), rs.getString("attribute"), 
                        rs.getInt("queryFrequency"), rs.getString("datatype"), rs.getInt("output"), rs.getInt("upperlimit"), rs.getInt("lowerlimit"));
                i++;
            }
            return activeArray;
        }
        
        public void insertResource(resource resource) throws SQLException {
            Statement stmt = conn.createStatement();
	    String befehl = "INSERT INTO resources(name, protocol, adress, attribute, queryFrequency, datatype, output, upperLimit, lowerLimit) VALUES('" +
                    resource.name + "','" + resource.protocol + "','" + resource.adress+ "','" +
                    resource.attribute + "','" + resource.queryFrquzy + "','" + resource.datatype + "','" + 
                    resource.output + "','" + resource.upperLimit + "','" + resource.lowerLimit + "')";
	    int count = stmt.executeUpdate(befehl);
            
            insertState();
	    //System.out.println("Anzahl eingefuegter Datensätze: " + count);
        }
        
        public void insertState() throws SQLException{
            Statement stmt = conn.createStatement();
	    
            int lastId = 0;
            ResultSet rs = stmt.executeQuery("select * from resources");
            while(rs.next())
                lastId = rs.getInt("idresources");
            
            String befehl = "INSERT INTO states (statename, idresource) VALUES (\"inactive\", '" + Integer.toString(lastId) + "')";
	    int count = stmt.executeUpdate(befehl);
        }
        
        public void insertData(int id, String value, String datetime) throws SQLException{
            Statement stmt = conn.createStatement();
            String query = null;
            if(!existing(id)){
                query = "INSERT INTO data (value, datetime, resources_idresources) VALUES ('" + value + "','" + datetime + "','" + Integer.toString(id) + "')";
                int count = stmt.executeUpdate(query);
            }else{
                query = "UPDATE data SET value = '" + value + "', datetime = '" + datetime + "' WHERE resources_idresources = '" + id + "'";
                //"UPDATE data SET value = '" + value + "', datetime = '" + datetime + "' WHERE resources_idresources = '" + value + "'"
                int count = stmt.executeUpdate(query);
            }
        }
        
        public Boolean existing(int id) throws SQLException{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM data WHERE resources_idresources = " + id);
            int count = 0;
            while (rs.next()) {
                count++;
            }
            if(count >= 1) return true;
            else return false;
        }
        
        /*
        public void insertActive(isActive active) throws SQLException {
            Statement stmt = conn.createStatement();
	    String befehl = "INSERT INTO isactive(active, resources_idresources) VALUES('" + active.active + "','" + active.resources_idresources + "')";
	    int count = stmt.executeUpdate(befehl);
	    //System.out.println("Anzahl eingefuegter Datensätze: " + count);
        }
        */
        public void setState(String id, boolean state) throws SQLException {
            Statement stmt = conn.createStatement();
            String query = null;
            if (state)
                query = "UPDATE states SET statename = 'active' WHERE idresource = " + id;
            else
                query = "UPDATE states SET statename = 'inactive' WHERE idresource = " + id;
	    int count = stmt.executeUpdate(query);
        }
        
        public void deleteResource(String id) throws SQLException{
            //First, we need to delete the state
            deleteState(id);
            
            //Now we need to delete the reffering data
            deleteData(id);
            //now the entry
            Statement stmt = conn.createStatement();
	    String befehl = "DELETE FROM resources WHERE idresources = " + id;
            int count = stmt.executeUpdate(befehl);
        }
        
        public void deleteData(String id) throws SQLException{
            Statement stmt = conn.createStatement();
	    String befehl = "DELETE FROM data WHERE resources_idresources = " + id;
            int count = stmt.executeUpdate(befehl);
        }
        
        public void deleteState(String id) throws SQLException{
            Statement stmt = conn.createStatement();
	    String befehl = "DELETE FROM states WHERE idresource = " + id;
            int count = stmt.executeUpdate(befehl);
        }
        /*
        
        public static void countRows(String tablename) throws SQLException{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from " + tablename);
            int count = 0;
            while (rs.next()) {
              count++;
            }  
            System.out.println(count);
        }
        */
        public String[] countColumns(String tablename) throws SQLException{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tablename);
            int col = rs.getMetaData().getColumnCount();
            System.out.println(col);
            String colNames[] = new String[col];
            for (int i = 0; i < col; i++){
                String col_name = rs.getMetaData().getColumnName(i);
                System.out.println(col_name);
                colNames[i] = col_name;
            }
            return colNames;
        }
        
	public static void main(String[] args) throws SQLException {
            db db1 = new db("coapproject");
            System.out.println(db1.countColumns("data")[0]);
	}
}
