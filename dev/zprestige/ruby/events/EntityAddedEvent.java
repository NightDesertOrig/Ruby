//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.events;

import dev.zprestige.ruby.eventbus.event.*;
import net.minecraft.entity.*;

public class EntityAddedEvent extends Event
{
    public Entity entity;
    
    public EntityAddedEvent(final Entity entity) {
        this.entity = entity;
    }
}
