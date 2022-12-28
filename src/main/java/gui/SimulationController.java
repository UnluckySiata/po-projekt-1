package gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import po.evolution.AbstractWorldMap;
import po.evolution.Animal;
import po.evolution.SimulationEngine;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;

public class SimulationController {

    private AbstractWorldMap map;
    private SimulationEngine engine;
    private GridPane grid = new GridPane();
    private SimulationController simulationController;
    private int fieldsNumX = 10;
    private int fieldsNumY = 10;
    private int mapBoxWidth = 540;
    private int mapBoxHeight = 540;
    private double fieldDim;
    private double imgWidht;
    private double imgHeight;
    @FXML
    private AnchorPane simulationBox, mapBox, statisticsBox;

    @FXML
    private Button stopSim, resumeSim;

    @FXML
    private Text animalsNum, plantsNum, emptyNum, dominantGenotype, avgAnimalEnergy, avgAnimalLifetime;

    public void setMap(AbstractWorldMap map) {
        this.map = map;
    }
    public void drawGrid() throws FileNotFoundException {

        int col = 0, row = 0, maxC = fieldsNumY, maxR = fieldsNumX;
        this.fieldDim = (mapBoxWidth/fieldsNumX);

        ColumnConstraints colC = new ColumnConstraints(this.fieldDim);
        RowConstraints rowC = new RowConstraints(this.fieldDim);

        for (int i = 0; i <= maxC + 1; ++i) {
            grid.getColumnConstraints().add(colC);
        }

        for (int i = 0; i <= maxR + 1; ++i) {
            grid.getRowConstraints().add(rowC);
        }
        addElements(); //to wyjątek rzuca zrób try catcha
        this.showStats();
        mapBox.getChildren().add(grid);
        grid.setGridLinesVisible(true);
    }


    public void addElements() throws FileNotFoundException {
        calculateImgDim();
        int x;
        int y;
        boolean[] grassFields = map.getPlants();
        for (int i = 0; i < grassFields.length; i++) {
            if (grassFields[i]) {
                GuiElementBox grass = new GuiElementBox(this.imgWidht, this.imgWidht);
                x = i % fieldsNumY;
                y = (int)(Math.floor(i/fieldsNumX));
                grid.add(grass.vbox, x, y);
            }
        }

        x = 0;
        y = 0;
        LinkedList<Animal> animalsFields = map.getAnimals();
        for (int i = 0; i < animalsFields.size(); i++) {
            Animal animalOnField = animalsFields.get(i);
            GuiElementBox animal = new GuiElementBox(animalOnField, this.imgWidht, this.imgWidht);
            x = animalOnField.getPosition().x;
            y = animalOnField.getPosition().y;
            grid.add(animal.vbox, x, y);
        }

    }

    public void calculateImgDim() {
        this.imgHeight = this.fieldDim - this.fieldDim/5;
        this.imgWidht = this.fieldDim - this.fieldDim/5;
    }

    public void showStats() {
        animalsNum.setText(Integer.toString(map.stats.getAnimalNum()));
        plantsNum.setText(Integer.toString(map.stats.getPlantNum()));
        emptyNum.setText(Integer.toString(map.stats.getFree()));
        dominantGenotype.setText(Arrays.toString(map.stats.getDominantGenotype()));
        avgAnimalEnergy.setText(String.valueOf(map.stats.getAverageEnergy()));
        avgAnimalLifetime.setText(String.valueOf(map.stats.getAverageLifetime()));
    }

    public void refreshMap() {
        Platform.runLater(() -> {
            grid.getChildren().clear();
            grid.setGridLinesVisible(false);
            grid.getColumnConstraints().clear();
            grid.getRowConstraints().clear();
            try {
                drawGrid();
                System.out.println("haloooo");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Button getStopBtn() {
        return stopSim;
    }

    public Button getResumeBtn() {
        return resumeSim;
    }
}
