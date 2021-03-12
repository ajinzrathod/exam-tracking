package client.faculty;

import client.chat.ClientReadHandler;
import client.chat.ChatClient;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

public class FacultyUI extends javax.swing.JFrame {

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
    private static String SERVER_URL;
    private static int SERVER_PORT;

    private final String EXAM_ID;
    private String examName;
    private String facultyUsername;

    private DefaultListModel model = new DefaultListModel();
    private PrintWriter writer;
    private Socket socket;

    public FacultyUI(String examId) {
        EXAM_ID = examId;
        initComponents();
        facultyUIComboBox.addItem("Everyone");

        loadDatabaseProperties();
        loadDataFromDatabase();

        loadServerProperties();

        ChatClient client = new ChatClient(SERVER_URL, SERVER_PORT);
        new ClientReadHandler(socket, client, model, facultyUIChatBox, facultyUIComboBox).start();
    }

    private void loadServerProperties() {
        try (FileReader reader = new FileReader("src/server.properties")) {
            Properties p = new Properties();
            p.load(reader);

            SERVER_URL = p.getProperty("SERVER_URL");
            SERVER_PORT = Integer.parseInt(p.getProperty("SERVER_PORT"));

            socket = new Socket(SERVER_URL, SERVER_PORT);
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
            writer.println(facultyUsername);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void loadDatabaseProperties() {
        try (FileReader reader = new FileReader("src/database.properties")) {
            Properties p = new Properties();
            p.load(reader);
            DB_URL = p.getProperty("DB_URL");
            JDBC_DRIVER = p.getProperty("JDBC_DRIVER");
            USER = p.getProperty("USER");
            PASSWORD = p.getProperty("PASSWORD");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FacultyUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FacultyUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadDataFromDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD); Statement statement = connection.createStatement()) {
            Class.forName(JDBC_DRIVER);
            System.out.println("Creating connection...");
            System.out.println("Creating statement...");

            String sql = "SELECT examName, facultyUsername, questions FROM exams WHERE examId='" + EXAM_ID + "'";
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            examName = rs.getString("examName");
            facultyUsername = rs.getString("facultyUsername");

            String questions = rs.getString("questions");
            facultyUIDisplayExamID.setText("Exam ID: " + EXAM_ID);
            facultyUIDisplayExamName.setText("Exam Name: " + examName);
            facultyUIDisplayFacultyUsername.setText("Faculty Username: " + facultyUsername);
            facultyUIDisplayExamQuestion.setText(questions);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        facultyUIChatBox = new javax.swing.JList<>();
        facultyUIComboBox = new javax.swing.JComboBox<>();
        facultyUIChatTextBox = new javax.swing.JTextField();
        facultyUISendButton = new javax.swing.JButton();
        facultyUIDisplayExamID = new javax.swing.JLabel();
        facultyUIDisplayExamName = new javax.swing.JLabel();
        facultyUIDisplayFacultyUsername = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        facultyUIDisplayExamQuestion = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(facultyUIChatBox);

        facultyUISendButton.setText("Send");
        facultyUISendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facultyUISendButtonActionPerformed(evt);
            }
        });

        facultyUIDisplayExamID.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        facultyUIDisplayExamID.setText("Exam ID: ");

        facultyUIDisplayExamName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        facultyUIDisplayExamName.setText("Exam Name: ");

        facultyUIDisplayFacultyUsername.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        facultyUIDisplayFacultyUsername.setText("Faculty Username: ");

        facultyUIDisplayExamQuestion.setColumns(20);
        facultyUIDisplayExamQuestion.setRows(5);
        jScrollPane2.setViewportView(facultyUIDisplayExamQuestion);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(facultyUIDisplayExamID)
                            .addComponent(facultyUIDisplayExamName)
                            .addComponent(facultyUIDisplayFacultyUsername))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(93, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 68, 68)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(facultyUIChatTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(facultyUISendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)
                    .addComponent(facultyUIComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(29, 29, 29))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(facultyUIComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(facultyUIChatTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(facultyUISendButton)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(facultyUIDisplayExamID)
                        .addGap(11, 11, 11)
                        .addComponent(facultyUIDisplayFacultyUsername)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(facultyUIDisplayExamName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void facultyUISendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facultyUISendButtonActionPerformed
        String sendTo = (String) facultyUIComboBox.getSelectedItem();
        sendTo = sendTo.startsWith("Everyone") ? "" : "@" + sendTo;
        writer.println(sendTo + " " + facultyUIChatTextBox.getText());
        model.addElement(facultyUIChatTextBox.getText());
        facultyUIChatBox.setModel(model);
    }//GEN-LAST:event_facultyUISendButtonActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FacultyUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FacultyUI("HQUxLt").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> facultyUIChatBox;
    private javax.swing.JTextField facultyUIChatTextBox;
    private javax.swing.JComboBox<String> facultyUIComboBox;
    private javax.swing.JLabel facultyUIDisplayExamID;
    private javax.swing.JLabel facultyUIDisplayExamName;
    private javax.swing.JTextArea facultyUIDisplayExamQuestion;
    private javax.swing.JLabel facultyUIDisplayFacultyUsername;
    private javax.swing.JButton facultyUISendButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
