package com.storyboard.graphx.ui.editor;

import com.storyboard.logic.GlobalVariable;
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
        globalVariable = new GlobalVariable("default");

        double w = varField.getPrefWidth() / 2;
        double h = varField.getPrefHeight();

        stringField = new TextField();
        stringField.setPromptText("value...");
        stringField.setPrefSize(w, h);

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
            e.consume();
        });

        varName.setText("var " + (parent.getChildren().size() - 1));

        setUpComboBox();
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
