package com.storyboard.graphx.ui.editor.inspector;

import com.storyboard.graphx.node.StoryNode;
import com.storyboard.graphx.node.comp.ArrowLine;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class Inspector extends StackPane {

    private final NodeProperties nodeProperties;
    private final LinkProperties linkProperties;

    public Inspector(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/storyboard/graphx/ui/editor/inspector/Inspector.fxml"));

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        nodeProperties = new NodeProperties();
        linkProperties = new LinkProperties();
    }

    public void showProperties(Node node) {
        getChildren().clear();
        System.out.println(node);

        if (node instanceof StoryNode storyNode) {
            getChildren().add(nodeProperties);
            nodeProperties.showProperties(storyNode);
        }else if(node instanceof ArrowLine arrowLine){
            getChildren().add(linkProperties);
            linkProperties.showProperties(arrowLine);
        }
    }

}
