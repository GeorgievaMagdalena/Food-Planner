package com.example.foodplaner;

public class AllergyModel {
    private int id;
    private String allergyName;
    //private int userId;

    public AllergyModel(int id, String allergyName){
        this.id = id;
        this.allergyName = allergyName;
        //this.userId = userId;
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

    //public int getUserId() {
      //  return userId;
    //}

    //public void setUserId(int userId) {
      //  this.userId = userId;
    //}


}
