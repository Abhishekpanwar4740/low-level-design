package com.example.demo.vendingMachineDesign.vendingStates.impl;

import com.example.demo.vendingMachineDesign.Coin;
import com.example.demo.vendingMachineDesign.Item;
import com.example.demo.vendingMachineDesign.VendingMachine;
import com.example.demo.vendingMachineDesign.vendingStates.State;

import java.util.List;

public class HasMoneyState implements State {
    public HasMoneyState() {
        System.out.println("Currently Vending machine is in HasMoneyState");
    }

    @Override
    public void clickOnInsertCoinButton(VendingMachine vendingMachine) throws Exception {
        return;
    }

    @Override
    public void clickOnStartProductSelectionButton(VendingMachine vendingMachine) throws Exception {
        vendingMachine.setVendingMachineState(new SelectionState());
    }

    @Override
    public void insertCoin(VendingMachine vendingMachine, Coin coin) throws Exception {
        System.out.println("Accepted the coin");
        vendingMachine.getCoinList().add(coin);
    }

    @Override
    public void chooseProduct(VendingMachine vendingMachine, int codeNumber) throws Exception {
        throw new Exception("you need to click on start product selection button first");
    }

    @Override
    public int getChange(int returnChangeMoney) throws Exception {
        throw new Exception("you can't get change in HasMoneyState");
    }

    @Override
    public Item dispenseProduct(VendingMachine vendingMachine,int codeNumber) throws Exception {
        throw new Exception("product can't be dispensed in HasMoneyState");
    }

    @Override
    public List<Coin> refundFullMoney(VendingMachine vendingMachine) throws Exception {
       System.out.println("Returned the full amount back in the Coin dispense tray");
       vendingMachine.setVendingMachineState(new IdleState(vendingMachine));
       return vendingMachine.getCoinList();
    }

    @Override
    public void updateInventory(VendingMachine vendingMachine, Item item, int codeNumber) throws Exception {
        vendingMachine.getInventory().addItem(item,codeNumber);
    }
}
