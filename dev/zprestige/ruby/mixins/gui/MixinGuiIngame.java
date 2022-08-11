//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.mixins.gui;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import dev.zprestige.ruby.module.client.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiIngame.class })
public class MixinGuiIngame
{
    @Inject(method = { "renderPotionEffects" }, at = { @At("HEAD") }, cancellable = true)
    protected void renderPotionEffects(final ScaledResolution scaledRes, final CallbackInfo ci) {
        if (Hud.Instance.nullCheck() && Hud.Instance.isEnabled()) {
            ci.cancel();
        }
    }
}
