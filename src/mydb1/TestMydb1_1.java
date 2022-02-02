package mydb1;
import java.awt.BorderLayout;
import java.sql.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import myConnect.Connect;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
public class TestMydb1_1 {
    public static void main(String args[])
    {
        try
        {
            LogManager.getLogManager().readConfiguration(
            TestMydb1_1.class.getResourceAsStream("../log/logging.properties"));
        }catch(IOException e)
        {
            System.err.println("Error logger configuration: " + e.toString());
        }
        Mydb1_1 sample = new Mydb1_1();
        sample.Mydb1_1Create();        
    }
}
class Mydb1_1{
    private static Logger log = Logger.getLogger(Mydb1.class.getName());
    public void Mydb1_1Create()
    {
        JTable table = null;
        String[] str = new String[4];
        str[0] = "select * from Authors";
        str[1] = "select * from Books";
        str[2] = "select * from BooksAuthors";
        str[3] = "select * from Publishers";
        String line = str[2];
        try(Connection conn = new Connect().getConnection();
            Statement stat = conn.createStatement())
        {
            try{
                boolean isResult = stat.execute(line);
                if(isResult){
                    try(ResultSet rs = stat.getResultSet()){
                        table = new JTable(buildTableModel(rs));
                    }
                }
            }catch(SQLException ex){
            for(Throwable e: ex)
                log.log(Level.SEVERE, "Exception: ", e);
            }
        }catch(SQLException ex)
        {
            for(Throwable e: ex)
                log.log(Level.SEVERE, "Exception: ", e);
        }catch(IOException e)
        {            
                log.log(Level.SEVERE, "Exception: ", e);
        }
        JFrame frame = new FrameTable(table,line);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
    public static DefaultTableModel buildTableModel(ResultSet rs)throws SQLException
    {
        ResultSetMetaData metaData = rs.getMetaData();
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for(int column = 1; column<=columnCount;column++)
        {
            columnNames.add(metaData.getColumnName(column));        
        }
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while(rs.next()){
            Vector<Object> vector = new Vector<Object>();
            for(int columnIndex = 1; columnIndex <=columnCount; columnIndex++)
            {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data,columnNames);
    }     
}
class FrameTable extends JFrame
  {
      public FrameTable(JTable tbl, String line)
      {
          setTitle(line);
          setSize(800,600);
          getContentPane().add(new JScrollPane(tbl),BorderLayout.CENTER);
      }
  }