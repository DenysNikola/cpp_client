package com.company.client;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


public class ChatController {


    @FXML
    private Button EnterMsgBtn;

    @FXML
    private TextField MsgTextField;

    @FXML
    private Label NameLabel;

    @FXML
    private ScrollPane ScrollP;

    @FXML
    private VBox VboxId;

    private String username;

    private Client client;

    public String getUsername() {
        return username;
    }

    void initialize(String username) {
        NameLabel.setText(username);
        this.username = NameLabel.getText();
        System.out.println(getUsername());
        //NameLabel.setText(username);
        try {
            client = new Client(new Socket("localhost", 1234), this.username);

            System.out.println("connected to server with name: " + this.username);
        } catch (IOException e){
            e.printStackTrace();
        }


        client.listenForMessage(VboxId);
        client.sendMessage("");

        EnterMsgBtn.setOnAction(actionEvent -> {
            String msgToSend = MsgTextField.getText();

            if (!msgToSend.isEmpty()){

                VboxId.getChildren().add(new Label("you: " + msgToSend));

                client.sendMessage(msgToSend);
                MsgTextField.clear();
            }
        });
        System.out.println(getUsername());

    }

    public static void addLabel(String msgFromGroupeChat, VBox vBox){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(new Label(msgFromGroupeChat));
            }
        });


    }




}
