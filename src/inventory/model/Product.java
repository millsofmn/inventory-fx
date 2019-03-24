package inventory.model;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    private IntegerProperty productId;
    private StringProperty name;
    private DoubleProperty price;
    private IntegerProperty inStock;
    private IntegerProperty min;
    private IntegerProperty max;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    public int getProductId() {
        return productId.get();
    }

    public void setProductId(int productId) {
        this.productId.set(productId);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public int getInStock() {
        return inStock.get();
    }

    public void setInStock(int inStock) {
        this.inStock.set(inStock);
    }

    public int getMin() {
        return min.get();
    }

    public void setMin(int min) {
        this.min.set(min);
    }

    public int getMax() {
        return max.get();
    }

    public void setMax(int max) {
        this.max.set(max);
    }
    
    public IntegerProperty productIdProperty(){
        return this.productId;
    }
    
    public StringProperty nameProperty(){
        return this.name;
    }
    
    public DoubleProperty priceProperty(){
        return this.price;
    }
    
    public IntegerProperty inStockProperty(){
        return this.inStock;
    }
    
    public IntegerProperty minProperty(){
        return this.min;
    }
    
    public IntegerProperty maxProperty(){
        return this.max;
    }
    
    public void addAssociatedPart(Part part){
        this.associatedParts.add(part);
    }
    
    public boolean removeAssociatedPart(int partId){
        return this.associatedParts.removeIf(prod -> prod.getPartId() == partId);
    }
    
    public Part lookupAssociatedPart(int partId){
        return this.associatedParts.stream()
                .filter(part -> part.getPartId() == partId)
                .findFirst()
                .orElse(null);
    }
}
