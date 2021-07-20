package ucf.assignments;

/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 William Zheng
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.CharConversionException;
import java.io.Serial;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {
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
    private Button AddEventButton;

    @FXML
    private Button ModifyEventButton;

    @FXML
    public void ImportFileButtonClicked(ActionEvent actionEvent) {
    }

    @FXML
    public void ExportFileButtonClicked(ActionEvent actionEvent) {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
    }

    private void AddItemToTable(){
        // adds new item to table view
        myToDoTable.getItems().add(new InventoryItem(Integer.parseInt(ItemValue.getText()), ItemSerialNumber.getText(), ItemName.getText()));
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
}
