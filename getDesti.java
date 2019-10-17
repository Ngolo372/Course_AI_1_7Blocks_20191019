import java.sql.*;
import java.util.*;

public class getDesti
{
    int id, x, y, al, ah, el, eh;
    List<point> q;
    point sd = new point();

  public getDesti(int i){

    Connection c = null;
    Statement stmt = null;
    try {

      q = new ArrayList<point>();

      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:test.db");
      stmt = c.createStatement();

      ResultSet rs = stmt.executeQuery( "SELECT * FROM lib1_19;" );

      while ( rs.next() ) {
        // this.id = rs.getInt("ID");
        // sd.x  = rs.getInt("x");
        // sd.y  = rs.getInt("y");
        // sd.al  = rs.getInt("al");
        // sd.ah  = rs.getInt("ah");
        // sd.el  = rs.getInt("el");
        // sd.eh  = rs.getInt("eh");

        // sd.getAngle();

        q.add(new point(rs.getFloat("x"), rs.getFloat("y"), rs.getInt("al"), rs.getInt("ah"), rs.getFloat("el"), rs.getFloat("eh")));

        // System.out.println(q.size());
        // System.out.println( "ID = " + this.id );
        // System.out.println( "x = " + q.get(q.size() - 1).x );
        // System.out.println( "y = " + q.get(q.size() - 1).y );
        // System.out.println( "al = " + q.get(q.size() - 1).al );
        // System.out.println( "ah = " + q.get(q.size() - 1).ah );
        // System.out.println( "el = " + q.get(q.size() - 1).el );
        // System.out.println( "eh = " + q.get(q.size() - 1).eh );
        // System.out.println();
        
      }

      rs.close();
      stmt.close();
      c.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Opened database successfully");
  }
}