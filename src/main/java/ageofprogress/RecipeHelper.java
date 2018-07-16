package ageofprogress;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryModifiable;

import java.util.ArrayList;
import java.util.List;

public class RecipeHelper {
    private static List<IForgeRegistryEntry> woodenAgeRecipes = new ArrayList<>();
    private static List<IForgeRegistryEntry> stoneAgeRecipes = new ArrayList<>();
    private static List<IForgeRegistryEntry> ironAgeRecipes = new ArrayList<>();
    private static List<IForgeRegistryEntry> enlightenedAgeRecipes = new ArrayList<>();
    private static List<IForgeRegistryEntry> redstoneAgeRecipes = new ArrayList<>();

    public static void discoverRecipes(Age age) {
        switch (age) {
            case wooden:
                addItemBackToCraftables(woodenAgeRecipes);
                break;
            case stone:
                addItemBackToCraftables(stoneAgeRecipes);
                break;
            case iron:
                addItemBackToCraftables(ironAgeRecipes);
                break;
            case enlightened:
                addItemBackToCraftables(enlightenedAgeRecipes);
                break;
            case redstone:
                addItemBackToCraftables(redstoneAgeRecipes);
                break;
            default:
                break;
        }
    }

    public static void removeRecipe(IForgeRegistryEntry entry, Age age) {
        switch (age) {
            case wooden:
                woodenAgeRecipes.add(entry);
                break;
            case stone:
                stoneAgeRecipes.add(entry);
                break;
            case iron:
                ironAgeRecipes.add(entry);
                break;
            case enlightened:
                enlightenedAgeRecipes.add(entry);
                break;
            case redstone:
                redstoneAgeRecipes.add(entry);
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
// TODO: 7/15/2018 unable to unhide recipes with JEI as handlers are not executed on the main Minecraft thread, causing JEI to throw
//            IRecipe recipe = CraftingManager.REGISTRY.getObject(entry.getRegistryName());
//            IRecipeRegistry recipeRegistry = JeiPlugin.JeiRunTime.getRecipeRegistry();
//            IRecipeWrapper recipeWrapper = recipeRegistry.getRecipeWrapper(recipe, VanillaRecipeCategoryUid.CRAFTING);
//            recipeRegistry.unhideRecipe(recipeWrapper, VanillaRecipeCategoryUid.CRAFTING);

            modifiableRegistry.register(entry);
            System.out.format("Unlocked recipes for: %s%n", entry.getRegistryName());
        }
        registry.freeze();
    }

    public static Iterable<IForgeRegistryEntry> getBlackListedRecipes() {
        List<IForgeRegistryEntry> blacklistedRecipes = new ArrayList<>();
        blacklistedRecipes.addAll(woodenAgeRecipes);
        blacklistedRecipes.addAll(stoneAgeRecipes);
        return blacklistedRecipes;
    }
}
