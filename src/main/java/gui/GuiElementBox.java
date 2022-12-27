package gui;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import po.evolution.Animal;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {

    VBox vbox = new VBox(0);
    Image image = null;

    //konstruktor dla zwierza
    public GuiElementBox(Animal animal, double width, double height) {
        try {
            image = animal.getImage();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.setImage(image, width, height);

    }

    //konstruktor dla trawy
    public GuiElementBox(double width, double height) throws FileNotFoundException {
        try {
            image = new Image(new FileInputStream("src/main/resources/grass.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.setImage(image, width, height);
    }

    public void setImage(Image image, double width, double height) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        vbox.getChildren().addAll(imageView);
        vbox.setAlignment(Pos.CENTER);
    }
}
