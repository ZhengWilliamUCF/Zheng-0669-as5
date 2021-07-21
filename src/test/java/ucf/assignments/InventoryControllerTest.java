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

    @Test
    void SaveToTSV(){
        List<InventoryItem> testing = new ArrayList<>();
        testing.add(new InventoryItem("12345", "ABCDE12345", "test1"));

        StringBuilder data = new StringBuilder();

        ObservableList<InventoryItem> list = FXCollections.observableArrayList(testing);
        for (InventoryItem item : list){
            data.append(item.getItemValue()).append("\t")
                    .append(item.getItemSerialNumber()).append("\t")
                    .append(item.getItemName()).append("\n");
        }
        String formattedData = String.valueOf(data);
        String actual = "12345" + "\t" + "ABCDE12345" + "\t" + "test1" +"\n";
        assertEquals(actual, formattedData);
    }

    @Test
    void SaveToHTML(){
        List<InventoryItem> testing = new ArrayList<>();
        testing.add(new InventoryItem("12345", "ABCDE12345", "test1"));

        StringBuilder data = new StringBuilder();

        ObservableList<InventoryItem> list = FXCollections.observableArrayList(testing);
        for (InventoryItem item: list){
            data.append("\n\t").append("<tr>").append("\n\t\t").append("<th>")
                    .append(item.getItemValue()).append("</th>").append("\n\t\t").append("<th>")
                    .append(item.getItemSerialNumber()).append("</th>").append("\n\t\t").append("<th>")
                    .append(item.getItemName()).append("</th>").append("\n\t").append("</tr>");
        }

        String formattedData = String.valueOf(data);
        String actual = """

                \t<tr>
                \t\t<th>12345</th>
                \t\t<th>ABCDE12345</th>
                \t\t<th>test1</th>
                \t</tr>""";
        assertEquals(actual, formattedData);
    }

    @Test
    void SaveToJSON(){
        List<InventoryItem> testing = new ArrayList<>();
        testing.add(new InventoryItem("12345", "ABCDE12345", "test1"));

        StringBuilder data = new StringBuilder();

        ObservableList<InventoryItem> list = FXCollections.observableArrayList(testing);
        for (InventoryItem item: list){
            if (item.getItemSerialNumber().equals(list.get(list.size() - 1).getItemSerialNumber()) || list.size() == 1){
                data.append("\n\t\t").append("{")
                        .append("\"Value\":\"").append(item.getItemValue()).append("\",")
                        .append("\"SerialNumber\":\"").append(item.getItemSerialNumber()).append("\",")
                        .append("\"Name\":\"").append(item.getItemName()).append("\"")
                        .append("}\n"); // if item is last item in list or list size is only one, don't put a comma
            }
        }
        String formattedData = String.valueOf(data);
        String actual = """

                \t\t{"Value":"12345","SerialNumber":"ABCDE12345","Name":"test1"}
                """;
        assertEquals(actual, formattedData);
    }

    @Test
    void importTSV(){
        List<InventoryItem> testing = new ArrayList<>();
        String fileData = "12345" +"\t" + "ABCDE12345" + "\t" + "test1" + "\n";
        int initialSize = testing.size();
        // splits String data
        String[] array = fileData.split("\t", 3);
        // adds to TableView is imported item's serial number is not already in table
        testing.add(new InventoryItem(array[0], array[1], array[2]));
        int afterImportSize = testing.size();
        assertNotEquals(initialSize, afterImportSize);
    }

    @Test
    void importHTML(){
        List<InventoryItem> testing = new ArrayList<>();
        String fileData = "<th>12345</th>" +"\n" + "<th>ABCDE12345</th>" + "\n" + "<th>test1</th>";
        int initialSize = testing.size();
        if (fileData.contains("<th>")) {
            fileData = fileData
                    .replaceAll("<th>", "")
                    .replaceAll("</th>", "");
        }
        String[] array = fileData.split("\n", 3);
        testing.add(new InventoryItem(array[0], array[1], array[2]));
        int afterImportSize = testing.size();
        assertNotEquals(initialSize, afterImportSize);
    }

    @Test
    void importJSON(){
        List<InventoryItem> testing = new ArrayList<>();
        String fileData = "{\"Value\":\"12345\",\"SerialNumber\":\"ABCDE12345\",\"Name\":\"test1\"}";
        int initialSize = testing.size();
        if (fileData.contains("Value")) {
            fileData = fileData.replaceAll("[{}\t\"]", "");
            fileData = fileData
                    .replaceAll("Value:", "")
                    .replace("SerialNumber:", "")
                    .replace("Name:", "");
        }
        String[] array = fileData.split(",", 3);
        testing.add(new InventoryItem(array[0], array[1], array[2]));
        int afterImportSize = testing.size();
        assertNotEquals(initialSize, afterImportSize);
    }
}