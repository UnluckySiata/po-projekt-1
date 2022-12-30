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
    private int fieldsNumX;
    private int fieldsNumY;
    private int mapBoxWidth = 540;
    private int mapBoxHeight = 540;
    private double fieldDim;
    private double imgWidht;
    private double imgHeight;
    private int startEnergy;
    @FXML
    private AnchorPane simulationBox, mapBox, statisticsBox;

    @FXML
    private Button stopSim, endSimulationBtn;

    @FXML
    private Text animalsNum, plantsNum, emptyNum, dominantGenotype, avgAnimalEnergy, avgAnimalLifetime;

    public void setMap(AbstractWorldMap map, int startingEnergy) {
        this.map = map;
        this.startEnergy = startingEnergy;
    }
    public void drawGrid(){
        this.fieldsNumY = map.getHeight();
        this.fieldsNumX = map.getWidth();

        int col = 0, row = 0, maxC = this.fieldsNumY, maxR = this.fieldsNumX;
        if (this.fieldsNumY > this.fieldsNumX) {
            this.fieldDim = (mapBoxHeight/this.fieldsNumY);
        } else {
            this.fieldDim = (mapBoxWidth/this.fieldsNumX);
        }

        ColumnConstraints colC = new ColumnConstraints(mapBoxWidth / fieldsNumX);
        RowConstraints rowC = new RowConstraints(mapBoxHeight / fieldsNumY);

        for (int i = 0; i < this.fieldsNumX; i+=1) {
            grid.getColumnConstraints().add(colC);
        }

        for (int i = 0; i < fieldsNumY; i+=1) {
            grid.getRowConstraints().add(rowC);
        }

        for (int i = 0; i < maxR; ++i) {
            for (int j = 0; j < maxC; ++j){
                Label label = new Label(" ");
                GridPane.setHalignment(label, HPos.CENTER);
                grid.add(label, i, j);
            }
        }

        addElements(); //to wyjątek rzuca zrób try catcha
        showStats();
        mapBox.getChildren().add(grid);
        grid.setGridLinesVisible(true);
    }

    public void refreshGrid() throws FileNotFoundException {
        addElements(); //to wyjątek rzuca zrób try catcha
        showStats();
        mapBox.getChildren().add(grid);
        grid.setGridLinesVisible(true);
    }


    public void addElements(){
        calculateImgDim();
        int x;
        int y;

        LinkedList<Animal> animalsFields = map.getAnimals();
        for (int i = 0; i < animalsFields.size(); i++) {
            Animal animalOnField = animalsFields.get(i);
            GuiElementBox animal = new GuiElementBox(startEnergy, animalOnField.getEnergy(),this.imgWidht, this.imgWidht);
            x = animalOnField.getPosition().x;
            y = animalOnField.getPosition().y;
            grid.add(animal.vbox, x, y);
        }
        x = 0;
        y = 0;
        boolean[] grassFields = map.getPlants();
        for (int i = 0; i < grassFields.length; i++) {
            x = i % this.fieldsNumX;
            y = i / this.fieldsNumX;
            if (grassFields[i] && !map.isOccupied(x, y)) {
                GuiElementBox grass = new GuiElementBox(this.imgWidht, this.imgWidht);
                grid.add(grass.vbox, x, y);
            }
            else {
                Label label = new Label(" ");
                GridPane.setHalignment(label, HPos.CENTER);
                grid.add(label, x, y);
            }
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
        avgAnimalEnergy.setText(Double.toString(map.stats.getAverageEnergy()));
        avgAnimalLifetime.setText(Double.toString(map.stats.getAverageLifetime()));
    }

    public void refreshMap() {
        Platform.runLater(() -> {
            mapBox.getChildren().clear();
            grid.getChildren().clear();
            grid.setGridLinesVisible(false);
            try {
                refreshGrid();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Button getStopBtn() {
        return stopSim;
    }

    public Button getEndSimulationBtn() {
        return endSimulationBtn;
    }

}
