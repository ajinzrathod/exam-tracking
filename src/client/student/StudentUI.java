package client.student;

import client.Student;
import client.chat.ChatClient;
import client.chat.ClientReadHandler;
import logger.KeyLogger;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import com.github.sarxos.webcam.Webcam;
import java.awt.HeadlessException;
import logger.Screenshot;

public class StudentUI extends javax.swing.JFrame {

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

    private DefaultListModel model = new DefaultListModel();
    private PrintWriter writer;
    private Socket socket;
    private final Student student;

    private String examName;
    private String questions;
    private String facultyUsername;
    private long duration;

    private int hour;
    private int minute;
    private int second;
    private int csecond;
    private boolean isStart;

    public StudentUI(final Student student) {
        initComponents();
        boolean success = true;

        //Do not delete this lines  , I was going to delete it LOL ;-)
//        GraphicsEnvironment graphics =
//        GraphicsEnvironment.getLocalGraphicsEnvironment();
//        GraphicsDevice device = graphics.getDefaultScreenDevice();
//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //till here
        success = loadDatabaseProperties();
        this.student = student;
        KeyLogger kg = new KeyLogger(student);
        success = success && loadServerProperties();

        if (success) {
            studentUIDisplayQuestion.setHighlighter(null);

            ChatClient client = new ChatClient(SERVER_URL, SERVER_PORT);
            new ClientReadHandler(socket, client, model, studentUIChatBox, null).start();
            loadDataFromDatabase();
            timerStart(this);
            getCamera(this);
            Runnable r = new Screenshot(this.student);
            new Thread(r).start();
        }

//        device.setFullScreenWindow(this); //do not delete  this line
    }

