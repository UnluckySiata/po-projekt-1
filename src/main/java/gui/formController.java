package gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.util.EventListener;
import java.util.Objects;

public class formController extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/forms.fxml")));

        primaryStage.setTitle("hello world");
        primaryStage.setScene(new Scene(root, 600, 826));
        primaryStage.show();
    }


//testuje scene buildera
//    @FXML
//    Circle MyCircle;
//
//    private double x;
//    private double y;
//
//    public void up(javafx.event.ActionEvent e) {
//        MyCircle.setCenterY(y-=5);
//        System.out.println("UP");
//    }
//    public void right(javafx.event.ActionEvent e) {
//        MyCircle.setCenterX(x+=5);
//        System.out.println("RIGHT");
//    }
//    public void down(javafx.event.ActionEvent e) {
//        MyCircle.setCenterY(y+=5);
//        System.out.println("DOWN");
//    }
//    public void left(javafx.event.ActionEvent e) {
//        MyCircle.setCenterX(x-=5);
//        System.out.println("LEFT");
//    }

}
