package io.github.wravvv.ihatenull.mixin;

import com.mojang.blaze3d.platform.NativeImage;
import io.github.wravvv.ihatenull.Ihatenull;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static io.github.wravvv.ihatenull.Ihatenull.*;

@Mixin(value = MissingTextureAtlasSprite.class,remap = false)
public abstract class MixinMissingTextureAtlasSprite {

    @Inject(at=@At(value="HEAD",remap = false),method= "generateMissingImage",remap=false,cancellable = true)
    private static void generateMissingImage(int p_249811_, int p_249362_, CallbackInfoReturnable<NativeImage> cir){
        cir.cancel();

        //boolean impatientThread = false;
        BufferedImage nullTexture = null;
        File textureFile = new File("config/ihatenull/null.png");

        new File("config/ihatenull").mkdirs();
        try {

            if (textureFile.createNewFile()) {
                try (InputStream configStream = Ihatenull.class.getResourceAsStream("/null.png")){
                    if (configStream != null) {
                        nullTexture = ImageIO.read(Ihatenull.class.getResource("/null.png"));
                        ImageIO.write(ImageIO.read(configStream),"png",textureFile);
                        LOGGER.info("{} >> Default null.png taken from: /null.png", MODNAME);
                    } else {
                        LOGGER.info("{} >> Default null.png Resource not found", MODNAME);
                    }
                } catch (Exception e) {
                    LOGGER.error("{} >> Error setting default", MODNAME);
                    LOGGER.error(e.getMessage());
                }
            } else {

                if(textureFile.length() > 0) {
                    try {
                        nullTexture = ImageIO.read(textureFile);
                    } catch (Exception e) {
                        LOGGER.error("{} >> Error reading texture", MODNAME);
                        LOGGER.error(e.getMessage());
                    }
                } else {
                    nullTexture = ImageIO.read(Ihatenull.class.getResource("/null.png"));
                }

            }
        } catch (IOException e) {
            LOGGER.error("{} >> Error setting up config", MODNAME);
            LOGGER.error(e.getMessage());
        }

        LOGGER.info("{} >> Texture empty? {}", MODNAME, (nullTexture == null));

        NativeImage missingTexture;
        if (nullTexture == null){
            missingTexture = new NativeImage(p_249811_, p_249362_, false);
        } else {
            missingTexture = new NativeImage(nullTexture.getWidth(), nullTexture.getHeight(), false);
        }

        for(int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {

                // LOGGER.info("IHateNull >> texture empty? " + (nullTexture==null));
                if (nullTexture == null){
                    if (i < 8 ^ j < 8) {
                        missingTexture.setPixelRGBA(j, i, -1);
                    } else {
                        missingTexture.setPixelRGBA(j, i, -16777216);
                    }
                } else {
                    int argb = nullTexture.getRGB(j,i);
                    missingTexture.setPixelRGBA(j, i, (argb & 0xFF00FF00) | ((argb & 0x000000FF) << 16) | ((argb & 0x00FF0000) >>> 16));
                }
            }
        }

        cir.setReturnValue(missingTexture);
    }
}
