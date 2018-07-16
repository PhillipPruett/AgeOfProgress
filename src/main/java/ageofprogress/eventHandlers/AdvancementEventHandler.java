package ageofprogress.eventHandlers;

import ageofprogress.Age;
import ageofprogress.RecipeHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AdvancementEventHandler {
    @SubscribeEvent
    public static void advance(AdvancementEvent event) {
        System.out.println("Advanced: " + event.getAdvancement().getId());

        ResourceLocation id = event.getAdvancement().getId();
        if (id.equals(new ResourceLocation("ageofprogress", "wooden"))) {
            RecipeHelper.unhideRecipes(Age.wooden);
        } else if (id.equals(new ResourceLocation("ageofprogress", "stone"))) {
            RecipeHelper.unhideRecipes(Age.stone);
        } else if (id.equals(new ResourceLocation("ageofprogress", "iron"))) {
            RecipeHelper.unhideRecipes(Age.iron);
        } else if (id.equals(new ResourceLocation("ageofprogress", "enlightened"))) {
            RecipeHelper.unhideRecipes(Age.enlightened);
        } else if (id.equals(new ResourceLocation("ageofprogress", "redstone"))) {
            RecipeHelper.unhideRecipes(Age.redstone);
        }
    }
}
