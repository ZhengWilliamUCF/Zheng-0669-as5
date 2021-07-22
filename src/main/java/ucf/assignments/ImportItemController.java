package ucf.assignments;

import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ImportItemController {
    public void readDataFromFile(File file, String filetype, ObservableList<InventoryItem> dataList) throws FileNotFoundException {
        switch (filetype) {
            case "txt" -> readFromTSV(file, dataList);
            case "html" -> readFromHTML(file, dataList);
            case "json" -> readFromJSON(file, dataList);
        }
    }

    public void readFromTSV(File file, ObservableList<InventoryItem> dataList) throws FileNotFoundException {
        Scanner reader = new Scanner(file);
        while (reader.hasNext()) {
            String fileData = reader.nextLine();
            // splits String data
            String[] array = fileData.split("\t", 3);
            // adds to TableView is imported item's serial number is not already in table
            if (ItemSerialExistsWhenImporting(array[1], dataList))
                dataList.add(new InventoryItem(array[0], array[1], array[2]));
        }
    }

    public void readFromHTML(File file, ObservableList<InventoryItem> dataList) throws FileNotFoundException {
        // counter for array
        int counter = 0;
        String[] array = new String[3];
        Scanner reader = new Scanner(file);
        while (reader.hasNext()) {
            String fileData = reader.nextLine();
            // cleans up line
            if (fileData.contains("\t\t<th>")) {
                fileData = fileData
                        .replaceAll("<th>", "")
                        .replaceAll("</th>", "")
                        .replaceAll("\t", "");
                array[counter] = fileData;
                counter++;
                // when counter reaches 3 reset to 0
                if (counter == 3){
                    counter = 0;
                    // adds to TableView is imported item's serial number is not already in table
                    if (ItemSerialExistsWhenImporting(array[1], dataList))
                        dataList.add(new InventoryItem(array[0], array[1], array[2]));
                }
            }
        }
    }

    public void readFromJSON(File file, ObservableList<InventoryItem> dataList) throws FileNotFoundException {
        Scanner reader = new Scanner(file);
        while (reader.hasNext()) {
            String fileData = reader.nextLine();
            // cleans up line
            if (fileData.contains("Value")) {
                fileData = fileData.replaceAll("[{}\t\"]", "");
                fileData = fileData
                        .replaceAll("Value:", "")
                        .replace("SerialNumber:", "")
                        .replace("Name:", "");
                // removes last character if it is ","
                fileData = removeLastCharacter(fileData);
                // Splits clean string
                String[] array = fileData.split(",", 3);
                // adds to TableView is imported item's serial number is not already in table
                if (ItemSerialExistsWhenImporting(array[1], dataList))
                    dataList.add(new InventoryItem(array[0], array[1], array[2]));
            }
        }
    }

    public String getFileExtension(File file){
        // gets the file extension and returns it
        String filename = file.getName();
        String[] array = filename.split("\\.");
        System.out.println(array[1]);
        return array[1];
    }

    public boolean ItemSerialExistsWhenImporting(String serial, ObservableList<InventoryItem> dataList){
        // return true if serial does not exist
        if (dataList.size() == 0){
            // table is empty so serial number does not exist so return true
            return true;
        }
        else {
            for (InventoryItem holder : dataList) {
                if (holder.getItemSerialNumber().equals(serial.toUpperCase())) {
                    ErrorMessageController App = new ErrorMessageController();
                    //Shows error if serial number already exists and returns false
                    App.showErrorMessageSerialNumberExists(holder.getItemSerialNumber());
                    return false;
                }
            }
        }
        return true;
    }

    private String removeLastCharacter(String fileData){
        // if last character of string is a comma, remove it.
        // used when importing JSON files
        if (fileData.charAt(fileData.length()-1) == ','){
            fileData = fileData.substring(0, fileData.length()-1);
        }
        return fileData;
    }
}
