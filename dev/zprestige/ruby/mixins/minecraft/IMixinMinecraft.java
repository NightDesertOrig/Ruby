//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.mixins.minecraft;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ Minecraft.class })
public interface IMixinMinecraft
{
    @Accessor("session")
    void setSession(final Session p0);
}
