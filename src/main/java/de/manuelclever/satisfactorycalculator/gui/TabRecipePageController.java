package de.manuelclever.satisfactorycalculator.gui;

import de.manuelclever.satisfactorycalculator.content.RecipeGraph;
import de.manuelclever.satisfactorycalculator.content.items.Edge;
import de.manuelclever.satisfactorycalculator.content.items.Item;
import de.manuelclever.satisfactorycalculator.content.items.ItemMap;
import de.manuelclever.satisfactorycalculator.gui.browser.WebBrowser;
import de.manuelclever.satisfactorycalculator.gui.utils.*;
import de.manuelclever.satisfactorycalculator.languages.CurrentLanguage;
import de.manuelclever.satisfactorycalculator.utils.LogGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

import java.util.*;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class TabRecipePageController {

    //panes
    @FXML private GridPane tabRecipePage;
    @FXML private GridPane gridPaneMiddle;

    //tableView
    @FXML private TableView<Item> recipeTable;
    private FilteredList<Item> filteredList;
    private ObservableList<Item> observableList;

    //customClasses
    private ReadAndWrite readAndWrite;
    private FileMenu fileMenu;
    private ResourceLineUtil resourceLineUtil;

    //labels
    @FXML private Label labelItem;
    @FXML private Label labelId;
    @FXML private Label labelMj;
    @FXML private Label labelResources;
    @FXML private Label labelFileName;
    @FXML private Label labelInfo;

    //fields
    @FXML private TextField nameField;
    @FXML private TextField idField;
    @FXML private TextField mjField;
    @FXML private TextField searchField;

    //buttons
    @FXML private ToggleButton bttEnableEditMode;
    @FXML private Button bttCreateNewItem;
    @FXML private Button bttApply;
    @FXML private Button bttRemoveItem;
    @FXML private Button bttAddRessourceLine;

    //menus
    @FXML private MenuButton fileMenuBtt;

    //vBoxes
    @FXML private VBox vBoxLabelId;
    @FXML private VBox vBoxFieldId;
    @FXML private VBox vBoxLabelQuantity;
    @FXML private VBox vBoxFieldQuantity;
    @FXML private VBox vBoxButton;

    //browser
    @FXML private WebView webView;
    WebBrowser webBrowser;

    //variables used to determine if new button should create a new item, or if a new item already exists
    private boolean dontAddNew = false;
    private Item newest = ItemMap.getItem(0);

    //variables which are holding the current and previous selected items
    //these are used because the change listener can't hold the previous item
    private Item oldSelected = ItemMap.getItem(0);
    private Item currentSelected;
    //the index of the currentSelected on the observable list is also being saved
    private int currentSelectedIndex;



    public void initialize() {
        initializeRecipeTable();

        readAndWrite = new ReadAndWrite(this, tabRecipePage, recipeTable,
                labelFileName, labelInfo);

        fileMenu = new FileMenu(this, bttEnableEditMode, fileMenuBtt, labelInfo);
        fileMenu.refreshSubMenus();
        fileMenu.initialize();

        webBrowser = new WebBrowser(recipeTable, webView);

        setFieldHeight(20);

        resourceLineUtil = new ResourceLineUtil(nameField, bttEnableEditMode,
                labelResources, vBoxLabelId, vBoxFieldId, vBoxLabelQuantity, vBoxFieldQuantity, vBoxButton);

        recipeTable.getSelectionModel().selectFirst();
        refreshSelection();

        LogGenerator.log(Level.INFO, LogGenerator.CLASS.TAB_RECIPE, "Initialized");
    }



/*  initializeRecipeTable   */
    private void initializeRecipeTable() {
        //because of problems with importing an observableMap from the item class, an observable list is used.
        //disadvantage is, that when adding and removing items from the ItemMap, they also have to be removed and added
        //to the observable list
        observableList = FXCollections.observableArrayList();

        //fills observable list and sets fileName
        repopulateObservableList();
        labelFileName.setText(ItemMap.getFileSourceName());

        //binds observable list to filteredList
        filteredList = new FilteredList<>(observableList);

        //create searchFilter for filteredList from searchField
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(item -> Integer.toString(item.getId()).contains(newValue) ||
                    item.getName().toLowerCase().contains(newValue.toLowerCase()));
        });

        //create sortedList from filteredList
        SortedList<Item> sortedList = new SortedList<>(filteredList);

        createSetAndBindColumns(sortedList);
        setHoverSettingsRecipeTable();
        setOnSelectionChange();
    }

    private void createSetAndBindColumns(SortedList<Item> sortedList) {
        //create and set columns
        TableColumn<Item, Integer> tableColumnId = new TableColumn<>(CurrentLanguage.getString("id"));
        TableColumn<Item, String> tableColumnName = new TableColumn<>(CurrentLanguage.getString("name"));

        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        tableColumnId.setMaxWidth(1000);
        tableColumnId.setStyle( "-fx-alignment: CENTER-RIGHT");

        //bind sortedList comparator to tableView
        recipeTable.setItems(sortedList);
        sortedList.comparatorProperty().bind(recipeTable.comparatorProperty());

        //add columns
        recipeTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        recipeTable.getColumns().addAll(tableColumnId, tableColumnName);
        recipeTable.getSortOrder().addAll(tableColumnId, tableColumnName);

        LogGenerator.log(Level.INFO, LogGenerator.CLASS.TAB_RECIPE, "Finished initialization of recipeTable");
    }

    private void setHoverSettingsRecipeTable() {
        //hover settings for table view, adds changeListener
        recipeTable.setRowFactory(tableView -> {
            final TableRow<Item> row = new TableRow<>();

            row.hoverProperty().addListener(observable -> {
                final Item item = row.getItem();

                if (row.isHover() && item != null) {
                    //color
                    row.setStyle("-fx-background-color: mediumseagreen");

                    //tooltip
                    StringBuilder tooltip = new StringBuilder();
                    item.getEdges().stream().filter(edge -> edge.getResourceId() != 0).forEach(edge -> {
                        String ressource = ItemMap.getItem(edge.getResourceId()).getName();
                        tooltip.append(ressource).append(": ").append(edge.getWeight()).append("\n");
                    });
                    tableView.setTooltip(new MyTooltip(tooltip.toString()));
                } else {
                    row.setStyle("");
                }
            });
            return row;
        });
    }

    private void setOnSelectionChange() {
        //what happens if the selected item changes
        recipeTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            //fills global variables with currently selected and beforehand selected items
            if(currentSelected != null) {
                oldSelected = new Item(currentSelected.getId(), currentSelected.getName(), currentSelected.getMj(), currentSelected.getEdges());
            }
            currentSelected = newValue;
            currentSelectedIndex = observableList.indexOf(newValue);

            //if a new item was added, selection stays on newItem until it gets saved or removed
            if(dontAddNew) {
                //select(index) works, select(item) doesn't work correctly
                recipeTable.getSelectionModel().select(observableList.indexOf(newest));
            }
            //shows the selected item on the info site on the right
            if (currentSelected != null && oldSelected != null && !currentSelected.equals(oldSelected)) {
                refreshSelection();
            }

        });
    }



