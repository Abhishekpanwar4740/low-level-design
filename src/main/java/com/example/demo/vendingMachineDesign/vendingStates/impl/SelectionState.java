package com.example.demo.vendingMachineDesign.vendingStates.impl;

import com.example.demo.vendingMachineDesign.Coin;
import com.example.demo.vendingMachineDesign.Item;
import com.example.demo.vendingMachineDesign.VendingMachine;
import com.example.demo.vendingMachineDesign.vendingStates.State;

import java.util.List;

public class  SelectionState implements State {
    public SelectionState() {
        System.out.println("Currently Vending machine is in SelectionState");
    }

    @Override
    public void clickOnInsertCoinButton(VendingMachine vendingMachine) throws Exception {
        throw new Exception("you can not click on insert coin button in Selection state");
    }

    @Override
    public void clickOnStartProductSelectionButton(VendingMachine vendingMachine) throws Exception {
        return;
    }

    @Override
    public void insertCoin(VendingMachine vendingMachine, Coin coin) throws Exception {
        throw new Exception("you can't insert coin in HasMoneyState");
    }

    @Override
    public void chooseProduct(VendingMachine vendingMachine, int codeNumber) throws Exception {
        Item item = vendingMachine.getInventory().getItem(codeNumber);
        int paidByUser = 0;
        for (Coin coin : vendingMachine.getCoinList()) {
            paidByUser = paidByUser + coin.value;
        }

        if (paidByUser < item.getPrice()) {
            System.out.println("Insuffucient amount, Product you selected is for price: " + item.getPrice() + " and you paid " + paidByUser);
            refundFullMoney(vendingMachine);
            throw new Exception("insufficient amount");
        } else if (paidByUser >= item.getPrice()) {
            if (paidByUser > item.getPrice()) {
                getChange(paidByUser - item.getPrice());
            }
            vendingMachine.setVendingMachineState(new DispenseState(vendingMachine, codeNumber));
        }
    }

    @Override
    public int getChange(int returnExtraMoney) throws Exception {
        System.out.println("Returned the change in the Coin Dispense Tray: " + returnExtraMoney);
        return returnExtraMoney;

    }

    @Override
    public Item dispenseProduct(VendingMachine vendingMachine, int codeNumber) throws Exception {
        throw new Exception("product can't be dispensed in HasMoneyState");
    }

    @Override
    public List<Coin> refundFullMoney(VendingMachine vendingMachine) throws Exception {
        System.out.println("Returned the full amount back in the Coin Dispense Tray");
        vendingMachine.setVendingMachineState(new IdleState(vendingMachine));
        return vendingMachine.getCoinList();

    }

    @Override
    public void updateInventory(VendingMachine vendingMachine, Item item, int codeNumber) throws Exception {
        vendingMachine.getInventory().addItem(item, codeNumber);
    }
}
