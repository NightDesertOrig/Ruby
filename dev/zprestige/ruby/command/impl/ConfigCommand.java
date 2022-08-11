//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.command.impl;

import dev.zprestige.ruby.command.*;
import dev.zprestige.ruby.*;
import java.io.*;

public class ConfigCommand extends Command
{
    public ConfigCommand() {
        super("config", "Config <Save/Load/Delete> <Folder>");
    }
    
    public void listener(final String string) {
        try {
            final String[] split = string.split(" ");
            if (split[1].equals("save")) {
                Ruby.configManager.save(split[2]);
                this.completeMessage("saved config " + split[2]);
            }
            if (split[1].equals("load")) {
                Ruby.configManager.load(split[2]);
                this.completeMessage("loaded config " + split[2]);
            }
            if (split[1].equals("delete")) {
                final File path = new File(this.mc.gameDir + File.separator + "ClientRewrite" + File.separator + "Configs" + split[2]);
                if (path.exists()) {
                    path.delete();
                }
            }
        }
        catch (Exception ignored) {
            this.throwException(this.format);
        }
    }
}
