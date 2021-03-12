package client.student;

import client.Student;
import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class FileUpload {

    private static String FILE_SERVER_URL;
    private static int FILE_SERVER_PORT;

    final Student user;

    public FileUpload(Student user) {
        this.user = user;

        try {
            Properties properties = new Properties();
            FileReader reader = new FileReader("src/server.properties");
            properties.load(reader);
            FILE_SERVER_URL = properties.getProperty("FILE_SERVER_URL");
            FILE_SERVER_PORT = Integer.parseInt(properties.getProperty("FILE_SERVER_PORT"));

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        var user = new Student("10", "Pradip", "10_Pradip_xyz.txt", "abc");
        new FileUpload(user).uploadFile("", "C:\\Users\\Nirav Chavda\\Downloads\\Question Bank DAD - Digital Copy.docx");
    }

    public void uploadFile(String path, String fileName) {
        try (Socket socket = new Socket(FILE_SERVER_URL, FILE_SERVER_PORT)) {
            File file = new File(fileName);

            OutputStream os = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);

            BufferedOutputStream bos = new BufferedOutputStream(os);
            long length = file.length();
            byte[] bytes = new byte[4 * 1024];
            InputStream in = new FileInputStream(file);
            OutputStream out = socket.getOutputStream();

            dos.writeLong(length);
            dos.writeUTF(file.getName());
            dos.writeUTF(user.rollNum);
            dos.writeUTF(user.name);
            dos.writeUTF(user.examId);
            int count;

            while ((count = in.read(bytes)) != -1) {
                out.write(bytes, 0, count);
                out.flush();
            }

            System.out.println("File uploaded Successfully!");

            out.close();
            in.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
