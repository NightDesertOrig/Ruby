//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.events;

import dev.zprestige.ruby.eventbus.event.*;
import net.minecraft.entity.*;

@IsCancellable
public class MoveEvent extends Event
{
    public double motionX;
    public double motionY;
    public double motionZ;
    private MoverType type;
    
    public MoveEvent(final MoverType type, final double x, final double y, final double z) {
        this.type = type;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
    }
    
    public MoverType getType() {
        return this.type;
    }
    
    public void setType(final MoverType type) {
        this.type = type;
    }
    
    public double getMotionX() {
        return this.motionX;
    }
    
    public void setMotionX(final double motionX) {
        this.motionX = motionX;
    }
    
    public double getMotionY() {
        return this.motionY;
    }
    
    public void setMotionY(final double motionY) {
        this.motionY = motionY;
    }
    
    public double getMotionZ() {
        return this.motionZ;
    }
    
    public void setMotionZ(final double motionZ) {
        this.motionZ = motionZ;
    }
    
    public void setMotion(final double x, final double y, final double z) {
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
    }
}
