package com.storyboard.logic;

import java.util.ArrayList;
import java.util.List;

public class Transition {

    public List<Condition> getConditionList() {
        return conditionList;
    }

    private final List<Condition> conditionList = new ArrayList<>();

}
