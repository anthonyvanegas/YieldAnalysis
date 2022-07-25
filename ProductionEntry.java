package com.company;

public class ProductionEntry {

    private String itemNumber;
    private int julianDate;
    private float initalP;
    private float finalP;

    public ProductionEntry(String itemNumber, int julianDate, float initialP, float finalP){
        this.itemNumber = itemNumber; this.julianDate = julianDate;
        this.initalP = initialP; this.finalP = finalP;
    }

    public void setItemNumber(String itemNumber){
        this.itemNumber = itemNumber;
    }
    public void setJulianDate(int julianDate){
        this.julianDate = julianDate;
    }
    public void setInitalP(float initalP){
        this.initalP = initalP;
    }
    public void setFinalP(float finalP){
        this.finalP = finalP;
    }

    public String getItemNumber(){
        return getItemNumber();
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
        return (finalP-initalP)/finalP;
    }

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
        if(!itemNumber.equals(c.getItemNumber()))
            return false;
        else if(julianDate != c.getJulianDate())
            return false;
        else if(initalP != c.getInitalP())
            return false;
        else if(finalP != c.getFinalP())
            return false;
        else
            return true;
    }
}
