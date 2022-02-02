package mydb1;
import java.sql.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import myConnect.Connect;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class TestMydb1 {
    public static void main(String args[])
    {
        try
        {
            LogManager.getLogManager().readConfiguration(
            TestMydb1.class.getResourceAsStream("../log/logging.properties"));
        }catch(IOException e)
        {
            System.err.println("Error logger configuration: " + e.toString());
        }
        
        
        int updateCount = new Mydb1().Mydb1Create();
        JOptionPane.showMessageDialog(null,"Добавлено строк"+ updateCount);
    }
}
class Mydb1{
    private static Logger log = Logger.getLogger(Mydb1.class.getName());
    public  int Mydb1Create()
    {
        int updateCount=0;
        String[] str = new String[4];
        str[0] = "src/StatSQL/Authors.sql";
        str[1] = "src/StatSQL/Books.sql";
        str[2] = "src/StatSQL/BooksAuthors.sql";
        str[3] = "src/StatSQL/Publishers.sql";
        try(Scanner in = new Scanner(Paths.get(str[3]),"UTF-8"))
        {
            try(Connection conn = new Connect().getConnection();
                Statement stat = conn.createStatement())
            {
                while(true)
                {
                    if(!in.hasNextLine())
                        return updateCount;
                    String line = in.nextLine().trim();
                    try
                    {
                        stat.execute(line);
                        updateCount +=stat.getUpdateCount();
                    }
                    catch (SQLException ex)
                    {
                        for(Throwable e: ex)
                        log.log(Level.SEVERE, "Exception: ", e);
                    }
                }
            }
            catch (SQLException ex)
            {
                for(Throwable e: ex)
                log.log(Level.SEVERE, "Exception: ", e);
            }
        }catch (IOException ex)
        {
            log.log(Level.SEVERE, "Exception: ",ex);
        }
        return updateCount;

    }
}
      
