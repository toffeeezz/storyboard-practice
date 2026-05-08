package com.storyboard.graphx.ui.editor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class ProjectPropertyWindow extends VBox {

    @FXML private FontIcon addVarButton;
    @FXML private AnchorPane emptyVarPane;

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
    }




    private void addGlobalVar(){
        stackRoot.getChildren().remove(emptyVarPane);
    }
}
