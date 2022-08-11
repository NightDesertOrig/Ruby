//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.visual;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;

public class Ambience extends Module
{
    public static Ambience Instance;
    public final ColorBox color;
    
    public Ambience() {
        this.color = this.Menu.Color("Color");
        Ambience.Instance = this;
    }
}
