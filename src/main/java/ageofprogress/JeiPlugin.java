package ageofprogress;

import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.client.Minecraft;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryModifiable;

import java.util.List;

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
        System.out.format("%nBLACKLIST%n%n");

    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        JeiRunTime = jeiRuntime;
        // blacklist = registry.getJeiHelpers().getIngredientBlacklist();

        //for every item that was removed during INIT by the item remover, we need to also remove these
        //items from JEI so players don't think they are craftable.
        RemoveRecipes(RecipeHelper.redstoneAgeRecipes);
        RemoveRecipes(RecipeHelper.enlightenedAgeRecipes);
        RemoveRecipes(RecipeHelper.ironAgeRecipes);
        RemoveRecipes(RecipeHelper.stoneAgeRecipes);
        RemoveRecipes(RecipeHelper.woodenAgeRecipes);
    }

    private void RemoveRecipes(List<IForgeRegistryEntry> recipes) {
        for (IForgeRegistryEntry<IRecipe> entry : recipes) {
            System.out.format("JEI Blacklisting: %s%n", entry.getRegistryName().toString());
            IRecipe recipe = CraftingManager.REGISTRY.getObject(entry.getRegistryName());
            IRecipeRegistry recipeRegistry = JeiPlugin.JeiRunTime.getRecipeRegistry();
            if (recipe != null) {
                IRecipeWrapper recipeWrapper = recipeRegistry.getRecipeWrapper(recipe, VanillaRecipeCategoryUid.CRAFTING);
                Runnable runnable = () -> {
                    System.out.println("ITS A HIDING PARTY: " + entry.getRegistryName());
                    try {
                        if (recipeWrapper != null) {
                            recipeRegistry.hideRecipe(recipeWrapper, VanillaRecipeCategoryUid.CRAFTING);
                            //we cant remove the recipes until after we remove them from JEI as CraftingManager will return null at that point
                            ((IForgeRegistryModifiable) ForgeRegistries.RECIPES).remove(entry.getRegistryName());
                        } else {
                            System.out.println("RECIPE WRAPPER NULL: " + entry.getRegistryName());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
                Minecraft.getMinecraft().addScheduledTask(runnable);
            } else {
                System.out.format("recipe was null for: %s%n", entry.getRegistryName());
            }
        }
    }
}