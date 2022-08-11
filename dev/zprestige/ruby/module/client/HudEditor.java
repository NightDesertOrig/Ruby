//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.client;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import dev.zprestige.ruby.ui.hudeditor.*;
import net.minecraft.client.gui.*;

public class HudEditor extends Module
{
    public static HudEditor Instance;
    public ColorBox color;
    public Switch alphaStep;
    public Slider index;
    
    public HudEditor() {
        this.color = this.Menu.Color("Color");
        this.alphaStep = this.Menu.Switch("Alpha Step");
        this.index = this.Menu.Slider("Index", 0, 100);
        HudEditor.Instance = this;
    }
    
    @Override
    public void onEnable() {
        this.mc.displayGuiScreen((GuiScreen)new HudEditorScreen());
    }
    
    @Override
    public void onTick() {
        if (!(this.mc.currentScreen instanceof HudEditorScreen)) {
            this.disableModule();
        }
    }
}
