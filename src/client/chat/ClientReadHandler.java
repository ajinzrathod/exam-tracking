package client.chat;

import java.io.*;
import java.net.*;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JComboBox;

public class ClientReadHandler extends Thread {

    private BufferedReader reader;
    private final Socket socket;
    private final ChatClient client;
    private final DefaultListModel model;
    private final JList chatbox;
    private final JComboBox<String> studentList;

    public ClientReadHandler(Socket socket, ChatClient client, DefaultListModel model, JList chatbox, JComboBox<String> studentList) {
        this.socket = socket;
        this.client = client;
        this.model = model;
        this.chatbox = chatbox;

        if (studentList != null) {
            this.studentList = studentList;
        } else {
            this.studentList = null;
        }

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Error getting input stream: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String response = reader.readLine();
                System.out.println("\n" + response);
                model.addElement(response);

                if (this.studentList != null) {
                    if (response.startsWith("Connected")) {
                        int index = response.indexOf("[");
                        System.out.println(response.substring(index, response.length()));
                        String list[] = response.substring(index + 1, response.length() - 1).split(", ");
                        for (String token : list) {
                            studentList.addItem(token);
                        }
                    } else if (response.startsWith("New User Connected")) {
                        int index = response.indexOf(":");
                        System.out.println(response.substring(index, response.length()));
                        String list = response.substring(index + 1, response.length()).trim();
                        studentList.addItem(list);
                    } else if (response.contains("disconnected...")) {
                        int index = response.indexOf("disconnected");
                        String item = response.substring(0, index).trim();
                        System.out.println(item);
                        studentList.removeItem(item);
                    }
                }

                this.chatbox.setModel(model);
                // prints the username after displaying the server's message
                if (client.getUsername() != null) {
                    System.out.print("[" + client.getUsername() + "]: ");
                }
            } catch (IOException e) {
                System.out.println("Error reading from server: " + e.getMessage());
                break;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                break;
            }
        }
    }
}
