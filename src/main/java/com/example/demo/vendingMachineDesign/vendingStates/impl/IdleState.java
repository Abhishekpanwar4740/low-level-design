package com.example.demo.vendingMachineDesign.vendingStates.impl;

import com.example.demo.vendingMachineDesign.Coin;
import com.example.demo.vendingMachineDesign.Item;
import com.example.demo.vendingMachineDesign.VendingMachine;
import com.example.demo.vendingMachineDesign.vendingStates.State;

import java.util.ArrayList;
import java.util.List;

public class IdleState implements State {
    public IdleState() {
        System.out.println("Currently Vending machine is in IdleState");
    }
    public IdleState(VendingMachine vendingMachine) {
        System.out.println("Currently Vending machine is in IdleState");
        vendingMachine.setCoinList(new ArrayList<>());
    }
    @Override
    public void clickOnInsertCoinButton(VendingMachine vendingMachine) throws Exception {
        vendingMachine.setVendingMachineState(new HasMoneyState());
    }

    @Override
    public void clickOnStartProductSelectionButton(VendingMachine vendingMachine) throws Exception {
        throw new Exception("first you need to click on insert coin button");
    }

    @Override
    public void insertCoin(VendingMachine vendingMachine, Coin coin) throws Exception {
        throw new Exception("you can't insert coin in idleState");
    }

    @Override
    public void chooseProduct(VendingMachine vendingMachine, int codeNumber) throws Exception {
        throw new Exception("you can't choose product in idleState");
    }

    @Override
    public int getChange(int returnChangeMoney) throws Exception {
        throw new Exception("you can't get change in idleState");
    }

    @Override
    public Item dispenseProduct(VendingMachine vendingMachine,int codeNumber) throws Exception {
        throw new Exception("product can't be dispensed in idleState");
    }

    @Override
    public List<Coin> refundFullMoney(VendingMachine vendingMachine) throws Exception {
        throw new Exception("you can't get refunded in idleState");
    }

    @Override
    public void updateInventory(VendingMachine vendingMachine, Item item, int codeNumber) throws Exception {
        vendingMachine.getInventory().addItem(item,codeNumber);
    }
}
