//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.events;

import dev.zprestige.ruby.eventbus.event.*;

public class ChorusEvent extends Event
{
    public double x;
    public double y;
    public double z;
    
    public ChorusEvent(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