/*  repopulateObservableList   */
    public void repopulateObservableList() {
        observableList.clear();

        //filters Item "0" from ItemMap, before adding to observable list
        //item "0" is only needed intern (refer to Calculator class for more details) and should not be visible to user
        observableList.addAll(ItemMap.get().values().stream()
                .filter(item -> item.getId() != 0)
                .collect(Collectors.toList()));
        //after have been refreshed, select first item
        recipeTable.getSelectionModel().select(currentSelected);
    }



/*  refreshSelection    */
    public void refreshSelection() {
        //gets the currently selected item and fills txtFields
        Item newItem = recipeTable.getSelectionModel().getSelectedItem();
        nameField.setText(newItem.nameProperty() != null ? newItem.getName() : "");
        idField.setText(Integer.toString(newItem.getId()));
        mjField.setText(Integer.toString(newItem.getMj()));

        //clears the old ressource lines, bevor adding the current content
        resourceLineUtil.clearRessourceLines();
        LinkedList<Edge> edges = newItem.getEdges();
        edges.forEach(edge -> resourceLineUtil.addResourceLine(Integer.toString(edge.getResourceId()), Integer.toString(edge.getWeight())));

        webBrowser.reactToSelectionChange(oldSelected, newItem);
    }



/*  clearSelection  */
    private void clearSelection() {
        nameField.clear();
        idField.clear();
        mjField.clear();
        resourceLineUtil.clearRessourceLines();
        recipeTable.getSelectionModel().clearSelection();
    }



