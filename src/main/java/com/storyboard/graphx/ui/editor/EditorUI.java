package com.storyboard.graphx.ui.editor;

import com.storyboard.graphx.ui.editor.inspector.Inspector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class EditorUI extends StackPane {

    @FXML private Pane inspectorWindow;
    @FXML private Pane projectPropertyPane;
    @FXML private Pane editorPane;

    private final Editor editor;
    private final QuickToolBar quickToolBar;

    public EditorUI(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/storyboard/graphx/ui/editor/EditorUI.fxml"));

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        editor = new Editor();
        Inspector inspector = new Inspector();
        quickToolBar = new QuickToolBar(editor);
        ProjectPropertyWindow projectWindow = new ProjectPropertyWindow();

        setInspector(inspector);

        editorPane.getChildren().addAll(editor);
        inspectorWindow.getChildren().addAll(inspector);
        projectPropertyPane.getChildren().addAll(projectWindow);

        editorPane.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            if(e.getButton() == MouseButton.SECONDARY){
                System.out.println("Right click");
                if(!editor.getOverlayPane().getChildren().contains(quickToolBar)) {
                    editor.getOverlayPane().getChildren().add(quickToolBar);

                    double x = e.getX();
                    double y = e.getY();

                    double spawnX = x > 790 ? x - quickToolBar.getWidth() : x;

                    quickToolBar.setLayoutX(spawnX);
                    quickToolBar.setLayoutY(y);
                }
                else
                    editor.getOverlayPane().getChildren().remove(quickToolBar);
                e.consume();
            }else{
                editorPane.getChildren().remove(quickToolBar);
            }
        });

    }

    private void setInspector(Inspector inspector){
        editor.selectedNodeProperty().addListener(_ -> inspector.showProperties(editor.getSelectedNode()));
    }
}
