# How to use the Application
A tutorial on how to use my java application.
___
## How to add an item
> Note: The Value, Serial Number, and Name of the item must be filled in before you can add it.
> Note: Left-click "Add Item" Button to add event.
___
### Filling in the Value
You can fill in the Value by left-clicking on the TextField and entering an input.
> Note: The input must be greater than zero and be a valid number. The value will be automatically rounded to the nearest cent.
### Filling in the Serial Number
You can fill in the Serial Number by left-clicking on the TextField and entering an input.
> Note: The input must be in the format of XXXXXXXXXX where X can be a letter or a digit.
### Filling in the Name
You can fill in the Name by left-clicking on the TextField and entering an input.
> Note: The name must have a character length between 2 and 256 characters (inclusive). There is no limit on character type.
### Displaying Information
The TableView is separated into three columns each with a corresponding data type.
- The leftmost column (indicated by "Value") displays the value of the item on the same row.
- The middle column (indicated by "Serial Number") displays the serial number of the event in the format of [XXXXXXXXXX].
- The rightmost column (indicated by "Name") displays the name of the item.
## Removing an item
You can remove an item simply by left-clicking on the item in TableView, then clicking the "Delete Item" Button
in the bottom right corner of the application.
## Searching for items
You can search for items by entering an input in the "search item" TextField. The corresponding items that match will be dynamically updated via TableView.
> Note: Items can be searched via their Serial Numbers or Names.
## Sorting Items
You can sort items by clicking on the column name of each column in the TableView. Clicking on it once sorts descending values. Clicking once more sort by Ascending values. Clicking it one more time will reset to no sorting.
## Editing an item
> Note: An item must be selected before you can edit an item.
---
### Selecting an item
An item can be selected by clicking on any column of the row the item is displayed on.
> Note: The input TextField will be automatically filled in accordingly after you select an item.
### Modifying the Item Value
To edit the item value, simply click on the corresponding TextField and modify the Item Value to your liking. (See Filling in the Item Value).\
Afterwards, simply click on the "Modify Item" Button to update the item in the TableView.
### Modifying the Serial Number
To edit the serial number, simply click on the corresponding TextField and modify the Serial Number to your liking. (See Filling in the Serial Number).\
Afterwards, simply click on the "Modify Item" Button to update the item in the TableView.
### Modifying the Item Name
To edit the item name, simply click on the corresponding TextField and modify the Item Name to your liking. (See Filling in the Item Name).\
Afterwards, simply click on the "Modify Item" Button to update the item in the TableView.
> Note: You can also modify the status of an item by left-clicking on the "Change Status" button in the bottom left corner of the application.
## Exporting a List
In order to export a list, simply click the "Export..." Button in the top left of the application.\
Afterwards, a new window will open up. You can select the file export type here. Choose a filename and save it to your selected destination on the computer.
## Importing a List
In order to import a list, simply click the "Import..." Button in the top left of the application.\
Afterwards, a new window will open up. Select a file on your computer and click open. The list will be loaded and displayed in TableView.
> Note: Importing a list will not overwrite any existing items. Any items that cannot be added will be displayed via error message.
