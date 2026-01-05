package com.example.demo.interpretorDesignPattern;

public class BionomialNonTerminalExpression implements AbstractExpression{
    AbstractExpression leftExpression;
    AbstractExpression rightExpression;
    String operation;

    public BionomialNonTerminalExpression(AbstractExpression leftExpression, AbstractExpression rightExpression, String operation) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.operation = operation;
    }

    @Override
    public int interpret(Context context) {
        return switch (operation) {
            case "+" -> leftExpression.interpret(context) + rightExpression.interpret(context);
            case "-" -> leftExpression.interpret(context) - rightExpression.interpret(context);
            case "*" -> leftExpression.interpret(context) * rightExpression.interpret(context);
            case "/" -> leftExpression.interpret(context) / rightExpression.interpret(context);
            default -> 0;
        };
    }
}
