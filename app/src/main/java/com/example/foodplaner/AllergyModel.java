package com.example.foodplaner;

public class AllergyModel {
    private int id;
    private String allergyName;

    public AllergyModel(int id, String allergyName){
        this.id = id;
        this.allergyName = allergyName;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAllergyName() {
        return allergyName;
    }

    public void setAllergyName(String allergy) {
        this.allergyName = allergy;
    }
}
