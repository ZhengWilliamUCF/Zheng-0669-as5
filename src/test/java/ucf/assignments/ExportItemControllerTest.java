package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExportItemControllerTest {
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
}