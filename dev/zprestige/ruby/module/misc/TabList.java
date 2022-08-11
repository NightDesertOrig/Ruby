//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.misc;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;

public class TabList extends Module
{
    public static TabList Instance;
    public final ComboBox order;
    public final Slider maxSize;
    public final Switch showPing;
    
    public TabList() {
        this.order = this.Menu.ComboBox("Order", new String[] { "Ping", "Alphabet", "Length" });
        this.maxSize = this.Menu.Slider("Max Size", 1, 1000);
        this.showPing = this.Menu.Switch("Show Ping");
        TabList.Instance = this;
    }
}
