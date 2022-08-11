//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.mixins.gui;

import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.inventory.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import dev.zprestige.ruby.util.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiContainer.class })
public abstract class MixinGuiContainer extends GuiScreen
{
    @Inject(method = { "drawScreen" }, at = { @At("TAIL") })
    protected void drawScreen(final int mouseX, final int mouseY, final float partialTicks, final CallbackInfo ci) {
        RenderUtil.renderLogo();
    }
}
