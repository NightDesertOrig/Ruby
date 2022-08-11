//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.player;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.block.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;

public class SelfFiller extends Module
{
    public final Slider force;
    public final Switch rotate;
    public final ComboBox block;
    public int slot;
    public BlockPos startPos;
    
    public SelfFiller() {
        this.force = this.Menu.Slider("Force", -5.0, 10.0);
        this.rotate = this.Menu.Switch("Rotate");
        this.block = this.Menu.ComboBox("Block", new String[] { "Obsidian", "EnderChests", "Fallback", "WitherSkulls", "Anvil" });
        this.slot = -1;
    }
    
    public void onEnable() {
        this.mc.player.motionX = 0.0;
        this.mc.player.motionZ = 0.0;
        this.startPos = BlockUtil.getPlayerPos();
    }
    
    public void onTick() {
        final String getCombo = this.block.GetCombo();
        switch (getCombo) {
            case "Obsidian": {
                this.slot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.OBSIDIAN));
                break;
            }
            case "EnderChests": {
                this.slot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.ENDER_CHEST));
                break;
            }
            case "Fallback": {
                final int slot2 = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.OBSIDIAN));
                if (slot2 != -1) {
                    this.slot = slot2;
                    break;
                }
                this.slot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.ENDER_CHEST));
                break;
            }
            case "WitherSkulls": {
                this.slot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock((Block)Blocks.SKULL));
                break;
            }
            case "Anvil": {
                this.slot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.ANVIL));
                break;
            }
        }
        if (this.slot == -1) {
            this.disableModule("No blocks found, disabling SelfFiller.");
            return;
        }
        this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 0.41999998688698, this.mc.player.posZ, true));
        this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 0.7531999805211997, this.mc.player.posZ, true));
        this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 1.00133597911214, this.mc.player.posZ, true));
        this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 1.16610926093821, this.mc.player.posZ, true));
        BlockUtil.placeBlockWithSwitch(this.startPos, EnumHand.MAIN_HAND, this.rotate.GetSwitch(), true, this.slot);
        this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + this.force.GetSlider(), this.mc.player.posZ, false));
        this.disableModule();
    }
}
