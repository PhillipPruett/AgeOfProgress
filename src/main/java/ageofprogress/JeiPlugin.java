package ageofprogress;

import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

@JEIPlugin
public class JeiPlugin implements IModPlugin {

    public static IJeiRuntime JeiRunTime;

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {

    }

    @Override
    public void registerIngredients(IModIngredientRegistration registry) {

    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {

    }

    @Override
    public void register(IModRegistry registry) {
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        JeiRunTime = jeiRuntime;
        // blacklist = registry.getJeiHelpers().getIngredientBlacklist();

        //for every item that was removed during INIT by the item remover, we need to also remove these
        //items from JEI so players don't think they are craftable.
        RecipeHelper.hideRecipes(JeiRunTime);
    }


}