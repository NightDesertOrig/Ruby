//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.client;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import dev.zprestige.ruby.ui.click.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import dev.zprestige.ruby.*;
import dev.zprestige.ruby.ui.hudeditor.*;

public class ClickGui extends Module
{
    public static ClickGui Instance;
    public final ColorBox color;
    public final ColorBox backgroundColor;
    public final Switch icons;
    public final Slider scrollSpeed;
    public final Slider animationSpeed;
    public MainScreen mainScreen;
    
    public ClickGui() {
        this.color = this.Menu.Color("Color");
        this.backgroundColor = this.Menu.Color("Background Color").defaultValue(new Color(0, 0, 0, 50));
        this.icons = this.Menu.Switch("Icons");
        this.scrollSpeed = this.Menu.Slider("Scroll Speed", 1, 20);
        this.animationSpeed = this.Menu.Slider("Animation Speed", 1, 20);
        this.mainScreen = null;
        (ClickGui.Instance = this).setKeybind(24);
    }
    
    @Override
    public void onEnable() {
        final MainScreen mainScreen = new MainScreen();
        this.mc.displayGuiScreen((GuiScreen)mainScreen);
        this.mainScreen = mainScreen;
    }
    
    @Override
    public void onDisable() {
        this.mc.displayGuiScreen((GuiScreen)null);
        this.mainScreen = null;
        Ruby.configManager.saveSocials();
    }
    
    @Override
    public void onTick() {
        if (!(this.mc.currentScreen instanceof MainScreen) && !(this.mc.currentScreen instanceof HudEditorScreen)) {
            this.disableModule();
        }
    }
}
