package net.tinkoproff.ported.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    
    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void renderCustomText(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo info) {
        context.drawTextWithShadow(this.textRenderer, "W+3 - ported by Tinkoprof", 10, 10, 0xFFFFFF);
    }
}
