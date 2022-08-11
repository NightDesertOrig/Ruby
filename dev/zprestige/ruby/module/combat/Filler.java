//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.combat;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import java.awt.*;
import dev.zprestige.ruby.util.*;
import dev.zprestige.ruby.*;
import java.util.*;

public class Filler extends Module
{
    public static Filler Instance;
    public final Parent ranges;
    public final Slider targetRange;
    public final Slider placeRange;
    public final Slider smartRange;
    public final Parent modes;
    public final ComboBox smartMode;
    public final ComboBox block;
    public final Parent misc;
    public final Switch multitask;
    public final Switch doubleHoles;
    public final Switch excludeY;
    public final Switch packet;
    public final Switch rotate;
    public final Parent rendering;
    public final Switch render;
    public final ColorSwitch box;
    public final ColorSwitch outline;
    public final Slider lineWidth;
    public final Slider fadeSpeed;
    public HashMap<BlockPos, Integer> filledBlocks;
    
    public Filler() {
        this.ranges = this.Menu.Parent("Ranges");
        this.targetRange = this.Menu.Slider("Target Range", 0.0f, 6.0f).parent(this.ranges);
        this.placeRange = this.Menu.Slider("Place Range", 0.0f, 6.0f).parent(this.ranges);
        this.smartRange = this.Menu.Slider("Smart Range", 0.0f, 6.0f).parent(this.ranges);
        this.modes = this.Menu.Parent("Modes");
        this.smartMode = this.Menu.ComboBox("Mode", new String[] { "Linear", "Complete" }).parent(this.modes);
        this.block = this.Menu.ComboBox("Block", new String[] { "Obsidian", "EChest", "Webs", "Fallback" }).parent(this.modes);
        this.misc = this.Menu.Parent("Misc");
        this.multitask = this.Menu.Switch("Multitask").parent(this.misc);
        this.doubleHoles = this.Menu.Switch("Double Holes").parent(this.misc);
        this.excludeY = this.Menu.Switch("Exclude Y").parent(this.misc);
        this.packet = this.Menu.Switch("Packet").parent(this.misc);
        this.rotate = this.Menu.Switch("Rotate").parent(this.misc);
        this.rendering = this.Menu.Parent("Rendering");
        this.render = this.Menu.Switch("Render").parent(this.rendering);
        this.box = this.Menu.ColorSwitch("Place Box").parent(this.rendering);
        this.outline = this.Menu.ColorSwitch("Place Outline").parent(this.rendering);
        this.lineWidth = this.Menu.Slider("Place Line Width", 0.0f, 5.0f).parent(this.rendering);
        this.fadeSpeed = this.Menu.Slider("Fade Speed", 100, 1000).parent(this.rendering);
        this.filledBlocks = new HashMap<BlockPos, Integer>();
        Filler.Instance = this;
    }
    
    @Override
    public void onEnable() {
        this.filledBlocks.clear();
    }
    
    @Override
    public void onTick() {
        final EntityPlayer entityPlayer = EntityUtil.getTarget(this.targetRange.GetSlider());
        if (entityPlayer == null) {
            return;
        }
        final BlockPos targetPos = BlockUtil.getClosestHoleToPlayer(entityPlayer, this.smartRange.GetSlider(), this.doubleHoles.GetSwitch());
        if (targetPos == null) {
            return;
        }
        if (this.mc.player.getDistanceSq(targetPos) > this.placeRange.GetSlider() * this.placeRange.GetSlider()) {
            return;
        }
        if (this.smartMode.GetCombo().equals("Linear") && (this.excludeY.GetSwitch() ? entityPlayer.getDistanceSq(new BlockPos((double)targetPos.getX(), entityPlayer.posY, (double)targetPos.getZ())) : entityPlayer.getDistanceSq(targetPos)) > this.smartRange.GetSlider() * this.smartRange.GetSlider()) {
            return;
        }
        if (!this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(targetPos)).isEmpty() || !this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(targetPos).setMaxY(1.0)).isEmpty()) {
            return;
        }
        if (!this.multitask.GetSwitch() && this.mc.player.getHeldItemMainhand().getItem().equals(Items.GOLDEN_APPLE) && this.mc.gameSettings.keyBindUseItem.isKeyDown()) {
            return;
        }
        int slot = -1;
        final String getCombo = this.block.GetCombo();
        switch (getCombo) {
            case "Obsidian": {
                slot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.OBSIDIAN));
                break;
            }
            case "EChest": {
                slot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.ENDER_CHEST));
                break;
            }
            case "Webs": {
                slot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.WEB));
                break;
            }
            case "Fallback": {
                slot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.OBSIDIAN));
                if (slot == -1) {
                    slot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.ENDER_CHEST));
                    break;
                }
                break;
            }
        }
        if (slot != -1) {
            BlockUtil.placeBlockWithSwitch(targetPos, EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), slot);
            this.filledBlocks.put(targetPos, this.box.GetColor().getAlpha());
            return;
        }
        this.disableModule("No blocks found in hotbar, disabling Filler.");
    }
    
    @Override
    public void onFrame(final float partialTicks) {
        if (this.render.GetSwitch()) {
            for (final Map.Entry<BlockPos, Integer> entry : this.filledBlocks.entrySet()) {
                this.filledBlocks.put(entry.getKey(), (int)(entry.getValue() - this.fadeSpeed.GetSlider() / 200.0f));
                if (entry.getValue() <= 0) {
                    this.filledBlocks.remove(entry.getKey());
                    return;
                }
                try {
                    RenderUtil.drawBoxESP(entry.getKey(), new Color(this.box.GetColor().getRed(), this.box.GetColor().getGreen(), this.box.GetColor().getBlue(), entry.getValue()), true, new Color(this.outline.GetColor().getRed(), this.outline.GetColor().getGreen(), this.outline.GetColor().getBlue(), entry.getValue() * 2), this.lineWidth.GetSlider(), this.outline.GetSwitch(), this.box.GetSwitch(), entry.getValue(), true);
                }
                catch (Exception exception) {
                    Ruby.chatManager.sendRemovableMessage("Alpha parameter out of range (Choose a different Alpha)" + exception, 1);
                }
            }
        }
    }
}
