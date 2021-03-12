package server;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler extends Thread {

    private final Socket socket;
    private final ChatServer server;
    private PrintWriter writer;
    public String username;

    public ClientHandler(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            listUsers();

            username = reader.readLine();
            server.addUser(username, this);

            String serverMessage = "New User Connected: " + username;
            server.sendToEveryone(serverMessage, this);

            String clientMessage;

            do {
                clientMessage = reader.readLine().trim();
                serverMessage = "[" + username + "]: " + clientMessage;

                if (clientMessage.startsWith("@")) {
                    int indexOfSpace = clientMessage.indexOf(' ');
                    if (indexOfSpace < 2) {
                        server.sendToEveryone(serverMessage, this);
                    } else {
                        System.out.println(clientMessage.substring(1, indexOfSpace));
                        server.sendTo(serverMessage, clientMessage.substring(1, indexOfSpace));
                    }
                } else {
                    server.sendToEveryone(serverMessage, this);
                }
            } while (!clientMessage.equals("exit"));
        } catch (IOException e) {
            System.out.println("Error in ClientHandler: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                server.removeUser(username);
                socket.close();
                String serverMessage = username + " disconnected...";
                server.sendToEveryone(serverMessage, this);
            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void listUsers() {
        if (server.hasUsers()) {
            writer.println("Connected users: " + server.getUserNames());
        } else {
            writer.println("No other users connected");
        }
    }

    void sendMessage(String message) {
        writer.println(message);
    }
}
