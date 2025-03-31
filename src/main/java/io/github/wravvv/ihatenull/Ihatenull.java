package io.github.wravvv.ihatenull;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

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

}
