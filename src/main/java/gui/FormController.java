package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class FormController {

    private List<String> userConfig = new ArrayList<>();
    private List<String> textToValidation = new ArrayList<>();
    private List<RadioButton> buttonsToValidation = new ArrayList<>();

    @FXML
    private TextField userHeight, userWidth, userPlantNumber, userPlantEnergy, userAnimalNumber, userAnimalEnergy, userNeededEnergy, userProcreationEnergy, userMinMutations, userMaxMutations;

    @FXML
    private RadioButton earth, infernalPortal, fullRandomness, slightCorrection, equator, toxicCorpses, fullPredestination, someMadness;

    @FXML
    private Button start, stop;

    public void addConfigFromUser() {
        textToValidation.clear();
        buttonsToValidation.clear();
        userConfig.clear();

        textToValidation.add(userHeight.getText());
        textToValidation.add(userWidth.getText());
        textToValidation.add(userPlantNumber.getText());
        textToValidation.add(userPlantEnergy.getText());
        textToValidation.add(userAnimalNumber.getText());
        textToValidation.add(userAnimalEnergy.getText());
        textToValidation.add(userNeededEnergy.getText());
        textToValidation.add(userProcreationEnergy.getText());
        textToValidation.add(userMinMutations.getText());
        textToValidation.add(userMaxMutations.getText());
        buttonsToValidation.add(earth);
        buttonsToValidation.add(infernalPortal);
        buttonsToValidation.add(fullRandomness);
        buttonsToValidation.add(slightCorrection);
        buttonsToValidation.add(equator);
        buttonsToValidation.add(toxicCorpses);
        buttonsToValidation.add(fullPredestination);
        buttonsToValidation.add(someMadness);
    }
    // przykładowa tablica z konfiguracją: [height, width, initialPlantNum, energyFromPlant, initialAnimalNum,
    // startingEnergy,energyNeeded, procreationEnergyShare, minMutations, maxMutations, WorldVariant(true-earth, false-infernalPortal),
    // MutationVariant(true-fullRandomness, flase-slightCorrection), PlantGrowthVariant (true-equator, false-toxicCorpses),
    // GeneProgressionVariant (true-fullPredestination, flase-someMadness)]
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
            return "true";
        } else if (button2.isSelected()) {
            return "false";
        } else {
            return "";
        }
    }
    public Button getStartButton() {
        return start;
    }

    public List<String> getUserConfig() {
        addConfigFromUser();
        return validation(textToValidation, buttonsToValidation);
    }

    public Button getStopButton() {
        return stop;
    }


    public int getHeight() {
        return Integer.parseInt(userConfig.get(0));
    }
    public int getWidth() {
        return Integer.parseInt(userConfig.get(1));
    }

}
