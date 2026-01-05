package com.example.demo.vendingMachineDesign.vendingStates;

import com.example.demo.vendingMachineDesign.Coin;
import com.example.demo.vendingMachineDesign.Item;
import com.example.demo.vendingMachineDesign.VendingMachine;

import javax.swing.plaf.SpinnerUI;
import java.beans.ExceptionListener;
import java.util.List;

public interface State {
    public void clickOnInsertCoinButton(VendingMachine vendingMachine) throws Exception;

    public void clickOnStartProductSelectionButton(VendingMachine vendingMachine) throws Exception;

    public void insertCoin(VendingMachine vendingMachine, Coin coin) throws Exception;

    public void chooseProduct(VendingMachine vendingMachine,int codeNumber) throws Exception;

    public int getChange(int returnChangeMoney) throws Exception;

    public Item dispenseProduct(VendingMachine vendingMachine,int codeNumber) throws Exception;

    public List<Coin> refundFullMoney(VendingMachine vendingMachine) throws Exception;

    public void updateInventory(VendingMachine vendingMachine,Item item,int codeNumber) throws Exception;
}
