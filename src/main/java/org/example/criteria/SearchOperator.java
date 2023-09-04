package org.example.criteria;

public enum SearchOperator {
    EQUALS("="),
    LESS("<"),
    MORE(">");

    private String op;

    SearchOperator(String op) {
        this.op = op;
    }

    public String getOp() {
        return op;
    }
}
