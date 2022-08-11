//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.events;

import dev.zprestige.ruby.eventbus.event.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;

@IsCancellable
public class RenderLivingEntityEvent extends Event
{
    private final ModelBase modelBase;
    private final EntityLivingBase entityLivingBase;
    private final float limbSwing;
    private final float limbSwingAmount;
    private final float ageInTicks;
    private final float netHeadYaw;
    private final float headPitch;
    private final float scaleFactor;
    
    public RenderLivingEntityEvent(final ModelBase modelBase, final EntityLivingBase entityLivingBase, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor) {
        this.modelBase = modelBase;
        this.entityLivingBase = entityLivingBase;
        this.limbSwing = limbSwing;
        this.limbSwingAmount = limbSwingAmount;
        this.ageInTicks = ageInTicks;
        this.netHeadYaw = netHeadYaw;
        this.headPitch = headPitch;
        this.scaleFactor = scaleFactor;
    }
    
    public ModelBase getModelBase() {
        return this.modelBase;
    }
    
    public EntityLivingBase getEntityLivingBase() {
        return this.entityLivingBase;
    }
    
    public float getLimbSwing() {
        return this.limbSwing;
    }
    
    public float getLimbSwingAmount() {
        return this.limbSwingAmount;
    }
    
    public float getAgeInTicks() {
        return this.ageInTicks;
    }
    
    public float getNetHeadYaw() {
        return this.netHeadYaw;
    }
    
    public float getHeadPitch() {
        return this.headPitch;
    }
    
    public float getScaleFactor() {
        return this.scaleFactor;
    }
}
