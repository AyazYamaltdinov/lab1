package mydb1;
import java.sql.*;
import java.io.*;
import java.util.logging.Level;
import myConnect.Connect;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
public class TestMydb0{
    public static void main(String[] args){
        try{
            LogManager.getLogManager().readConfiguration(
            TestMydb0.class.getResourceAsStream("../log/logging.properties"));
        } catch(IOException e){
            System.err.println("Error logger configuration: "+ e.toString());
        }
        new Mydb0().Mydb0Create();
    }    
}
class Mydb0 {
    private static Logger log = Logger.getLogger(Mydb0.class.getName());
    public void Mydb0Create(){
        try{
            Connection conn = new Connect().getConnection();
            Statement stat = conn.createStatement();
            stat.execute("CREATE TABLE MyTable1(Message VarChar(50))");
            stat.execute("Insert Into MyTable1 VALUES ('It\"s MyTable1!')");
            
            ResultSet result = stat.executeQuery("SELECT * FROM MyTable1");
            result.next();
            JOptionPane.showMessageDialog(null, result.getString(1));
            JOptionPane.showMessageDialog(null, result.getString("Message"));            
            result.close();
            stat.execute("DROP TABLE MyTable1");            
            JOptionPane.showMessageDialog(null, "DROP TABLE MyTable1");
            stat.close();
            conn.close();
            
        }
        catch(SQLException ex){
            while (ex!= null){
                log.log(Level.SEVERE,"Exception: ", ex);
                ex = ex.getNextException();
            }
        }
        catch(IOException ex){
            log.log(Level.SEVERE, "Exception: ", ex);
        }
    }
}
    
