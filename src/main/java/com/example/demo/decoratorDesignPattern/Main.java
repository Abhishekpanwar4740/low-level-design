package com.example.demo.decoratorDesignPattern;

import com.example.demo.decoratorDesignPattern.pizza.BasePizza;
import com.example.demo.decoratorDesignPattern.pizza.Farmhouse;
import com.example.demo.decoratorDesignPattern.toppingDecorator.ExtraCheese;
import com.example.demo.decoratorDesignPattern.toppingDecorator.Mushroom;

public class Main {
    public static void main(String[] args) {

        BasePizza basePizza1 = new Farmhouse();
        System.out.println("cost of a farmhouse pizza " + basePizza1.cost());

        BasePizza basePizza2=new ExtraCheese(new Farmhouse());
        System.out.println("cost of a farmhouse with extra cheese pizza " + basePizza2.cost());

        BasePizza basePizza3=new Mushroom(new ExtraCheese(new Farmhouse()));
        System.out.println("cost of a farmhouse with mushroom and extra cheese pizza " + basePizza3.cost());

    }
}
