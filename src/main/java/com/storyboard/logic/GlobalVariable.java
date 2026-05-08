package com.storyboard.logic;

public class GlobalVariable {

    public enum Type {
        STRING, INT, BOOLEAN, DOUBLE
    }

    private Type type;
    private String name;
    private Object value;

    public GlobalVariable(String name){
        this.name = name;
    }

    public Object get(){
        return value;
    }

    public void setType(Type type){
        this.type = type;
    }

    public void setName(String name){
        this.name = name;
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
