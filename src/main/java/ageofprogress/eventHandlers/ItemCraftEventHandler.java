package ageofprogress.eventHandlers;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class ItemCraftEventHandler {
    @SubscribeEvent
    public static void itemCrafted(PlayerEvent.ItemCraftedEvent event) {
        System.out.println("lol you tried to craft an item");
        //event.crafting.setCount(0);


    }
}
