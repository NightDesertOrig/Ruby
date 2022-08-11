//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.events;

import dev.zprestige.ruby.eventbus.event.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.block.model.*;

@IsCancellable
public class RenderItemInFirstPersonEvent extends Event
{
    public EntityLivingBase entity;
    public ItemStack stack;
    public ItemCameraTransforms.TransformType transformType;
    public boolean leftHanded;
    public boolean isPre;
    
    public RenderItemInFirstPersonEvent(final EntityLivingBase entitylivingbaseIn, final ItemStack heldStack, final ItemCameraTransforms.TransformType transform, final boolean leftHanded, final boolean pre) {
        this.entity = entitylivingbaseIn;
        this.stack = heldStack;
        this.transformType = transform;
        this.leftHanded = leftHanded;
        this.isPre = pre;
    }
    
    public ItemCameraTransforms.TransformType getTransformType() {
        return this.transformType;
    }
    
    public ItemStack getStack() {
        return this.stack;
    }
    
    public void setStack(final ItemStack stack) {
        this.stack = stack;
    }
    
    public EntityLivingBase getEntity() {
        return this.entity;
    }
}
