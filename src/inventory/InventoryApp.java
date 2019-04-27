package inventory;

import inventory.model.InHouse;
import inventory.model.Inventory;
import inventory.model.Outsourced;
import inventory.model.Part;
import inventory.model.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InventoryApp extends Application {
    
    public static Inventory inventoryRepository = new Inventory();

    protected static List<Part> partsList = new ArrayList<>();
        
    protected static List<Product> productsList = new ArrayList<>();
    
    @Override
    public void start(Stage stage) throws Exception {
        InHouse wheelBearing = new InHouse();
        wheelBearing.setName("Wheel Bearing");
        wheelBearing.setInStock(10);
        wheelBearing.setMachineId(12345789);
        wheelBearing.setMax(10);
        wheelBearing.setMin(1);
        wheelBearing.setPartId(2345);
        wheelBearing.setPrice(6.89);
        partsList.add(wheelBearing);
        
        InHouse wheelNut = new InHouse();
        wheelNut.setName("Wheel Nut");
        wheelNut.setInStock(10);
        wheelNut.setMachineId(123456789);
        wheelNut.setMax(10);
        wheelNut.setMin(1);
        wheelNut.setPartId(1234);
        wheelNut.setPrice(3.89);
        partsList.add(wheelNut);
        
        InHouse wheelShaft = new InHouse();
        wheelShaft.setName("Wheel Shaft");
        wheelShaft.setInStock(10);
        wheelShaft.setMachineId(1256789);
        wheelShaft.setMax(10);
        wheelShaft.setMin(1);
        wheelShaft.setPartId(7890);
        wheelShaft.setPrice(16.89);
        partsList.add(wheelShaft);
        
        Outsourced grease = new Outsourced();
        grease.setName("Grease");
        grease.setInStock(10);
        grease.setCompanyName("Millers");
        grease.setMax(10);
        grease.setMin(1);
        grease.setPartId(23455);
        grease.setPrice(6.89);
        partsList.add(grease);
        
        Outsourced tire = new Outsourced();
        tire.setName("Tire");
        tire.setInStock(12);
        tire.setCompanyName("Bad Year");
        tire.setMax(10);
        tire.setMin(1);
        tire.setPartId(2334245);
        tire.setPrice(116.89);
        partsList.add(tire);
        
        Outsourced spring = new Outsourced();
        spring.setName("Spring");
        spring.setInStock(10);
        spring.setCompanyName("Millers");
        spring.setMax(10);
        spring.setMin(1);
        spring.setPartId(1213876);
        spring.setPrice(16.89);
        partsList.add(spring);
        
        Product axel = new Product();
        axel.setProductId(14567);
        axel.setName("Axel");
        axel.setInStock(4);
        axel.setMax(10);
        axel.setMin(3);
        axel.setInStock(3);
        axel.setPrice(3456.0);
        axel.addAllAssociatedParts(Arrays.asList(wheelBearing, wheelNut, wheelShaft, spring, grease));
        productsList.add(axel);
        
        Product wheeles = new Product();
        wheeles.setProductId(4567);
        wheeles.setName("Rims");
        wheeles.setInStock(4);
        wheeles.setMax(10);
        wheeles.setMin(3);
        wheeles.setInStock(3);
        wheeles.setPrice(5678.0);
        wheeles.addAllAssociatedParts(Arrays.asList(tire, spring, grease));
        productsList.add(wheeles);

        inventoryRepository.addAllParts(partsList);
        inventoryRepository.addAllProducts(productsList);

        Parent root = FXMLLoader.load(getClass().getResource("view/Main.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
   
}
