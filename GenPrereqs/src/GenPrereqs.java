import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.Formatter;
import java.util.Scanner;
public class GenPrereqs {
   
   static void closeEm(Object... toClose) {
      for (Object obj: toClose){
         if (obj != null)
            try {
            obj.getClass().getMethod("close").invoke(obj);
            } catch (Throwable t) {System.out.println("Log bad close");}
      }
   }

   static void clearDatabase(Connection cnc) throws SQLException {
      Statement st = cnc.createStatement();
      try{
         st.execute("SET foreign_key_checks = 0");
         st.execute("DELETE FROM Course");
         st.execute("DELETE FROM Student");
         st.execute("DELETE FROM Enrollment");
         st.execute("DELETE FROM Prereq");
         st.execute("SET foreign_key_checks = 1");
      }
      finally {
         closeEm(st);
      }
   }
   
   static void makeCourses(Connection cnc, 
      int numCourses) throws SQLException {
      Statement stm = cnc.createStatement();
      Formatter fmt = new Formatter(new StringBuilder
      ("insert into Course(name) values"));

      try {
         for(int count = 0;count < numCourses; count++){
            fmt.format("%c('%s')", count == 0 ? ' ' : ',',
               "Course"+String.format("%03d", count));
         }
      stm.executeUpdate(fmt.toString());
      }
      finally {
         closeEm(fmt, stm);
      }
  }
   
   static void makeStudents(Connection cnc, 
      int numStudents) throws SQLException {
      Statement stm = cnc.createStatement();
      Formatter fmt = new Formatter(new StringBuilder
      ("insert into Student(firstName, lastName) values"));
      
      try {
         for(int count = 0;count < numStudents; count++){
            fmt.format("%c('%s', '%s')", count == 0 ? ' ' : ',',
               "First"+String.format("%03d", count), 
               "Last"+String.format("%03d", count));
         }
      stm.executeUpdate(fmt.toString());
      }
      finally {
         closeEm(fmt, stm);
      }
   }
   
   static void makePrereqs(Connection cnc, 
      int frequency, int numCourses, int preexistingCourses) throws SQLException {
      Statement stm = cnc.createStatement();
      Formatter fmt = new Formatter(new StringBuilder
      ("insert into Prereq(dependent, requirement) values"));
      int count = 0, dependent = 1 + preexistingCourses, requirement = 1 + preexistingCourses;
      numCourses += preexistingCourses;
      try {
         for(;requirement < numCourses; requirement++){
            for(;dependent <= numCourses; dependent++){
               if((requirement + dependent) * 8191 %frequency == 0 
                  && requirement < dependent){
                  fmt.format("%c(%d,%d)", 
                     (count == 0) ? ' ' : ',', 
                     dependent, requirement);
                  count++;
               }
            }
            dependent = 1;
         }
         stm.executeUpdate(fmt.toString());
      }
      finally {
         closeEm(fmt, stm);
      }  
   }

   static void makeEnrollments(Connection cnc, 
      int numStudents, int pS, 
      int numCourses, int pC) throws SQLException {
      Statement stm = cnc.createStatement();
      Formatter fmt = new Formatter(new StringBuilder
         ("insert into Enrollment(studentId, courseId) values"));
      ResultSet rst = null;
      PreparedStatement pStm = null;
      int cId = 1 + pC, sId = 1 + pS, diff=pS-pC, count = 1, first = 1;
      numCourses += pC;
      numStudents += pS;
      try{
         for(;cId <= numCourses; cId++, count++, sId = pS + count){
            for(;sId <= numStudents; sId++){
               int dependent = sId - diff;
               int newDep = cId + 1;
               if(dependent > numCourses){
                  dependent -= (numCourses-pC);
               }
               if(dependent == cId){
                  fmt.format("%c(%d,%d)", 
                     (first == 1) ? ' ' : ',', 
                     sId, cId);
               }
               first++;
               for(;newDep > cId;){
                  pStm  = cnc.prepareStatement(
                     "SELECT requirement FROM Prereq "
                     + "WHERE dependent = ? ORDER BY requirement");
                  pStm.setInt(1,dependent);
                  rst = pStm.executeQuery();
                  if(rst.next()){
                     rst.last();
                  newDep = rst.getInt(1);
                     if(newDep == cId){
                        fmt.format(",(%d,%d)", sId, cId);
                     }
                  dependent = newDep;
                  }
                  else{
                     newDep--;
                  }
               }
            }
         }
         stm.executeUpdate(fmt.toString());
      }
      finally {
         closeEm(fmt, stm, rst, pStm);
      }
   }
   
   public static void main(String[] args) {
      Connection cnc = null;
      Scanner in = new Scanner(System.in);
      int numCourses, numStudents, frequency;
      String clearFlag = "Nope";
      ResultSet rst = null;
      ResultSet rst2 = null;
      Statement stm = null;
      
      try {
         cnc = DriverManager.getConnection(args[0], args[1], args[2]);
         numCourses = in.nextInt();
         numStudents = in.nextInt();
         frequency = in.nextInt();
         if(in.hasNext()){
            clearFlag = in.next();
         }
         if(clearFlag.equals("clear")){
            clearDatabase(cnc);
         }
         makeCourses(cnc, numCourses);
         makeStudents(cnc, numStudents);
         
         stm = cnc.createStatement();
         rst = stm.executeQuery("Select id From Course ORDER BY id DESC");
         rst.next();
         int preexistingCourses = rst.getInt(1);
         preexistingCourses -= numCourses;
         rst2 = stm.executeQuery("Select id From Student ORDER BY id DESC");
         rst2.next();
         int preexistingStudents = rst2.getInt(1);
         preexistingStudents -= numStudents;
         
         makePrereqs(cnc, frequency, numCourses, preexistingCourses);
         makeEnrollments(cnc, numStudents, preexistingStudents, numCourses, preexistingCourses);
      }
      catch (SQLException err) {
         System.out.println(err.getMessage());
      }
      finally {
         closeEm(cnc, in, stm, rst, rst2);
      }
   }
}