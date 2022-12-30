package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import po.evolution.SimulationParameters;
import po.evolution.SimulationVariant;
import po.evolution.WorldVariant;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static po.evolution.ConfigurationParser.writeAndGet;

public class FormController {

    private List<String> userConfig = new ArrayList<>();
    private List<String> textToValidation = new ArrayList<>();
    private List<RadioButton> buttonsToValidation = new ArrayList<>();
    private List<String> validatedConfig = new ArrayList<>();
    private SimulationParameters userParameters;

    @FXML
    private TextField userHeight, userWidth, userPlantNumber, userPlantEnergy, userPlantPerDay, userAnimalNumber, userAnimalEnergy, userNeededEnergy, userProcreationEnergy, userMinMutations, userMaxMutations, userGenotypeLen;

    @FXML
    private RadioButton earth, infernalPortal, fullRandomness, slightCorrection, equator, toxicCorpses, fullPredestination, someMadness;

    @FXML
    private Button start, stop, exConfig1, exConfig2;


    public void addConfigFromUser() {
        textToValidation.clear();
        buttonsToValidation.clear();
        userConfig.clear();

        textToValidation.add(userHeight.getText());
        textToValidation.add(userWidth.getText());
        textToValidation.add(userPlantNumber.getText());
        textToValidation.add(userPlantEnergy.getText());
        textToValidation.add(userPlantPerDay.getText());
        textToValidation.add(userAnimalNumber.getText());
        textToValidation.add(userAnimalEnergy.getText());
        textToValidation.add(userNeededEnergy.getText());
        textToValidation.add(userProcreationEnergy.getText());
        textToValidation.add(userMinMutations.getText());
        textToValidation.add(userMaxMutations.getText());
        textToValidation.add(userGenotypeLen.getText());
        buttonsToValidation.add(earth);
        buttonsToValidation.add(infernalPortal);
        buttonsToValidation.add(fullRandomness);
        buttonsToValidation.add(slightCorrection);
        buttonsToValidation.add(equator);
        buttonsToValidation.add(toxicCorpses);
        buttonsToValidation.add(fullPredestination);
        buttonsToValidation.add(someMadness);
    }
    public List<String> validation(List<String> text, List<RadioButton> buttons) throws IllegalArgumentException{
        for (String input : text) {
            if (input.equals("")) {
                throw new IllegalArgumentException("Wszystkie pola muszą być wypełnione!");
            } else if (!input.matches("[0-9]+")) {
                throw new IllegalArgumentException("Tylko liczby!");
            }
            userConfig.add(input);
        }
        for (int i = 0; i < buttons.size() - 1; i += 2) {
            String button = getFromToggleGroup(buttons.get(i), buttons.get(i + 1));
//            System.out.println(button);
            if (button.equals("")) {
                throw new IllegalArgumentException("Z każej pary chociaż jeden przycisk musi być zaznaczony!");
            }
            userConfig.add(button);
        }
        System.out.println(userConfig);
        for (int i=0; i < userConfig.size(); i++) {
            System.out.println(userConfig.get(i));
        }
        return userConfig;
    }
    public String getFromToggleGroup(RadioButton button1, RadioButton button2){
        if (button1.isSelected()) {
            return String.valueOf(SimulationVariant.parse(button1.getText()));
        } else if (button2.isSelected()) {
            return String.valueOf(SimulationVariant.parse(button2.getText()));
        } else {
            return "";
        }
    }

    public SimulationParameters getUserConfig() {
        addConfigFromUser();
        validatedConfig = validation(textToValidation, buttonsToValidation);
        System.out.println(validatedConfig);
//        SimulationParameters p = new SimulationParameters();
        return writeAndGet(validatedConfig, "userconfig.csv");
//        return p;
    }

    public void displayExampleConfig(SimulationParameters configs) {
        Hashtable<String, String> configTab = new Hashtable<String, String>();
        configTab = configs.configsInTab();
        System.out.println(configTab);
        userHeight.setText(configTab.get("mapHeight"));
        userWidth.setText(configTab.get("mapWidth"));
        userPlantNumber.setText(configTab.get("initialPlantNum"));
        userPlantEnergy.setText(configTab.get("energyFromPlant"));
        userPlantPerDay.setText(configTab.get("plantsPerDay"));
        userAnimalNumber.setText(configTab.get("initialAnimalNum"));
        userAnimalEnergy.setText(configTab.get("startingEnergy"));
        userNeededEnergy.setText(configTab.get("energyNeeded"));
        userProcreationEnergy.setText(configTab.get("minMutations"));
        userMinMutations.setText(configTab.get("maxMutations"));
        userMaxMutations.setText(configTab.get("genotypeLength"));
        userGenotypeLen.setText(configTab.get("mapHeight"));
        if (configTab.get("worldVariant").equals("EARTH")) {
            earth.setSelected(true);
        } else {
            infernalPortal.setSelected(true);
        }
        if (configTab.get("plantGrowthVariant").equals("EQUATOR")){
            equator.setSelected(true);
        } else {
            toxicCorpses.setSelected(true);
        }
        if (configTab.get("mutationVariant").equals("FULL_RANDOMNESS")) {
            fullRandomness.setSelected(true);
        } else {
            slightCorrection.setSelected(true);
        }
        if (configTab.get("geneProgressionVariant").equals("FULL_PREDESTINATION")) {
            fullPredestination.setSelected(true);
        } else {
            someMadness.setSelected(true);
        }
        userHeight.setText(configTab.get("mapHeight"));
    }

    public Button getStartButton() {
        return start;
    }

    public Button getStopButton() {
        return stop;
    }

    public Button getExConfig1Btn() {
        return exConfig1;
    }

    public Button getExConfig2Btn() {
        return exConfig2;
    }

}
