//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.mixins.gui;

import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import java.awt.*;
import dev.zprestige.ruby.util.*;
import dev.zprestige.ruby.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiButton.class })
public abstract class MixinGuiButton
{
    @Shadow
    public int x;
    @Shadow
    public int y;
    @Shadow
    public int width;
    @Shadow
    public int height;
    @Shadow
    public boolean visible;
    @Shadow
    public boolean enabled;
    @Shadow
    public String displayString;
    @Shadow
    protected boolean hovered;
    
    @Shadow
    protected abstract void mouseDragged(final Minecraft p0, final int p1, final int p2);
    
    @Inject(method = { "drawButton" }, at = { @At("HEAD") }, cancellable = true)
    protected void drawButton(final Minecraft mc, final int mouseX, final int mouseY, final float partialTicks, final CallbackInfo ci) {
        if (this.visible) {
            this.hovered = (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height);
            RenderUtil.drawRect((float)this.x, (float)this.y, (float)(this.x + this.width), (float)(this.y + this.height), new Color(0, 0, 0, 50).getRGB());
            if (this.hovered) {
                RenderUtil.drawRect((float)this.x, (float)this.y, (float)(this.x + this.width), (float)(this.y + this.height), new Color(0, 0, 0, 50).getRGB());
            }
            Ruby.fontManager.drawStringWithShadow(this.displayString, this.x + this.width / 2.0f - Ruby.fontManager.getStringWidth(this.displayString) / 2.0f, this.y + (this.height - 10) / 2.0f, this.enabled ? (this.hovered ? 16777120 : 14737632) : 10526880);
            this.mouseDragged(mc, mouseX, mouseY);
        }
        ci.cancel();
    }
}
