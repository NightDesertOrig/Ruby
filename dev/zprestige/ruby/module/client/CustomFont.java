//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.client;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import dev.zprestige.ruby.*;

public class CustomFont extends Module
{
    public static CustomFont Instance;
    public final Slider fontSize;
    
    public CustomFont() {
        this.fontSize = this.Menu.Slider("Font Size (%)", 20, 100);
        (CustomFont.Instance = this).setEnabled(true);
    }
    
    @Override
    public void onTick() {
        final int slider = (int)this.fontSize.GetSlider();
        if (Ruby.fontManager.getSize() != slider) {
            Ruby.fontManager.loadFont(slider);
        }
    }
}
