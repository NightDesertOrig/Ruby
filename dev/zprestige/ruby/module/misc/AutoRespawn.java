//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.misc;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.util.math.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.client.gui.*;

public class AutoRespawn extends Module
{
    public final Switch showDeath;
    public final ColorBox color;
    public AxisAlignedBB bb;
    
    public AutoRespawn() {
        this.showDeath = this.Menu.Switch("Show Death");
        this.color = this.Menu.Color("Color");
    }
    
    @Override
    public void onFrame(final float partialTicks) {
        if (this.bb != null) {
            RenderUtil.drawBBBoxWithHeightDepth(this.bb, this.color.GetColor(), this.color.GetColor().getAlpha(), (float)(256.0 - this.bb.minY));
        }
    }
    
    @Override
    public void onTick() {
        if (!(this.mc.currentScreen instanceof GuiGameOver)) {
            return;
        }
        if (this.showDeath.GetSwitch()) {
            this.bb = new AxisAlignedBB(BlockUtil.getPlayerPos());
        }
        this.mc.player.respawnPlayer();
        this.mc.displayGuiScreen((GuiScreen)null);
    }
}
