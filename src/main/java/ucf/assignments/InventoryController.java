package ucf.assignments;

/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 William Zheng
 */

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.Scanner;

public class InventoryController implements Initializable {

    @FXML
    public TextField ItemSearch;

    @FXML
    private TextField ItemName;

    @FXML
    private TextField ItemSerialNumber;

    @FXML
    private TextField ItemValue;

    @FXML
    public TableView<InventoryItem> myToDoTable;

    @FXML
    private TableColumn<InventoryItem, String> InventoryItemSerialNumberColumn;

    @FXML
    private TableColumn<InventoryItem, String> InventoryItemNameColumn;

    @FXML
    private TableColumn<InventoryItem, String> InventoryItemValueColumn;

    @FXML
    public final FileChooser fileChooser = new FileChooser();

    @FXML
    public void ImportFileButtonClicked(ActionEvent actionEvent) throws FileNotFoundException {
        //ImportFileController app = new ImportFileController();
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
    public void DeleteItemButtonClicked(ActionEvent actionEvent) {
        RemoveItem();
    }

    @FXML
    public void EditItemButtonClicked(ActionEvent actionEvent) {
        modifyItemInformation();
    }

    public ObservableList<InventoryItem> dataList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // sets column data
        InventoryItemValueColumn.setCellValueFactory(new PropertyValueFactory<>("itemValue"));
        InventoryItemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        InventoryItemSerialNumberColumn.setCellValueFactory(new PropertyValueFactory<>("itemSerialNumber"));

        // prints out dollar symbol on item value
        DecimalFormat currency = new DecimalFormat("$0.00");
        InventoryItemValueColumn.setCellValueFactory(cellData -> {
            String formattedCost = currency.format(getValue(cellData.getValue()));
            return new SimpleStringProperty(formattedCost);
        });

        // prints out letter sin uppercase
        //InventoryItemSerialNumberColumn.setCellValueFactory(cellData -> {
            //return new SimpleStringProperty(setUpperCase(cellData.getValue()));
        //});

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
        dataList.add(new InventoryItem((ItemValue.getText()), ItemSerialNumber.getText().toUpperCase(), ItemName.getText()));
        ItemIsUnselected();
    }

    private void AddItemToTable2(){


    }

    private boolean isItemNameValid(){
        // checks length of item name
        if (ItemName.getText().length() <= 256 && ItemName.getText().length() >= 2){
            return true;
        }
        showErrorMessageInvalidNameLength();
        return false;
    }

    private boolean isItemSerialNumberValid(){
        // check if initial length is too long or short
        if (ItemSerialNumber.getText().length() != 10) {
            showErrorMessageSerialNumberLength();
            return false;
        }
        // makes the SerialNumber into an array
        char[] SerialArray = ItemSerialNumber.getText().toCharArray();
        for (char c : SerialArray) {
            if (!Character.isDigit(c) && !Character.isLetter(c)) {
                showErrorMessageSerialNumberInvalid();
                return false;
            }
        }
        return ItemSerialExists();
    }

    private boolean isItemSerialNumberValidWhenEditing(){
        // check if initial length is too long or short
        if (ItemSerialNumber.getText().length() != 10) {
            showErrorMessageSerialNumberLength();
            return false;
        }
        // makes the SerialNumber into an array
        char[] SerialArray = ItemSerialNumber.getText().toCharArray();
        for (char c : SerialArray) {
            if (!Character.isDigit(c) && !Character.isLetter(c)) {
                showErrorMessageSerialNumberInvalid();
                return false;
            }
        }
        return ItemSerialExistsWhenEditing();
    }

    private boolean isItemValueValid(){
        try {
            Float.parseFloat(ItemValue.getText());
        } catch (NumberFormatException | NullPointerException e){
            // checks if string is null or not entirely an integer
            showErrorMessageNullValue();
            return false;
        }
        // check for negative number
        if (Float.parseFloat(ItemValue.getText()) <= 0) {
            showErrorMessageBadValue();
            return false;
        }
        return true;
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
                if (holder.getItemSerialNumber().equals(ItemSerialNumber.getText().toUpperCase())){
                    //System.out.println("Serial number already exists in table");
                    showErrorMessageSerialNumberExists(holder.getItemSerialNumber());
                    return false;
                }
            }
        }
        return true;
    }

    private boolean ItemSerialExistsWhenEditing(){
        // return true if serial does not exist
        if (myToDoTable.getItems().size() == 0){
            return true;
        }
        else {
            for (int i = 0; i < myToDoTable.getItems().size(); i++){
                InventoryItem holder = myToDoTable.getItems().get(i);
                if (holder.getItemSerialNumber().equals(ItemSerialNumber.getText().toUpperCase())){
                    showErrorMessageSerialNumberExists(holder.getItemSerialNumber());
                    return false;
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
            if (holder.getItemSerialNumber().equals(serial.toUpperCase())){
                System.out.println("Serial number already exists in table");
                showErrorMessageSerialNumberExists(holder.getItemSerialNumber());
                return false;
            }
        }
        }
        return true;
    }

    private void RemoveItem(){
        // gets selected index and removes it
        int selected = myToDoTable.getSelectionModel().getSelectedIndex();
        // item must be selected before removing something
        if (selected >= 0)
            dataList.remove(selected);
    }

    private void modifyItemInformation(){
        if (isItemValidWhenEditing()) {
            myToDoTable.getSelectionModel().getSelectedItem().setItemValue((ItemValue.getText()));
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

    private void OpenFileChooserExport() throws FileNotFoundException {
        ExportItemController App = new ExportItemController();
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
            //saveDataToFile(file, getFileExtension(file));
            // gets items in table and add to list
            ObservableList<InventoryItem> list = myToDoTable.getItems();
            App.saveDataToFile(file, App.getFileExtension(file), list);
        }
    }

    private void OpenFileChooserImport() throws FileNotFoundException {
        ImportItemController App = new ImportItemController();
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
            App.readDataFromFile(file, App.getFileExtension(file), dataList);
        }
    }

    private double getValue(InventoryItem item){
        return Double.parseDouble(item.getItemValue());
    }

    public void showErrorMessageSerialNumberExists(String SN){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        String error = "Cannot add item. The serial number " + SN + " already exists.";
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(error);
        errorAlert.showAndWait();
    }

    private void showErrorMessageSerialNumberLength(){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        String error = "Cannot add item. The serial number must be in the format XXXXXXXXXX.";
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(error);
        errorAlert.showAndWait();
    }

    private void showErrorMessageSerialNumberInvalid(){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        String error = "Cannot add item. The serial number must contain only letters or digits.";
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(error);
        errorAlert.showAndWait();
    }

    private void showErrorMessageBadValue(){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        String error = "Cannot add item. The value must be greater than $0.00.";
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(error);
        errorAlert.showAndWait();
    }


    private void showErrorMessageNullValue() {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        String error = "Cannot add item. The value must be a valid number.";
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(error);
        errorAlert.showAndWait();
    }

    private void showErrorMessageInvalidNameLength(){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        String error = "Cannot add item. The name must be between 2 and 256 characters in length.";
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(error);
        errorAlert.showAndWait();
    }
}
