//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.player;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import dev.zprestige.ruby.events.*;
import net.minecraft.init.*;
import dev.zprestige.ruby.eventbus.annotation.*;

public class NoEntityTrace extends Module
{
    public final Switch pickaxe;
    public final Switch gapple;
    
    public NoEntityTrace() {
        this.pickaxe = this.Menu.Switch("Pickaxe");
        this.gapple = this.Menu.Switch("Gapple");
    }
    
    @RegisterListener
    public void mouseOverEvent(final MouseOverEvent event) {
        if (this.nullCheck() || !this.isEnabled()) {
            return;
        }
        if ((this.pickaxe.GetSwitch() && this.mc.player.getHeldItemMainhand().getItem().equals(Items.DIAMOND_PICKAXE)) || (this.gapple.GetSwitch() && this.mc.player.getHeldItemMainhand().getItem().equals(Items.GOLDEN_APPLE))) {
            event.setCancelled(true);
        }
    }
}
