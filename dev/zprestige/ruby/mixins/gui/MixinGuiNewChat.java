//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.mixins.gui;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import java.awt.*;
import dev.zprestige.ruby.util.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiNewChat.class })
public class MixinGuiNewChat
{
    @Redirect(method = { "drawChat" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;drawRect(IIIII)V", ordinal = 0))
    protected void drawRect(final int left, final int top, final int right, final int bottom, final int color) {
        RenderUtil.drawRect((float)left, (float)top, (float)right, (float)bottom, new Color(0, 0, 0, 50).getRGB());
    }
}
