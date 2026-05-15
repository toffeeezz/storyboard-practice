package com.storyboard.graphx.ui.editor.inspector;

import com.storyboard.graphx.node.comp.ArrowLine;
import com.storyboard.graphx.ui.editor.window.TransitionWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class LinkProperties extends VBox {

    @FXML private Label fromLabel;
    @FXML private Label toLabel;

    @FXML private Button editButton;

    private ArrowLine arrowLine;


    LinkProperties(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/storyboard/graphx/ui/editor/inspector/LinkProperties.fxml"));

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        editButton.setOnAction(e -> {
            new TransitionWindow(arrowLine).show();
            e.consume();
        });
    }

    protected void showProperties(ArrowLine line){
        arrowLine = line;
        System.out.println("Display link");
        fromLabel.setText(line.getNodeOrigin().getId());
        toLabel.setText(line.getNodeTo().getId());
    }


}
