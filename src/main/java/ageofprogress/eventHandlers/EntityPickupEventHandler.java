package ageofprogress.eventHandlers;

import ageofprogress.triggers.ModTriggers;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityPickupEventHandler {

    @SubscribeEvent
    public static void itemPickup(EntityItemPickupEvent event) {
        String unlocalizedName = event.getItem().getItem().getUnlocalizedName();
        System.out.format("picked up: %s%n", unlocalizedName);
    }
}
