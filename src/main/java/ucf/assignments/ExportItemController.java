package ucf.assignments;

import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class ExportItemController {
    public String convertDataToTSV(ObservableList<InventoryItem> list){
        StringBuilder data = new StringBuilder();
        for (InventoryItem item : list){
            data.append(item.getItemValue()).append("\t")
                    .append(item.getItemSerialNumber()).append("\t")
                    .append(item.getItemName()).append("\n");
        }
        System.out.println(data);
        return String.valueOf(data);
    }

    public void saveDataToFile(File file, String filetype, ObservableList<InventoryItem> list) throws FileNotFoundException {
        switch (filetype) {
            case "txt" -> saveFileAsTSV(file, list);
            case "html" -> saveFileAsHTML(file, list);
            case "json" -> saveFileAsJSON(file, list);
        }
    }

    public void saveFileAsTSV(File file, ObservableList<InventoryItem> list) throws FileNotFoundException {
        // saves data to file
        PrintWriter writer;
        writer = new PrintWriter(file);
        writer.println(convertDataToTSV(list));
        writer.close();
    }

    private String convertDataToHTML(ObservableList<InventoryItem> list){
        StringBuilder data = new StringBuilder("""
                <html>
                <style>
                table, th, td {
                  border: 1px solid black;
                  border-collapse: collapse;
                }
                </style>
                </head>
                <body>
                                
                <table>
                    <tr>
                        <th>Value</th>
                        <th>Serial Number</th>
                        <th>Name</th>
                    <tr>""");
        for (InventoryItem item: list){
            data.append("\n\t").append("<tr>").append("\n\t\t").append("<th>")
                    .append(item.getItemValue()).append("</th>").append("\n\t\t").append("<th>")
                    .append(item.getItemSerialNumber()).append("</th>").append("\n\t\t").append("<th>")
                    .append(item.getItemName()).append("</th>").append("\n\t").append("</tr>");
        }
        data.append("""
                
                </table>
                                
                </body>
                </html>
                """);
        return data.toString();
    }

    private void saveFileAsHTML(File file, ObservableList<InventoryItem> list) throws FileNotFoundException {
        // saves data to file
        System.out.println("now saving as html");
        PrintWriter writer;
        writer = new PrintWriter(file);
        writer.println(convertDataToHTML(list));
        writer.close();
    }

    private String convertDataToJSON(ObservableList<InventoryItem> list){
        StringBuilder data = new StringBuilder("""
                {
                    "Inventory":[""");
        for (InventoryItem item: list){
            if (item.getItemSerialNumber().equals(list.get(list.size() - 1).getItemSerialNumber()) || list.size() == 1){
                data.append("\n\t\t").append("{")
                        .append("\"Value\":\"").append(item.getItemValue()).append("\",")
                        .append("\"SerialNumber\":\"").append(item.getItemSerialNumber()).append("\",")
                        .append("\"Name\":\"").append(item.getItemName()).append("\"")
                        .append("}\n"); // if item is last item in list or list size is only one, don't put a comma
            }
            else
                data.append("\n\t\t").append("{")
                        .append("\"Value\":\"").append(item.getItemValue()).append("\",")
                        .append("\"SerialNumber\":\"").append(item.getItemSerialNumber()).append("\",")
                        .append("\"Name\":\"").append(item.getItemName()).append("\"")
                        .append("},"); // put a comma
        }
        data.append("""
                    ]
                }""");
        return data.toString();
    }

    private void saveFileAsJSON(File file, ObservableList<InventoryItem> list) throws FileNotFoundException {
        // saves data to file
        PrintWriter writer;
        writer = new PrintWriter(file);
        writer.println(convertDataToJSON(list));
        writer.close();
    }

    public String getFileExtension(File file){
        // gets the file extension and returns it
        String filename = file.getName();
        String[] array = filename.split("\\.");
        System.out.println(array[1]);
        return array[1];
    }
}
