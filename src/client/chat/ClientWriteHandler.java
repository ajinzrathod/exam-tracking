package client.chat;

import java.io.*;
import java.net.*;
import java.util.Scanner;
 
public class ClientWriteHandler extends Thread {
    private PrintWriter writer;
    private final Socket socket;
    private final ChatClient client;
 
    public ClientWriteHandler(Socket socket, ChatClient client) {
        this.socket = socket;
        this.client = client;
 
        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException e) {
            System.out.println("Error getting output stream: " + e.getMessage());
        }
    }
 
    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
 
        System.out.print("Enter your name: ");
        String userName = sc.nextLine();
        client.setUserName(userName);
        writer.println(userName);
 
        String text;
 
        do {
            System.out.print("[" + userName + "]: ");
            text = sc.nextLine();
            writer.println(text);
        } while (!text.equals("exit"));
 
        try {
            socket.close();
        } catch (IOException e) { 
            System.out.println("Error writing to server: " + e.getMessage());
        } catch (Exception e) { 
            System.out.println("Error: " + e.getMessage());
        }
    }
}