package com.storyboard.graphx.ui.editor.window;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.util.Objects;

public class TransitionWindow extends VBox {

    @FXML private FontIcon closeButton;

    private Stage stage;

    public TransitionWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/storyboard/graphx/ui/editor/window/TransitionWindow.fxml"));

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final double[] dragDelta = new double[2];

        setOnMousePressed(e -> {
            dragDelta[0] = stage.getX() - e.getScreenX();
            dragDelta[1] = stage.getY() - e.getScreenY();
        });

        setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() + dragDelta[0]);
            stage.setY(e.getScreenY() + dragDelta[1]);
        });
    }

    public void show(){
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        StackPane wrapper = new StackPane(this);
        wrapper.setStyle("-fx-padding: 20; -fx-background-color: transparent;");

        Scene scene = new Scene(wrapper);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);

        closeButton.setOnMousePressed(e -> {
            stage.close();
            e.consume();
        });

        stage.showAndWait();

    }
}
