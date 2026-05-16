package com.storyboard.graphx.ui.editor;

import com.storyboard.graphx.node.StoryNode;
import com.storyboard.utils.Vector2;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.reflections.Reflections;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class QuickToolBar extends VBox {



    public QuickToolBar(Editor editor){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/storyboard/graphx/ui/editor/QuickToolBar.fxml"));

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Reflections reflections = new Reflections("com.storyboard.graphx.node");
        Set<Class<? extends StoryNode>> nodeSubClasses = reflections.getSubTypesOf(StoryNode.class);

        for(Class< ? extends StoryNode> storyNode : nodeSubClasses){
            Button button = createButton(editor, storyNode);
            getChildren().add(button);
        }

    }

    private static Button createButton(Editor editor, Class<? extends StoryNode> storyNode) {
        Button button = new Button(storyNode.getSimpleName());
        button.setPrefWidth(Double.MAX_VALUE);
        button.setOnMousePressed(e -> {
            try{
                StoryNode node = storyNode.getDeclaredConstructor(Editor.class).newInstance(editor);
                Vector2 pos = editor.camera.getMouseWorldPos(e);
                editor.addNode(node, pos);
            } catch (InvocationTargetException | InstantiationException | NoSuchMethodException |
                     IllegalAccessException exception) {
                throw new RuntimeException(exception);
            }
        });
        return button;
    }
}
