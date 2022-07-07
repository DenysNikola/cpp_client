package com.company.client;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FirstWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button EnterName;

    @FXML
    private TextField NameField;

    @FXML
    private Label warningLabel;

    private String username = "default";

    public void setName(String name){
        username = name;
    }

    public String getName(){
        return username;
    }
    DB db = null;

    private ArrayList<String> names = new ArrayList<>();

    private boolean is_name_exist;

    @FXML
    void initialize() {
        db = new DB();


        EnterName.setOnAction(actionEvent -> {
            try {
                names = db.getNames();
            }catch (SQLException e){
                e.printStackTrace();
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }

            is_name_exist = isNameExist(NameField.getText(), names);

            if (!is_name_exist){
                setName(NameField.getText());
                //EnterName.setText(username);
                try {
                    db.insertName(NameField.getText());
                }catch (SQLException e){
                    e.printStackTrace();
                }catch (ClassNotFoundException e){
                    e.printStackTrace();
                }


                EnterName.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/company/client/chat-view.fxml"));

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ChatController controller = loader.getController();
                controller.initialize(username);
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();
            }else{
                System.out.println("Name Exist");
                System.out.println(names);
                System.out.println(NameField.getText());
                warningLabel.setText("tag name is already taken");
            }



        });

    }

    public boolean isNameExist(String name, ArrayList<String> names_list){
        return names_list.contains(name);
    }



}
