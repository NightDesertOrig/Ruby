//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.command.impl;

import dev.zprestige.ruby.command.*;
import dev.zprestige.ruby.*;

public class PrefixCommand extends Command
{
    public PrefixCommand() {
        super("prefix", "Prefix <Prefix>");
    }
    
    public void listener(final String string) {
        try {
            final String[] split = string.split(" ");
            Ruby.commandManager.setPrefix(split[1]);
            this.completeMessage("set prefix to " + split[1]);
        }
        catch (Exception ignored) {
            this.throwException(this.format);
        }
    }
}
