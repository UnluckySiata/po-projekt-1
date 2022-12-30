package gui;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.io.FileNotFoundException;

public class GuiElementBox {

    VBox vbox = new VBox(0);
    Image image = null;

    //konstruktor dla zwierza
//    public GuiElementBox(Animal animal, double width, double height) {
//        try {
//            image = animal.getImage();
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        this.setImage(image, width, height);
//
//    }

    public GuiElementBox(int maxEnergy, int energy, double width, double height) {
        Circle anim = new Circle();
        anim.setRadius(width/2);
        if (maxEnergy/2 > energy && energy > maxEnergy/3) {
            anim.setFill(Color.valueOf("Orange"));
        } else if (energy <= maxEnergy/3) {
            anim.setFill(Color.valueOf("Tomato"));
        } else if (energy == 0) {
            anim.setFill(Color.valueOf("Black"));
        } else {
            anim.setFill(Color.valueOf("Grey"));
        }
        vbox.getChildren().addAll(anim);
        vbox.setAlignment(Pos.CENTER);
        vbox.setOnMouseClicked(event -> {
            System.out.println("klikłem");
        });
    }

    //konstruktor dla trawy
//    public GuiElementBox(double width, double height) throws FileNotFoundException {
//        try {
//            image = new Image(new FileInputStream("src/main/resources/grass.png"));
//            Circle grass = new Circle();
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        this.setImage(image, width, height);
//    }
    public GuiElementBox(double width, double height) throws FileNotFoundException {
        Circle grass = new Circle();
        grass.setRadius(width/2);
        grass.setFill(Color.valueOf("Green"));
        vbox.getChildren().addAll(grass);
        vbox.setAlignment(Pos.CENTER);
    }

    public void setImage(Image image, double width, double height) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        vbox.getChildren().addAll(imageView);
        vbox.setAlignment(Pos.CENTER);
    }
}
