//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.movement;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import java.util.*;
import dev.zprestige.ruby.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.client.gui.*;
import dev.zprestige.ruby.module.client.*;
import java.math.*;
import net.minecraft.tileentity.*;

public class StashHunter extends Module
{
    protected final Slider leftRightSeconds;
    protected final Slider forwardsSeconds;
    protected final Slider minimumChests;
    protected final Calendar calendar;
    protected int ticks;
    protected Stage stage;
    protected LeftRightStage leftRightStage;
    
    public StashHunter() {
        this.leftRightSeconds = this.Menu.Slider("Left Right (S)", 1, 60);
        this.forwardsSeconds = this.Menu.Slider("Forwards (S)", 1, 10);
        this.minimumChests = this.Menu.Slider("Minimum Chests", 1, 100);
        this.calendar = Calendar.getInstance();
        this.ticks = 0;
        this.stage = Stage.LeftRight;
        this.leftRightStage = LeftRightStage.Right;
    }
    
    public void onEnable() {
        this.stage = Stage.LeftRight;
        this.leftRightStage = LeftRightStage.Right;
    }
    
    public void onTick() {
        ++this.ticks;
        final long chests = this.mc.world.loadedTileEntityList.stream().filter(e -> e instanceof TileEntityChest).count();
        if (chests >= this.minimumChests.GetSlider()) {
            Ruby.chatManager.sendMessage("[StashHunter] " + ChatFormatting.WHITE + "[" + this.calendar.getTime() + "] " + chests + " Chests found when flying at X: " + this.roundNumber(this.mc.player.posX, 1) + " | Z: " + this.roundNumber(this.mc.player.posZ, 1));
        }
        switch (this.stage) {
            case LeftRight: {
                if (this.ticks / 20.0f >= this.leftRightSeconds.GetSlider()) {
                    this.stage = Stage.Forwards;
                    switch (this.leftRightStage) {
                        case Right: {
                            this.leftRightStage = LeftRightStage.Left;
                            break;
                        }
                        case Left: {
                            this.leftRightStage = LeftRightStage.Right;
                            break;
                        }
                    }
                    this.ticks = 0;
                }
                switch (this.leftRightStage) {
                    case Right: {
                        this.mc.gameSettings.keyBindForward.pressed = false;
                        this.mc.gameSettings.keyBindLeft.pressed = false;
                        this.mc.gameSettings.keyBindRight.pressed = true;
                        break;
                    }
                    case Left: {
                        this.mc.gameSettings.keyBindForward.pressed = false;
                        this.mc.gameSettings.keyBindRight.pressed = false;
                        this.mc.gameSettings.keyBindLeft.pressed = true;
                        break;
                    }
                }
                break;
            }
            case Forwards: {
                if (this.ticks / 20.0f >= this.forwardsSeconds.GetSlider()) {
                    this.stage = Stage.LeftRight;
                    this.ticks = 0;
                }
                this.mc.gameSettings.keyBindForward.pressed = true;
                break;
            }
        }
    }
    
    public void onFrame2D() {
        final String string = "Stage: " + this.stage.toString() + (this.stage.equals(Stage.LeftRight) ? (" " + this.leftRightStage.toString()) : "");
        Ruby.fontManager.drawStringWithShadow(string, new ScaledResolution(this.mc).getScaledWidth() / 2.0f - Ruby.fontManager.getStringWidth(string) / 2.0f, Hud.Instance.welcomer.GetSwitch() ? 10.0f : 0.0f, -1);
    }
    
    public float roundNumber(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal decimal = BigDecimal.valueOf(value);
        decimal = decimal.setScale(places, RoundingMode.FLOOR);
        return decimal.floatValue();
    }
    
    public enum LeftRightStage
    {
        Left, 
        Right;
    }
    
    public enum Stage
    {
        LeftRight, 
        Forwards;
    }
}
