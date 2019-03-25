package inventory.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class InHouse extends Part {
    
    private IntegerProperty machineId = new SimpleIntegerProperty();

    public int getMachineId() {
        return machineId.get();
    }

    public void setMachineId(int machineId) {
        this.machineId.set(machineId);
    }
    
    public IntegerProperty machineIdProperty(){
        return this.machineId;
    }
}
