//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.events;

import dev.zprestige.ruby.eventbus.event.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

@IsCancellable
public class BlockInteractEvent extends Event
{
    public BlockPos pos;
    public EnumFacing facing;
    
    public BlockInteractEvent(final BlockPos pos, final EnumFacing facing) {
        this.pos = pos;
        this.facing = facing;
    }
    
    @IsCancellable
    public static class ClickBlock extends BlockInteractEvent
    {
        public ClickBlock(final BlockPos pos, final EnumFacing facing) {
            super(pos, facing);
        }
    }
    
    @IsCancellable
    public static class DamageBlock extends BlockInteractEvent
    {
        public DamageBlock(final BlockPos pos, final EnumFacing facing) {
            super(pos, facing);
        }
    }
}
