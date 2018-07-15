package ageofprogress;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryModifiable;

import java.util.ArrayList;
import java.util.List;

public class RecipeHelper {
    private static List<IForgeRegistryEntry> step1Recipes = new ArrayList<>();
    private static List<IForgeRegistryEntry> step2Recipes = new ArrayList<>();

    public static void discoverRecipes(Step step) {
        switch (step) {
            case step1:
                addItemBackToCraftables(step1Recipes);
                break;
            default:
                break;
        }
    }

    public static void removeRecipe(IForgeRegistryEntry entry, Step step) {
        switch (step) {
            case step1:
                step1Recipes.add(entry);
                break;
            default:
                break;
        }
    }

    private static void addItemBackToCraftables(List<IForgeRegistryEntry> items) {
        IForgeRegistryModifiable modifiableRegistry = (IForgeRegistryModifiable) ForgeRegistries.RECIPES;
        ForgeRegistry<IRecipe> registry = (ForgeRegistry<IRecipe>) modifiableRegistry;

        registry.unfreeze();
        for (IForgeRegistryEntry entry : items) {
            modifiableRegistry.register(entry);
            System.out.format("Unlocked recipes for: %s%n", entry.getRegistryName());
        }
        registry.freeze();
    }

    public static Iterable<IForgeRegistryEntry> getBlackListedRecipes() {
        List<IForgeRegistryEntry> blacklistedRecipes = new ArrayList<>();
        blacklistedRecipes.addAll(step1Recipes);
        blacklistedRecipes.addAll(step2Recipes);
        return blacklistedRecipes;
    }
}
