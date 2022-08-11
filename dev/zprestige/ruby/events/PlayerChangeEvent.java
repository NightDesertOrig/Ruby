//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.events;

import dev.zprestige.ruby.eventbus.event.*;
import net.minecraft.entity.player.*;

public class PlayerChangeEvent extends Event
{
    public EntityPlayer entityPlayer;
    
    public PlayerChangeEvent(final EntityPlayer entityPlayer) {
        this.entityPlayer = entityPlayer;
    }
    
    public static class TotemPop extends PlayerChangeEvent
    {
        public TotemPop(final EntityPlayer player) {
            super(player);
        }
    }
    
    public static class Death extends PlayerChangeEvent
    {
        public Death(final EntityPlayer player) {
            super(player);
        }
    }
}
