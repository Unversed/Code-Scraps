import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Formatter;
import java.util.HashSet;
import java.util.Scanner;

public class ListPrereqs {
   
   static void closeEm(Object... toClose) {
      for (Object obj: toClose){
         if (obj != null){
            try {
               obj.getClass().getMethod("close").invoke(obj);
            }
            catch (Throwable t) {
               System.out.println("Log bad close");
            }
         }
      }
   }
   
   static HashSet<Integer> previouslyCompleted(Connection cnc, 
      int studentId) throws SQLException {
      HashSet<Integer> completed = new HashSet<Integer>();
      PreparedStatement pStm = null;
      ResultSet rst = null;
      try{
         pStm = cnc.prepareStatement("SELECT courseId" 
            + " FROM Enrollment WHERE studentId = ?");
         pStm.setInt(1,studentId);
         rst = pStm.executeQuery();
         while(rst.next()){
            completed.add(rst.getInt(1));
         }
      return completed;
      }
      finally {
         closeEm(pStm, rst);
      }
   }

   private static void addMissingClasses(Connection cnc, int studentId,
      int courseId, HashSet<Integer> completed) throws SQLException {
      Statement stm = null;
      PreparedStatement pStm = null;
      ResultSet rst = null;
      Formatter fmt = new Formatter(new StringBuilder
         ("insert into MissingPrereq(studentId, courseId, prereqId) values"));
      
      try{
         stm = cnc.createStatement();
         int dependent = courseId;
         boolean noPreviousEntry = true;
         do{
            pStm  = cnc.prepareStatement(
               "SELECT max(requirement) FROM Prereq "
               + "WHERE dependent = ?");
            pStm.setInt(1, dependent);
            rst = pStm.executeQuery();
            if(rst.next()){
            dependent = rst.getInt(1);
               if(!completed.contains(dependent) && dependent != 0){
                  fmt.format("%c(%d, %d, %d)", (noPreviousEntry) ? ' ' : ',', 
                     studentId, courseId, dependent);
                  noPreviousEntry = false;
               }
            }
         } while(dependent != 0);
         if(!noPreviousEntry){
            stm.executeUpdate(fmt.toString());
         }
      }
      finally {
         closeEm(stm, pStm, rst, fmt);
      }  
   }
   
   public static void main(String[] args) {
      Connection cnc = null;
      Scanner in = new Scanner(System.in);
      int studentId;
      int courseId;
      HashSet<Integer> completed = new HashSet<Integer>();

      
      try {
         cnc = DriverManager.getConnection(args[0], args[1], args[2]);
         studentId = in.nextInt();
         while(in.hasNext()){
            
            courseId = in.nextInt();
            completed = previouslyCompleted(cnc, studentId);
            addMissingClasses(cnc, studentId, courseId, completed);
         }
      }
      catch (SQLException err) {
         System.out.println(err.getMessage());
      }
      finally {
         closeEm(cnc, in);
      }
   }
}

