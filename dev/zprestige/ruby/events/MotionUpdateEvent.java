//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.events;

import dev.zprestige.ruby.eventbus.event.*;

@IsCancellable
public class MotionUpdateEvent extends Event
{
    public double x;
    public double y;
    public double z;
    public float rotationYaw;
    public float rotationPitch;
    public boolean onGround;
    public boolean modified;
    public int stage;
    
    public MotionUpdateEvent(final int stage, final double x, final double y, final double z, final float rotationYaw, final float rotationPitch, final boolean onGround) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.rotationYaw = rotationYaw;
        this.rotationPitch = rotationPitch;
        this.onGround = onGround;
        this.stage = stage;
    }
    
    public MotionUpdateEvent(final int stage, final MotionUpdateEvent event) {
        this(stage, event.x, event.y, event.z, event.rotationYaw, event.rotationPitch, event.onGround);
    }
    
    public boolean isModified() {
        return this.modified;
    }
    
    public double getX() {
        return this.x;
    }
    
    public void setX(final double x) {
        this.modified = true;
        this.x = x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setY(final double y) {
        this.modified = true;
        this.y = y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public void setZ(final double z) {
        this.modified = true;
        this.z = z;
    }
    
    public float getYaw() {
        return this.rotationYaw;
    }
    
    public void setYaw(final float rotationYaw) {
        this.modified = true;
        this.rotationYaw = rotationYaw;
    }
    
    public float getPitch() {
        return this.rotationPitch;
    }
    
    public void setPitch(final float rotationPitch) {
        this.modified = true;
        this.rotationPitch = rotationPitch;
    }
    
    public boolean isOnGround() {
        return this.onGround;
    }
    
    public void setOnGround(final boolean onGround) {
        this.modified = true;
        this.onGround = onGround;
    }
}
