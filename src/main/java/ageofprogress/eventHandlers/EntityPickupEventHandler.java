package ageofprogress.eventHandlers;

import ageofprogress.triggers.ModTriggers;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityPickupEventHandler {
    public static int count = 0;

    @SubscribeEvent
    public static void itemPickup(EntityItemPickupEvent event) {
        String unlocalizedName = event.getItem().getItem().getUnlocalizedName();
        System.out.format("picked up: %s%n", unlocalizedName);

        //these are just here to test the manually triggered advancements
        if (count == 1) ModTriggers.ITEMPRED2.trigger((EntityPlayerMP) event.getEntityPlayer());
        if (count == 2) ModTriggers.ITEMPRED2_1.trigger((EntityPlayerMP) event.getEntityPlayer());
        if (count == 3) ModTriggers.ITEMPRED2_2.trigger((EntityPlayerMP) event.getEntityPlayer());
        if (count == 4) ModTriggers.ITEMPRED3.trigger((EntityPlayerMP) event.getEntityPlayer());

        count++;
    }
}
