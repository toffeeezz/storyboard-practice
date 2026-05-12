package com.storyboard.graphx.input;

import com.storyboard.graphx.node.comp.ArrowLine;
import com.storyboard.graphx.node.StoryNode;
import com.storyboard.graphx.node.Port;
import com.storyboard.graphx.node.comp.DialogueEntry;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

public class NodeLinking implements Command{


    private final Port startPort;
    private final Port currentEndPort;

    private final StoryNode parentNode;

    private boolean isLinking = false;

    private ArrowLine arrowLine;
    private final DialogueEntry dialogueEntry;


    public NodeLinking(Port startPort, Port currentEndPort, StoryNode parentNode) {
        this.startPort = startPort;
        this.currentEndPort = currentEndPort;

        this.parentNode = parentNode;
        dialogueEntry = startPort.getEntry();

    }

    @Override
    public void onHover(MouseEvent e) {
        if(!startPort.getStyleClass().contains("hover"))
            startPort.getStyleClass().add("hover");
    }

    @Override
    public void onExit(MouseEvent e) {
        startPort.getStyleClass().remove("hover");
    }

    @Override
    public void onReleased(MouseEvent e) {
        if(arrowLine != null && !isLinking){
            startPort.getStyleClass().remove("active");
            parentNode.getEditor().removeArrow(arrowLine);
            arrowLine = null;
        }
        isLinking = false;

        e.consume();
    }

    @Override
    public void onPress(MouseEvent e) {
        startPort.getStyleClass().remove("hover");

        if(!startPort.getStyleClass().contains("active"))
            startPort.getStyleClass().add("active");

        if(dialogueEntry != null && startPort.getEntry().getArrowLine() != null) {
            parentNode.getEditor().removeArrow(startPort.getEntry().getArrowLine());
            dialogueEntry.setArrowLine(null);
            dialogueEntry.getConnectedPort().linkedPropertyProperty().set(false);
            dialogueEntry.getConnectedPort().getStyleClass().remove("hover");
            dialogueEntry.setConnectedPort(null);
            dialogueEntry.setNextEntry(null);
        }

        arrowLine = new ArrowLine(20, startPort.getCenterPos(), parentNode.getEditor().getCamera().getMousePixelPos(e), parentNode);

        arrowLine.setTransparentMouse(true);
        parentNode.getEditor().drawArrowLines(arrowLine);
        e.consume();
    }

    @Override
    public void onDragged(MouseEvent e) {
        if(arrowLine == null) return;
        arrowLine.bindEndpoint(parentNode.getEditor().getCamera().getMousePixelPos(e));
    }

    @Override
    public void onDropEntered(MouseDragEvent e, Object target) {
        if(!(target instanceof Port endPort)) return;

        endPort.getStyleClass().add("hover");
        isLinking = true;
    }

    @Override
    public void onDropExited(MouseDragEvent e, Object target) {
        if(!(target instanceof Port endPort)) return;

        isLinking = false;
        endPort.getStyleClass().remove("hover");
    }

    @Override
    public void onDropReleased(MouseDragEvent e, Object target) {

        if(!(target instanceof Port endPort)) {
            onDropExited(e, target);
            return;
        }

        if(currentEndPort.equals(endPort)){
            onDropExited(e, endPort);
            return;
        }


        arrowLine.bindEndpoint(endPort.getCenterPos());
        endPort.linkedPropertyProperty().set(true);
        dialogueEntry.setNextEntry(endPort.getEntry());
        dialogueEntry.setArrowLine(arrowLine);
        dialogueEntry.setConnectedPort(endPort);
        arrowLine.setTransparentMouse(false);
        arrowLine.setNodeTo(endPort.getStoryNode());
    }
}
