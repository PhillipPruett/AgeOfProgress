package ageofprogress;

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

import java.util.ArrayList;
import java.util.List;

public class RecipeHelper {
    public static List<IForgeRegistryEntry> woodenAgeRecipes = new ArrayList<>();
    public static List<IForgeRegistryEntry> stoneAgeRecipes = new ArrayList<>();
    public static List<IForgeRegistryEntry> ironAgeRecipes = new ArrayList<>();
    public static List<IForgeRegistryEntry> enlightenedAgeRecipes = new ArrayList<>();
    public static List<IForgeRegistryEntry> redstoneAgeRecipes = new ArrayList<>();

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
        if (redstoneAgeRecipes.contains(entry)
                || enlightenedAgeRecipes.contains(entry)
                || ironAgeRecipes.contains(entry)
                || stoneAgeRecipes.contains(entry)
                || woodenAgeRecipes.contains(entry)) {
            return;
        }

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
            System.out.format("Unlocking recipes for: %s%n", entry.getRegistryName());
            modifiableRegistry.register(entry);
            IRecipe recipe = CraftingManager.REGISTRY.getObject(entry.getRegistryName());
            if (recipe != null) {
                IRecipeRegistry recipeRegistry = JeiPlugin.JeiRunTime.getRecipeRegistry();
                IRecipeWrapper recipeWrapper = recipeRegistry.getRecipeWrapper(recipe, VanillaRecipeCategoryUid.CRAFTING);
                Runnable runnable = () -> {
                    System.out.println("ITS A UNHIDING PARTY: " + entry.getRegistryName());
                    try {
                        if (recipeWrapper != null) {
                            recipeRegistry.unhideRecipe(recipeWrapper, VanillaRecipeCategoryUid.CRAFTING);
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
        registry.freeze();
    }

    public static Iterable<IForgeRegistryEntry> getBlackListedRecipes() {
        List<IForgeRegistryEntry> blacklistedRecipes = new ArrayList<>();
        blacklistedRecipes.addAll(woodenAgeRecipes);
        blacklistedRecipes.addAll(stoneAgeRecipes);
        blacklistedRecipes.addAll(ironAgeRecipes);
        blacklistedRecipes.addAll(enlightenedAgeRecipes);
        blacklistedRecipes.addAll(redstoneAgeRecipes);
        return blacklistedRecipes;
    }
}
