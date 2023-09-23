package com.example.foodplaner;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChoiceRecipe extends AppCompatActivity {

    RecyclerView mRecyclerView;
    myAdapter mAdapter;
    Spinner spinnerKinds;

    Integer selectedKind = -1; // Declare at the class level
    ArrayList<String> recipesNames;
    ArrayList<String> recipesIngredients;
    ArrayList<Integer> recipesIds;
    int userId;
    boolean nextAv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_recipe);

        spinnerKinds = findViewById(R.id.spinnerKinds);
        String[] kinds = {"All", "Breakfast", "Lunch", "Dinner"};
        Integer[] kindsInt = {-1, 1, 2, 3};

        recipesNames = new ArrayList<>();
        recipesIngredients = new ArrayList<>();
        recipesIds = new ArrayList<>();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, kinds);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKinds.setAdapter(spinnerAdapter);

        DatabaseHandler databaseHandler = new DatabaseHandler(ChoiceRecipe.this);
        userId = getIntent().getIntExtra("userId", 0);
        ArrayList<Integer> userAllergies = databaseHandler.getUserAllergies(userId);
        Log.i("IMA AL", String.valueOf(userAllergies)); //[1, 3]
        boolean isVeg = databaseHandler.isUserVegetarian(userId);
        Log.i("veg", String.valueOf(isVeg));


        spinnerKinds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedKind = kindsInt[position];
                Log.i("KIND", String.valueOf(selectedKind));

                recipesNames.clear();
                recipesIngredients.clear();
                recipesIds.clear();

                if (selectedKind == -1) {
                    recipesNames = (ArrayList<String>) databaseHandler.getRecipesNames(-1);
                    recipesIngredients = (ArrayList<String>) databaseHandler.getRecipesIngredients(-1);
                    recipesIds = (ArrayList<Integer>) databaseHandler.getRecipesIds(-1);
                } else {
                    if (userAllergies == null) {
                        recipesNames = (ArrayList<String>) databaseHandler.getRecipesNames(selectedKind);
                        recipesIngredients = (ArrayList<String>) databaseHandler.getRecipesIngredients(selectedKind);
                        recipesIds = (ArrayList<Integer>) databaseHandler.getRecipesIds(selectedKind);

                        if (isVeg) {
                            ArrayList<myAdapter.Recipe> vegetarianRecipes = new ArrayList<>();
                            for (int i = 0; i < recipesIds.size(); i++) {
                                int recipeId = recipesIds.get(i);
                                boolean isRecipeVegetarian = databaseHandler.isRecipeVegetarian(recipeId);
                                if (isRecipeVegetarian) {
                                    vegetarianRecipes.add(new myAdapter.Recipe(recipesNames.get(i), recipesIngredients.get(i), recipeId));
                                }
                            }
                            recipesNames.clear();
                            recipesIngredients.clear();
                            recipesIds.clear();

                            for (myAdapter.Recipe recipe : vegetarianRecipes) {
                                recipesNames.add(recipe.getName());
                                recipesIngredients.add(recipe.getIngredients());
                                recipesIds.add(recipe.getId());
                            }
                        }
                    } else {
                    }
                    ArrayList<myAdapter.Recipe> recipesWithoutAllergies = databaseHandler.getRecipesWithoutAllergies(userAllergies, selectedKind);

                    for (myAdapter.Recipe recipe : recipesWithoutAllergies) {
                        recipesNames.add(recipe.getName());
                        recipesIngredients.add(recipe.getIngredients());
                        recipesIds.add(recipe.getId());
                    }
                    Log.i("IMINJA", String.valueOf(recipesNames));
                    Log.i("IN", String.valueOf(recipesIngredients));
                    Log.i("IDS", String.valueOf(recipesIds));

                    if (isVeg) {
                        ArrayList<myAdapter.Recipe> vegetarianRecipes = new ArrayList<>();
                        for (int i = 0; i < recipesIds.size(); i++) {
                            int recipeId = recipesIds.get(i);
                            boolean isRecipeVegetarian = databaseHandler.isRecipeVegetarian(recipeId);
                            if (isRecipeVegetarian) {
                                vegetarianRecipes.add(new myAdapter.Recipe(recipesNames.get(i), recipesIngredients.get(i), recipeId));
                            }
                        }
                        recipesNames.clear();
                        recipesIngredients.clear();
                        recipesIds.clear();
                        for (myAdapter.Recipe recipe : vegetarianRecipes) {
                            recipesNames.add(recipe.getName());
                            recipesIngredients.add(recipe.getIngredients());
                            recipesIds.add(recipe.getId());
                        }
                    }
                }
                mAdapter.updateData(recipesNames, recipesIngredients, recipesIds);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selectedKind = -1;
                Log.i("NISHTO", "NISHTO");
                    //SO OVA NE SE ZEMAAT VO PREDVIT ALERGIITE I VEGE OPCIITE
                recipesNames.clear();
                recipesIngredients.clear();
                recipesIds.clear();
                recipesNames = (ArrayList<String>) databaseHandler.getRecipesNames(selectedKind);
                recipesIngredients = (ArrayList<String>) databaseHandler.getRecipesIngredients(selectedKind);
                recipesIds = (ArrayList<Integer>) databaseHandler.getRecipesIds(selectedKind);
                mAdapter.updateData(recipesNames, recipesIngredients, recipesIds);

            }
        });

        Button button = (Button) findViewById(R.id.saveButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean inserted = true;
                if (mAdapter.areRecipesSelected()) {
                    inserted = mAdapter.insertRecipeToUser(userId);
                    nextAv = true;
                } else {
                    Toast.makeText(ChoiceRecipe.this, "Please select at least one recipe before saving.", Toast.LENGTH_SHORT).show();
                    nextAv=false;
                }

                if(inserted) {
                    Intent intent = new Intent(getApplicationContext(), MealPlan.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                }else{
                    Toast.makeText(ChoiceRecipe.this, "GRESHKA VO BAZA", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button next = (Button) findViewById(R.id.nextButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MealPlan.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });


        //сетирање на RecyclerView контејнерот
        mRecyclerView = (RecyclerView) findViewById(R.id.listRecipesToChoice);

        // оваа карактеристика може да се користи ако се знае дека промените
        // во содржината нема да ја сменат layout големината на RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // ќе користиме LinearLayoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // и default animator (без анимации)
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // сетирање на кориснички дефиниран адаптер myAdapter (посебна класа)
        mAdapter = new myAdapter(recipesNames,recipesIngredients,recipesIds, databaseHandler, R.layout.recipes_to_choice, this);

        //прикачување на адаптерот на RecyclerView
        mRecyclerView.setAdapter(mAdapter);

    }
}
