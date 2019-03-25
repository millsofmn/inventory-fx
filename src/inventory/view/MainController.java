/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author m108491
 */
public class MainController implements Initializable {

    private static Logger logger = Logger.getLogger(MainController.class.getName());
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void handleAddPart(ActionEvent event) throws IOException {        
        showPartsScreen(event);
    }
    
    
    @FXML
    private void handleExitButton(ActionEvent event){
        logger.info("Exiting MainScreenController");
        Platform.exit();
        System.exit(0);
    }
    
    private void showPartsScreen(ActionEvent event) throws IOException {
        logger.info("Loading parts screen");
        Parent loader = FXMLLoader.load(getClass().getResource("Parts.fxml"));
        Scene scene = new Scene(loader);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
}
