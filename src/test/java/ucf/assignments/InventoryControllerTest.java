package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryControllerTest {

    @Test
    void canContain100Items(){
        ArrayList<InventoryItem> test = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String value = String.valueOf(i);
            test.add(new InventoryItem(value, "ABCDE12345", "test1"));
        }
        assertEquals(100, test.size());
    }

    @Test
    void isValueValidWithZero(){
        String value = "0";
        boolean actual;
        try {
            Float.parseFloat(value);
        } catch (NumberFormatException | NullPointerException e){
            // checks if string is null or not entirely an integer
            actual = false;
        }
        // check for negative number
        if (Float.parseFloat(value) <= 0) {
            actual = false;
        }
        else actual = true;
        assertEquals(false, actual);
    }

    @Test
    void isValueValidWithNegative(){
        String value = "-1";
        boolean actual;
        try {
            Float.parseFloat(value);
        } catch (NumberFormatException | NullPointerException e){
            // checks if string is null or not entirely an integer
            actual = false;
        }
        // check for negative number
        if (Float.parseFloat(value) <= 0) {
            actual = false;
        }
        else actual = true;
        assertEquals(false, actual);
    }

    @Test
    void isValueValidWithNull(){
        String value = "$";
        boolean actual = true;
        try {
            Float.parseFloat(value);
        } catch (NumberFormatException | NullPointerException e){
            // checks if string is null or not entirely an integer
            actual = false;
        }
        assertEquals(false, actual);
    }

    @Test
    void isValueValidWithPositive(){
        String value = "1";
        boolean actual;
        try {
            Float.parseFloat(value);
        } catch (NumberFormatException | NullPointerException e){
            // checks if string is null or not entirely an integer
            actual = false;
        }
        // check for negative number
        if (Float.parseFloat(value) <= 0) {
            actual = false;
        }
        else actual = true;
        assertEquals(true, actual);
    }

    @Test
    void isItemNameValidLessThan2(){
        String itemName = "T";
        boolean actual;
        // checks length of item name
        if (itemName.length() <= 256 && itemName.length() >= 2){
            actual = true;
        }
        else actual = false;
        assertEquals(false, actual);
    }

    @Test
    void isItemNameValidMoreThan256(){
        String itemName = "rc6n2uHQQQ6RrUuuznMwlOMw" +
                "lwFs4Udvvv0UoaXLxxSuiyzEZ7o8ou" +
                "1shO6ofCna087yeQK59f0cLb8RL5hW" +
                "tOIX9FIMTZUAdg44EobEiWnxu72MRX" +
                "2mBDnIpyCL0RuENE6OSDkJK64N4cZb" +
                "LjlnIDNuXg6SiSETy6escD9IKdBgoN" +
                "C33WdSa2iXmCI5ljRRjy907j3PUiB5" +
                "L2O2szTuP8mwP4btSabIKLKLxDEkxZ" +
                "qgyF5EwDq1MVf2jV1mTQY4T";
        boolean actual;
        // checks length of item name
        if (itemName.length() <= 256 && itemName.length() >= 2){
            actual = true;
        }
        else actual = false;
        assertEquals(false, actual);
    }

    @Test
    void isItemNameValidExact256(){
        String itemName = "rc6n2uHQQQ6RrUuuznMwlOMw" +
                "lwFs4Udvvv0UoaXLxxSuiyzEZ7o8ou" +
                "1shO6ofCna087yeQK59f0cLb8RL5hW" +
                "tOIX9FIMTZUAdg44EobEiWnxu72MRX" +
                "2mBDnIpyCL0RuENE6OSDkJK64N4cZb" +
                "LjlnIDNuXg6SiSETy6escD9IKdBgoN" +
                "C33WdSa2iXmCI5ljRRjy907j3PUiB5" +
                "L2O2szTuP8mwP4btSabIKLKLxDEkxZ" +
                "qgyF5EwDq1MVf2jV1mTQY4";
        boolean actual;
        // checks length of item name
        if (itemName.length() <= 256 && itemName.length() >= 2){
            actual = true;
        }
        else actual = false;
        assertEquals(true, actual);
    }

    @Test
    void isItemNameValidExact2(){
        String itemName = "TT";
        boolean actual;
        // checks length of item name
        if (itemName.length() <= 256 && itemName.length() >= 2){
            actual = true;
        }
        else actual = false;
        assertEquals(true, actual);
    }

    @Test
    void AddDataToTableTest(){
        ArrayList<InventoryItem> test = new ArrayList<>();
        int initialSize = test.size();
        test.add(new InventoryItem("12345", "ABCDE12345", "test1"));
        int afterAddItemSize = test.size();
        assertNotEquals(initialSize, afterAddItemSize);
    }

    @Test
    void removeItemFromList(){
        ArrayList<InventoryItem> test = new ArrayList<>();
        test.add(new InventoryItem("12345", "ABCDE12345", "test1"));
        int initialSize = test.size();
        test.remove(0);
        int afterAddItemSize = test.size();
        assertNotEquals(initialSize, afterAddItemSize);
    }

    @Test
    void editValue(){
        ArrayList<InventoryItem> test = new ArrayList<>();
        test.add(new InventoryItem("12345", "ABCDE12345", "test1"));
        test.get(0).setItemValue("5");
        String actual = test.get(0).getItemValue();
        assertEquals("5", actual);
    }

    @Test
    void editSerialNumber(){
        ArrayList<InventoryItem> test = new ArrayList<>();
        test.add(new InventoryItem("12345", "ABCDE12345", "test1"));
        test.get(0).setItemSerialNumber("12345ABCDE");
        String actual = test.get(0).getItemSerialNumber();
        assertEquals("12345ABCDE", actual);
    }

    @Test
    void editSerialNumberSame(){
        ArrayList<InventoryItem> test = new ArrayList<>();
        test.add(new InventoryItem("12345", "ABCDE12345", "test1"));
        if (!test.get(0).getItemSerialNumber().equals("ABCDE12345")){
            test.get(0).setItemSerialNumber("12345ABCDE");
        }
        String actual = test.get(0).getItemSerialNumber();
        assertEquals("ABCDE12345", actual);
    }

    @Test
    void editName(){
        ArrayList<InventoryItem> test = new ArrayList<>();
        test.add(new InventoryItem("12345", "ABCDE12345", "test1"));
        test.get(0).setItemName("test2");
        String actual = test.get(0).getItemName();
        assertEquals("test2", actual);
    }

    @Test
    void findItemViaSerialNumberTrue(){
        ArrayList<InventoryItem> test = new ArrayList<>();
        test.add(new InventoryItem("12345", "ABCDE12345", "test1"));
        test.add(new InventoryItem("12345", "ABCDE12346", "test1"));
        test.add(new InventoryItem("12345", "ABCDE12347", "test1"));
        test.add(new InventoryItem("12345", "ABCDE12349", "test1"));
        String searched = "9";
        boolean found = false;
        for (InventoryItem item : test){
            if (item.getItemSerialNumber().contains(searched)){
                found = true;
            }
        }
        assertEquals(true, found);
    }

    @Test
    void findItemViaSerialNumberFalse(){
        ArrayList<InventoryItem> test = new ArrayList<>();
        test.add(new InventoryItem("12345", "ABCDE12345", "test1"));
        test.add(new InventoryItem("12345", "ABCDE12346", "test1"));
        test.add(new InventoryItem("12345", "ABCDE12347", "test1"));
        test.add(new InventoryItem("12345", "ABCDE12349", "test1"));
        String searched = "8";
        boolean found = false;
        for (InventoryItem item : test){
            if (item.getItemSerialNumber().contains(searched)){
                found = true;
            }
        }
        assertEquals(false, found);
    }

    @Test
    void findItemViaNameTrue(){
        ArrayList<InventoryItem> test = new ArrayList<>();
        test.add(new InventoryItem("12345", "ABCDE12345", "test1"));
        test.add(new InventoryItem("12345", "ABCDE12346", "test2"));
        test.add(new InventoryItem("12345", "ABCDE12347", "test3"));
        test.add(new InventoryItem("12345", "ABCDE12349", "best4"));
        String searched = "b";
        boolean found = false;
        for (InventoryItem item : test){
            if (item.getItemName().contains(searched)){
                found = true;
            }
        }
        assertEquals(true, found);
    }

    @Test
    void findItemViaNameFalse(){
        ArrayList<InventoryItem> test = new ArrayList<>();
        test.add(new InventoryItem("12345", "ABCDE12345", "test1"));
        test.add(new InventoryItem("12345", "ABCDE12346", "test3"));
        test.add(new InventoryItem("12345", "ABCDE12347", "test4"));
        test.add(new InventoryItem("12345", "ABCDE12349", "test5"));
        String searched = "2";
        boolean found = false;
        for (InventoryItem item : test){
            if (item.getItemName().contains(searched)){
                found = true;
            }
        }
        assertEquals(false, found);
    }
}