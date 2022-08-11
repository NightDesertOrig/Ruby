//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.combat;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import java.util.*;

public class AntiCity extends Module
{
    public final Parent timing;
    public final Slider placeDelay;
    public final Slider placeRange;
    public final Parent misc;
    public final Switch inLiquids;
    public final Switch rotate;
    public final Switch packet;
    public Timer timer;
    
    public AntiCity() {
        this.timing = this.Menu.Parent("Timing");
        this.placeDelay = this.Menu.Slider("Place Delay", 0, 1000).parent(this.timing);
        this.placeRange = this.Menu.Slider("Place Range", 0.1f, 6.0f).parent(this.timing);
        this.misc = this.Menu.Parent("Misc");
        this.inLiquids = this.Menu.Switch("In Liquids").parent(this.misc);
        this.rotate = this.Menu.Switch("Rotate").parent(this.misc);
        this.packet = this.Menu.Switch("Packet").parent(this.misc);
        this.timer = new Timer();
    }
    
    @Override
    public void onTick() {
        if (this.timer.getTime((long)this.placeDelay.GetSlider())) {
            final BlockPos pos = this.getNextPos();
            if (pos == null) {
                this.disableModule("No posses found, disabling AntiCity");
                return;
            }
            BlockUtil.placeBlockWithSwitch(pos, EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.OBSIDIAN)), this.timer);
        }
    }
    
    public BlockPos getNextPos() {
        final TreeMap<Float, BlockPos> posTreeMap = new TreeMap<Float, BlockPos>();
        for (final BlockPos pos : BlockUtil.getSphere(this.placeRange.GetSlider() - 1.0f, BlockUtil.AirType.OnlyAir, (EntityPlayer)this.mc.player)) {
            if (this.isPosSurroundedByBlocks(pos)) {
                if (!this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos)).isEmpty()) {
                    continue;
                }
                posTreeMap.put((float)this.mc.player.getDistanceSq(pos), pos);
            }
        }
        if (!posTreeMap.isEmpty()) {
            return posTreeMap.firstEntry().getValue();
        }
        return null;
    }
    
    public boolean isPosSurroundedByBlocks(final BlockPos blockPos) {
        return this.canPlace(blockPos.north()) || this.canPlace(blockPos.east()) || this.canPlace(blockPos.south()) || this.canPlace(blockPos.west()) || this.canPlace(blockPos.down()) || this.canPlace(blockPos.up());
    }
    
    public boolean canPlace(final BlockPos pos) {
        return !this.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) && (!this.inLiquids.GetSwitch() || (!this.mc.world.getBlockState(pos).getBlock().equals(Blocks.WATER) && !this.mc.world.getBlockState(pos).getBlock().equals(Blocks.FLOWING_WATER) && !this.mc.world.getBlockState(pos).getBlock().equals(Blocks.LAVA) && !this.mc.world.getBlockState(pos).getBlock().equals(Blocks.FLOWING_LAVA)));
    }
}
