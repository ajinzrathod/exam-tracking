package server;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.DefaultListModel;

public class ChatServer {

    private final Map<String, ClientHandler> users = new HashMap<>();
    private DefaultListModel userModel;
    private final int SERVER_PORT;

    public ChatServer(int port) {
        this.SERVER_PORT = port;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {

            System.out.println("----- Chat Server -----");
            System.out.println("Listening on port " + SERVER_PORT + "...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New User Connected...");
                ClientHandler newUser = new ClientHandler(socket, this);
                newUser.start();
            }
        } catch (IOException e) {
            System.out.println("Error in the server: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        int port;
        try {
            FileReader reader = new FileReader("src/server.properties");
            Properties p = new Properties();
            p.load(reader);

            port = Integer.parseInt(p.getProperty("SERVER_PORT"));
            ChatServer server = new ChatServer(port);
            server.start();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    void sendToEveryone(String message, ClientHandler notToUser) {
        for (Map.Entry<String, ClientHandler> aUser : users.entrySet()) {
            if (aUser.getValue() != notToUser) {
                aUser.getValue().sendMessage(message);
            }
        }
    }

    void sendTo(String message, String username) {
        var user = getUser(username);
        if (user != null) {
            user.sendMessage(message);
        }
    }

    private ClientHandler getUser(String username) {
        for (Map.Entry<String, ClientHandler> aUser : users.entrySet()) {
            if (aUser.getKey().equals(username)) {
                return aUser.getValue();
            }
        }
        return null;
    }

    void addUser(String userName, ClientHandler userThread) {
        users.put(userName, userThread);
    }

    void removeUser(String userName) {
        users.remove(userName);
    }

    Set<String> getUserNames() {
        return this.users.keySet();
    }

    boolean hasUsers() {
        return !this.users.isEmpty();
    }
}
