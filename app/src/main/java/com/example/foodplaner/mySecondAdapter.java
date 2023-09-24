package com.example.foodplaner;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class mySecondAdapter extends RecyclerView.Adapter<mySecondAdapter.ViewHolder> {

    private int rowLayout1;
    private Context mContext;
    private ArrayList<mySecondAdapter.Recipe> chosenRecipes;

    public static class Recipe {
        private String name;
        private String ingredients;
        private int id;
        private String directions;
        public Recipe(String name, String ingredients,String directions, int id) {
            this.name = name;
            this.ingredients = ingredients;
            this.directions = directions;
            this.id = id;
        }

        public String getName() {return name;}
        public void setName(String name) {this.name = name;}
        public String getIngredients() {return ingredients;}
        public void setIngredients(String ingredients) {this.ingredients = ingredients;}
        public int getId() {return id;}
        public void setId(int id) {this.id = id;}
        public String getDirection() {return directions;}
        public void setDirections(String directions) {this.directions = directions;}
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mName;
        public TextView mIngredients;
        public ImageView Pic;

        public ViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.NameRecipeSRV);
            mIngredients = (TextView) itemView.findViewById(R.id.IngredientsRecipeSRV);
            //Pic = (ImageView) itemView.findViewById(R.id.picture);
        }
    }

    public mySecondAdapter(ArrayList<mySecondAdapter.Recipe> chosenRecipes, int rowLayout1, Context context) {
        this.chosenRecipes = chosenRecipes;
        this.rowLayout1 = rowLayout1;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout1, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        mySecondAdapter.Recipe recipe = chosenRecipes.get(i);

        viewHolder.mName.setText(recipe.getName());
        viewHolder.mIngredients.setText("Ingredients: " + recipe.getIngredients());
        String directions = recipe.getDirection();

        viewHolder.mName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DirectionsView.class);
                intent.putExtra("directions", directions);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return chosenRecipes == null ? 0 : chosenRecipes.size();
    }

}
