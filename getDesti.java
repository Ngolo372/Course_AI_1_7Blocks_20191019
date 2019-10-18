import java.sql.*;
import java.util.*;

//getDesti类 ： 从数据库中获取图像库
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

      //打开通道
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:test.db");
      stmt = c.createStatement();

      ResultSet rs = stmt.executeQuery( gi );

      //逐个读取
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
  }
}