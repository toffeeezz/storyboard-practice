package com.storyboard.graphx.ui.editor.inspector;

import com.storyboard.graphx.node.comp.ArrowLine;
import com.storyboard.logic.GlobalVariable;
import com.storyboard.logic.ProjectSettings;
import com.storyboard.logic.Transition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class TransitionEntry extends StackPane {

    @FXML private FontIcon deleteButton;
    @FXML private ComboBox<GlobalVariable> variableBox;

    private final Transition transition;

    public TransitionEntry(VBox entryPane, Transition transition, ArrowLine arrowLine){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/storyboard/graphx/ui/editor/inspector/TransitionEntry.fxml"));

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.transition = transition;

        deleteButton.setOnMousePressed(e -> {
            entryPane.getChildren().remove(this);
            arrowLine.transitionList.remove(transition);
            e.consume();
        });
        variableBox.setItems(ProjectSettings.getInstance().getGlobalVarList());
        variableBox.setCellFactory(_ -> typeCell());
        variableBox.setButtonCell(typeCell());
        if(!variableBox.getItems().isEmpty())
            variableBox.setValue(variableBox.getItems().getFirst());
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
