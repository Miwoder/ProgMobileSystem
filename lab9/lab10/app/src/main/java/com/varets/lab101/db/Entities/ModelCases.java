package com.varets.lab101.db.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ModelCases {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nameCase;
    private String dateCase;
    private String locationCase;

    public ModelCases(String name, String location, String date) {
        this.nameCase = name;
        this.locationCase = location;
        this.dateCase = date;
    }
    public  ModelCases(){  }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNameCase() {
        return nameCase;
    }

    public void setNameCase(String nameCase) {
        this.nameCase = nameCase;
    }

    public String getDateCase() {
        return dateCase;
    }

    public void setDateCase(String dateCase) {
        this.dateCase = dateCase;
    }

    public String getLocationCase() {
        return locationCase;
    }

    public void setLocationCase(String locationCase) {
        this.locationCase = locationCase;
    }
    @NonNull
    @Override
    public String toString() {
        return this.nameCase + " " + this.dateCase + " " + this.locationCase;
    }
}
