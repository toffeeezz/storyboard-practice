package com.storyboard.graphx.ui.editor.inspector;

import com.storyboard.graphx.node.StoryNode;
import com.storyboard.graphx.node.comp.ArrowLine;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class LinkProperties extends VBox {

    @FXML private Label fromLabel;
    @FXML private Label toLabel;


    LinkProperties(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/storyboard/graphx/ui/editor/inspector/LinkProperties.fxml"));

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void showProperties(ArrowLine line){
        System.out.println("Display link");
        fromLabel.setText(line.getNodeOrigin().getId());
        toLabel.setText(line.getNodeTo().getId());
    }
}
