package inventory.view;

import inventory.InventoryApp;
import inventory.model.Part;
import inventory.model.Product;
import inventory.model.ValidationException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProductsController implements Initializable {

    private static Logger logger = Logger.getLogger(ProductsController.class.getName());
    
    private static final String SCREEN_TITLE_ADD = "Add Product";
    private static final String SCREEN_TITLE_MODIFY = "Modify Product";
    
    @FXML
    private Label screenLabel;
    
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
    private TableView<Part> searchResultsTable;
    
    @FXML
    private TableColumn<Part, Integer> searchResultPartId;
    
    @FXML
    private TableColumn<Part, String> searchResultPartName;
    
    @FXML
    private TableColumn<Part, Integer> searchResultPartQty;
    
    @FXML
    private TableColumn<Part, Double> searchResultPartPrice;
    
    
    @FXML
    private TextField productIdTxtFld;
    
    @FXML
    private TextField productNameTxtFld;
    
    @FXML
    private TextField productQtyTxtFld;
    
    @FXML
    private TextField productPriceTxtFld;
    
    @FXML
    private TextField productMaxTxtFld;
    
    @FXML
    private TextField productMinTxtFld;
    
    @FXML
    private TextField searchTxtFld;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logger.info("Initalizing Products Controller");
        
        partId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getPartId()).asObject());
        partName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        partQty.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getInStock()).asObject());
        partPrice.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());
        
        
        searchResultPartId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getPartId()).asObject());
        searchResultPartName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        searchResultPartQty.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getInStock()).asObject());
        searchResultPartPrice.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());
        
        if(MainController.getModifyProductId() != null) {
            Product product = InventoryApp.inventoryRepository.lookupProduct(MainController.getModifyProductId());
            logger.info("Found Product " + product.getName() + " " + product.getProductId());
            productToScreen(product);
            screenLabel.setText(SCREEN_TITLE_MODIFY);
        } else {
            screenLabel.setText(SCREEN_TITLE_ADD);
        }
    }    
    
    @FXML
    private void handleSearchButton(ActionEvent event) throws IOException {
        logger.info("Clicked Search Button");
        
        if(searchTxtFld.getText().isEmpty()){
            searchResultsTable.setItems(FXCollections.observableArrayList(InventoryApp.inventoryRepository.getAllParts()));
        } else {
            searchResultsTable.setItems(FXCollections.observableArrayList(InventoryApp.inventoryRepository.findParts(searchTxtFld.getText())));
        }
    }
    
    @FXML
    private void handleAddButton(ActionEvent event) throws IOException {
        logger.info("Clicked Add Button");
        Part selectedPart = searchResultsTable.getSelectionModel().getSelectedItem();
        
        partsTable.getItems().add(InventoryApp.inventoryRepository.lookupPart(selectedPart.getPartId()));
    }
    
    @FXML
    private void handleDeleteButton(ActionEvent event) throws IOException {
        logger.info("Clicked Delete Button");
        
        Part part = partsTable.getSelectionModel().getSelectedItem();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Delete part");
        alert.setHeaderText("Confirm deletion?");
        alert.setContentText("Are you sure you want to remove " + part.getName() + " from product?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            partsTable.getItems().remove(part);
        }
    }
    
    @FXML
    private void handleSaveButton(ActionEvent event) throws IOException {
        logger.info("Clicked Save Button");
        
        try {
            if(MainController.getModifyProductId() != null){
                updateProduct();
                logger.info("Product Updated");
            } else {
                addNewProduct();
                logger.info("Product added");
            }
            loadMainScreen(event);
        } catch (ValidationException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ValidationError");
            alert.setHeaderText("Product not valid");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleCancelButton(ActionEvent event) throws IOException {
        logger.info("Clicked Cancel Button");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Cancel");
        alert.setHeaderText("Confirm cancellation");
        alert.setContentText("Are you sure you want to go back to the main screen?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            loadMainScreen(event);
        }
    }
    
    private void loadMainScreen(ActionEvent event) throws IOException{
        Parent loader = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(loader);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    private void addNewProduct(){
        Product product = screenToProduct();
        InventoryApp.inventoryRepository.addProduct(product);
        
    }
    
    private void updateProduct(){
        Product product = screenToProduct();
        InventoryApp.inventoryRepository.updateProduct(product);
    }
    
    private Product screenToProduct(){
        
        if(productNameTxtFld.getText().isEmpty()){
            throw new ValidationException("Name Must Be Filled In");
        }
        
        if(productQtyTxtFld.getText().isEmpty()) {
            productQtyTxtFld.setText("0");
        }
        if(productQtyTxtFld.getText().isEmpty() || !isInt(productQtyTxtFld.getText())){
            throw new ValidationException("Inventory Must Be Numeric");
        }
        
        if(productPriceTxtFld.getText().isEmpty() || !isDouble(productPriceTxtFld.getText())){
            throw new ValidationException("Price must be Numeric");
        }
        
        if(productMaxTxtFld.getText().isEmpty() || !isInt(productMaxTxtFld.getText())){
            throw new ValidationException("Max must be numeric");
        }
        
        if(productMinTxtFld.getText().isEmpty() || !isInt(productMinTxtFld.getText())){
            throw new ValidationException("Min must be numeric");
        }
        
        if(Integer.valueOf(productMinTxtFld.getText()) > Integer.valueOf(productMaxTxtFld.getText())){
            throw new ValidationException("Min must be less than Max");
        }
        
        if (Integer.valueOf(productQtyTxtFld.getText()) > Integer.valueOf(productMaxTxtFld.getText())) {
            throw new ValidationException("Inventory cannot exceed max!");
        }

        if (Integer.valueOf(productQtyTxtFld.getText()) < Integer.valueOf(productMinTxtFld.getText())) {
            throw new ValidationException("Inventory cannot be less than min!");
        }

        if(partsTable.getItems().size() <= 0){
            throw new ValidationException("Product must have associated parts");
        }
        
        double price = Double.valueOf(productPriceTxtFld.getText());
        double cost = 0.0;
        
        for(Part p : partsTable.getItems()){
            cost += p.getPrice();
        }
        
        if(cost > price){
            throw new ValidationException("Price of product must be more than cost.");
        }
        
        Product product = new Product();
        product.setName(productNameTxtFld.getText());
        product.setInStock(Integer.valueOf(productQtyTxtFld.getText()));
        product.setPrice(price);
        product.setMax(Integer.valueOf(productMaxTxtFld.getText()));
        product.setMin(Integer.valueOf(productMinTxtFld.getText()));
        
        product.addAllAssociatedParts(partsTable.getItems());
        
        if(isInt(productIdTxtFld.getText())){
            Integer id = Integer.valueOf(productIdTxtFld.getText());
            product.setProductId(id);
        }
        
        return product;
    }
    
    private void productToScreen(Product product){
        
        productIdTxtFld.setText(Integer.toString(product.getProductId()));
        
        productNameTxtFld.setText(product.getName());
        productQtyTxtFld.setText(Integer.toString(product.getInStock()));
        productPriceTxtFld.setText(Double.toString(product.getPrice()));
        productMaxTxtFld.setText(Integer.toString(product.getMax()));
        productMinTxtFld.setText(Integer.toString(product.getMin()));
        
        partsTable.setItems(FXCollections.observableArrayList(product.getAllAssociatedParts()));
    }
    
    
    private boolean isInt(String str){
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
    
    private boolean isDouble(String str){
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
}
