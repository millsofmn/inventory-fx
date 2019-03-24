package inventory.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    
    private ObservableList<Product> products = FXCollections.observableArrayList();
    private ObservableList<Part> parts = FXCollections.observableArrayList();

    public void addProduct(Product product){
        this.products.add(product);
    }
    
    public boolean removeProduct(int productId){
        return this.products.removeIf(prod -> prod.getProductId() == productId);
    }
    
    public Product lookupProduct(int productId){
        return this.products.stream()
                .filter(product -> product.getProductId() == productId)
                .findFirst()
                .orElse(null);
    }
    
    public void updateProduct(int id){
        ;
    }
    
    public void addPart(Part part){
        this.parts.add(part);
    }
    
    public boolean removePart(int partId){
        return this.parts.removeIf(prod -> prod.getPartId() == partId);
    }
    
    public Part lookupPart(int partId){
        return this.parts.stream()
                .filter(part -> part.getPartId() == partId)
                .findFirst()
                .orElse(null);
    }
    
    public void updatePart(int id){
        ;
    }
    
}
