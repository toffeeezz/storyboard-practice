package com.storyboard.graphx.ui.editor.inspector;

import com.storyboard.graphx.node.comp.ArrowLine;
import com.storyboard.logic.GlobalVariable;
import com.storyboard.logic.Transition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.util.List;

public class LinkProperties extends VBox {

    @FXML private Label fromLabel;
    @FXML private Label toLabel;

    @FXML private FontIcon addButton;

    @FXML private VBox entryPane;

    private ComboBox<GlobalVariable> globalVarBox;
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

        addButton.setOnMousePressed(e -> {
            arrowLine.transitionList.add(new Transition());
            displayTransitions();
        });
    }

    protected void showProperties(ArrowLine line){
        arrowLine = line;
        System.out.println("Display link");
        fromLabel.setText(line.getNodeOrigin().getId());
        toLabel.setText(line.getNodeTo().getId());
        displayTransitions();
    }

    private void displayTransitions(){
        entryPane.getChildren().clear();
        if(arrowLine.transitionList.isEmpty()) return;

        for(Transition transition : arrowLine.transitionList){
            entryPane.getChildren().add(new TransitionEntry(entryPane, transition, arrowLine));
        }
    }


}
