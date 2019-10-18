import java.sql.*;
import java.util.*;

public class getDesti
{
    int id, x, y, al, ah, el, eh;
    List<point> q;
    point sd = new point();

  public getDesti(String i){

    Connection c = null;
    Statement stmt = null;
    String gi = "SELECT * FROM lib1_" + i + ";";
    try {

      q = new ArrayList<point>();

      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:test.db");
      stmt = c.createStatement();

      ResultSet rs = stmt.executeQuery( gi );
      // System.out.println(gi);

      while ( rs.next() ) {

        q.add(new point(rs.getFloat("x"), rs.getFloat("y"), rs.getInt("al"), rs.getInt("ah"), rs.getFloat("el"), rs.getFloat("eh")));
        
      }

      rs.close();
      stmt.close();
      c.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    // System.out.println("Opened database successfully");
  }
}