package com.storyboard.logic;

import com.storyboard.utils.Alert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProjectSettings {

    private static ProjectSettings instance;

    private final ObservableList<GlobalVariable> globalVarList = FXCollections.observableArrayList();
    private final ObservableList<Transition> transitionList = FXCollections.observableArrayList();
    String projectName;

    private ProjectSettings () {}

    public static ProjectSettings getInstance(){
        if(instance == null)
            instance = new ProjectSettings();
        return instance;
    }

    public ObservableList<GlobalVariable> getGlobalVarList() {
        return globalVarList;
    }

    public void setProjectName(String name){ projectName = name;}
    public void addVar(GlobalVariable var){globalVarList.add(var);}
    public void removeVar(GlobalVariable var){globalVarList.remove(var);}
    public void addTransition(Transition trans){transitionList.add(trans);}
    public void removeTransition(Transition trans){transitionList.remove(trans);}

    public GlobalVariable findByName(String name){
        return globalVarList.stream()
                .filter(v -> v.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public boolean checkVariableUsage(GlobalVariable var){

        String name = var.getName();
        for(Transition transition : transitionList){
            for(Condition condition : transition.getConditionList()){
                if(name.equals(condition.getVarName())) {
                    String message = "Type can't changed when it is being used by a transition in:\n"
                            + transition.getFromNode().getId() + " -> " + transition.getToNode().getId();
                    Alert.showWarning(message);
                    return true;
                }
            }
        }
        return false;
    }
}
