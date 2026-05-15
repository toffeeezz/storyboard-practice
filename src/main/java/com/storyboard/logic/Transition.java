package com.storyboard.logic;

import com.storyboard.graphx.node.StoryNode;

import java.util.ArrayList;
import java.util.List;

public class Transition {

    public List<Condition> getConditionList() {
        return conditionList;
    }

    public enum LogicMode{
        OR, AND
    }

    public LogicMode getLogicMode() {
        return logicMode;
    }

    public void setLogicMode(LogicMode logicMode) {
        this.logicMode = logicMode;
    }

    private LogicMode logicMode;

    private final List<Condition> conditionList = new ArrayList<>();

    public StoryNode getToNode() {
        return toNode;
    }

    public void setToNode(StoryNode toNode) {
        this.toNode = toNode;
    }

    public StoryNode getFromNode() {
        return fromNode;
    }

    public void setFromNode(StoryNode fromNode) {
        this.fromNode = fromNode;
    }

    private StoryNode fromNode;
    private StoryNode toNode;

}
