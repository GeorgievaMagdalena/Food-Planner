package com.example.foodplaner;

import java.util.List;

public class RecipeModel {
    private int id;
    private String RecipeName;
    private String Ingredients;
    private String Directions;
    private boolean isVegetarian;
    private int Kind;


    public RecipeModel(int id, String recipeName, String ingredients, String directions, boolean isVegetarian, int Kind) {
        this.id = id;
        RecipeName = recipeName;
        Ingredients = ingredients;
        Directions = directions;
        this.Kind = Kind;
        this.isVegetarian = isVegetarian;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipeName() {
        return RecipeName;
    }

    public void setRecipeName(String recipeName) {
        RecipeName = recipeName;
    }

    public String getIngredients() {
        return Ingredients;
    }

    public void setIngredients(String ingredients) {
        Ingredients = ingredients;
    }

    public String getDirections() {
        return Directions;
    }

    public void setDirections(String directions) {
        Directions = directions;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    public int getKind() {
        return Kind;
    }

    public void setKind(int kind) {
        Kind = kind;
    }
}
