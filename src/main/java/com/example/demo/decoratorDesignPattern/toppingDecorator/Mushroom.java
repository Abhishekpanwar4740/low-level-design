package com.example.demo.decoratorDesignPattern.toppingDecorator;

import com.example.demo.decoratorDesignPattern.pizza.BasePizza;

public class Mushroom extends ToppingDecorator{
    BasePizza basePizza;

    public Mushroom(BasePizza basePizza) {
        this.basePizza = basePizza;
    }

    @Override
    public int cost() {
        return basePizza.cost()+40;
    }
}
