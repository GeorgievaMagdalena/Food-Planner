package com.example.foodplaner;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    //registrate user
    private static final String DATABASE_NAME="FoodPlannerInformation.db";
    private static final int DATABASE_VERSION =2;
    private static final String TABLE_USER="user_data";
    private static final String COLUMN_ID="UserID";
    private static final String COLUMN_FIRSTNAME="FirstName";
    private static final String COLUMN_LASTNAME="LastName";
    private static final String COLUMN_EMAIL="Email";
    private static final String COlUMN_PASSWORD="Password";
    private static final String COlUMN_GENDER="Gender";
    private static final String COlUMN_LOCATION="Location";
    private static final String COlUMN_VEGETARIAN= "Vegetarian";

    //allergy table
    private static final String TABLE_ALLERGY="allergy_data";
    private static final String COLUMN_ID_ALLERGY="AllergyId";
    private static final String COLUMN_ALLERGY_NAME="AllergyName";

    //Recipe table
    private static final String TABLE_RECIPE = "recipe_data";
    private static final String COLUMN_RECIPE_ID = "RecipeId";
    private static final String COLUMN_RECIPE_NAME = "RecipeName";
    private static final String COLUMN_INGREDIENTS = "Ingredients";
    private static final String COLUMN_DIRECTIONS = "Directions";
    private static final String COLUMN_IS_VEGETARIAN = "isVegetarian";
    private static final String COLUMN_KIND = "Kind";

    //RECIPE - ALLERGY TABLE
    private static final String TABLE_RECIPE_ALLERGY_LINK = "recipe_allergy_link";
    private static final String COLUMN_RECIPEALLERGY_RECIPE_ID = "RecipeId";
    private static final String COLUMN_RECIPEALLERGY_ALLERGY_ID = "AllergyId";

    //RECIPE - USER TABLE
    private static final String TABLE_USER_RECIPE_LINK = "user_recipe_link";
    private static final String COLUMN_USER_RECIPE_USER_ID = "UserId";
    private static final String COLUMN_USER_RECIPE_RECIPE_ID = "RecipeId";

    // User-Allergy Table
    private static final String TABLE_USER_ALLERGY_LINK = "user_allergy_link";
    private static final String COLUMN_USERALLERGY_USER_ID = "UserId";
    private static final String COLUMN_USERALLERGY_ALLERGY_ID = "AllergyId";

    SQLiteDatabase database;

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database=getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableQuery =" CREATE TABLE IF NOT EXISTS "+TABLE_USER+" ( "+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_FIRSTNAME+" TEXT, "+
                COLUMN_LASTNAME+" TEXT, "+ COLUMN_EMAIL+" TEXT, "+ COlUMN_PASSWORD+" TEXT, "+ COlUMN_GENDER+" TEXT, "+ COlUMN_LOCATION+" TEXT, "+ COlUMN_VEGETARIAN+" TEXT) ";

        String allergyQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_ALLERGY + " ( "
                + COLUMN_ID_ALLERGY + " INTEGER PRIMARY KEY, "
                + COLUMN_ALLERGY_NAME + " TEXT)";

        String recipeQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_RECIPE + " ( "
                + COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_RECIPE_NAME + " TEXT, "
                + COLUMN_INGREDIENTS + " TEXT, "
                + COLUMN_DIRECTIONS + " TEXT, "
                + COLUMN_IS_VEGETARIAN + " TEXT, "
                + COLUMN_KIND + " INTEGER "
                + ")";
        db.execSQL(recipeQuery);

        String allergyRecipeLinkQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_RECIPE_ALLERGY_LINK + " ( "
                + COLUMN_RECIPEALLERGY_RECIPE_ID + " INTEGER, "
                + COLUMN_RECIPEALLERGY_ALLERGY_ID + " INTEGER, "
                + "FOREIGN KEY (" + COLUMN_RECIPEALLERGY_RECIPE_ID + ") REFERENCES " + TABLE_RECIPE + "(" + COLUMN_RECIPE_ID + "), "
                + "FOREIGN KEY (" + COLUMN_RECIPEALLERGY_ALLERGY_ID + ") REFERENCES " + TABLE_ALLERGY + "(" + COLUMN_ID_ALLERGY + ")"
                + ")";
        db.execSQL(allergyRecipeLinkQuery);


        String userRecipeLinkQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_USER_RECIPE_LINK + " ( "
                + COLUMN_USER_RECIPE_USER_ID + " INTEGER, "
                + COLUMN_USER_RECIPE_RECIPE_ID + " INTEGER, "
                + "FOREIGN KEY (" + COLUMN_USER_RECIPE_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ID + "), "
                + "FOREIGN KEY (" + COLUMN_USER_RECIPE_RECIPE_ID + ") REFERENCES " + TABLE_RECIPE + "(" + COLUMN_RECIPE_ID + ")"
                + ")";
        db.execSQL(userRecipeLinkQuery);

        String userAllergyLinkQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_USER_ALLERGY_LINK + " ( "
                + COLUMN_USERALLERGY_USER_ID + " INTEGER, "
                + COLUMN_USERALLERGY_ALLERGY_ID + " INTEGER, "
                + "FOREIGN KEY (" + COLUMN_USERALLERGY_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ID + "), "
                + "FOREIGN KEY (" + COLUMN_USERALLERGY_ALLERGY_ID + ") REFERENCES " + TABLE_ALLERGY + "(" + COLUMN_ID_ALLERGY + ")"
                + ")";
        db.execSQL(userAllergyLinkQuery);


        db.execSQL(tableQuery);
        db.execSQL(allergyQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            Log.i("VLEZE", "VLEZE VO if VO ON UPGRADE");
        }else{
            Log.i("VLEZE", "VLEZE VO ELSE VO ON UPGRADE");
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public boolean RegisterUser(UserModel um){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put(COLUMN_FIRSTNAME, um.getFirstName());
        cv.put(COLUMN_LASTNAME,um.getLastName());
        cv.put(COLUMN_EMAIL,um.getEmail());
        cv.put(COlUMN_PASSWORD,um.getPassword());
        cv.put(COlUMN_GENDER,um.getGender());
        cv.put(COlUMN_LOCATION,um.getLocation());
        cv.put(COlUMN_VEGETARIAN,um.isVegeterian());

        long insert=db.insert(TABLE_USER,null,cv);
        db.close();

        if(insert==-1){
            return false;
        }else{
            return true;
        }
    }

    public boolean AdditionalInfo(String UserName, String gender, String location, boolean isVegetarian){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COlUMN_GENDER, gender);
        cv.put(COlUMN_LOCATION, location);
        cv.put(COlUMN_VEGETARIAN, isVegetarian ? "true" : "false");

        String whereClause = COLUMN_FIRSTNAME + "=?";
        String[] whereArgs = {UserName};

        int rowsUpdated = db.update(TABLE_USER, cv, whereClause, whereArgs);
        db.close();

        return rowsUpdated > 0;
    }

    public boolean RegisterAllergy(AllergyModel am){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put(COLUMN_ALLERGY_NAME, am.getAllergyName());
        //cv.put(COLUMN_USER_ID,am.getUserId());

        long insert=db.insert(TABLE_ALLERGY,null,cv);
        db.close();

        if(insert==-1){
            return false;
        }else{
            return true;
        }
    }

    public boolean InsertRecipe(RecipeModel rm, int[] allergyIds) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_RECIPE_NAME, rm.getRecipeName());
        cv.put(COLUMN_INGREDIENTS, rm.getIngredients());
        cv.put(COLUMN_DIRECTIONS, rm.getDirections());
        cv.put(COLUMN_IS_VEGETARIAN, rm.isVegetarian() ? "true" : "false");
        cv.put(COLUMN_KIND, rm.getKind());

        long insert = db.insert(TABLE_RECIPE, null, cv);

        if (insert != -1) {
            int recipeId = (int) insert;

            for (int allergyId : allergyIds) {
                ContentValues allergyCv = new ContentValues();
                allergyCv.put(COLUMN_RECIPEALLERGY_RECIPE_ID, recipeId);
                allergyCv.put(COLUMN_RECIPEALLERGY_ALLERGY_ID, allergyId);
                long allergyInsert = db.insert(TABLE_RECIPE_ALLERGY_LINK, null, allergyCv);

                if (allergyInsert == -1) {
                    Log.i("ALvoRE", "GRESHKA");
                    db.close();
                    return false;
                }
            }
        }

        db.close();

        return insert != -1;
    }

    public boolean InsertAllergyToUser(int userId, int[] allergyIds){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        boolean allInserted = true;

        for (int allergyId : allergyIds) {
            if (allergyId != -1) {
                cv.put(COLUMN_USERALLERGY_USER_ID, userId);
                cv.put(COLUMN_USERALLERGY_ALLERGY_ID, allergyId);
                long allergyInsert = db.insert(TABLE_USER_ALLERGY_LINK, null, cv);

                if (allergyInsert == -1) {
                    Log.i("ALvoUS", "GRESHKA");
                    allInserted = false;
                }
            }
        }
        db.close();
        return allInserted;
    }

    public boolean InsertRecipeToUser(int userId, int recipeId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        boolean inserted = true;

        cv.put(COLUMN_USER_RECIPE_USER_ID, userId);
        cv.put(COLUMN_USER_RECIPE_RECIPE_ID, recipeId);

        long recipeInsert = db.insert(TABLE_USER_RECIPE_LINK, null, cv);

        if (recipeInsert == -1) {
            Log.i("ALvoUS", "GRESHKA");
            inserted = false;
        }

        db.close();
        return inserted;
    }

    public Boolean checkEmail(String email){
        SQLiteDatabase info=this.getWritableDatabase();
        Cursor cursor=info.rawQuery("Select * from "+TABLE_USER+" where "+COLUMN_EMAIL+" =? ", new String[]{email});

        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }

    }
    public Boolean checkEmailPassword(String email, String password){
        SQLiteDatabase info=this.getWritableDatabase();
        Cursor cursor=info.rawQuery("Select * from "+TABLE_USER+" where "+COLUMN_EMAIL+" =? and "+COlUMN_PASSWORD+" =? ", new String[]{email, password});

        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }

    }

    @SuppressLint("Range")
    public String returnName(String email){
        SQLiteDatabase info=this.getWritableDatabase();
        String[] projection = { "FirstName" };
        String selection = "Email = ?";
        String[] selectionArgs = { email };

        Cursor cursor = info.query("user_data", projection, selection, selectionArgs, null, null, null);

        String userName = null;
        if (cursor.moveToFirst()) {
            userName = cursor.getString(cursor.getColumnIndex("FirstName"));
        }

        cursor.close();
        info.close();

        return userName;
    }



    public int returnId(String email){
        SQLiteDatabase info=this.getWritableDatabase();
        String[] projection = { "UserID" };
        String selection = "Email = ?";
        String[] selectionArgs = { email };

        Cursor cursor = info.query("user_data", projection, selection, selectionArgs, null, null, null);

        int userId = -1;
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndexOrThrow("UserID"));;
        }

        cursor.close();
        info.close();

        return userId;
    }

    public Cursor getAllergies()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+TABLE_ALLERGY, null);
        return cursor;
    }

    @SuppressLint("Range")
    public ArrayList<String> getRecipesNames(int kind) {
        ArrayList<String> recipeNames = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_RECIPE_NAME + " FROM " + TABLE_RECIPE;

        if (kind != -1) {
            query += " WHERE " + COLUMN_KIND + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(kind)});

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String recipeName = cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_NAME));
                    recipeNames.add(recipeName);
                } while (cursor.moveToNext());

                cursor.close();
            }
        } else {
            Cursor cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String recipeName = cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_NAME));
                    recipeNames.add(recipeName);
                } while (cursor.moveToNext());

                cursor.close();
            }
        }

        db.close();
        return recipeNames;
    }

    @SuppressLint("Range")
    public ArrayList<Integer> getRecipesIds(int kind) {
        ArrayList<Integer> recipeIds = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_RECIPE_ID + " FROM " + TABLE_RECIPE;

        if (kind!= -1) {
            query += " WHERE " + COLUMN_KIND + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(kind)});

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Integer recipeId = cursor.getInt(cursor.getColumnIndex(COLUMN_RECIPE_ID));
                    recipeIds.add(recipeId);
                } while (cursor.moveToNext());

                cursor.close();
            }
        } else {
            Cursor cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Integer recipeId = cursor.getInt(cursor.getColumnIndex(COLUMN_RECIPE_ID));
                    recipeIds.add(recipeId);
                } while (cursor.moveToNext());

                cursor.close();
            }
        }

        db.close();
        return recipeIds;
    }

    @SuppressLint("Range")
    public ArrayList<String> getRecipesIngredients(int kind)
    {
        ArrayList<String> recipeIngredients = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_INGREDIENTS + " FROM " + TABLE_RECIPE;

        if (kind != -1) {
            query += " WHERE " + COLUMN_KIND + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(kind)});

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String recipeIngredient = cursor.getString(cursor.getColumnIndex(COLUMN_INGREDIENTS));
                    recipeIngredients.add(recipeIngredient);
                } while (cursor.moveToNext());

                cursor.close();
            }
        } else {
            Cursor cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String recipeIngredient = cursor.getString(cursor.getColumnIndex(COLUMN_INGREDIENTS));
                    recipeIngredients.add(recipeIngredient);
                } while (cursor.moveToNext());

                cursor.close();
            }
        }

        db.close();
        return recipeIngredients;
    }

    @SuppressLint("Range")
    public ArrayList<Integer> getUserAllergies(int userId) {
        ArrayList<Integer> allergyIds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_USERALLERGY_ALLERGY_ID +
                " FROM " + TABLE_USER_ALLERGY_LINK +
                " WHERE " + COLUMN_USERALLERGY_USER_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int allergyId = cursor.getInt(cursor.getColumnIndex(COLUMN_USERALLERGY_ALLERGY_ID));
                    allergyIds.add(allergyId);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        db.close();
        return allergyIds;
    }

    @SuppressLint("Range")
    public boolean isUserVegetarian(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean isVegetarian = false;

        String[] projection = {COlUMN_VEGETARIAN};

        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query(TABLE_USER, projection, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            isVegetarian = "true".equals(cursor.getString(cursor.getColumnIndex(COlUMN_VEGETARIAN)));
            cursor.close();
        }

        db.close();
        return isVegetarian;
    }
    @SuppressLint("Range")
    public boolean isRecipeVegetarian(int recipeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean isVegetarian = false;

        String[] projection = {COLUMN_IS_VEGETARIAN};

        String selection = COLUMN_RECIPE_ID + " = ?";
        String[] selectionArgs = {String.valueOf(recipeId)};

        Cursor cursor = db.query(TABLE_RECIPE, projection, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            isVegetarian = "true".equals(cursor.getString(cursor.getColumnIndex(COLUMN_IS_VEGETARIAN)));
            cursor.close();
        }
        db.close();
        return isVegetarian;
    }


    @SuppressLint("Range")
    public ArrayList<myAdapter.Recipe> getRecipesWithoutAllergies(ArrayList<Integer> userAllergies, int selectedKind) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<myAdapter.Recipe> recipesWithoutAllergies = new ArrayList<>();

       String userAllergyIds = TextUtils.join(",", userAllergies);

        String query = "SELECT " + TABLE_RECIPE + ".* " +
                "FROM " + TABLE_RECIPE + " AS " + TABLE_RECIPE + " " +
                "LEFT JOIN " + TABLE_RECIPE_ALLERGY_LINK + " AS " + TABLE_RECIPE_ALLERGY_LINK + " " +
                "ON " + TABLE_RECIPE + "." + COLUMN_RECIPE_ID + " = " + TABLE_RECIPE_ALLERGY_LINK + "." + COLUMN_RECIPEALLERGY_RECIPE_ID + " " +
                "WHERE (" +
                "  " + TABLE_RECIPE + "." + COLUMN_KIND + " = ?" +
                "  OR ? = -1" +
                ") AND " + TABLE_RECIPE + "." + COLUMN_RECIPE_ID + " NOT IN (" +
                "  SELECT " + TABLE_RECIPE_ALLERGY_LINK + "." + COLUMN_RECIPEALLERGY_RECIPE_ID +
                "  FROM " + TABLE_RECIPE_ALLERGY_LINK +
                "  WHERE " + TABLE_RECIPE_ALLERGY_LINK + "." + COLUMN_RECIPEALLERGY_ALLERGY_ID + " IN (" + userAllergyIds + ")" +
                ")" +
                "GROUP BY " + TABLE_RECIPE + "." + COLUMN_RECIPE_ID;


        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(selectedKind)});

       // Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int recipeIdColumnIndex = cursor.getColumnIndex(COLUMN_RECIPE_ID);
                int recipeNameColumnIndex = cursor.getColumnIndex(COLUMN_RECIPE_NAME);
                int recipeIngredientsColumnIndex = cursor.getColumnIndex(COLUMN_INGREDIENTS);

                int recipeId = cursor.getInt(recipeIdColumnIndex);
                String recipeName = cursor.getString(recipeNameColumnIndex);
                String recipeIngredients = cursor.getString(recipeIngredientsColumnIndex);

                myAdapter.Recipe recipe = new myAdapter.Recipe(recipeName, recipeIngredients, recipeId);
                recipesWithoutAllergies.add(recipe);
                Log.i("Bez allergii", recipeName);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();

        return recipesWithoutAllergies;
    }

    @SuppressLint("Range")
    public ArrayList<mySecondAdapter.Recipe> getMeals(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<mySecondAdapter.Recipe> myMeals = new ArrayList<>();

        String query = "SELECT " + DatabaseHandler.COLUMN_USER_RECIPE_RECIPE_ID +
                " FROM " + DatabaseHandler.TABLE_USER_RECIPE_LINK +
                " WHERE " + DatabaseHandler.COLUMN_USER_RECIPE_USER_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int recipeId = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.COLUMN_USER_RECIPE_RECIPE_ID));

                mySecondAdapter.Recipe recipe = getRecipeById(recipeId);
                if (recipe != null) {
                    myMeals.add(recipe);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return myMeals;
    }

    @SuppressLint("Range")
    private mySecondAdapter.Recipe getRecipeById(int recipeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        mySecondAdapter.Recipe recipe = null;

        String query = "SELECT * FROM " + DatabaseHandler.TABLE_RECIPE +
                " WHERE " + DatabaseHandler.COLUMN_RECIPE_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(recipeId)});

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_RECIPE_NAME));
            String ingredients = cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_INGREDIENTS));
            String directions = cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_DIRECTIONS));

            recipe = new mySecondAdapter.Recipe(name, ingredients, directions, recipeId);
            cursor.close();
        }

        db.close();
        return recipe;
    }

    public void deleteAllRowsFromTable() {
        SQLiteDatabase db = this.getWritableDatabase();

        int rowsDeleted = db.delete("allergy_data", null, null);

        if (rowsDeleted > 0) {
            Log.d("DatabaseHandler", "Deleted " + rowsDeleted + " rows from " + "allergy_data");
        } else {
            Log.d("DatabaseHandler", "No rows deleted from " + "allergy_data");
        }

        db.close();
    }
}
