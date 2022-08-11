//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.movement;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.util.*;

public class Sprint extends Module
{
    public void onTick() {
        if (EntityUtil.isMoving()) {
            this.mc.player.setSprinting(true);
        }
    }
}
