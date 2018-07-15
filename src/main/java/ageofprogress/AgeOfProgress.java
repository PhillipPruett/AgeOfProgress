package ageofprogress;

import ageofprogress.eventHandlers.AdvancementEventHandler;
import ageofprogress.eventHandlers.EntityPickupEventHandler;
import ageofprogress.eventHandlers.ItemCraftEventHandler;
import ageofprogress.eventHandlers.RegisterRecipesEventHandler;
import ageofprogress.triggers.CustomTrigger;
import ageofprogress.triggers.ModTriggers;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Mod(modid = AgeOfProgress.MODID, name = AgeOfProgress.NAME, version = AgeOfProgress.VERSION)
public class AgeOfProgress {
    public static final String MODID = "ageofprogress";
    public static final String NAME = "ageofprogress";
    public static final String VERSION = "1.0";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();

        // Register preInitialization eventHandlers
        MinecraftForge.EVENT_BUS.register(RegisterRecipesEventHandler.class);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        // Register eventHandlers
        MinecraftForge.EVENT_BUS.register(ItemCraftEventHandler.class);
        MinecraftForge.EVENT_BUS.register(EntityPickupEventHandler.class);
        MinecraftForge.EVENT_BUS.register(AdvancementEventHandler.class);

        registerManuallyTriggeredAdvancements();
    }

    private void registerManuallyTriggeredAdvancements() {
        Method method;
        method = ReflectionHelper.findMethod(CriteriaTriggers.class, "register", "func_192118_a", ICriterionTrigger.class);
        method.setAccessible(true);

        for (int i = 0; i < ModTriggers.TRIGGER_ARRAY.length; i++) {
            try {
                CustomTrigger customTrigger = ModTriggers.TRIGGER_ARRAY[i];
                System.out.println("Registering trigger: " + customTrigger.getId());
                method.invoke(null, customTrigger);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
