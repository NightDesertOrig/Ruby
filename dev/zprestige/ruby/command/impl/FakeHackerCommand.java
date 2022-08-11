//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.command.impl;

import dev.zprestige.ruby.command.*;
import dev.zprestige.ruby.module.player.*;
import net.minecraft.entity.player.*;

public class FakeHackerCommand extends Command
{
    public FakeHackerCommand() {
        super("fakehacker", "FakeHacker target <name>");
    }
    
    public void listener(final String string) {
        try {
            final String[] split = string.split(" ");
            this.mc.world.playerEntities.stream().filter(entityPlayer -> entityPlayer.getName().equals(split[2])).forEach(entityPlayer -> FakeHacker.target = entityPlayer.getName());
        }
        catch (Exception ignored) {
            this.throwException(this.format);
        }
    }
}
