package inventory.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Inventory {

    private Random rand = new Random();
    private List<Product> products = new ArrayList<>();
    private List<Part> allParts = new ArrayList<>();

    public void addProduct(Product product){
        if(product.getProductId() <= 1){
            int id = rand.nextInt(1000);
            product.setProductId(id);
        }
        this.products.add(product);
    }
    
    public List<Product> findProducts(String text){
        List<Product> thisProduct = new ArrayList<>();
        thisProduct.addAll(this.products.stream()
                .filter(p -> p.getName().toLowerCase().contains(text.toLowerCase()) || 
                        String.valueOf(p.getProductId()).contains(text.toLowerCase()) ||
                        String.valueOf(p.getPrice()).contains(text.toLowerCase()))
                .collect(Collectors.toList()));
        return thisProduct;
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
    
    public void updateProduct(Product product){
        removeProduct(product.getProductId());
        addProduct(product);
    }
    
    public void addPart(Part part){
        if(part.getPartId() <= 1){
            int id = rand.nextInt(1000);
            part.setPartId(id);
        }
        this.allParts.add(part);
    }
    
    public void addAllParts(Collection<Part> parts) {
        this.allParts.addAll(parts);
    }
    
    public List<Part> findParts(String text){
        List<Part> parts = new ArrayList<>();
        parts.addAll(this.allParts.stream()
                .filter(p -> p.getName().toLowerCase().contains(text.toLowerCase()) || 
                        String.valueOf(p.getPartId()).contains(text.toLowerCase()) ||
                        String.valueOf(p.getPrice()).contains(text.toLowerCase()))
                .collect(Collectors.toList()));
        return parts;
    }
    
    public boolean removePart(int partId){
        return this.allParts.removeIf(prod -> prod.getPartId() == partId);
    }
    
    public Part lookupPart(int partId){
        return this.allParts.stream()
                .filter(part -> part.getPartId() == partId)
                .findFirst()
                .orElse(null);
    }
    
    public void updatePart(Part part){
        removePart(part.getPartId());
        addPart(part);
    }
    
    public void addAllProducts(Collection<Product> products){
        this.products.addAll(products);
    }

    public List<Part> getAllParts() {
        return allParts;
    }

    public List<Product> getAllProducts() {
        return products;
    }
}
