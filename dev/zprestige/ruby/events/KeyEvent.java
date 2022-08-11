//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.events;

import dev.zprestige.ruby.eventbus.event.*;

public class KeyEvent extends Event
{
    public boolean info;
    public boolean pressed;
    
    public KeyEvent(final boolean info, final boolean pressed) {
        this.info = info;
        this.pressed = pressed;
    }
}
