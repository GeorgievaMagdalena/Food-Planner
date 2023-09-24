package com.example.foodplaner;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {

    private ArrayList<String> ListNames;
    private ArrayList<String> ListIngredients;
    private ArrayList<Integer> ListRecipeIds;
    private int rowLayout;
    private Context mContext;
    private HashSet<Integer> selectedRecipeIds;

    Button saveButton;
    private DatabaseHandler databaseHandler;
    private ArrayList<Recipe> chosenRecipes;


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView myName;
        public TextView myIngredients;
        public ImageView Pic;
        public CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            myName = (TextView) itemView.findViewById(R.id.NameRecipe);
            myIngredients = (TextView) itemView.findViewById(R.id.IngredientsRecipe);
            Pic = (ImageView) itemView.findViewById(R.id.picture);
            checkBox = (CheckBox) itemView.findViewById(R.id.Checkbox);
        }
    }

    public myAdapter(ArrayList<String> ListNames,ArrayList<String> ListIngredients,ArrayList<Integer> ListRecipeIds, DatabaseHandler databaseHandler,  int rowLayout, Context context) {
        this.ListNames = ListNames;
        this.ListIngredients = ListIngredients;
        this.ListRecipeIds = ListRecipeIds;
        this.rowLayout = rowLayout;
        this.mContext = context;
        this.selectedRecipeIds = new HashSet<>();
        this.databaseHandler = databaseHandler;
    }

    public static class Recipe {
        private String name;
        private String ingredients;
        private boolean isChecked = false;
        private int id;
        public Recipe(String name, String ingredients, int id) {
            this.name = name;
            this.ingredients = ingredients;
            this.id = id;
        }

        public String getName() {return name;}
        public void setName(String name) {this.name = name;}
        public String getIngredients() {return ingredients;}
        public void setIngredients(String ingredients) {this.ingredients = ingredients;}
        public boolean isChecked() {return isChecked;}
        public void setChecked(boolean isChecked) {this.isChecked = isChecked;}
        public int getId() {return id;}
        public void setId(int id) {this.id = id;}
    }

    public boolean insertRecipeToUser(int userId) {
        boolean allinserted = true;

        List<Integer> recipeIdsList = new ArrayList<>(selectedRecipeIds);
        Collections.reverse(recipeIdsList);
        HashSet<Integer> reversedRecipeIds = new HashSet<>(recipeIdsList);

        for (Integer recipeId : selectedRecipeIds) {
            Log.i("Recipeid", String.valueOf(recipeId));
            boolean success = databaseHandler.InsertRecipeToUser(userId, recipeId);
           Log.i("VN", String.valueOf(success) + " " + recipeId);

           if(success == false){ allinserted = false; }
        }
        selectedRecipeIds.clear();
        reversedRecipeIds.clear();

        return allinserted;
    }

    public boolean areRecipesSelected() {
        return !selectedRecipeIds.isEmpty();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
            String name = ListNames.get(i);
            String ingredients = ListIngredients.get(i);
            int recipeId = ListRecipeIds.get(i);

            Recipe recipe = new Recipe(name, ingredients, recipeId);

            //viewHolder.checkBox.setChecked(false);

            viewHolder.myName.setText(recipe.getName());
            viewHolder.myName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView tv = (TextView) v;
                    Toast.makeText(mContext, tv.getText(), Toast.LENGTH_SHORT).show();
                }
            });

            viewHolder.myIngredients.setText("Ingredients: "+ recipe.getIngredients());


            viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        selectedRecipeIds.add(recipe.getId());
                        recipe.setChecked(isChecked);
                        notifyDataSetChanged();
                        Log.i("EN", String.valueOf(recipe.getId()));
                    } else {
                        selectedRecipeIds.remove(recipe.getId());
                        recipe.setChecked(isChecked);
                        Log.i("EN", String.valueOf(recipe.getId()) + " TRGNATO");
                        notifyDataSetChanged();
                    }
                }
            });

        }

    public void updateData(ArrayList<String> recipesNames, ArrayList<String> recipesIngredients, ArrayList<Integer> recipesIds) {
        this.ListNames = recipesNames;
        this.ListIngredients = recipesIngredients;
        this.ListRecipeIds = recipesIds;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return ListNames == null ? 0 : ListNames.size();
    }

}
