package com.company.InputManager;

public class ProductionEntry {

    private final int itemNumber;
    private final String itemDescription;
    private final int julianDate;
    private final float initalP;
    private final float finalP;

    public ProductionEntry(int itemNumber, String itemDescription, int julianDate, float initialP, float finalP){
        this.itemNumber = itemNumber; this.itemDescription = itemDescription;
        this.julianDate = julianDate;
        this.initalP = initialP; this.finalP = finalP;
    }

    public int getItemNumber(){
        return itemNumber;
    }
    public String getItemDescription() {
        return itemDescription;
    }
    public int getJulianDate() {
        return julianDate;
    }
    public float getInitalP(){
        return initalP;
    }
    public float getFinalP(){
        return finalP;
    }

    public float getYieldPercentage(){
        return (initalP-finalP)/initalP;
    }

    //Overriding toString()
    @Override
    public String toString(){
        return String.format("Item Number: %s\n " +
                             "Julian Date: " + julianDate + "\n " +
                             "Item Initial #: %f\n " +
                             "Item Final #: %f",
                             itemNumber, initalP, finalP);

    }
    // Overriding equals() to compare two ProductionEntry objects
    @Override
    public boolean equals(Object obj) {
        if (obj == this){
            return true;
        }
        if(!(obj instanceof ProductionEntry)) {
            return false;
        }
        ProductionEntry c = (ProductionEntry) obj;
        if(itemNumber != c.getItemNumber())
            return false;
        else if(!itemDescription.equals(c.getItemDescription()))
            return false;
        else if(julianDate != c.getJulianDate())
            return false;
        else if(initalP != c.getInitalP())
            return false;
        else return finalP == c.getFinalP();
    }
}
