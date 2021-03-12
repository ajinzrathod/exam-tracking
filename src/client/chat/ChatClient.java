package client.chat;

import java.net.*;
import java.io.*;

public class ChatClient {

    private final String host;
    private final int port;
    private String username;

    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        try {
            Socket socket = new Socket(host, port);

            System.out.println("Connected to the server...");

            var writeHandler = new ClientWriteHandler(socket, this);
            writeHandler.start();

        } catch (UnknownHostException e) {
            System.out.println("Server not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    void setUserName(String username) {
        this.username = username;
    }

    String getUsername() {
        return this.username;
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient("127.0.0.1", 8989);
        client.start();
    }
}
