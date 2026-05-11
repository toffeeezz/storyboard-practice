package com.storyboard.graphx.ui.editor;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class ProjectPropertyWindow extends VBox {

    @FXML private FontIcon addVarButton;
    @FXML private Label emptyVarLabel;

    @FXML private VBox varPane;

    @FXML private StackPane stackRoot;

    public ProjectPropertyWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/storyboard/graphx/ui/editor/ProjectPropertyWindow.fxml"));

        loader.setRoot(this);
        loader.setController(this);


        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        addVarButton.setCursor(Cursor.HAND);
        addVarButton.setOnMousePressed(_ -> addGlobalVar());

        varPane.getChildren().addListener((ListChangeListener<Node>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    Platform.runLater(() -> {
                        if(!varPane.getChildren().contains(emptyVarLabel) && varPane.getChildren().size() == 1){
                            varPane.getChildren().addFirst(emptyVarLabel);
                        }
                    });
                }
                if (change.wasRemoved()) {
                    Platform.runLater(() -> {
                        if (varPane.getChildren().size() == 1)
                            varPane.getChildren().addFirst(emptyVarLabel);
                    });
                }
            }
        });

        setOnMousePressed(_ -> requestFocus());
    }


    private void addGlobalVar() {


        varPane.getChildren().remove(emptyVarLabel);
        varPane.getChildren().add(varPane.getChildren().size() - 1, new GlobalVarEntry(varPane));
    }
}
