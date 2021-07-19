package ucf.assignments;

/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 William Zheng
 */

public class InventoryItem {
    private int itemValue;
    private String itemSerialNumber;
    private String itemName;

    public InventoryItem(int itemValue, String itemSerialNumber, String itemName){
        this.itemValue = itemValue;
        this.itemSerialNumber = itemSerialNumber;
        this.itemName = itemName;
    }

    public int getItemValue(){return itemValue;}

    public String getItemSerialNumber(){return itemSerialNumber;}

    public String getItemName(){ return itemName;}

    public void setItemValue(int itemValue){
        this.itemValue = itemValue;
    }

    public void setItemSerialNumber(String itemSerialNumber){
        this.itemSerialNumber = itemSerialNumber;
    }

    public void setItemName(String itemName){
        this.itemName = itemName;
    }
}