/*  setOnApply  */
    public void setOnApply() {
        int idSelectedItem = recipeTable.getSelectionModel().getSelectedItem().getId();

        //gets content from txtFields
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        int mj = Integer.parseInt(mjField.getText());

        // checks entry for validity, if not error is shown and method cancels
        if(!isEntryValid(name, id, mj)) {
            LogGenerator.log(Level.INFO, LogGenerator.CLASS.TAB_RECIPE, "Failed to edit entry, invalid input");
            return;
        }

        // if createEdgeList() fails it returns null, error alert is created in method
        LinkedList<Edge> edges = createEdgeList(id);
        if(edges == null) {
            LogGenerator.log(Level.INFO, LogGenerator.CLASS.TAB_RECIPE, "Failed to edit entry, invalid resources");
            return;
        }

        //add item
        Item newItem = new Item(id, name, mj, edges);
        //put the newItem on the itemMap
        ItemMap.put(newItem);

        //if item's id was changed, check each item in ItemMap for an edge connection
        //if found replace the old value with the new one
        if(idSelectedItem != id) {
            ItemMap.get().values().forEach(item -> {
                for(Edge edge : item.getEdges()) {
                    if(edge.getResourceId() == idSelectedItem) {
                        edge.setResource(id);
                    }
                }
            });
            LogGenerator.log(Level.INFO, LogGenerator.CLASS.TAB_RECIPE, "Changed the id for all connected entries");
        }

        //and to the observable list, replace if already existing
        repopulateObservableList();
        //also refresh the recipeGraph, which can show the linkages between the items visually
        RecipeGraph.refresh();
        //last selected the newly added item in the tableView
        recipeTable.getSelectionModel().select(newItem);
        //set info about which item got changed
        labelInfo.setText(CurrentLanguage.getString("appliedChanges") + id + ", " + name);

        dontAddNew = false;

        LogGenerator.log(Level.INFO, LogGenerator.CLASS.TAB_RECIPE, "Entry successfully edited");
    }

    private boolean isEntryValid(String name, int id, int mj) {
        //check for valid name
        if(name == null || name.equals("")) {
            AlertError alert = new AlertError(tabRecipePage, CurrentLanguage.getString("itemNotSaved"),
                    CurrentLanguage.getString("nameNotSelected"), true, ButtonType.OK);
            return false;
        }

        //check for valid id
        if(ItemMap.getItem(id) != null || id <= 0) {
            AlertError alert = new AlertError(tabRecipePage, CurrentLanguage.getString("itemNotSaved"),
                    CurrentLanguage.getString("idExists"), true, ButtonType.OK);
            return false;
        }

        // check for valid mj
        if(mj < 0) {
            AlertError alert = new AlertError(tabRecipePage, CurrentLanguage.getString("invalidNumber"),
                    CurrentLanguage.getString("mjInvalid"), true, ButtonType.OK);
            return false;
        }

        //checks for name duplicate, other than itself
        if(ItemMap.containsName(name)) {
            return !isDuplicateName(name, recipeTable.getSelectionModel().getSelectedItem().getId());
        }

        return true;
    }

    private LinkedList<Edge> createEdgeList(int id) {
        LinkedList<Edge> edges = new LinkedList<>();

        //two temp lists, filled with all children to create the edges later
        List<Integer> ressource = new ArrayList<>();
        List<Integer> weight = new ArrayList<>();
        vBoxFieldId.getChildren().filtered(field -> !((TextField) field).getText().equals(""))
                .forEach((field) -> ressource.add( Integer.parseInt( ( (TextField) field).getText())));
        vBoxFieldQuantity.getChildren().filtered(field -> !((TextField) field).getText().equals(""))
                .forEach((field) -> weight.add( Integer.parseInt( ( (TextField) field).getText())));


        // if both vBoxes have the same count of entries, fill edge list
        if(ressource.size() == weight.size()) {

            //if it is a primary ressource(no predecessors) the edge 0,0 is added, which points to the
            //dummy item. More on that in the calculator class
            if(ressource.size() == 0) {
                edges.add(new Edge(id, 0, 0));
            } else {
                for (int i = 0; i < ressource.size(); i++) {
                    if(ItemMap.containsKey(ressource.get(i))) {
                        if(weight.get(i) > 0) {
                            edges.add(new Edge(id, ressource.get(i), weight.get(i)));
                        } else {
                            AlertError alert = new AlertError(tabRecipePage, CurrentLanguage.getString("itemNotSaved"),
                                    CurrentLanguage.getString("quantityWrong"), true, ButtonType.OK);
                            return null;
                        }
                    } else {
                        AlertError alert = new AlertError(tabRecipePage, CurrentLanguage.getString("itemNotSaved"),
                                CurrentLanguage.getString("noPredecessor") + ressource.get(i), true, ButtonType.OK);
                        return null;
                    }
                }
            }
        } else {
            AlertError alert = new AlertError(tabRecipePage, "Item couldn't be saved",
                    "Ressource input was faulty.", true, ButtonType.OK);
            return null;
        }
        return edges;
    }

    private boolean isDuplicateName(String name, int idSelectedItem) {
        // collects all items with the same name in a list, should only be one
        List<Item> result = observableList.stream().filter(item ->
                item.getName().equalsIgnoreCase(name)).collect(Collectors.toList());
        int idExists = 0;
        if(result.size() == 1) {
            for (Item item : result) {
                idExists = item.getId();
            }
        } else {
            // to Do: create alert which shows line of code or write to log
        }

        if(idExists != idSelectedItem) {
            AlertError alert = new AlertError(tabRecipePage, CurrentLanguage.getString("itemNotSaved"),
                    CurrentLanguage.getString("nameExists") + "\n\n\t" + idExists, true, ButtonType.OK);
            return true;
        }
        return false;
    }



