package logger;

import client.Student;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class KeyLoggerFile {

    FileWriter myWriter;
    final Student user;

    public KeyLoggerFile(Student user) {
        this.user = user;
        // Creating new directory if not exists
        File theDir = new File("local/");
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
    }

    public void keyLoggerWriter(CharSequence log) throws IOException {
        try {
            myWriter = new FileWriter("local/"+user.keyLogFile, true); // write key values into file.
        } catch (IOException e) {
            e.printStackTrace();
        }
        myWriter.append(log); // append key values
        myWriter.close();
    }
}
