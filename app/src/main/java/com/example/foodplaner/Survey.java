package com.example.foodplaner;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Survey extends AppCompatActivity {
    String location;
    String gender;
    boolean isVeg;
    CheckBox PeanutsCB, MilkCB, EggCB, FishCB, NutsCB, GlutenCB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.maleButton) {
                    gender = "male";
                } else if(checkedId==R.id.femaleButton) {
                    gender = "female";
                } else{
                    gender=null;
                }
            }
        });

        Switch isVegSwitch = findViewById(R.id.switch1);

        //TABELA ZA ALERGII
        PeanutsCB = findViewById(R.id.peanuts);
        MilkCB = findViewById(R.id.milk);
        EggCB = findViewById(R.id.egg);
        FishCB = findViewById(R.id.fish);
        NutsCB = findViewById(R.id.nuts);
        GlutenCB = findViewById(R.id.gluten);

        String userEmail = getIntent().getStringExtra("email");


        DatabaseHandler db = new DatabaseHandler(Survey.this);

        String userName = db.returnName(userEmail);
        int userId= db.returnId(userEmail);
        Log.i("ID", String.valueOf(userId));

        /*
        //statichki dodavame alergii
        AllergyModel allergyModel = new AllergyModel(1, "peanuts");
        boolean success = db.RegisterAllergy(allergyModel);
        Log.i("AL", String.valueOf(success));

        AllergyModel allergyModel1 = new AllergyModel(2, "milk");
        boolean success1 = db.RegisterAllergy(allergyModel1);
        Log.i("AL1", String.valueOf(success1));

        AllergyModel allergyModel2 = new AllergyModel(3, "egg");
        boolean success2 = db.RegisterAllergy(allergyModel2);
        Log.i("AL2", String.valueOf(success2));

        AllergyModel allergyModel3 = new AllergyModel(4, "fish");
        boolean success3 = db.RegisterAllergy(allergyModel3);
        Log.i("AL3", String.valueOf(success3));

        AllergyModel allergyModel4 = new AllergyModel(5, "nuts");
        boolean success4 = db.RegisterAllergy(allergyModel4);
        Log.i("AL4", String.valueOf(success4));

        AllergyModel allergyModel5 = new AllergyModel(6, "gluten");
        boolean success5 = db.RegisterAllergy(allergyModel5);
        Log.i("AL5", String.valueOf(success5));

        //insertSampleRecipes();
*/

        Button button = (Button) findViewById(R.id.addinfo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText inputLocation = (EditText) findViewById(R.id.editTextTextPostalAddress);
                location = inputLocation.getText().toString();

                isVeg = isVegSwitch.isChecked();//vrakja true ako e switchnato na on

                boolean peanuts = PeanutsCB.isChecked(); //true ako e chekirano
                boolean milk = MilkCB.isChecked();
                boolean egg = EggCB.isChecked();
                boolean fish = FishCB.isChecked();
                boolean nuts = NutsCB.isChecked();
                boolean gluten = GlutenCB.isChecked();

                int[] allergyIds = new int[6];

                allergyIds[0] = peanuts ? 1 : -1;
                allergyIds[1] = milk ? 2 : -1;
                allergyIds[2] = egg ? 3 : -1;
                allergyIds[3] = fish ? 4 : -1;
                allergyIds[4] = nuts ? 5 : -1;
                allergyIds[5] = gluten ? 6 : -1;

                Log.i("GEN", gender);
                Log.i("LOC", location);
                Log.i("VEG", String.valueOf(isVeg));

                boolean updatedInfo = db.AdditionalInfo(userName, gender, location, isVeg);
                boolean addedAllergies = db.InsertAllergyToUser(userId, allergyIds);

                if (updatedInfo && addedAllergies) {
                    Toast.makeText(Survey.this, "Successfully updated info!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Survey.this, "Successfully added allergies!", Toast.LENGTH_SHORT).show();
                    Log.i("AL", String.valueOf(addedAllergies));

                    Intent intent = new Intent(getApplicationContext(), ChoiceRecipe.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                } else {
                    Toast.makeText(Survey.this, "Problem updating info or allergies", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



// DODADENI VEKJE NE MORA DA SE POVIKUVA STALNO
    private void insertSampleRecipes() {
        DatabaseHandler databaseHandler = new DatabaseHandler(Survey.this);

        RecipeModel omelet = new RecipeModel(-1, "Omelet", "butter or margarine, chopped fully cooked ham, chopped bell pepper, finely chopped onion, eggs (beaten)",
                getString(R.string.omelet_directions), false,1 );
        boolean success = databaseHandler.InsertRecipe(omelet, new int[]{3});//egg
        Log.i("VN", String.valueOf(success));

        RecipeModel pancake = new RecipeModel(-1, "Pancake", "flour, sugar, baking powder, milk, oil, egg",
                getString(R.string.pancake_directions), true, 1);
        boolean success1 = databaseHandler.InsertRecipe(pancake, new int[]{2,3,6});//milk,egg,gluten
        Log.i("VN", String.valueOf(success1));

        RecipeModel smoothie = new RecipeModel(-1, "Smoothie", "full-fat milk, natural yogurt, banana, frozen fruits of the forest, blueberries, chia seeds, cinnamon, goji berries, mixed seeds, honey ",
                getString(R.string.smoothie_directions), true, 1);
        boolean success2 = databaseHandler.InsertRecipe(smoothie, new int[]{2});//milk
        Log.i("VN", String.valueOf(success2));

        RecipeModel tunawrap = new RecipeModel(-1, "Crunchy Tuna wrap", "tuna, celery, onions, sliced chestnuts, red pepper, no-eggs mayonnaise, mustard, tortillas, lettuce",
                getString(R.string.tunawrap_directions), false, 2);
        boolean success3 = databaseHandler.InsertRecipe(tunawrap, new int[]{4,5});//fish, nuts
        Log.i("VN", String.valueOf(success3));

        RecipeModel pizza = new RecipeModel(-1,"Pizza Margherita", "store-bought pizza crust, extra virgin olive oil, pizza sauce, mozzarella cheese, parmesan cheese, basil",
                getString(R.string.pizza_directions), true, 2);
        boolean success4 = databaseHandler.InsertRecipe(pizza, new int[]{2,6});//milk, gluten
        Log.i("VN", String.valueOf(success4));

        RecipeModel chickenalfredo = new RecipeModel(-1, "Chicken and Broccoli Alfredo", "fettuccine, fresh broccoli florets, Italian dressing, chicken breasts, milk, cream cheese, parmesan cheese, dried basil leaves",
                getString(R.string.chickedalfredo_directions), false, 2);
        boolean success5 = databaseHandler.InsertRecipe(chickenalfredo, new int[]{2});//milk
        Log.i("VN", String.valueOf(success5));

        RecipeModel salmon = new RecipeModel(-1, "Grilled Salmon with Lemon Herb Butter", "salmon fillets, butter, lemon juice, fresh herbs, garlic",
                getString(R.string.salmon_directions), false, 3);
        boolean success6 = databaseHandler.InsertRecipe(salmon, new int[]{4});//fish
        Log.i("VN", String.valueOf(success6));

        RecipeModel beeftacos = new RecipeModel(-1, "Beef Tacos", "ground beef, taco seasoning, tortillas, lettuce, tomatoes, cheese, salsa",
                getString(R.string.beeftaco_directions), false, 3);
        boolean success7 = databaseHandler.InsertRecipe(beeftacos, new int[]{2});//milk
        Log.i("VN", String.valueOf(success7));

        RecipeModel risotto = new RecipeModel(-1, "Mushroom Risotto","rice, mushrooms, vegetable broth, white wine, onion, parmesan cheese",
                getString(R.string.risotto_directions), true, 3);
        boolean success8 = databaseHandler.InsertRecipe(risotto, new int[]{2});//milk
        Log.i("VN", String.valueOf(success8));

    }

}