/*  createNewItem   */
    public void createNewItem() {
        if(!dontAddNew) {
            dontAddNew = true;

            Item newItem = new Item();
            newest = newItem;
            newItem.setId(ItemMap.getFreeID()[1]);
            newItem.addEdge(newItem.getId(), 0, 0);
            observableList.add(newItem);
            recipeTable.getSelectionModel().select(newItem);
            LogGenerator.log(Level.INFO, LogGenerator.CLASS.TAB_RECIPE, "New item created");
        } else {
            LogGenerator.log(Level.WARNING, LogGenerator.CLASS.TAB_RECIPE,
                    "User tried creating new entry, while an unsaved new entry already existed");
        }
    }


/*  removeItem  */
    public void removeItem() {
        Item itemToBeRemoved = recipeTable.getSelectionModel().getSelectedItem();

        if(!removeConnections(itemToBeRemoved)) {
            LogGenerator.log(Level.INFO, LogGenerator.CLASS.TAB_RECIPE, "Removed connections in other entries");
            return;
        }

        //removes item from the ItemMap
        ItemMap.remove(itemToBeRemoved);
        //removes item from the observable list
        observableList.remove(itemToBeRemoved);
        //refreshes the recipeGraph, which can show the linkages between the items visually
        RecipeGraph.refresh();

        labelInfo.setText(CurrentLanguage.getString("removedItem") + itemToBeRemoved.getId() + ", " +
                itemToBeRemoved.getName() + CurrentLanguage.getString("andConnections"));
        //if the newly created item was removed, change the state of don't add new to false,
        //because now you should be able to add a new item again
        if (itemToBeRemoved.getId() == newest.getId()) {
            dontAddNew = false;
        }

        LogGenerator.log(Level.INFO, LogGenerator.CLASS.TAB_RECIPE, "Removed entry: " + itemToBeRemoved.getName());
    }

    private boolean removeConnections(Item itemToBeRemoved) {
        Map<Item, Edge> connectedItems = returnConnectedItems(itemToBeRemoved);

        //if connections were found a warning is issued
        if (connectedItems.size() > 0) {
            //builds a string with all id's and names from connected items
            Supplier<String> supplier = () -> {
                StringBuilder sb = new StringBuilder();
                connectedItems.forEach((item, edge) -> {
                    sb.append(edge.getProduct()).append(", ").append(ItemMap.getItem(edge.getProduct()).getName())
                            .append("\n");
                });
                return sb.toString();
            };

            //creates an error with all connected items
            AlertError error = new AlertError(tabRecipePage, CurrentLanguage.getString("removingItemTitle"),
                    CurrentLanguage.getString("removingItem") + "\n" + supplier.get(),
                    false, ButtonType.OK, ButtonType.CANCEL);

            //if ok is pressed, all connections are getting removed from other items
            Optional<ButtonType> result = error.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                connectedItems.forEach((item, edge) -> item.getEdges().remove(edge));
                return true;
            } else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                //if a cancel was issued, the method ends without removing an item
                return false;
            }
        }
        return true;
    }

    private Map<Item, Edge> returnConnectedItems(Item itemToBeRemoved) {
        Map<Item, Edge> connectedItems = new HashMap<>();
        ItemMap.get().values().forEach(item -> {
            for(Edge edge : item.getEdges()) {
                if(edge.getResourceId() == itemToBeRemoved.getId()) {
                    connectedItems.put(item, edge);
                    break;
                }
            }
        });
        return connectedItems;
    }



