package ageofprogress.eventHandlers;

import ageofprogress.RecipeHelper;
import ageofprogress.Step;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistryModifiable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Removes the ability to craft certain items from the game. All items that are removed will be stored in the
 * step1Recipes list in order to be removed from JEI. If any item is ever added back both JEI and the removed
 * item list should be kept in sync.
 * <p>
 * Must be executed during pre-init.
 */
public class RegisterRecipesEventHandler {

    static String[] woodenAgeTechnology = new String[]{
            "minecraft:stick",
            "minecraft:wooden_button",
    };

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        System.out.println("REMOVING RECIPES");
        IForgeRegistryModifiable modRegistry = (IForgeRegistryModifiable) event.getRegistry();

        Set<String> toBeRemoved = recursivelyRemoveTechnology(woodenAgeTechnology, event.getRegistry().getEntries());

        for (String entry : toBeRemoved) {
            RecipeHelper.removeRecipe(modRegistry.remove((new ResourceLocation(entry))), Step.step1);
        }
    }

    private static Set<String> recursivelyRemoveTechnology(String[] technology, Set<Map.Entry<ResourceLocation, IRecipe>> recipes) {
        int previousSize = 0;
        Set<String> technologySet = new HashSet<String>(Arrays.asList(technology));

        //Remove all of the recipes tier by tier that eventually depends on the technology. we loop here until there
        //are no further modifications because we would like to disable the recipe for 'dispensers' if we disable
        //recipes for sticks
        while (previousSize != technologySet.size()) {
            previousSize = technologySet.size();
            for (Map.Entry<ResourceLocation, IRecipe> recipe : recipes) {
                String recipeId = recipe.getKey().toString();

                for (Ingredient ingredient : recipe.getValue().getIngredients()) {
                    for (ItemStack itemstack : ingredient.getMatchingStacks()) {
                        String ingredientId = itemstack.getItem().getRegistryName().toString();

                        if (technologySet.contains(ingredientId)) {
                            System.out.format("Removing by ingredient %s: %s%n", ingredientId, recipeId);
                            technologySet.add(recipeId);
                        }
                    }
                }
            }
        }
        return technologySet;
    }
}
