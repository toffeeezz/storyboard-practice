package com.storyboard.graphx.ui.editor.inspector;

import com.storyboard.graphx.node.StoryNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class NodeProperties extends VBox {

    @FXML private Label nameLabel;


    NodeProperties(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/storyboard/graphx/ui/editor/inspector/NodeProperties.fxml"));

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void showProperties(StoryNode node){
        String name = node.getId();

        node.idProperty().addListener(_ -> {
            String newName = node.getId();
            nameLabel.setText(newName);
        });
        nameLabel.setText(name);
    }
}
