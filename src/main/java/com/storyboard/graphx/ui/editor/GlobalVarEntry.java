package com.storyboard.graphx.ui.editor;

import com.storyboard.logic.GlobalVariable;
import com.storyboard.logic.ProjectSettings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class GlobalVarEntry extends StackPane {

    @FXML private ComboBox<GlobalVariable.Type> varBox;
    @FXML private TextField varName;
    @FXML private HBox varField;
    @FXML private FontIcon deleteButton;

    public GlobalVariable getGlobalVariable() {
        globalVariable.setType(varBox.getValue());
        globalVariable.setName(varName.getText());

        switch (varBox.getValue()){
            case GlobalVariable.Type.STRING -> globalVariable.setValue(stringField.getText());
            case GlobalVariable.Type.BOOLEAN -> globalVariable.setValue(boolBox.getValue());
            case GlobalVariable.Type.INT -> globalVariable.setValue(intSpinner.getValue());
            default -> globalVariable.setValue(doubleSpinner.getValue());
        }

        return globalVariable;

    }

    private final GlobalVariable globalVariable;

    private final Spinner<Integer> intSpinner;
    private final Spinner<Double> doubleSpinner;
    private final ComboBox<Boolean> boolBox;
    private final TextField stringField;

    public GlobalVarEntry(VBox parent){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/storyboard/graphx/ui/editor/GlobalVarEntry.fxml"));

        loader.setRoot(this);
        loader.setController(this);


        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        double w = varField.getPrefWidth() / 2;
        double h = varField.getPrefHeight();

        stringField = new TextField();
        stringField.setPromptText("value...");
        stringField.setPrefSize(w, h);

        varName.setText("var " + (parent.getChildren().size() - 1));
        globalVariable = new GlobalVariable(varName.getText());

        boolBox = new ComboBox<>();
        boolBox.setItems(FXCollections.observableArrayList(Boolean.TRUE, Boolean.FALSE));
        boolBox.setPrefSize(w, h);
        boolBox.setValue(true);

        intSpinner = new Spinner<>(0, Integer.MAX_VALUE, 0);
        intSpinner.setPrefSize(w, h);
        intSpinner.setEditable(true);

        doubleSpinner = new Spinner<>(0, Double.MAX_VALUE, 0);
        doubleSpinner.setPrefSize(w, h);
        doubleSpinner.setEditable(true);

        deleteButton.setCursor(Cursor.HAND);
        deleteButton.setOnMousePressed(e -> {
            parent.getChildren().remove(this);
            ProjectSettings.getInstance().removeVar(globalVariable);
            e.consume();
        });



        setUpComboBox();

        varName.textProperty().addListener((_, _, newVal) -> globalVariable.setName(newVal));

        stringField.textProperty().addListener((_, _, newVal) -> globalVariable.setValue(newVal));
        boolBox.valueProperty().addListener((_, _, newVal) -> globalVariable.setValue(newVal));
        intSpinner.valueProperty().addListener((_, _, newVal) -> globalVariable.setValue(newVal));
        doubleSpinner.valueProperty().addListener((_, _, newVal) -> globalVariable.setValue(newVal));

        ProjectSettings.getInstance().addVar(globalVariable);
    }

    private void setUpComboBox(){
        varBox.setItems(FXCollections.observableArrayList(GlobalVariable.Type.values()));

        varBox.valueProperty().addListener((_, _, newVal) -> {
            switch (newVal){
                case GlobalVariable.Type.STRING -> switchInputField(stringField);
                case GlobalVariable.Type.BOOLEAN -> switchInputField(boolBox);
                case GlobalVariable.Type.INT -> switchInputField(intSpinner);
                default -> switchInputField(doubleSpinner);
            }
        });


        varBox.setOnAction(e -> {
            globalVariable.setType(varBox.getValue());
            e.consume();
        });

        varBox.setValue(GlobalVariable.Type.STRING);
    }


    private void switchInputField(Node node){
        if (!varField.getChildren().isEmpty())
            varField.getChildren().removeFirst();
        varField.getChildren().add(node);
    }
}
