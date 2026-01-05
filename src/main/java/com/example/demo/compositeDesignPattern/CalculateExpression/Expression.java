package com.example.demo.compositeDesignPattern.CalculateExpression;


public class Expression implements ArithmeticExpression{

    ArithmeticExpression leftArithmeticExpressionList;
    ArithmeticExpression rightArithmeticExpressionList;
    Operation operation;

    public Expression(ArithmeticExpression leftArithmeticExpressionList, ArithmeticExpression rightArithmeticExpressionList, Operation operation) {
        this.leftArithmeticExpressionList = leftArithmeticExpressionList;
        this.rightArithmeticExpressionList = rightArithmeticExpressionList;
        this.operation = operation;
    }

    public int evaluate(){
        int value=0;
        switch(operation){
            case ADD: value= leftArithmeticExpressionList.evaluate()+ rightArithmeticExpressionList.evaluate();break;
            case SUBTRACT: value= leftArithmeticExpressionList.evaluate()-rightArithmeticExpressionList.evaluate();break;
            case MULTIPLY: value= leftArithmeticExpressionList.evaluate()*rightArithmeticExpressionList.evaluate();break;
            case DIVIDE: value= leftArithmeticExpressionList.evaluate()/ rightArithmeticExpressionList.evaluate();break;
        }
        System.out.println("Evaluation value is "+value);
        return value;
    }
}
