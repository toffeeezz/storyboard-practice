package com.storyboard.logic;

import javafx.beans.property.SimpleStringProperty;

public class GlobalVariable {

    public String getName() {
        return name.get();
    }
    public SimpleStringProperty nameProperty(){return name;}

    public enum Type {
        STRING, INT, BOOLEAN, DOUBLE
    }

    public Type getType() {
        return type;
    }

    private Type type;
    private final SimpleStringProperty name = new SimpleStringProperty();
    private Object value;

    public GlobalVariable(String name){
        this.name.set(name);
    }

    public Object getValue(){
        return value;
    }

    public void setType(Type type){
        this.type = type;
    }

    public void setName(String name){
        this.name.set(name);
    }

    public void setValue(Object value){
        switch (type){
            case STRING -> { if (!(value instanceof String)) throw new IllegalArgumentException("Expected String");}
            case INT -> { if (!(value instanceof Integer)) throw new IllegalArgumentException("Expected Integer");}
            case BOOLEAN -> { if (!(value instanceof Boolean)) throw new IllegalArgumentException("Expected Boolean");}
            case DOUBLE -> { if (!(value instanceof Double)) throw new IllegalArgumentException("Expected Double");}
            case null -> {throw new NullPointerException();}
        }

        this.value = value;
    }
}
