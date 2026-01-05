package com.example.demo.compositeDesignPattern.CalculateExpression;

public class Number implements ArithmeticExpression{
    int value;

    public Number(int value) {
        this.value = value;
    }
    public  int evaluate(){
        return value;
    }
}
