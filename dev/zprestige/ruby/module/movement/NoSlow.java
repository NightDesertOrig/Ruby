//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.movement;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.client.settings.*;
import net.minecraft.client.gui.inventory.*;
import java.util.*;
import org.lwjgl.input.*;
import net.minecraftforge.client.event.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import dev.zprestige.ruby.events.*;
import net.minecraft.client.gui.*;
import dev.zprestige.ruby.eventbus.annotation.*;

public class NoSlow extends Module
{
    public static NoSlow Instance;
    public final Switch items;
    public final Switch guiMove;
    public KeyBinding[] keys;
    
    public NoSlow() {
        this.items = this.Menu.Switch("Items");
        this.guiMove = this.Menu.Switch("Inventory");
        this.keys = new KeyBinding[] { this.mc.gameSettings.keyBindForward, this.mc.gameSettings.keyBindBack, this.mc.gameSettings.keyBindLeft, this.mc.gameSettings.keyBindRight, this.mc.gameSettings.keyBindJump, this.mc.gameSettings.keyBindSprint };
        NoSlow.Instance = this;
    }
    
    public void onTick() {
        if (this.guiMove.GetSwitch()) {
            if (this.mc.currentScreen instanceof GuiOptions || this.mc.currentScreen instanceof GuiVideoSettings || this.mc.currentScreen instanceof GuiScreenOptionsSounds || this.mc.currentScreen instanceof GuiContainer || this.mc.currentScreen instanceof GuiIngameMenu) {
                Arrays.stream(this.keys).forEach(bind -> KeyBinding.setKeyBindState(bind.getKeyCode(), Keyboard.isKeyDown(bind.getKeyCode())));
            }
            else if (this.mc.currentScreen == null) {
                for (final KeyBinding bind2 : this.keys) {
                    if (!Keyboard.isKeyDown(bind2.getKeyCode())) {
                        KeyBinding.setKeyBindState(bind2.getKeyCode(), false);
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onItemEat(final InputUpdateEvent event) {
        if (this.items.GetSwitch() && this.mc.player.isHandActive()) {
            final MovementInput movementInput = event.getMovementInput();
            movementInput.moveStrafe *= 5.0f;
            final MovementInput movementInput2 = event.getMovementInput();
            movementInput2.moveForward *= 5.0f;
        }
    }
    
    @RegisterListener
    public void onKeyEvent(final KeyEvent event) {
        if (this.guiMove.GetSwitch() && !(this.mc.currentScreen instanceof GuiChat)) {
            event.info = event.pressed;
        }
    }
}
