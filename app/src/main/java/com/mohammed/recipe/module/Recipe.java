package com.mohammed.recipe.module;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable {
    private String id;
    private String recipe_Title;
    private String description;
    private String total_Time;
    private String ingredients;
    private String directions;
    private String img;
    private String rate;


    protected Recipe(Parcel in) {
        id = in.readString();
        recipe_Title = in.readString();
        description = in.readString();
        total_Time = in.readString();
        ingredients = in.readString();
        directions = in.readString();
        img = in.readString();
        rate = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public String toString() {
        return "Recipe{" +
                "id='" + id + '\'' +
                ", recipe_Title='" + recipe_Title + '\'' +
                ", description='" + description + '\'' +
                ", total_Time='" + total_Time + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", directions='" + directions + '\'' +
                ", img='" + img + '\'' +
                ", rate='" + rate + '\'' +
                '}';
    }

    public Recipe() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecipe_Title() {
        return recipe_Title;
    }

    public void setRecipe_Title(String recipe_Title) {
        this.recipe_Title = recipe_Title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTotal_Time() {
        return total_Time;
    }

    public void setTotal_Time(String total_Time) {
        this.total_Time = total_Time;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public Recipe(String recipe_Title, String total_Time, String ingredients, String img) {
        this.recipe_Title = recipe_Title;
        this.total_Time = total_Time;
        this.ingredients = ingredients;
        this.img = img;
    }

    public Recipe(String recipe_Title, String description, String total_Time, String ingredients, String directions, String img) {
        this.recipe_Title = recipe_Title;
        this.description = description;
        this.total_Time = total_Time;
        this.ingredients = ingredients;
        this.directions = directions;
        this.img = img;
    }

    public Recipe(String id, String recipe_Title, String description, String total_Time, String ingredients, String directions, String img) {
        this.id = id;
        this.recipe_Title = recipe_Title;
        this.description = description;
        this.total_Time = total_Time;
        this.ingredients = ingredients;
        this.directions = directions;
        this.img = img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(recipe_Title);
        parcel.writeString(description);
        parcel.writeString(total_Time);
        parcel.writeString(ingredients);
        parcel.writeString(directions);
        parcel.writeString(img);
        parcel.writeString(rate);
    }
}
