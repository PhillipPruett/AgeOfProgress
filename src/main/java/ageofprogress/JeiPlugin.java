package ageofprogress;

import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.registries.IForgeRegistryEntry;

@JEIPlugin
public class JeiPlugin implements IModPlugin {

    public static IIngredientBlacklist blacklist;

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
        System.out.format("%nBLACKLIST%n%n");
        blacklist = registry.getJeiHelpers().getIngredientBlacklist();

        //for every item that was removed during INIT by the item remover, we need to also remove these
        //items from JEI so players don't think they are craftable.
        for (IForgeRegistryEntry<IRecipe> recipe : RecipeHelper.getBlackListedRecipes()) {
            System.out.format("JEI Blacklisting: %s%n", recipe.getRegistryName().toString());
            blacklist.addIngredientToBlacklist(new ItemStack(Item.getByNameOrId(recipe.getRegistryName().toString())));
        }
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        // jeiRuntime.
    }
}