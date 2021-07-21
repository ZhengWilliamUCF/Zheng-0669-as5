package ucf.assignments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryItemTest {

    @Test
    void getItemValue() {
        InventoryItem test = new InventoryItem("12345", "ABCDE12345", "test1");
        String actual = test.getItemValue();
        assertEquals("12345", actual);
    }

    @Test
    void getItemSerialNumber() {
        InventoryItem test = new InventoryItem("12345", "ABCDE12345", "test1");
        String actual = test.getItemSerialNumber();
        assertEquals("ABCDE12345", actual);
    }

    @Test
    void getItemName() {
        InventoryItem test = new InventoryItem("12345", "ABCDE12345", "test1");
        String actual = test.getItemName();
        assertEquals("test1", actual);
    }

    @Test
    void setItemValue() {
        InventoryItem test = new InventoryItem("12345", "ABCDE12345", "test1");
        test.setItemValue("12.56");
        String actual = test.getItemValue();
        assertEquals("12.56", actual);
    }

    @Test
    void setItemSerialNumber() {
        InventoryItem test = new InventoryItem("12345", "ABCDE12345", "test1");
        test.setItemSerialNumber("12345ABCDE");
        String actual = test.getItemSerialNumber();
        assertEquals("12345ABCDE", actual);
    }

    @Test
    void setItemName() {
        InventoryItem test = new InventoryItem("12345", "ABCDE12345", "test1");
        test.setItemName("test2");
        String actual = test.getItemName();
        assertEquals("test2", actual);
    }
}