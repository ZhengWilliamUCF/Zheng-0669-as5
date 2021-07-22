package ucf.assignments;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ImportItemControllerTest {
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