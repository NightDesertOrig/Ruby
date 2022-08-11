//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.combat;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import dev.zprestige.ruby.util.*;

public class Trap extends Module
{
    public static Trap Instance;
    public final Parent placing;
    public final ComboBox placeMode;
    public final Slider placeDelay;
    public final Parent ranges;
    public final Slider targetRange;
    public final Slider placeRange;
    public final Parent misc;
    public final Switch inLiquids;
    public final Switch extraTop;
    public final Switch packet;
    public final Switch rotate;
    public final Parent rendering;
    public final Switch render;
    public final ColorSwitch box;
    public final ColorSwitch outline;
    public final Slider outlineWidth;
    public BlockPos placePos;
    public Timer timer;
    public ArrayList<BlockPos> firstLayerPosses;
    public BlockPos postFirstLayerPos;
    
    public Trap() {
        this.placing = this.Menu.Parent("Placing");
        this.placeMode = this.Menu.ComboBox("Place Mode", new String[] { "Linear", "Gradually" }).parent(this.placing);
        this.placeDelay = this.Menu.Slider("Place Delay", 0, 500).parent(this.placing);
        this.ranges = this.Menu.Parent("Ranges");
        this.targetRange = this.Menu.Slider("Target Range", 0.1f, 15.0f).parent(this.ranges);
        this.placeRange = this.Menu.Slider("Place Range", 0.1f, 6.0f).parent(this.ranges);
        this.misc = this.Menu.Parent("Misc");
        this.inLiquids = this.Menu.Switch("In Liquids").parent(this.misc);
        this.extraTop = this.Menu.Switch("ExtraTop").parent(this.misc);
        this.packet = this.Menu.Switch("Packet").parent(this.misc);
        this.rotate = this.Menu.Switch("Rotate").parent(this.misc);
        this.rendering = this.Menu.Parent("Rendering");
        this.render = this.Menu.Switch("Render").parent(this.rendering);
        this.box = this.Menu.ColorSwitch("Box").parent(this.rendering);
        this.outline = this.Menu.ColorSwitch("Outline").parent(this.rendering);
        this.outlineWidth = this.Menu.Slider("Outline Width", 0.10000000149011612, 5.0).parent(this.rendering);
        this.placePos = null;
        this.timer = new Timer();
        this.firstLayerPosses = new ArrayList<BlockPos>();
        this.postFirstLayerPos = null;
        Trap.Instance = this;
    }
    
