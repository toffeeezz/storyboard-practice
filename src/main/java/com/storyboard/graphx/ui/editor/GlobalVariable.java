package com.storyboard.graphx.ui.editor;

public class GlobalVariable {

    public enum Type {
        STRING, INT, BOOLEAN, DOUBLE
    }

    private final Type type;
    private String name;
    private Object value;

    public GlobalVariable(Type type, Object object){
        this.type = type;
        set(object);
    }

    public Object get(){
        return value;
    }

    public void set(Object value){
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
