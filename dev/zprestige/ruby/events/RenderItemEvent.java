//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.events;

import dev.zprestige.ruby.eventbus.event.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;

public class RenderItemEvent extends Event
{
    public ItemStack stack;
    public EntityLivingBase entityLivingBase;
    
    public RenderItemEvent(final ItemStack stack, final EntityLivingBase entityLivingBase) {
        this.stack = stack;
        this.entityLivingBase = entityLivingBase;
    }
    
    public static class MainHand extends RenderItemEvent
    {
        public MainHand(final ItemStack stack, final EntityLivingBase entityLivingBase) {
            super(stack, entityLivingBase);
        }
    }
    
    public static class Offhand extends RenderItemEvent
    {
        public Offhand(final ItemStack stack, final EntityLivingBase entityLivingBase) {
            super(stack, entityLivingBase);
        }
    }
}
