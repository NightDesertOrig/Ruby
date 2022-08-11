//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.combat;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import java.util.*;
import net.minecraft.init.*;

public class AntiTrap extends Module
{
    public final Slider placeDelay;
    public final Slider targetRange;
    public final Switch rotate;
    public final Switch packet;
    public ArrayList<BlockPos> placedPosses;
    public Timer timer;
    
    public AntiTrap() {
        this.placeDelay = this.Menu.Slider("Place Delay", 0, 500);
        this.targetRange = this.Menu.Slider("Target Range", 0.1f, 15.0f);
        this.rotate = this.Menu.Switch("Rotate");
        this.packet = this.Menu.Switch("Packet");
        this.placedPosses = new ArrayList<BlockPos>();
        this.timer = new Timer();
    }
    
    @Override
    public void onTick() {
        final EntityPlayer entityPlayer = EntityUtil.getTarget(this.targetRange.GetSlider());
        final BlockPos pos = EntityUtil.getPlayerPos((EntityPlayer)this.mc.player).up();
        if (entityPlayer == null) {
            return;
        }
        final BlockPos targetPos = this.getBestSide(entityPlayer);
        if (!BlockUtil.isPlayerSafe((EntityPlayer)this.mc.player) || !this.isTrapped() || targetPos == null || !this.timer.getTime((long)this.placeDelay.GetSlider())) {
            if (!this.placedPosses.isEmpty()) {
                this.placedPosses.clear();
            }
            return;
        }
        if (this.placedPosses.contains(pos)) {
            return;
        }
        final int slot = InventoryUtil.getItemFromHotbar(Items.END_CRYSTAL);
        final int currentSlot = this.mc.player.inventory.currentItem;
        if (slot == -1) {
            return;
        }
        InventoryUtil.switchToSlot(slot);
        this.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(targetPos, EnumFacing.UP, this.mc.player.getHeldItemOffhand().getItem().equals(Items.END_CRYSTAL) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.5f, 0.5f, 0.5f));
        this.mc.player.inventory.currentItem = currentSlot;
        this.mc.playerController.updateController();
    }
    
    public BlockPos getBestSide(final EntityPlayer entityPlayer) {
        final BlockPos pos = EntityUtil.getPlayerPos((EntityPlayer)this.mc.player).up();
        final TreeMap<Double, BlockPos> posTreeMap = new TreeMap<Double, BlockPos>();
        if (this.isntAir(pos.north()) && !this.isntAir(pos.north().up()) && !this.isntAir(pos.north().up().up())) {
            posTreeMap.put(entityPlayer.getDistanceSq(pos.north()), pos.north());
        }
        if (this.isntAir(pos.east()) && !this.isntAir(pos.east().up()) && !this.isntAir(pos.east().up().up())) {
            posTreeMap.put(entityPlayer.getDistanceSq(pos.east()), pos.east());
        }
        if (this.isntAir(pos.south()) && !this.isntAir(pos.south().up()) && !this.isntAir(pos.south().up().up())) {
            posTreeMap.put(entityPlayer.getDistanceSq(pos.south()), pos.south());
        }
        if (this.isntAir(pos.west()) && !this.isntAir(pos.west().up()) && !this.isntAir(pos.west().up().up())) {
            posTreeMap.put(entityPlayer.getDistanceSq(pos.west()), pos.west());
        }
        if (!posTreeMap.isEmpty()) {
            return posTreeMap.lastEntry().getValue();
        }
        return null;
    }
    
    public boolean isTrapped() {
        final BlockPos pos = EntityUtil.getPlayerPos((EntityPlayer)this.mc.player).up();
        return this.isntAir(pos.north()) && this.isntAir(pos.east()) && this.isntAir(pos.south()) && this.isntAir(pos.west());
    }
    
    public boolean isntAir(final BlockPos pos) {
        return !this.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR);
    }
}