    @Override
    public void onTick() {
        final EntityPlayer entityPlayer = EntityUtil.getTarget(this.targetRange.GetSlider());
        if (entityPlayer == null || !BlockUtil.isPlayerSafe(entityPlayer)) {
            this.disableModule();
            return;
        }
        final int slot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.OBSIDIAN));
        if (slot == -1) {
            this.disableModule("No Obsidian found in hotbar, disabling Trap");
            return;
        }
        final BlockPos entityPlayerPos = EntityUtil.getPlayerPos(entityPlayer);
        final String getCombo = this.placeMode.GetCombo();
        switch (getCombo) {
            case "Gradually": {
                this.setPlacePos(entityPlayerPos);
                if (this.placePos != null && this.mc.player.getDistanceSq(this.placePos) < this.placeRange.GetSlider() * this.placeRange.GetSlider() && this.timer.getTime((long)this.placeDelay.GetSlider())) {
                    BlockUtil.placeBlockWithSwitch(this.placePos, EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), slot, this.timer);
                    break;
                }
                break;
            }
            case "Linear": {
                this.setFirstLayer(entityPlayerPos);
                if (!this.firstLayerPosses.isEmpty()) {
                    this.firstLayerPosses.stream().filter(pos -> this.mc.player.getDistanceSq(pos) < this.placeRange.GetSlider() * this.placeRange.GetSlider()).forEach(pos -> BlockUtil.placeBlockWithSwitch(pos, EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), slot));
                    break;
                }
                if (this.canPlace(entityPlayerPos.up().up().north()) && this.canPlace(entityPlayerPos.up().up().east()) && this.canPlace(entityPlayerPos.up().up().south()) && this.canPlace(entityPlayerPos.up().up().west())) {
                    if (this.mc.player.getDistanceSq(entityPlayerPos.up().up().north()) < this.placeRange.GetSlider() * this.placeRange.GetSlider()) {
                        BlockUtil.placeBlockWithSwitch(entityPlayerPos.up().up().north(), EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), slot);
                        this.postFirstLayerPos = entityPlayerPos.up().up().north();
                        break;
                    }
                    break;
                }
                else if (this.canPlace(entityPlayerPos.up().up())) {
                    if (this.mc.player.getDistanceSq(entityPlayerPos.up().up()) < this.placeRange.GetSlider() * this.placeRange.GetSlider()) {
                        BlockUtil.placeBlockWithSwitch(entityPlayerPos.up().up(), EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), slot);
                        this.postFirstLayerPos = entityPlayerPos.up().up();
                        break;
                    }
                    break;
                }
                else {
                    if (!this.extraTop.GetSwitch() || !this.canPlace(entityPlayerPos.up().up().up())) {
                        this.postFirstLayerPos = null;
                        break;
                    }
                    if (this.mc.player.getDistanceSq(entityPlayerPos.up().up().up()) < this.placeRange.GetSlider() * this.placeRange.GetSlider()) {
                        BlockUtil.placeBlockWithSwitch(entityPlayerPos.up().up().up(), EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), slot);
                        this.postFirstLayerPos = entityPlayerPos.up().up().up();
                        break;
                    }
                    break;
                }
                break;
            }
        }
    }
    
    public void setFirstLayer(final BlockPos pos) {
        this.firstLayerPosses.clear();
        if (this.canPlace(pos.up().north())) {
            this.firstLayerPosses.add(pos.up().north());
        }
        if (this.canPlace(pos.up().east())) {
            this.firstLayerPosses.add(pos.up().east());
        }
        if (this.canPlace(pos.up().south())) {
            this.firstLayerPosses.add(pos.up().south());
        }
        if (this.canPlace(pos.up().west())) {
            this.firstLayerPosses.add(pos.up().west());
        }
    }
    
    public void setPlacePos(final BlockPos pos) {
        if (this.canPlace(pos.up().north())) {
            this.placePos = pos.up().north();
        }
        else if (this.canPlace(pos.up().east())) {
            this.placePos = pos.up().east();
        }
        else if (this.canPlace(pos.up().south())) {
            this.placePos = pos.up().south();
        }
        else if (this.canPlace(pos.up().west())) {
            this.placePos = pos.up().west();
        }
        else if (this.canPlace(pos.up().up().north()) && this.canPlace(pos.up().up().east()) && this.canPlace(pos.up().up().south()) && this.canPlace(pos.up().up().west())) {
            this.placePos = pos.up().up().north();
        }
        else if (this.canPlace(pos.up().up())) {
            this.placePos = pos.up().up();
        }
        else if (this.extraTop.GetSwitch() && this.canPlace(pos.up().up().up())) {
            this.placePos = pos.up().up().up();
        }
        else {
            this.placePos = null;
        }
    }
    
    public boolean canPlace(final BlockPos pos) {
        return this.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) || (this.inLiquids.GetSwitch() && (this.mc.world.getBlockState(pos).getBlock().equals(Blocks.WATER) || this.mc.world.getBlockState(pos).getBlock().equals(Blocks.FLOWING_WATER) || this.mc.world.getBlockState(pos).getBlock().equals(Blocks.LAVA) || this.mc.world.getBlockState(pos).getBlock().equals(Blocks.FLOWING_LAVA)));
    }
    
    @Override
    public void onFrame(final float partialTicks) {
        final String getCombo = this.placeMode.GetCombo();
        switch (getCombo) {
            case "Gradually": {
                if (this.placePos == null || !this.render.GetSwitch()) {
                    return;
                }
                RenderUtil.drawBoxESP(this.placePos, this.box.GetColor(), true, this.outline.GetColor(), this.outlineWidth.GetSlider(), this.outline.GetSwitch(), this.box.GetSwitch(), this.box.GetColor().getAlpha(), true);
                break;
            }
            case "Linear": {
                if (!this.firstLayerPosses.isEmpty()) {
                    this.firstLayerPosses.forEach(pos -> RenderUtil.drawBoxESP(pos, this.box.GetColor(), true, this.outline.GetColor(), this.outlineWidth.GetSlider(), this.outline.GetSwitch(), this.box.GetSwitch(), this.box.GetColor().getAlpha(), true));
                    break;
                }
                if (this.postFirstLayerPos != null) {
                    RenderUtil.drawBoxESP(this.postFirstLayerPos, this.box.GetColor(), true, this.outline.GetColor(), this.outlineWidth.GetSlider(), this.outline.GetSwitch(), this.box.GetSwitch(), this.box.GetColor().getAlpha(), true);
                    break;
                }
                break;
            }
        }
    }
}
