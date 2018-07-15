package ageofprogress.eventHandlers;

import ageofprogress.RecipeHelper;
import ageofprogress.Step;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AdvancementEventHandler {
    @SubscribeEvent
    public static void advance(AdvancementEvent event) {
        System.out.println("Advanced: " + event.getAdvancement().getId());

        ResourceLocation id = event.getAdvancement().getId();
        if (id.equals(new ResourceLocation("ageofprogress", "step1"))) {
            RecipeHelper.discoverRecipes(Step.step1);
        }
    }
}
