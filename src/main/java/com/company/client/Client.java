package com.company.client;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public Client(Socket socket, String username){
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        } catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage(String msgToGroupeChat){
        try {
            bufferedWriter.write("[" + username + "] " + msgToGroupeChat);
            bufferedWriter.newLine();
            bufferedWriter.flush();

        } catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
            System.out.println("Error sending message");
        }
    }

    public void listenForMessage(VBox vBox){
        new Thread(() -> {
            String msgFromGroupeChat;

            while(socket.isConnected()){
                try {
                    msgFromGroupeChat = bufferedReader.readLine();
                    ChatController.addLabel(msgFromGroupeChat, vBox);
                    System.out.println(msgFromGroupeChat);
                } catch (IOException e){
                    closeEverything(socket, bufferedReader, bufferedWriter);
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try {
            if (bufferedReader != null) bufferedReader.close();
            if (bufferedWriter != null) bufferedWriter.close();
            if (socket != null) socket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /*public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        Socket socket = new Socket("localhost", 1234);
        Client client = new Client(socket, username);
        client.listenForMessage();
        client.sendMessage();
    }*/
}
