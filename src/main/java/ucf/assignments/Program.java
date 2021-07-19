package ucf.assignments;

/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 William Zheng
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Program extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // locate FXMl file
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Inventory.fxml")));
            Scene scene = new Scene(root);
            // setup scene
            primaryStage.setScene(scene);
            // name scene
            primaryStage.setTitle("Program.exe");
            // open scene
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}