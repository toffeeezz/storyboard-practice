package com.storyboard.graphx.ui.editor.inspector;

import com.storyboard.graphx.node.StoryNode;
import com.storyboard.graphx.node.comp.ArrowLine;
import com.storyboard.logic.GlobalVariable;
import com.storyboard.logic.ProjectSettings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class LinkProperties extends VBox {

    @FXML private Label fromLabel;
    @FXML private Label toLabel;

    @FXML private ComboBox<GlobalVariable> globalVarBox;


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
        globalVarBox.setItems(ProjectSettings.getInstance().getGlobalVarList());

        globalVarBox.setCellFactory(_ -> typeCell());
        globalVarBox.setButtonCell(typeCell());
    }

    private ListCell<GlobalVariable> typeCell(){
        return new ListCell<>() {
            @Override
            protected void updateItem(GlobalVariable globalVariable, boolean b) {
                super.updateItem(globalVariable, b);
                if (b || globalVariable == null) {
                    textProperty().unbind();
                    setText(null);
                } else {
                    textProperty().bind(globalVariable.nameProperty());
                }
            }
        };
    }
}
