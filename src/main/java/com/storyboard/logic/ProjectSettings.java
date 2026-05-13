package com.storyboard.logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProjectSettings {

    private static ProjectSettings instance;

    private final ObservableList<GlobalVariable> globalVarList = FXCollections.observableArrayList();
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

    public GlobalVariable findByName(String name){
        return globalVarList.stream()
                .filter(v -> v.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
