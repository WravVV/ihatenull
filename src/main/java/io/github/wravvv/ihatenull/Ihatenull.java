package io.github.wravvv.ihatenull;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;

import java.util.Arrays;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Ihatenull.MODID)
public class Ihatenull {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "ihatenull";
    public static final String MODNAME = "IHateNull";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public Ihatenull() {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("Ihatenull debug");
            LOGGER.info(Arrays.toString(NativeImage.class.getDeclaredMethods()));
        }
    }

}
