package inventory.view;

import inventory.InventoryApp;
import inventory.model.InHouse;
import inventory.model.Outsourced;
import inventory.model.Part;
import inventory.model.ValidationException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PartsController implements Initializable {
    
    private static Logger logger = Logger.getLogger(PartsController.class.getName());
    
    private static final String MAN_IN_HOUSE_TXT = "Machaine Id";
    private static final String MAN_OUTSOURCE_TXT = "Company Name";
    private static final String SCREEN_TITLE_ADD = "Add Part";
    private static final String SCREEN_TITLE_MODIFY = "Modify Part";
    
    @FXML
    private RadioButton inHouseBtn;
    
    @FXML
    private RadioButton outsourcedBtn;
    
    @FXML
    private TextField partIdTxtFld;
    
    @FXML
    private TextField partNameTxtFld;
    
    @FXML
    private TextField partQtyTxtFld;
    
    @FXML
    private TextField partPriceTxtFld;
    
    @FXML
    private TextField partMaxTxtFld;
    
    @FXML
    private TextField partMinTxtFld;
    
    @FXML
    private TextField partManTxtFld;
    
    @FXML
    private Label manufactureLabel;
    
    @FXML
    private Label screenLabel;
    
    @FXML
    private TextField manufactureTxtFld;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logger.info("Initalizing PartsController");
        if(MainController.getModifyPartId() != null){
            Part part = InventoryApp.inventoryRepository.lookupPart(MainController.getModifyPartId());
            logger.info("Found part " + part.getName() + " " + part.getPartId());
            partToScreen(part);
            screenLabel.setText(SCREEN_TITLE_MODIFY);
            
        } else {
            setFormInHouse();
            screenLabel.setText(SCREEN_TITLE_ADD);
        }
    }    
    
    @FXML 
    private void handleInHouseButton(ActionEvent event) {
        logger.info("Clicked InHouse Button");
        setFormInHouse();
    }
    
    @FXML 
    private void handleOutsourcedButton(ActionEvent event) {
        logger.info("Clicked Outsourced Button");
        setFormOutsource();
    }
    
    @FXML
    private void handleSaveButton(ActionEvent event) throws IOException {
        logger.info("Clicked Save Button");
        try {
            if(MainController.getModifyPartId() != null){
                updatePart();
                logger.info("Part Updated");
            } else {
                addNewPart();
                logger.info("Added new part");
            }
            loadMainScreen(event);
        } catch (ValidationException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ValidationError");
            alert.setHeaderText("Part not valid");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleCancelButton(ActionEvent event) throws IOException{
        logger.info("Clicked Cancel Button");
        
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
    
    private void setFormOutsource(){
        outsourcedBtn.setSelected(true);
        manufactureLabel.setText(MAN_OUTSOURCE_TXT);
        manufactureTxtFld.setPromptText(MAN_OUTSOURCE_TXT);
    }
    
    private void setFormInHouse(){
        inHouseBtn.setSelected(true);
        manufactureLabel.setText(MAN_IN_HOUSE_TXT);
        manufactureTxtFld.setPromptText(MAN_IN_HOUSE_TXT);
    }
    
    private void addNewPart(){
        Part part = screenToPart();
        InventoryApp.inventoryRepository.addPart(part);
    }
    
    private void updatePart(){
        Part part = screenToPart();
        InventoryApp.inventoryRepository.updatePart(part);
    }
    
    private Part screenToPart(){
        
        if(partNameTxtFld.getText().isEmpty()){
            throw new ValidationException("Name Must Be Filled In");
        }
        
        if(partQtyTxtFld.getText().isEmpty()) {
            partQtyTxtFld.setText("0");
        }
        if(partQtyTxtFld.getText().isEmpty() || !isInt(partQtyTxtFld.getText())){
            throw new ValidationException("Inventory Must Be Numeric");
        }
        
        if(partPriceTxtFld.getText().isEmpty() || !isDouble(partPriceTxtFld.getText())){
            throw new ValidationException("Price must be Numeric");
        }
        
        if(partMaxTxtFld.getText().isEmpty() || !isInt(partMaxTxtFld.getText())){
            throw new ValidationException("Max must be numeric");
        }
        
        if(partMinTxtFld.getText().isEmpty() || !isInt(partMinTxtFld.getText())){
            throw new ValidationException("Min must be numeric");
        }
        
        if(Integer.valueOf(partMinTxtFld.getText()) > Integer.valueOf(partMaxTxtFld.getText())){
            throw new ValidationException("Min must be less than Max");
        }
        
        if (Integer.valueOf(partQtyTxtFld.getText()) > Integer.valueOf(partMaxTxtFld.getText())) {
            throw new ValidationException("Inventory cannot exceed max!");
        }

        if (Integer.valueOf(partQtyTxtFld.getText()) < Integer.valueOf(partMinTxtFld.getText())) {
            throw new ValidationException("Inventory cannot be less than min!");
        }

        Part part;
        if(inHouseBtn.isSelected()){
            
            if(!isInt(manufactureTxtFld.getText())){
                throw new ValidationException("Manufacture Id must be numeric");
            }
            part = new InHouse();
            ((InHouse)part).setMachineId(Integer.valueOf(manufactureTxtFld.getText()));
        } else {
            if(manufactureTxtFld.getText().isEmpty()){
                throw new ValidationException(MAN_OUTSOURCE_TXT + " must be filled in");
            }
            part = new Outsourced();
            ((Outsourced)part).setCompanyName(manufactureTxtFld.getText());
        }
        
        part.setName(partNameTxtFld.getText());
        part.setInStock(Integer.valueOf(partQtyTxtFld.getText()));
        part.setPrice(Double.valueOf(partPriceTxtFld.getText()));
        part.setMax(Integer.valueOf(partMaxTxtFld.getText()));
        part.setMin(Integer.valueOf(partMinTxtFld.getText()));
        
        if(isInt(partIdTxtFld.getText())){
            Integer id = Integer.valueOf(partIdTxtFld.getText());
            part.setPartId(id);
        }
        
        return part;
    }
    
    private void partToScreen(Part part){
        logger.info("Loading part to screen " + part.getName() + " " + part.getPartId());
        partIdTxtFld.setText(Integer.toString(part.getPartId()));
        partNameTxtFld.setText(part.getName());
        partMinTxtFld.setText(Integer.toString(part.getMin()));
        partMaxTxtFld.setText(Integer.toString(part.getMax()));
        partQtyTxtFld.setText(Integer.toString(part.getInStock()));
        partPriceTxtFld.setText(Double.toString(part.getPrice()));

        if(part instanceof InHouse){
            setFormInHouse();
            manufactureTxtFld.setText(Integer.toString(((InHouse)part).getMachineId()));
        } else {
            setFormOutsource();
            manufactureTxtFld.setText(((Outsourced)part).getCompanyName());
        }
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
