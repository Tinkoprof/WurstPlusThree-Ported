package net.tinkoproff.ported.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    private static final Logger LOGGER = LoggerFactory.getLogger("WurstPlus");

    @Inject(method = "render", at = @At("TAIL"))
    private void renderCustomText(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
        LOGGER.info("Rendering custom text on title screen");
        MinecraftClient.getInstance().textRenderer.drawWithShadow(
            matrices,
            "W+3 - ported by Tinkoprof",
            10,
            10,
            0xFFFFFF
        );
    }
}
