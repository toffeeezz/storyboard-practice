package com.storyboard.graphx.ui.editor.window;

import com.storyboard.graphx.node.comp.ArrowLine;
import com.storyboard.graphx.ui.editor.inspector.TransitionEntry;
import com.storyboard.logic.Condition;
import com.storyboard.utils.Alert;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TransitionWindow extends VBox {

    @FXML private FontIcon closeButton;
    @FXML private Button addButton;

    @FXML private VBox entryPane;

    private Stage stage;

    private final ArrowLine arrowLine;

    public TransitionWindow(ArrowLine arrowLine){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/storyboard/graphx/ui/editor/window/TransitionWindow.fxml"));

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final double[] dragDelta = new double[2];
        this.arrowLine = arrowLine;

        setOnMousePressed(e -> {
            dragDelta[0] = stage.getX() - e.getScreenX();
            dragDelta[1] = stage.getY() - e.getScreenY();
        });

        setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() + dragDelta[0]);
            stage.setY(e.getScreenY() + dragDelta[1]);
        });

        addButton.setOnAction(e -> {
            arrowLine.getTransition().getConditionList().add(new Condition());
            showTransitions();
            e.consume();
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
            List<String> errors = entryPane.getChildren().stream()
                    .filter(n -> n instanceof TransitionEntry)
                    .map(n -> (TransitionEntry) n)
                    .map(TransitionEntry::validate)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            if (!errors.isEmpty()) {
                Alert.showWarning(String.join("\n", errors));
                return;
            }

            entryPane.getChildren().stream()
                    .filter(n -> n instanceof TransitionEntry)
                    .map(n -> (TransitionEntry) n)
                    .forEach(TransitionEntry::save);

            stage.close();
            e.consume();
        });

        showTransitions();
        stage.showAndWait();

    }

    private void showTransitions(){
        entryPane.getChildren().clear();

        if (arrowLine.getTransition().getConditionList().isEmpty()) return;

        List<Condition> conditionList = arrowLine.getTransition().getConditionList();

        for(Condition condition : conditionList){
            entryPane.getChildren().add(new TransitionEntry(entryPane, conditionList, condition));
        }
    }
}
