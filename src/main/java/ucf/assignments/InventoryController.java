package ucf.assignments;

/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 William Zheng
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class InventoryController implements Initializable {
    @FXML
    private TextField ItemSearch;

    @FXML
    private TextField ItemName;

    @FXML
    private TextField ItemSerialNumber;

    @FXML
    private TextField ItemValue;

    @FXML
    private TableView<InventoryItem> myToDoTable;

    @FXML
    private TableColumn<InventoryItem, String> InventoryItemSerialNumberColumn;

    @FXML
    private TableColumn<InventoryItem, String> InventoryItemNameColumn;

    @FXML
    private TableColumn<InventoryItem, Integer> InventoryItemValueColumn;

    @FXML
    private final FileChooser fileChooser = new FileChooser();

    @FXML
    public void ImportFileButtonClicked(ActionEvent actionEvent) throws FileNotFoundException {
        OpenFileChooserImport();
    }

    @FXML
    public void ExportFileButtonClicked(ActionEvent actionEvent) throws FileNotFoundException {
        OpenFileChooserExport();
    }

    @FXML
    public void AddItemButtonClicked(ActionEvent actionEvent) {
        if (isItemValid()){
            AddItemToTable();
        }
    }

    @FXML
    public void EditItemButtonClicked(ActionEvent actionEvent) {
        modifyItemInformation();
    }

    private final ObservableList<InventoryItem> dataList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //dataList.add(new InventoryItem(12345, "12345abcde", "test"));
        // sets column data
        InventoryItemValueColumn.setCellValueFactory(new PropertyValueFactory<>("itemValue"));
        InventoryItemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        InventoryItemSerialNumberColumn.setCellValueFactory(new PropertyValueFactory<>("itemSerialNumber"));

        // only allows modification and deletion of a selected item
        myToDoTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (myToDoTable.getSelectionModel().getSelectedIndex() >= 0) {
                ItemIsSelected();
            } else {
                ItemIsUnselected();
            }
        });

        FilteredList<InventoryItem> filteredData = new FilteredList<>(dataList, b -> true);
        ItemSearch.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(InventoryItem -> {
            // show everything if search bar is empty
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            //sets everything to lowercase
            String lowercaseFilter = newValue.toLowerCase();

            if (InventoryItem.getItemSerialNumber().toLowerCase().contains(lowercaseFilter)){
                return true; // matches serial number
            } else return InventoryItem.getItemName().toLowerCase().contains(lowercaseFilter);
            // matches item name
            // return false otherwise
        }));

        // create new sortedlist
        SortedList<InventoryItem> sortedData = new SortedList<>(filteredData);
        // Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(myToDoTable.comparatorProperty());
        // Add sorted (and filtered) data to the table.
        myToDoTable.setItems(sortedData);
    }

    private void AddItemToTable(){
        // adds new item to table view
        //myToDoTable.getItems().add(new InventoryItem(Integer.parseInt(ItemValue.getText()), ItemSerialNumber.getText(), ItemName.getText()));
        dataList.add(new InventoryItem(Integer.parseInt(ItemValue.getText()), ItemSerialNumber.getText(), ItemName.getText()));
        ItemIsUnselected();
    }

    private boolean isItemNameValid(){
        // checks length of item name
        return ItemName.getText().length() <= 256 && ItemName.getText().length() >= 2;
    }

    private boolean isItemSerialNumberValid(){
        // check if initial length is too long or short
        if (ItemSerialNumber.getText().length() != 10) {
            //System.out.println(ItemSerialNumber.getText().length());
            return false;
        }
        // makes the SerialNumber into an array
        char[] SerialArray = ItemSerialNumber.getText().toCharArray();
        for (char c : SerialArray) {
            if (!Character.isDigit(c) && !Character.isLetter(c)) {
                return false;
            }
        }
        return ItemSerialExists();
    }

    private boolean isItemSerialNumberValidWhenEditing(){
        // check if initial length is too long or short
        if (ItemSerialNumber.getText().length() != 10) {
            //System.out.println(ItemSerialNumber.getText().length());
            return false;
        }
        // makes the SerialNumber into an array
        char[] SerialArray = ItemSerialNumber.getText().toCharArray();
        for (char c : SerialArray) {
            if (!Character.isDigit(c) && !Character.isLetter(c)) {
                return false;
            }
        }
        return ItemSerialExistsWhenEditing();
    }

    private boolean isItemValueValid(){
        try {
            Integer.parseInt(ItemValue.getText());
        } catch (NumberFormatException | NullPointerException e){
            // checks if string is null or not entirely an integer
            return false;
        }
        // check for negative number
        return Integer.parseInt(ItemValue.getText()) > 0;
    }

    private boolean isItemValid(){
        return isItemValueValid() && isItemSerialNumberValid() && isItemNameValid();
    }

    private boolean isItemValidWhenEditing(){
        return isItemValueValid() && isItemSerialNumberValidWhenEditing() && isItemNameValid();
    }

    private boolean ItemSerialExists(){
        // return true if serial does not exist
        if (myToDoTable.getItems().size() == 0){
            System.out.println("table is empty");
            return true;
        }
        else { for (int i = 0; i < myToDoTable.getItems().size(); i++){
                InventoryItem holder = myToDoTable.getItems().get(i);
                if (holder.getItemSerialNumber().equals(ItemSerialNumber.getText())){
                    //System.out.println("Serial number already exists in table");
                    return false;
                }
            }
        }
        return true;
    }

    private boolean ItemSerialExistsWhenEditing(){
        int num = 0;
        // return true if serial does not exist
        if (myToDoTable.getItems().size() == 0){
            return true;
        }
        else { for (int i = 0; i < myToDoTable.getItems().size(); i++){
            InventoryItem holder = myToDoTable.getItems().get(i);
            if (num == 1){return false;}
            if (holder.getItemSerialNumber().equals(ItemSerialNumber.getText())){
                num++;
            }
        }
        }
        return true;
    }

    private boolean ItemSerialExistsWhenImporting(String serial){
        // return true if serial does not exist
        if (myToDoTable.getItems().size() == 0){
            System.out.println("table is empty");
            return true;
        }
        else { for (int i = 0; i < myToDoTable.getItems().size(); i++){
            InventoryItem holder = myToDoTable.getItems().get(i);
            if (holder.getItemSerialNumber().equals(serial)){
                System.out.println("Serial number already exists in table");
                return false;
            }
        }
        }
        return true;
    }

    private void RemoveItem(){
        // gets selected index and removes it
        int selected = myToDoTable.getSelectionModel().getSelectedIndex();
        myToDoTable.getItems().remove(selected);
    }

    private void modifyItemInformation(){
        if (isItemValidWhenEditing()) {
            myToDoTable.getSelectionModel().getSelectedItem().setItemValue(Integer.parseInt(ItemValue.getText()));
            myToDoTable.getSelectionModel().getSelectedItem().setItemSerialNumber(ItemSerialNumber.getText());
            myToDoTable.getSelectionModel().getSelectedItem().setItemName(ItemName.getText());
            // refreshes table
            myToDoTable.refresh();
            // clears input fields
            ItemIsUnselected();
        }
        else
            System.out.println("cannot modify item");
    }

    private void ItemIsSelected(){
        InventoryItem item = myToDoTable.getSelectionModel().getSelectedItem();
        // set text fields
        ItemValue.setText(String.valueOf(item.getItemValue()));
        ItemSerialNumber.setText(item.getItemSerialNumber());
        ItemName.setText(item.getItemName());
    }

    private void ItemIsUnselected(){
        ItemValue.clear();
        ItemSerialNumber.clear();
        ItemName.clear();
    }

    private void SortByName(){
        // TableView auto sorts so this function is not needed
        InventoryItemNameColumn.setSortType(TableColumn.SortType.DESCENDING);
        myToDoTable.getSortOrder().add(InventoryItemNameColumn);
        myToDoTable.sort();
    }

    private String convertDataToTSV(){
        StringBuilder data = new StringBuilder();
        ObservableList<InventoryItem> list = myToDoTable.getItems();
        for (InventoryItem item : list){
            data.append(item.getItemValue()).append("\t")
                    .append(item.getItemSerialNumber()).append("\t")
                    .append(item.getItemName()).append("\n");
        }
        System.out.println(data);
        return String.valueOf(data);
    }

    private void saveFileAsTSV(File file) throws FileNotFoundException {
        // saves data to file
        PrintWriter writer;
        writer = new PrintWriter(file);
        writer.println(convertDataToTSV());
        writer.close();
    }

    private String convertDataToHTML(){
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
        ObservableList<InventoryItem> list = myToDoTable.getItems();
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

    private void saveFileAsHTML(File file) throws FileNotFoundException {
        // saves data to file
        System.out.println("now saving as html");
        PrintWriter writer;
        writer = new PrintWriter(file);
        writer.println(convertDataToHTML());
        writer.close();
    }

    private String convertDataToJSON(){
        StringBuilder data = new StringBuilder("""
                {
                    "Inventory":[""");
        ObservableList<InventoryItem> list = myToDoTable.getItems();
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

    private void saveFileAsJSON(File file) throws FileNotFoundException {
        // saves data to file
        PrintWriter writer;
        writer = new PrintWriter(file);
        writer.println(convertDataToJSON());
        writer.close();
    }

    private void OpenFileChooserExport() throws FileNotFoundException {
        // creates new window
        Window stage = ItemSearch.getScene().getWindow();
        // set window name
        fileChooser.setTitle("Exporter");
        // set default file name
        fileChooser.setInitialFileName("inventory");
        // set extensions for file
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TSV Files", "*.txt"),
                new FileChooser.ExtensionFilter("JSON Files", "*.json"),
                new FileChooser.ExtensionFilter("HTML Files", "*.html"));
        // Shows save dialog
        File file = fileChooser.showSaveDialog(stage);
        // checks if file exists
        if (file!=null){
            // calls function to save data to said file
            saveDataToFile(file, getFileExtension(file));
        }
    }

    private String getFileExtension(File file){
        // gets the file extension and returns it
        String filename = file.getName();
        String[] array = filename.split("\\.");
        System.out.println(array[1]);
        return array[1];
    }

    private void saveDataToFile(File file, String filetype) throws FileNotFoundException {
        switch (filetype) {
            case "txt" -> saveFileAsTSV(file);
            case "html" -> saveFileAsHTML(file);
            case "json" -> saveFileAsJSON(file);
        }
    }

    private void OpenFileChooserImport() throws FileNotFoundException {
        // creates new window
        Window stage = ItemSearch.getScene().getWindow();
        // set window name
        fileChooser.setTitle("RobbinHood.exe");
        // set extensions for file
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TSV Files", "*.txt"),
                new FileChooser.ExtensionFilter("JSON Files", "*.json"),
                new FileChooser.ExtensionFilter("HTML Files", "*.html"));
        // makes sure file exists before reading it
        File file = fileChooser.showOpenDialog(stage);
        if (file!=null) {
            System.out.println("Start import!");
            readDataFromFile(file, getFileExtension(file));
        }
    }

    private void readDataFromFile(File file, String filetype) throws FileNotFoundException {
        switch (filetype) {
            case "txt" -> readFromTSV(file);
            case "html" -> readFromHTML(file);
            case "json" -> readFromJSON(file);
        }
    }

    private void readFromTSV(File file) throws FileNotFoundException {
        Scanner reader = new Scanner(file);
        while (reader.hasNext()) {
            String fileData = reader.nextLine();
            // splits String data
            String[] array = fileData.split("\t", 3);
            // parses value
            int value = Integer.parseInt(array[0]);
            // adds to TableView is imported item's serial number is not already in table
            if (ItemSerialExistsWhenImporting(array[1]))
                dataList.add(new InventoryItem(value, array[1], array[2]));
        }
    }

    private void readFromHTML(File file) throws FileNotFoundException {
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
                    // parse value
                    int value = Integer.parseInt(array[0]);
                    // adds to TableView is imported item's serial number is not already in table
                    if (ItemSerialExistsWhenImporting(array[1]))
                        dataList.add(new InventoryItem(value, array[1], array[2]));
                }
            }
        }
    }

    private void readFromJSON(File file) throws FileNotFoundException {
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
                // Splits clean string
                String[] array = fileData.split(",", 3);
                // parses value
                int value = Integer.parseInt(array[0]);
                // adds to TableView is imported item's serial number is not already in table
                if (ItemSerialExistsWhenImporting(array[1]))
                    dataList.add(new InventoryItem(value, array[1], array[2]));
            }
        }
    }
}
