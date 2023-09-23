package com.example.foodplaner;

public class RecipeAllergyLinkModel {
    private int id;
    private int recipeId;
    private int allergyId;

    public RecipeAllergyLinkModel(int id, int recipeId, int allergyId) {
        this.id = id;
        this.recipeId = recipeId;
        this.allergyId = allergyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getAllergyId() {
        return allergyId;
    }

    public void setAllergyId(int allergyId) {
        this.allergyId = allergyId;
    }

}



