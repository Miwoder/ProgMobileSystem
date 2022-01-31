package by.govoronok.lab5.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

public class Item implements Serializable {
    private Integer id;
    private String name;
    private String description;
    private String foundPlace;
    private String foundDate;
    private String returningPlace;
    private String picture = "newpict";



    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFoundPlace() {
        return foundPlace;
    }

    public void setFoundPlace(String foundPlace) {
        this.foundPlace = foundPlace;
    }

    public String getFoundDate() {
        return foundDate;
    }

    public void setFoundDate(String foundDate) {
        this.foundDate = foundDate;
    }

    public String getReturningPlace() {
        return returningPlace;
    }

    public void setReturningPlace(String returningPlace) {
        this.returningPlace = returningPlace;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Item(String name, String description, String foundPlace, String foundDate, String returningPlace, String picture) {
        this.name = name;
        this.id =  new Random().nextInt(10000);
        this.description = description;
        this.foundPlace = foundPlace;
        this.foundDate = foundDate;
        this.returningPlace = returningPlace;
        this.picture = picture;
    }

    public Item() {
        this.id =  new Random().nextInt(10000);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", foundPlace='" + foundPlace + '\'' +
                ", foundDate='" + foundDate + '\'' +
                ", returningPlace='" + returningPlace + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
