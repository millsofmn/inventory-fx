package inventory.view;

import inventory.InventoryApp;
import inventory.model.Part;
import inventory.model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController implements Initializable {

    private static Logger logger = Logger.getLogger(MainController.class.getName());
    
    private static Integer selectedPartsId;
    
    private static Integer selectedProductsId;
    
    @FXML
    private TableView<Part> partsTable;
    
    @FXML
    private TableColumn<Part, Integer> partId;
    
    @FXML
    private TableColumn<Part, String> partName;
    
    @FXML
    private TableColumn<Part, Integer> partQty;
    
    @FXML
    private TableColumn<Part, Double> partPrice;
    
    @FXML
    private TableView<Product> productsTable;
    
    @FXML
    private TableColumn<Product, Integer> productId;
    
    @FXML
    private TableColumn<Product, String> productName;
    
    @FXML
    private TableColumn<Product, Integer> productQty;
    
    @FXML
    private TableColumn<Product, Double> productPrice;
    
    @FXML
    private TextField searchPartsTxtFld;
    
    @FXML
    private TextField searchProductsTxtFld;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logger.info("Initalizing MainController");
        loadScreen();
    }    
    
    private void loadScreen(){
        selectedPartsId = null;
        selectedProductsId = null;

        searchPartsTxtFld.setText("");
        searchProductsTxtFld.setText("");
        
        partId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getPartId()).asObject());
        partName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        partQty.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getInStock()).asObject());
        partPrice.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());
        
        partsTable.setItems(FXCollections.observableArrayList(InventoryApp.inventoryRepository.getAllParts()));
        
        
        productId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getProductId()).asObject());
        productName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        productQty.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getInStock()).asObject());
        productPrice.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());
        
        productsTable.setItems(FXCollections.observableArrayList(InventoryApp.inventoryRepository.getAllProducts()));
    }
    
    @FXML
    private void handleAddPartButton(ActionEvent event) throws IOException {   
        logger.info("Clicked Add Part Button");     
        showPartsScreen(event);
    }
    
    @FXML
    private void handleDeletePartButton(ActionEvent event) throws IOException {
        Part part = partsTable.getSelectionModel().getSelectedItem();
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Delete part");
        alert.setHeaderText("Confirm deletion?");
        alert.setContentText("Are you sure you want to delete " + part.getName() + "?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            InventoryApp.inventoryRepository.removePart(part.getPartId());
            loadScreen();
        }
    }
    
    @FXML
    private void handleModifyPartButton(ActionEvent event) throws IOException {
        selectedPartsId = partsTable.getSelectionModel().getSelectedItem().getPartId();
        logger.info("Selecting Part : " + selectedPartsId);
        showPartsScreen(event);
    }
    
    @FXML
    private void handleSearchPartButton(ActionEvent event) throws IOException {   
        logger.info("Clicked Search Part Button");
        
        if(searchPartsTxtFld.getText().isEmpty()){
            partsTable.setItems(FXCollections.observableArrayList(InventoryApp.inventoryRepository.getAllParts()));
        } else {
            partsTable.setItems(FXCollections.observableArrayList(InventoryApp.inventoryRepository.findParts(searchPartsTxtFld.getText())));
        }
    }
    
    @FXML
    private void handleAddProductButton(ActionEvent event) throws IOException {   
        logger.info("Clicked Add Product Button");
        showProductsScreen(event);
    }
    
    @FXML
    private void handleDeleteProductButton(ActionEvent event) throws IOException {  
        logger.info("Clicked Delete Product Button");
        Product product = productsTable.getSelectionModel().getSelectedItem();
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Delete Product");
        alert.setHeaderText("Confirm deletion?");
        alert.setContentText("Are you sure you want to delete " + product.getName() + "?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            InventoryApp.inventoryRepository.removeProduct(product.getProductId());
            loadScreen();
        }
    }
    
    @FXML
    private void handleModifyProductButton(ActionEvent event) throws IOException {  
        selectedProductsId = productsTable.getSelectionModel().getSelectedItem().getProductId();
        logger.info("Clicked Modify Product Button for " + selectedProductsId);
        showProductsScreen(event);
    }
    
    @FXML
    private void handleSearchProductButton(ActionEvent event) throws IOException {   
        logger.info("Clicked Search Product Button");   
        
        if(searchProductsTxtFld.getText().isEmpty()){
            productsTable.setItems(FXCollections.observableArrayList(InventoryApp.inventoryRepository.getAllProducts()));
        } else {
            productsTable.setItems(FXCollections.observableArrayList(InventoryApp.inventoryRepository.findProducts(searchProductsTxtFld.getText())));
        }
    }
    
    @FXML
    private void handleExitButton(ActionEvent event){
        logger.info("Exiting MainScreenController");
        Platform.exit();
        System.exit(0);
    }
    
    private void showPartsScreen(ActionEvent event) throws IOException {
        logger.info("Loading Parts Screen");
        Parent loader = FXMLLoader.load(getClass().getResource("Parts.fxml"));
        Scene scene = new Scene(loader);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    private void showProductsScreen(ActionEvent event) throws IOException {
        logger.info("Loading Products Screen");
        Parent loader = FXMLLoader.load(getClass().getResource("Products.fxml"));
        Scene scene = new Scene(loader);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public static Integer getModifyPartId() {
        return selectedPartsId;
    }
    
    public static Integer getModifyProductId() {
        return selectedProductsId;
    }
    
}
