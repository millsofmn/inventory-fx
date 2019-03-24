package inventory.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public abstract class Part {
    
    private IntegerProperty partId;
    private StringProperty name;
    private DoubleProperty price;
    private IntegerProperty inStock;
    private IntegerProperty min;
    private IntegerProperty max;

    public int getPartId() {
        return partId.get();
    }

    public void setPartId(int partId) {
        this.partId.set(partId);
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

    public IntegerProperty getPartIdProperty() {
        return partId;
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public DoubleProperty getPriceProperty() {
        return price;
    }

    public IntegerProperty getInStockProperty() {
        return inStock;
    }

    public IntegerProperty getMinProperty() {
        return min;
    }

    public IntegerProperty getMaxProperty() {
        return max;
    }
    
    
}
