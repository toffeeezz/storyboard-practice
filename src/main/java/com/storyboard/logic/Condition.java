package com.storyboard.logic;


public class Condition {

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public Object getCompareValue() {
        return compareValue;
    }

    public void setCompareValue(Object compareValue) {
        this.compareValue = compareValue;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    private String varName;
    private Object compareValue;
    private Operation operation;


    public enum Operation {
        EQUALS, NOT_EQUALS, GREATER_THAN, LESS_THAN
    }

    public boolean compare() {
        GlobalVariable var = ProjectSettings.getInstance().findByName(varName);
        if (var == null) throw new RuntimeException("No such variable named " + varName + " exists");
        if (!isValidType(compareValue)) throw new RuntimeException("Invalid type when comparing value: "
                + compareValue.getClass().getSimpleName());

        return switch (var.getType()) {
            case INT -> {
                int a = (Integer) var.getValue();
                int b = (Integer) compareValue;
                yield switch (operation) {
                    case EQUALS -> a == b;
                    case NOT_EQUALS -> a != b;
                    case GREATER_THAN -> a > b;
                    case LESS_THAN -> a < b;
                };
            }
            case DOUBLE -> {
                double a = (Double) var.getValue();
                double b = (Double) compareValue;
                yield switch (operation) {
                    case EQUALS -> a == b;
                    case NOT_EQUALS -> a != b;
                    case GREATER_THAN -> a > b;
                    case LESS_THAN -> a < b;
                };
            }
            case STRING -> {
                String a = (String) var.getValue();
                String b = (String) compareValue;
                yield switch (operation) {
                    case EQUALS -> a.equals(b);
                    case NOT_EQUALS -> !a.equals(b);
                    default -> throw new RuntimeException("Cannot use GREATER/LESS on String");
                };
            }
            case BOOLEAN -> {
                boolean a = (Boolean) var.getValue();
                boolean b = (Boolean) compareValue;
                yield switch (operation) {
                    case EQUALS -> a == b;
                    case NOT_EQUALS -> a != b;
                    default -> throw new RuntimeException("Cannot use GREATER/LESS on Boolean");
                };
            }
        };

    }

    private boolean isValidType(Object value) {
        return switch (value) {
            case String _, Double _, Integer _, Boolean _ -> true;
            default -> false;
        };
    }

}
