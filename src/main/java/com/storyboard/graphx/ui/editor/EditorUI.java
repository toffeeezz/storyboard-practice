package com.storyboard.graphx.ui.editor;

import com.storyboard.graphx.node.DialogueNode;
import com.storyboard.graphx.node.StoryNode;
import com.storyboard.graphx.ui.editor.inspector.Inspector;
import com.storyboard.utils.Vector2;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class EditorUI extends StackPane {

    @FXML private Pane inspectorWindow;
    @FXML private Pane projectPropertyPane;
    @FXML private Pane editorPane;

    private final Inspector inspector;
    private final ProjectPropertyWindow projectWindow;
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
        inspector = new Inspector();
        quickToolBar = new QuickToolBar();
        projectWindow = new ProjectPropertyWindow();

        setInspector(inspector);
        setQuickToolBar(quickToolBar);

        editorPane.getChildren().addAll(editor);
        inspectorWindow.getChildren().addAll(inspector);
        projectPropertyPane.getChildren().addAll(projectWindow);

        editorPane.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            if(e.getButton() == MouseButton.SECONDARY){
                System.out.println("Right click");
                if(!editorPane.getChildren().contains(quickToolBar)) {
                    editorPane.getChildren().add(quickToolBar);
                    System.out.println(e.getSceneX());
                    double spawnX = e.getSceneX() > 790 ? e.getSceneX() - quickToolBar.getWidth() : e.getSceneX();
                    quickToolBar.relocate(spawnX, e.getSceneY());
                }
                else
                    editorPane.getChildren().remove(quickToolBar);
                e.consume();
            }else{
                editorPane.getChildren().remove(quickToolBar);
            }
        });

    }

    private void setQuickToolBar(QuickToolBar quickToolBar){
        quickToolBar.onDialogueButtonPressed(e -> {
            Vector2 pos = editor.camera.getMouseWorldPos(e);

            editor.addNode(new DialogueNode(editor), pos);
        });
    }

    private void setInspector(Inspector inspector){
        editor.selectedNodeProperty().addListener(_ -> {
            inspector.showProperties(editor.getSelectedNode());
        });
    }
}
