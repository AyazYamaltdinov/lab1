package myConnect;
import java.io.*;
import java.sql.*;
import java.util.Properties;
public class Connect {
 public Connection getConnection() throws  IOException ,SQLException {  
    Properties props = new Properties();
    FileInputStream in = new FileInputStream("prop/mydb.properties");
    props.load(in);
    in.close();
    String url = props.getProperty("jdbc.url");
    String username = props.getProperty("jdbc.username");
    String password = props.getProperty("jdbc.password");
    return  DriverManager.getConnection(url, username, password);
} }
