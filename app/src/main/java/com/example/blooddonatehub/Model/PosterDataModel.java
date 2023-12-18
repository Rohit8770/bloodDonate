package com.example.blooddonatehub.Model;

public class PosterDataModel {
    private  String name;
    private  String brand;
    private  String price;
    private  String purchase_year;
    private  String location;

    private  String img;

    public PosterDataModel(String name, String brand, String price, String purchase_year, String location, String img) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.purchase_year = purchase_year;
        this.location = location;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPurchase_year() {
        return purchase_year;
    }

    public void setPurchase_year(String purchase_year) {
        this.purchase_year = purchase_year;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
