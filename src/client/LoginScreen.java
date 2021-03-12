package client;

import client.faculty.FacultyUI;
import client.faculty.CreateExam;
import client.student.StudentUI;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.Properties;
import javax.swing.JOptionPane;

public class LoginScreen extends javax.swing.JFrame {

    //FOR LOCALHOST
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/exam_management";
//    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
//    private static final String USER = "root";
//    private static final String PASSWORD = "";
    //FOR AWS
//    private static final String DB_URL = "jdbc:mysql://exam-management-aws.cpyjaypv4zdd.us-east-1.rds.amazonaws.com/exam_management";
//    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
//    private static final String USER = "admin";
//    private static final String PASSWORD = "7801898047";
    private static String DB_URL;
    private static String JDBC_DRIVER;
    private static String USER;
    private static String PASSWORD;

    private Socket socket;
    private static String SERVER_URL;
    private static int SERVER_PORT;

    public LoginScreen() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        studentLoginRollNo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        studentLoginName = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        studentLoginExamId = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        facultyLoginPassword = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        facultyLoginExamId = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Roll No:");

        studentLoginRollNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentLoginRollNoActionPerformed(evt);
            }
        });

        jLabel2.setText("Your Name");

        studentLoginName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentLoginNameActionPerformed(evt);
            }
        });

        jButton1.setText("Login");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Exam ID");

        jButton2.setText("Create New Exam");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("OR");

        jLabel5.setText("Exam ID");

        jLabel6.setText("Password");

        jButton3.setText("Start Exam");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(facultyLoginExamId, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel4)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(studentLoginExamId, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(studentLoginName)
                            .addComponent(studentLoginRollNo)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                            .addComponent(facultyLoginPassword, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(studentLoginExamId, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(studentLoginName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(studentLoginRollNo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(facultyLoginExamId, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(facultyLoginPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        var check = networkConnections();
        if (!check) {
            JOptionPane.showMessageDialog(this, "Error 500: Server error!");
            return;
        }
        final String rollno = studentLoginRollNo.getText();
        final String name = studentLoginName.getText();
        final String examId = studentLoginExamId.getText();

        if (!isValid(rollno, name, examId)) {
            return;
        }

        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD); Statement stmt = con.createStatement()) {
            Class.forName(JDBC_DRIVER);
            String sql = "SELECT * FROM exams WHERE examId='" + examId + "'";
            ResultSet rs = stmt.executeQuery(sql);
            boolean isSuccess = rs.next();
            if (!isSuccess) {
                JOptionPane.showMessageDialog(this, "Exam not found!");
                return;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Error 500: Server error!");
            return;
        }

        var user = new Student(rollno, name, (rollno + "_" + name + "_" + examId + ".txt"), examId);
        StudentUI studentExam = new StudentUI(user);
        studentExam.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private boolean isValid(final String rollno, final String name, final String examId) {
        if (rollno == null || rollno.equals("")) {
            return false;
        }
        if (name == null || name.equals("")) {
            return false;
        }
        if (examId == null || examId.equals("")) {
            return false;
        }
        return true;
    }

    private void studentLoginRollNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentLoginRollNoActionPerformed
    }//GEN-LAST:event_studentLoginRollNoActionPerformed

    private void studentLoginNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentLoginNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_studentLoginNameActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        var check = networkConnections();
        if (!check) {
            JOptionPane.showMessageDialog(this, "Error 500: Server error!");
            return;
        }
        var createExam = new CreateExam();
        createExam.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        var check = networkConnections();
        if (!check) {
            JOptionPane.showMessageDialog(this, "Error 500: Server error!");
            return;
        }
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD); Statement statement = connection.createStatement()) {
            Class.forName(JDBC_DRIVER);
            System.out.println("Creating connection...");
            System.out.println("Creating statement...");

            String userExamId = facultyLoginExamId.getText();
            String userPassword = facultyLoginPassword.getText();

            String sql = "SELECT password FROM exams WHERE BINARY examId='" + userExamId + "'";
            ResultSet rs = statement.executeQuery(sql);
            var isSuccess = rs.next();

            if (!isSuccess) {
                JOptionPane.showMessageDialog(this, "Error 404: Not Found");
                return;
            }

            String password = rs.getString("password");

            if (userPassword.equals(password)) {
                var facultyUI = new FacultyUI(userExamId);
                facultyUI.setVisible(true);
                this.dispose();
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField facultyLoginExamId;
    private javax.swing.JTextField facultyLoginPassword;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField studentLoginExamId;
    private javax.swing.JTextField studentLoginName;
    private javax.swing.JTextField studentLoginRollNo;
    // End of variables declaration//GEN-END:variables

    private boolean networkConnections() {
        boolean success = true;
        FileReader reader;

        try {

            reader = new FileReader("src/database.properties");
            Properties p = new Properties();
            p.load(reader);
            DB_URL = p.getProperty("DB_URL");
            JDBC_DRIVER = p.getProperty("JDBC_DRIVER");
            USER = p.getProperty("USER");
            PASSWORD = p.getProperty("PASSWORD");

            Properties properties = new Properties();
            reader = new FileReader("src/server.properties");
            properties.load(reader);

//            SERVER_URL = properties.getProperty("SERVER_URL");
//            SERVER_PORT = Integer.parseInt(properties.getProperty("SERVER_PORT"));
//            socket = new Socket(SERVER_URL, SERVER_PORT);
        } catch (UnknownHostException e) {
            System.out.println("Unknown Host");
            success = false;

        } catch (IOException e) {
            success = false;

        } catch (Exception e) {
            success = false;
        }
        return success;
    }
}
