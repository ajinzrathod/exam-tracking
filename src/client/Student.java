package client;

public class Student {
   public final String rollNum;
   public final String name;
   public final String keyLogFile;
   public final String examId;
   
   public Student(String rollNum, String name, String keyLogFile, String examId) {
       this.rollNum = rollNum;
       this.name = name;
       this.keyLogFile = keyLogFile;
       this.examId = examId;
   }
}
