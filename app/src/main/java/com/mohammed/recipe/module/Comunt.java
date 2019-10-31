package com.mohammed.recipe.module;

public class Comunt {
    private String id;
    private Recipe userRecipe;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Comunt(Recipe userRecipe) {

        this.userRecipe = userRecipe;
    }

    public Comunt() {
    }


    public Recipe getUserRecipe() {
        return userRecipe;
    }

    public void setUserRecipe(Recipe userRecipe) {
        this.userRecipe = userRecipe;
    }
}
