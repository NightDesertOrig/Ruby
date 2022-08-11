//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.manager;

import net.minecraft.client.*;
import dev.zprestige.ruby.*;
import dev.zprestige.ruby.events.*;
import dev.zprestige.ruby.eventbus.annotation.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import java.util.stream.*;
import net.minecraft.util.math.*;
import java.util.*;

public class HoleManager
{
    public Minecraft mc;
    public ArrayList<HolePos> holes;
    public Vec3i[] Hole;
    public Vec3i[] DoubleHoleNorth;
    public Vec3i[] DoubleHoleWest;
    
    public HoleManager() {
        this.mc = Ruby.mc;
        this.holes = new ArrayList<HolePos>();
        this.Hole = new Vec3i[] { new Vec3i(1, 0, 0), new Vec3i(-1, 0, 0), new Vec3i(0, -1, 0), new Vec3i(0, 0, 1), new Vec3i(0, 0, -1) };
        this.DoubleHoleNorth = new Vec3i[] { new Vec3i(0, 0, -2), new Vec3i(-1, 0, -1), new Vec3i(1, 0, -1), new Vec3i(0, -1, -1), new Vec3i(0, -1, 0), new Vec3i(-1, 0, 0), new Vec3i(1, 0, 0), new Vec3i(0, 0, 1) };
        this.DoubleHoleWest = new Vec3i[] { new Vec3i(-2, 0, 0), new Vec3i(-1, 0, 1), new Vec3i(-1, 0, -1), new Vec3i(-1, -1, 0), new Vec3i(0, -1, 0), new Vec3i(0, 0, 1), new Vec3i(0, 0, -1), new Vec3i(1, 0, 0) };
        Ruby.eventBus.register((Object)this);
    }
    
    @RegisterListener
    public void onFrame(final Render3DEvent ignored) {
        Ruby.threadManager.run(() -> this.holes = this.getHoles());
    }
    
    public ArrayList<HolePos> getHoles() {
        final ArrayList<HolePos> holes = new ArrayList<HolePos>();
        for (final BlockPos pos : BlockUtil.getSphere(50.0, BlockUtil.AirType.OnlyAir, (EntityPlayer)this.mc.player).stream().filter(blockPos -> this.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR)).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList())) {
            if (this.isEnterable(pos)) {
                boolean isSafe = true;
                for (final Vec3i vec3i : this.Hole) {
                    if (this.isntSafe(pos.add(vec3i))) {
                        isSafe = false;
                    }
                }
                if (isSafe) {
                    holes.add(new HolePos(pos, Type.Bedrock));
                }
                else {
                    boolean isUnsafe = true;
                    for (final Vec3i vec3i2 : this.Hole) {
                        if (this.isntUnsafe(pos.add(vec3i2))) {
                            isUnsafe = false;
                        }
                    }
                    if (isUnsafe) {
                        holes.add(new HolePos(pos, Type.Obsidian));
                    }
                    else {
                        boolean isSafeDoubleNorth = true;
                        for (final Vec3i vec3i3 : this.DoubleHoleNorth) {
                            if (this.isntSafe(pos.add(vec3i3))) {
                                isSafeDoubleNorth = false;
                            }
                        }
                        if (isSafeDoubleNorth) {
                            holes.add(new HolePos(pos, Type.DoubleBedrockNorth));
                        }
                        else {
                            boolean isUnSafeDoubleNorth = true;
                            for (final Vec3i vec3i4 : this.DoubleHoleNorth) {
                                if (this.isntUnsafe(pos.add(vec3i4))) {
                                    isUnSafeDoubleNorth = false;
                                }
                            }
                            if (isUnSafeDoubleNorth) {
                                holes.add(new HolePos(pos, Type.DoubleObsidianNorth));
                            }
                            else {
                                boolean isSafeDoubleWest = true;
                                for (final Vec3i vec3i5 : this.DoubleHoleWest) {
                                    if (this.isntUnsafe(pos.add(vec3i5))) {
                                        isSafeDoubleWest = false;
                                    }
                                }
                                if (isSafeDoubleWest) {
                                    holes.add(new HolePos(pos, Type.DoubleBedrockWest));
                                }
                                else {
                                    boolean isUnSafeDoubleWest = true;
                                    for (final Vec3i vec3i6 : this.DoubleHoleWest) {
                                        if (this.isntUnsafe(pos.add(vec3i6))) {
                                            isUnSafeDoubleWest = false;
                                        }
                                    }
                                    if (!isUnSafeDoubleWest) {
                                        continue;
                                    }
                                    holes.add(new HolePos(pos, Type.DoubleObsidianWest));
                                }
                            }
                        }
                    }
                }
            }
        }
        return holes;
    }
    
    public boolean isEnterable(final BlockPos pos) {
        return this.mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR) && this.mc.world.getBlockState(pos.up().up()).getBlock().equals(Blocks.AIR);
    }
    
    public boolean isntUnsafe(final BlockPos pos) {
        return !this.mc.world.getBlockState(pos).getBlock().equals(Blocks.BEDROCK) && !this.mc.world.getBlockState(pos).getBlock().equals(Blocks.OBSIDIAN);
    }
    
    public boolean isntSafe(final BlockPos pos) {
        return !this.mc.world.getBlockState(pos).getBlock().equals(Blocks.BEDROCK);
    }
    
    public enum Type
    {
        Bedrock, 
        Obsidian, 
        DoubleBedrockNorth, 
        DoubleBedrockWest, 
        DoubleObsidianNorth, 
        DoubleObsidianWest;
    }
    
    public static class HolePos
    {
        public BlockPos pos;
        public Type holeType;
        
        public HolePos(final BlockPos pos, final Type holeType) {
            this.pos = pos;
            this.holeType = holeType;
        }
    }
}
