package com.example.demo.decoratorDesignPattern.toppingDecorator;

import com.example.demo.decoratorDesignPattern.pizza.BasePizza;

public class ExtraCheese extends ToppingDecorator{
    BasePizza basePizza;

    public ExtraCheese(BasePizza basePizza) {
        this.basePizza = basePizza;
    }

    @Override
    public int cost() {
        return basePizza.cost()+30;
    }
}
