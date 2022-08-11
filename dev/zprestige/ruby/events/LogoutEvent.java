//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.events;

import dev.zprestige.ruby.eventbus.event.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;

public class LogoutEvent extends Event
{
    public EntityPlayer entityPlayer;
    public BlockPos pos;
    public int entityId;
    public long currentTimeMillis;
    
    public LogoutEvent(final EntityPlayer entityPlayer, final BlockPos pos, final long currentTimeMillis, final int entityId) {
        this.entityPlayer = entityPlayer;
        this.pos = pos;
        this.currentTimeMillis = currentTimeMillis;
        this.entityId = entityId;
    }
    
    public static class LoginEvent extends Event
    {
        public EntityPlayer entityPlayer;
        
        public LoginEvent(final EntityPlayer entityPlayer) {
            this.entityPlayer = entityPlayer;
        }
    }
}
