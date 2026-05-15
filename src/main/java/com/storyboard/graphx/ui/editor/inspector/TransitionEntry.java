package com.storyboard.graphx.ui.editor.inspector;

import com.storyboard.logic.Condition;
import com.storyboard.logic.GlobalVariable;
import com.storyboard.logic.ProjectSettings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TransitionEntry extends StackPane {

    @FXML private FontIcon deleteButton;
    @FXML private ComboBox<GlobalVariable> variableBox;
    @FXML private ComboBox<Condition.Operation> operationBox;
    @FXML private TilePane tilePane;
    @FXML private Label conditionLabel;

    private GlobalVariable globalVariable;

    private Spinner<Integer> intSpinner;
    private Spinner<Double> doubleSpinner;
    private ComboBox<Boolean> boolBox;
    private TextField stringField;

    private final Condition condition;

    public TransitionEntry(VBox entryPane, List<Condition> conditionList, Condition condition){

        this.condition = condition;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/storyboard/graphx/ui/editor/inspector/TransitionEntry.fxml"));

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        deleteButton.setOnMousePressed(e -> {
            entryPane.getChildren().remove(this);
            conditionList.remove(condition);
            e.consume();
        });

        initInputFields();

        int index = conditionList.indexOf(condition);
        conditionLabel.setText(conditionLabel.getText() + " #" + (index + 1));

        operationBox.setItems(FXCollections.observableArrayList(Condition.Operation.values()));
        variableBox.setItems(ProjectSettings.getInstance().getGlobalVarList());
        variableBox.setCellFactory(_ -> typeCell());
        variableBox.setButtonCell(typeCell());



        if(condition.getOperation() != null)
            operationBox.setValue(condition.getOperation());
        if(condition.getVarName() != null) {
            GlobalVariable var = ProjectSettings.getInstance().findByName(condition.getVarName());
            variableBox.setValue(var);
            if (condition.getCompareValue() != null) {
                switchInputField(getInputField(var.getType(), condition.getCompareValue()));
            }
        }

        operationBox.valueProperty().addListener((_, _, newVal) -> {
            condition.setOperation(newVal);
        });

        variableBox.valueProperty().addListener((_, _, newVal) -> {
            condition.setVarName(newVal.getName());
            condition.setCompareValue(null);
            switchInputField(getInputField(newVal.getType(), condition.getCompareValue()));
        });
    }

    private Control getInputField(GlobalVariable.Type type, Object value) {
        return switch (type) {
            case INT -> {
                if (value != null) intSpinner.getValueFactory().setValue((Integer) value);
                yield intSpinner;
            }
            case DOUBLE -> {
                if (value != null) doubleSpinner.getValueFactory().setValue((Double) value);
                yield doubleSpinner;
            }
            case STRING -> {
                if (value != null) stringField.setText((String) value);
                yield stringField;
            }
            default -> {
                if (value != null) boolBox.setValue((Boolean) value);
                yield boolBox;
            }
        };
    }


    private void initInputFields() {
        boolBox = new ComboBox<>();
        boolBox.setItems(FXCollections.observableArrayList(Boolean.TRUE, Boolean.FALSE));
        boolBox.setValue(true);

        intSpinner = new Spinner<>(0, Integer.MAX_VALUE, 0);
        intSpinner.setEditable(true);
        intSpinner.getEditor().focusedProperty().addListener((_, _, isFocused) -> {
            if (!isFocused) intSpinner.increment(0);
        });

        doubleSpinner = new Spinner<>(0, Double.MAX_VALUE, 0);
        doubleSpinner.setEditable(true);
        doubleSpinner.getEditor().focusedProperty().addListener((_, _, isFocused) -> {
            if (!isFocused) doubleSpinner.increment(0);
        });

        stringField = new TextField();
        stringField.setPrefWidth(tilePane.getPrefTileWidth());
        stringField.setPrefHeight(tilePane.getPrefTileHeight());
        stringField.setPromptText("value...");
    }

    public String validate() {
        List<String> missing = new ArrayList<>();
        if (variableBox.getValue() == null) missing.add("Variable");
        if (operationBox.getValue() == null) missing.add("Operation");
        if (tilePane.getChildren().size() < 6) missing.add("Value");

        if (missing.isEmpty()) return null; // valid
        return conditionLabel.getText() + " is missing: " + String.join(", ", missing);
    }

    public void save() {
        if (validate() != null) return;
        condition.setOperation(operationBox.getValue());
        condition.setVarName(variableBox.getValue().getName());
        condition.setCompareValue(switch (variableBox.getValue().getType()) {
            case INT -> intSpinner.getValue();
            case DOUBLE -> doubleSpinner.getValue();
            case STRING -> stringField.getText();
            default -> boolBox.getValue();
        });
    }



    private void switchInputField(Node node){
        if(tilePane.getChildren().size() == 6)
            tilePane.getChildren().removeLast();
        tilePane.getChildren().add(node);
    }


    private ListCell<GlobalVariable> typeCell(){
        return new ListCell<>() {
            @Override
            protected void updateItem(GlobalVariable globalVariable, boolean b) {
                super.updateItem(globalVariable, b);
                if (b || globalVariable == null) {
                    textProperty().unbind();
                    setText(null);
                } else {
                    textProperty().bind(globalVariable.nameProperty());
                }
            }
        };
    }
}
