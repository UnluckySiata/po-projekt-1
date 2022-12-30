package gui;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GuiElementBox {

    VBox vbox = new VBox(0);

    //konstruktor dla zwierza

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
    }

    //konstruktor dla trawy
    public GuiElementBox(double width, double height){
        Circle grass = new Circle();
        grass.setRadius(width/2);
        grass.setFill(Color.valueOf("Green"));
        vbox.getChildren().addAll(grass);
        vbox.setAlignment(Pos.CENTER);
    }

}
