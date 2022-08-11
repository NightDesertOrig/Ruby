//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.combat;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import dev.zprestige.ruby.util.*;

public class AutoWeb extends Module
{
    public final Slider targetRange;
    public final Slider placeRange;
    public final Switch predict;
    public final Slider predictTicks;
    public final Switch packet;
    public final Switch rotate;
    public final Switch onGroundOnly;
    public final Switch onMoveCancel;
    
    public AutoWeb() {
        this.targetRange = this.Menu.Slider("Target Range", 0.1f, 15.0f);
        this.placeRange = this.Menu.Slider("Place Range", 0.1f, 6.0f);
        this.predict = this.Menu.Switch("Predict");
        this.predictTicks = this.Menu.Slider("Predict Ticks", 1, 5);
        this.packet = this.Menu.Switch("Packet");
        this.rotate = this.Menu.Switch("Rotate");
        this.onGroundOnly = this.Menu.Switch("On Ground Only");
        this.onMoveCancel = this.Menu.Switch("On Move Cancel");
    }
    
    @Override
    public void onTick() {
        final EntityPlayer entityPlayer = EntityUtil.getTarget(this.targetRange.GetSlider());
        if (entityPlayer == null) {
            return;
        }
        if (this.predict.GetSwitch()) {
            entityPlayer.setEntityBoundingBox(new AxisAlignedBB(new BlockPos((Vec3i)EntityUtil.getPlayerPos((EntityPlayer)EntityUtil.getPredictedPosition((Entity)entityPlayer, this.predictTicks.GetSlider())))));
        }
        final BlockPos pos = EntityUtil.getPlayerPos(entityPlayer);
        if (this.mc.player.getDistanceSq(pos) > this.placeRange.GetSlider() * this.placeRange.GetSlider() || !this.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR)) {
            return;
        }
        if ((this.onGroundOnly.GetSwitch() && !this.mc.player.onGround) || (this.onMoveCancel.GetSwitch() && EntityUtil.isMoving())) {
            return;
        }
        final int slot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.WEB));
        if (slot == -1) {
            return;
        }
        BlockUtil.placeBlockWithSwitch(pos, EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), slot);
    }
}
