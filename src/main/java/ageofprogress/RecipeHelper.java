package ageofprogress;

import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.client.Minecraft;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryModifiable;

import java.util.HashMap;
import java.util.Map;

public class RecipeHelper {

    public static HashMap<IForgeRegistryEntry, Age> recipes = new HashMap<>();

    public static void addRecipe(IForgeRegistryEntry entry, Age age) {
        recipes.putIfAbsent(entry, age);
    }

    public static void hideRecipes(IJeiRuntime jeiRunTime) {
        for (Map.Entry<IForgeRegistryEntry, Age> mapEntry : recipes.entrySet()) {
            IForgeRegistryEntry entry = mapEntry.getKey();
            hideRecipe(entry, jeiRunTime);
        }
    }

    public static void unhideRecipes(Age age) {

        IForgeRegistryModifiable modifiableRegistry = (IForgeRegistryModifiable) ForgeRegistries.RECIPES;
        ForgeRegistry<IRecipe> registry = (ForgeRegistry<IRecipe>) modifiableRegistry;

        registry.unfreeze();
        for (Map.Entry<IForgeRegistryEntry, Age> mapEntry : recipes.entrySet()) {
            if (mapEntry.getValue().equals(age)) {
                unhideRecipe(modifiableRegistry, mapEntry.getKey());
            }
        }
        registry.freeze();
    }

    private static void unhideRecipe(IForgeRegistryModifiable modifiableRegistry, IForgeRegistryEntry entry) {
        System.out.format("Unhiding recipes for: %s%n", entry.getRegistryName());
        modifiableRegistry.register(entry);
        IRecipe recipe = CraftingManager.REGISTRY.getObject(entry.getRegistryName());
        if (recipe != null) {
            IRecipeRegistry recipeRegistry = JeiPlugin.JeiRunTime.getRecipeRegistry();
            IRecipeWrapper recipeWrapper = recipeRegistry.getRecipeWrapper(recipe, VanillaRecipeCategoryUid.CRAFTING);
            Runnable runnable = () -> {
                System.out.println("unhiding: " + entry.getRegistryName());
                try {
                    if (recipeWrapper != null) {
                        recipeRegistry.unhideRecipe(recipeWrapper, VanillaRecipeCategoryUid.CRAFTING);
                    } else {
                        System.out.println("recipeWrapper was null: " + entry.getRegistryName());
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

    private static void hideRecipe(IForgeRegistryEntry entry, IJeiRuntime jeiRunTime) {
        System.out.format("hideRecipe: %s%n", entry.getRegistryName().toString());
        IRecipe recipe = CraftingManager.REGISTRY.getObject(entry.getRegistryName());
        IRecipeRegistry recipeRegistry = jeiRunTime.getRecipeRegistry();
        if (recipe != null) {
            IRecipeWrapper recipeWrapper = recipeRegistry.getRecipeWrapper(recipe, VanillaRecipeCategoryUid.CRAFTING);
            Runnable runnable = () -> {
                System.out.println("hiding: " + entry.getRegistryName());
                try {
                    if (recipeWrapper != null) {
                        recipeRegistry.hideRecipe(recipeWrapper, VanillaRecipeCategoryUid.CRAFTING);
                        //we cant remove the recipes until after we remove them from JEI as CraftingManager will return null at that point
                        ((IForgeRegistryModifiable) ForgeRegistries.RECIPES).remove(entry.getRegistryName());
                    } else {
                        System.out.println("recipeWrapper was null: " + entry.getRegistryName());
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
