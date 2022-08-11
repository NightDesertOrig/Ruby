//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.client;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;

public class Friends extends Module
{
    public static Friends Instance;
    public final Switch tabHighlight;
    public final Switch tabPrefix;
    
    public Friends() {
        this.tabHighlight = this.Menu.Switch("Tab Highlight");
        this.tabPrefix = this.Menu.Switch("Tab Prefix");
        Friends.Instance = this;
    }
}
