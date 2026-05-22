package com.example.kursova.requests;

public class CarFilterRequest {

    private String search; // marka або model
    private String color;
    private String bodyType;
    private String transmission;

    private Integer yearFrom;
    private Integer yearTo;

    private Integer priceFrom;
    private Integer priceTo;

    private Integer enginePowerFrom;
    private Integer enginePowerTo;

    private Integer doorFrom;
    private Integer doorTo;

    private String engineType;

    private String condition;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public Integer getYearFrom() {
        return yearFrom;
    }

    public void setYearFrom(Integer yearFrom) {
        this.yearFrom = yearFrom;
    }

    public Integer getYearTo() {
        return yearTo;
    }

    public void setYearTo(Integer yearTo) {
        this.yearTo = yearTo;
    }

    public Integer getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(Integer priceFrom) {
        this.priceFrom = priceFrom;
    }

    public Integer getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(Integer priceTo) {
        this.priceTo = priceTo;
    }

    public Integer getEnginePowerFrom() {
        return enginePowerFrom;
    }

    public void setEnginePowerFrom(Integer enginePowerFrom) {
        this.enginePowerFrom = enginePowerFrom;
    }

    public Integer getEnginePowerTo() {
        return enginePowerTo;
    }

    public void setEnginePowerTo(Integer enginePowerTo) {
        this.enginePowerTo = enginePowerTo;
    }

    public Integer getDoorFrom() {
        return doorFrom;
    }

    public void setDoorFrom(Integer doorFrom) {
        this.doorFrom = doorFrom;
    }

    public Integer getDoorTo() {
        return doorTo;
    }

    public void setDoorTo(Integer doorTo) {
        this.doorTo = doorTo;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