    private boolean loadServerProperties()  {
        try (final FileReader reader = new FileReader("src/server.properties")) {
            Properties properties = new Properties();
            properties.load(reader);
            SERVER_URL = properties.getProperty("SERVER_URL");
            SERVER_PORT = Integer.parseInt(properties.getProperty("SERVER_PORT"));
            socket = new Socket(SERVER_URL, SERVER_PORT);
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
            writer.println(student.rollNum); //user id
            //user id
        } catch (UnknownHostException e) {
            System.out.println("Unknown Host");
            JOptionPane.showMessageDialog(this, "Error 500: Server error!");
            this.dispose();
        } catch (IOException e) {
            return false;
        }catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean loadDatabaseProperties() {
        try (FileReader reader = new FileReader("src/database.properties")) {
            Properties p = new Properties();
            p.load(reader);
            DB_URL = p.getProperty("DB_URL");
            JDBC_DRIVER = p.getProperty("JDBC_DRIVER");
            USER = p.getProperty("USER");
            PASSWORD = p.getProperty("PASSWORD");
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
        return true;
    }

    private void setEndTime() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD); Statement statement = connection.createStatement()) {
            Class.forName(JDBC_DRIVER);
            System.out.println("Creating connection...");
            System.out.println("Creating statement...");

            String sql = "UPDATE students SET endTime=current_timestamp() WHERE rollNo=" + student.rollNum + " and examId='" + student.examId + "'";
            int i = statement.executeUpdate(sql);
            System.out.println("End Time Result: " + i);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void loadDataFromDatabase() {
        String insert = "INSERT INTO students (rollNo, studentName, examId) VALUES(?,?,?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD); Statement fetchStatement = connection.createStatement(); PreparedStatement insertStatement = connection.prepareStatement(insert)) {
            Class.forName(JDBC_DRIVER);
            System.out.println("Creating connection...");
            System.out.println("Creating statement...");

            String sql = "SELECT examName, questions, facultyUsername, duration FROM exams WHERE BINARY examId='" + student.examId + "'";
            ResultSet rs = fetchStatement.executeQuery(sql);
            rs.next();
            examName = rs.getString("examName");
            facultyUsername = rs.getString("facultyUsername");
            questions = rs.getString("questions");
            duration = (long) rs.getInt("duration");

            studentUIDisplayExamID.setText("Exam ID: " + student.examId);
            studentUIDisplayRollNum.setText("Roll Num: " + student.rollNum);
            studentUIDisplayExamName.setText("Exam Name: " + examName);
            studentUIDisplayQuestion.setText(questions);

            insertStatement.setInt(1, Integer.parseInt(student.rollNum));
            insertStatement.setString(2, student.name);
            insertStatement.setString(3, student.examId);

            int i = insertStatement.executeUpdate();
            System.out.println("Result: " + i);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        studentUIChatBox = new javax.swing.JList<>();
        studentUIUploadPdfButton = new javax.swing.JButton();
        studentUIChatTextBox = new javax.swing.JTextField();
        studentUISendButton = new javax.swing.JButton();
        canvas1 = new java.awt.Canvas();
        studentUIExitButton = new javax.swing.JToggleButton();
        studentUIDisplayExamName = new javax.swing.JLabel();
        studentUIDisplayExamID = new javax.swing.JLabel();
        studentUIDisplayRollNum = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        studentUIDisplayQuestion = new javax.swing.JTextArea();
        hour1 = new javax.swing.JLabel();
        min = new javax.swing.JLabel();
        sec = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        studentUIDisplayCamera = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jScrollPane1.setViewportView(studentUIChatBox);

        studentUIUploadPdfButton.setText("UPLOAD PDF");
        studentUIUploadPdfButton.setToolTipText("");
        studentUIUploadPdfButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentUIUploadPdfButtonActionPerformed(evt);
            }
        });

        studentUISendButton.setText("SEND");
        studentUISendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentUISendButtonActionPerformed(evt);
            }
        });

        studentUIExitButton.setSelected(true);
        studentUIExitButton.setText("EXIT");
        studentUIExitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentUIExitButtonActionPerformed(evt);
            }
        });

        studentUIDisplayExamName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        studentUIDisplayExamName.setText("Exam Name: ");

        studentUIDisplayExamID.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        studentUIDisplayExamID.setText("Exam ID: ");

        studentUIDisplayRollNum.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        studentUIDisplayRollNum.setText("Roll Num:");

        studentUIDisplayQuestion.setEditable(false);
        studentUIDisplayQuestion.setColumns(20);
        studentUIDisplayQuestion.setRows(5);
        studentUIDisplayQuestion.setDragEnabled(true);
        jScrollPane2.setViewportView(studentUIDisplayQuestion);

        hour1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        hour1.setForeground(new java.awt.Color(0, 0, 20));
        hour1.setText("00");
        hour1.setMaximumSize(new java.awt.Dimension(20, 20));
        hour1.setMinimumSize(new java.awt.Dimension(20, 20));

        min.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        min.setForeground(new java.awt.Color(0, 0, 20));
        min.setText("00");
        min.setMaximumSize(new java.awt.Dimension(20, 20));
        min.setMinimumSize(new java.awt.Dimension(20, 20));

        sec.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        sec.setForeground(new java.awt.Color(0, 0, 20));
        sec.setText("00");
        sec.setMaximumSize(new java.awt.Dimension(20, 20));
        sec.setMinimumSize(new java.awt.Dimension(20, 20));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Sec");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Min");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Hour");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Remaining Time:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(280, 280, 280)
                .addComponent(studentUIExitButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(135, 135, 135)
                                                .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(studentUIDisplayRollNum)
                                            .addComponent(studentUIDisplayExamName))
                                        .addGap(288, 288, 288))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(studentUIDisplayExamID)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel1)
                                        .addGap(18, 18, 18)
                                        .addComponent(hour1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(3, 3, 3)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(min, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(sec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel9))))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap(115, Short.MAX_VALUE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)))
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(studentUIDisplayCamera, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(studentUIChatTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(studentUISendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(23, 23, 23))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(studentUIUploadPdfButton)
                        .addGap(69, 69, 69))))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {hour1, min, sec});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(min, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(hour1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(sec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel1))
                                    .addComponent(studentUIDisplayExamID))
                                .addGap(10, 10, 10)
                                .addComponent(studentUIDisplayExamName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(studentUIDisplayRollNum)
                                .addGap(16, 16, 16))
                            .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(studentUIChatTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(studentUISendButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                        .addComponent(studentUIUploadPdfButton)
                        .addGap(5, 5, 5))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(studentUIDisplayCamera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(studentUIExitButton)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {hour1, min, sec});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void studentUISendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentUISendButtonActionPerformed
        String text = "@" + facultyUsername + " " + studentUIChatTextBox.getText();
        writer.println(text);
        model.addElement(text);
        studentUIChatBox.setModel(model);
    }//GEN-LAST:event_studentUISendButtonActionPerformed

    private void studentUIUploadPdfButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentUIUploadPdfButtonActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Documents ONLY", "pdf", "txt"));
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            System.out.println("Selected file name: " + selectedFile.getName());
            System.out.println("Selected file path: " + selectedFile.getPath());
            new FileUpload(student).uploadFile(selectedFile.getPath(), selectedFile.getPath());
            JOptionPane.showMessageDialog(this, "File uploaded sucessfully");
        }
    }//GEN-LAST:event_studentUIUploadPdfButtonActionPerformed

    private void studentUIExitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentUIExitButtonActionPerformed

        int opt = JOptionPane.showConfirmDialog(this, "Are you sure?", "Exit", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            this.setEndTime();
            new FileUpload(student).uploadFile("", "local\\" + student.keyLogFile);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.dispose();
            System.exit(0);
        }
    }//GEN-LAST:event_studentUIExitButtonActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StudentUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentUI(new Student("19", "mn", "5_Milind_qekvD7.txt", "qekvD7")).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Canvas canvas1;
    private javax.swing.JLabel hour1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel min;
    private javax.swing.JLabel sec;
    private javax.swing.JList<String> studentUIChatBox;
    private javax.swing.JTextField studentUIChatTextBox;
    private javax.swing.JLabel studentUIDisplayCamera;
    private javax.swing.JLabel studentUIDisplayExamID;
    private javax.swing.JLabel studentUIDisplayExamName;
    private javax.swing.JTextArea studentUIDisplayQuestion;
    private javax.swing.JLabel studentUIDisplayRollNum;
    private javax.swing.JToggleButton studentUIExitButton;
    private javax.swing.JButton studentUISendButton;
    private javax.swing.JButton studentUIUploadPdfButton;
    // End of variables declaration//GEN-END:variables

    private void timerStart(JFrame frame) {
        Thread th = new Thread() {
            public void run() {
                System.out.println(duration);
                long seconds = duration;    //databaase connection for duration fetching
                while (seconds > 0) {
                    int Hours = (int) seconds / 3600;
                    int remainder = (int) seconds - Hours * 3600;
                    int Minutes = remainder / 60;
                    remainder = remainder - Minutes * 60;
                    int Secs = remainder;
                    try {
                        // 1 second delay
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(StudentUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    --seconds;
                    // System.out.println("Hour : " + Hours + " Minutes : " + Minutes + " Seconds : " + Secs);
                    hour1.setText("0" + Hours);
                    min.setText("0" + Minutes);
                    sec.setText("0" + Secs);
                }
                if (seconds < 0) {
                    frame.dispose();
                }
                // System.out.println("Exam Khatam");
                JOptionPane.showMessageDialog(null, "Exam Over!");
                System.exit(0);
            }
        };
        th.start();


    }

    private void getCamera(JFrame frame) {
        Thread th = new Thread() {
            public void run() {

                while (true) {
                    try {
                        Webcam webcam = Webcam.getDefault();
                        webcam.open();
                        if (webcam.isOpen()) {
                            BufferedImage image = webcam.getImage();
                            studentUIDisplayCamera.setSize(1500, 1000);
                            studentUIDisplayCamera.setIcon(new ImageIcon(image));
                            frame.validate();
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(StudentUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        };
//         };
        th.start();

    }
}
