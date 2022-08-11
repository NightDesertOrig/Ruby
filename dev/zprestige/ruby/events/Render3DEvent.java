//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.events;

import dev.zprestige.ruby.eventbus.event.*;

public class Render3DEvent extends Event
{
    public float partialTicks;
    
    public Render3DEvent(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
}
