package net.tinkoproff.ported.mixin;

import net.minecraft.client.gui.screen.WurstPlus;
import net.minecraft.client.util.math.MatrixStack;
import net.tinkoproff.ported.TemplateMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {

    @Inject(method = "render", at = @At("TAIL"))
    private void renderCustomText(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
        WurstPlus.LOGGER.info("Rendering custom text on title screen");
        net.minecraft.client.MinecraftClient.getInstance().textRenderer.drawWithShadow(
            matrices,
            "W+3 - ported by Tinkoprof",
            10,
            10,
            0xFFFFFF
        );
    }
}
