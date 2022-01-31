package com.example.lab6;

import java.io.Serializable;
import java.util.Random;

public class ModelRecipe implements Serializable {

    private String meal;
    private String category;
    private String ingredients;
    private String recipe;
    private String time;

    private String photo;
    {
        photo = "photo";
    }

    public String getMeal() {
        return meal;
    }

    public String getCategory() {
        return category;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getRecipe() {
        return recipe;
    }

    public String getTime() {
        return time;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo)  {
        this.photo = photo;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ModelRecipe() {

    }
    public ModelRecipe(String meal, String category, String recipe, String time,String ingredients) {
        this.meal = meal;
        this.category = category;
        this.recipe = recipe;
        this.time = time;
        this.ingredients = ingredients;
    }


    @Override
    public String toString() {
        return "ModelRecipe{" +
                "meal='" + meal + '\'' +
                ", category='" + category + '\'' +
                ", recipe='" + recipe + '\'' +
                ", time='" + time + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