/*  toggleEditMode  */
    public void toggleEditMode() {
        //toggle all the settings to allow or disable edit abilities
        if(bttEnableEditMode.isSelected()) {
            idField.setEditable(true);
            nameField.setEditable(true);
            mjField.setEditable(true);
            vBoxFieldId.getChildren().forEach(field -> ( (TextField) field).setEditable(true));
            vBoxFieldQuantity.getChildren().forEach(field -> ( (TextField) field).setEditable(true));

            bttApply.setVisible(true);
            bttCreateNewItem.setVisible(true);
            bttRemoveItem.setVisible(true);
            vBoxButton.getChildren().forEach(button -> button.setVisible(true));
            bttAddRessourceLine.setVisible(true);

            LogGenerator.log(Level.INFO, LogGenerator.CLASS.TAB_RECIPE, "Edit mode enabled");
        } else {
            idField.setEditable(false);
            nameField.setEditable(false);
            mjField.setEditable(false);
            vBoxFieldId.getChildren().forEach(field -> ( (TextField) field).setEditable(false));
            vBoxFieldQuantity.getChildren().forEach(field -> ( (TextField) field).setEditable(false));

            bttApply.setVisible(false);
            bttCreateNewItem.setVisible(false);
            bttRemoveItem.setVisible(false);
            vBoxButton.getChildren().forEach((button) -> button.setVisible(false));
            bttAddRessourceLine.setVisible(false);

            //refresh the selection and observableList, so neither new, but not saved, nor edited,
            //but not saved, items will be shown
            Item selectedItem = recipeTable.getSelectionModel().getSelectedItem();
            repopulateObservableList();
            //select the last edited item, if that doesn't exist anymore, select the last item on the table instead
            recipeTable.getSelectionModel().select(selectedItem.getName() != null ? selectedItem : observableList.get(observableList.size()-1));
            refreshSelection();

            dontAddNew = false;

            LogGenerator.log(Level.INFO, LogGenerator.CLASS.TAB_RECIPE, "Edit mode disabled");
        }
    }



/*  setFieldHeight - sets all the fields, except documentation and hyperlinks, to the same height   */
    private void setFieldHeight(int height) {
        labelItem.setPrefHeight(height);
        labelId.setPrefHeight(height);
        labelMj.setPrefHeight(height);

        idField.setPrefHeight(height);
        nameField.setPrefHeight(height);
        mjField.setPrefHeight(height);
        vBoxFieldId.getChildren().forEach(field -> ( (TextField) field).setPrefHeight(height));

        vBoxButton.getChildren().forEach(button -> ( (Button) button).setPrefHeight(height));
        bttAddRessourceLine.setPrefHeight(height);
    }



/*  addResourceLine */
    public void addResourceLine() {
        resourceLineUtil.addResourceLine();
    }



/*  getters */
    public FileMenu getFileMenu() {
        return fileMenu;
    }

    public ReadAndWrite getReadAndWrite() {
        return readAndWrite;
    }

    public Item getOldSelected() {
        return oldSelected;
    }
}