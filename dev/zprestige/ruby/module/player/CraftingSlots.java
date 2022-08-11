//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.player;

import dev.zprestige.ruby.module.*;
import net.minecraft.network.play.client.*;
import dev.zprestige.ruby.eventbus.annotation.*;
import dev.zprestige.ruby.events.*;

public class CraftingSlots extends Module
{
    @RegisterListener
    public void onPacketSend(final PacketEvent.PacketSendEvent event) {
        if (this.nullCheck() || !this.isEnabled() || !(event.getPacket() instanceof CPacketCloseWindow)) {
            return;
        }
        event.setCancelled(((CPacketCloseWindow)event.getPacket()).windowId == 0);
    }
    
    @RegisterListener
    public void onInventoryClose(final CloseInventoryEvent event) {
        if (this.nullCheck() || !this.isEnabled()) {
            return;
        }
        event.setCancelled(true);
    }
}
