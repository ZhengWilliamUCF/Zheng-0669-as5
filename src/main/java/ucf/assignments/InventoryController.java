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
        System.out.println(isItemValueValid());
        System.out.println(isItemSerialNumberValid());
        System.out.println(isItemNameValid());
    }

    @FXML
    public void EditItemButtonClicked(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // sets column data
        InventoryItemValueColumn.setCellValueFactory(new PropertyValueFactory<>("itemValue"));
        InventoryItemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        InventoryItemSerialNumberColumn.setCellValueFactory(new PropertyValueFactory<>("itemSerialNumber"));
    }

    private void AddItemToTable(){
        // adds new item to table view
        myToDoTable.getItems().add(new InventoryItem(Integer.parseInt(ItemValue.getText()), ItemSerialNumber.getText(), ItemName.getText()));
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
        return true;
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
}
