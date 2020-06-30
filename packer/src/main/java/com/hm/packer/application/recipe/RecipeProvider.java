package com.hm.packer.application.recipe;

import com.hm.packer.model.dto.Recipe;
import lombok.Getter;

@Getter
public class RecipeProvider {

    private Recipe[] recipes;

    public RecipeProvider(Recipe[] recipes){
        this.recipes = recipes;
    }

    public Recipe get(int index) throws Exception{
        if(index >= recipes.length)
            throw new Exception("recipe does not exist.");
        return recipes[index];
    }
}
