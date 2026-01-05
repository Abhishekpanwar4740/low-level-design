package com.example.demo.interpretorDesignPattern;

public class Main {
    public static void main(String[] args) {
        Context context = new Context();
        context.put("a", 1);
        context.put("b", 2);

        AbstractExpression abstractExpression = new BionomialNonTerminalExpression(new BionomialNonTerminalExpression(new NumberTerminalExpression("a"), new NumberTerminalExpression("b"), "-"), new BionomialNonTerminalExpression(new NumberTerminalExpression("a"), new NumberTerminalExpression("b"), "+"), "-");
        System.out.println("calulated value: " + abstractExpression.interpret(context));
    }
}
