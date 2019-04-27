package inventory.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Product {

    private int productId;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;
    private List<Part> associatedParts = new ArrayList<>();

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }


    public void addAssociatedPart(Part part){
        this.associatedParts.add(part);
    }

    public void addAllAssociatedParts(Collection<Part> associatedParts){
        this.associatedParts.addAll(associatedParts);
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

    public List<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
