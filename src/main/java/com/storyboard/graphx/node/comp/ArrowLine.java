package com.storyboard.graphx.node.comp;

import com.storyboard.graphx.node.StoryNode;
import com.storyboard.graphx.ui.editor.Editor;
import com.storyboard.logic.Transition;
import com.storyboard.utils.Vector2;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class ArrowLine extends Polygon {


    public final SimpleDoubleProperty angle;
    public final Vector2 endPoint;
    public final Vector2 startPoint;
    public final Vector2 dist;
    private StoryNode nodeTo;
    private final StoryNode nodeOrigin;

    public final List<Shape> shapes = new ArrayList<>();

    public Transition getTransition() {
        return transition;
    }


    private final Transition transition = new Transition();


    public ArrowLine(double arrowSize, Vector2 start, Vector2 end, StoryNode nodeOrigin) {


        angle = new SimpleDoubleProperty();
        endPoint = new Vector2();
        startPoint = new Vector2();
        dist = new Vector2();
        this.nodeOrigin = nodeOrigin;

        dist.x.addListener(_ -> updateAngle());
        dist.y.addListener(_ -> updateAngle());

        startPoint.x.bind(start.x);
        startPoint.y.bind(start.y);

        bindEndpoint(end);

        Line line = new Line();
        line.startXProperty().bind(startPoint.x);
        line.startYProperty().bind(startPoint.y);
        line.setStrokeWidth(5);
        line.getStyleClass().add("arrow");
        line.setViewOrder(Editor.lineViewOrder);
        line.endYProperty().bind(endPoint.y);
        line.endXProperty().bind(endPoint.x);

        Polygon head = new Polygon(
                0, 0,        //Tip
                -arrowSize, -arrowSize / 2,    // Left
                -arrowSize, arrowSize / 2      // Right
        );
        head.setViewOrder(Editor.arrowViewOrder);
        head.layoutXProperty().bind(startPoint.x.add(endPoint.x).divide(2).add(arrowSize / 2));
        head.layoutYProperty().bind(startPoint.y.add(endPoint.y).divide(2));
        head.getStyleClass().add("arrow");
        head.rotateProperty().bind(angle);

        shapes.add(line);
        shapes.add(head);

        line.setFocusTraversable(true);
        head.setFocusTraversable(true);

        line.setOnMousePressed(e -> {
            nodeOrigin.getEditor().setSelectedNode(this);
            System.out.println("Clicked");
            line.getStyleClass().add("focused");
            head.getStyleClass().add("focused");
            line.requestFocus();
            e.consume();
        });
        head.setOnMousePressed(e -> {
            nodeOrigin.getEditor().setSelectedNode(this);
            line.getStyleClass().add("focused");
            head.getStyleClass().add("focused");
            line.requestFocus();
            e.consume();
        });

        transition.setFromNode(nodeOrigin);
    }

    public void setNodeTo(StoryNode node){

        nodeTo = node;
        transition.setToNode(nodeTo);
    }

    private void updateAngle(){
        double dx = dist.getX();
        double dy = dist.getY();
        double radians = Math.atan2(dy, dx);
        angle.set(Math.toDegrees(radians));

    }

    public void setTransparentMouse(boolean value){
        for(Shape shape : shapes)
            shape.setMouseTransparent(value);
    }

    public void bindEndpoint(Vector2 point){
        if(endPoint.x.isBound() && endPoint.y.isBound()){
            endPoint.x.unbind();
            endPoint.y.unbind();
        }
        endPoint.x.bind(point.x);
        endPoint.y.bind(point.y);

        if(dist.x.isBound() && dist.y.isBound()){
            dist.x.unbind();
            dist.y.unbind();
        }
        dist.x.bind(endPoint.x.subtract(startPoint.x));
        dist.y.bind(endPoint.y.subtract(startPoint.y));
    }

    public StoryNode getNodeOrigin() {
        return nodeOrigin;
    }

    public StoryNode getNodeTo() {
        return nodeTo;
    }
}
