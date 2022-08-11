//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.command.impl;

import dev.zprestige.ruby.command.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.util.text.*;
import dev.zprestige.ruby.*;

public class HelpCommand extends Command
{
    public HelpCommand() {
        super("help", "Help");
    }
    
    public void listener(final String string) {
        try {
            this.mc.player.sendMessage((ITextComponent)new TextComponentString(ChatFormatting.RED + "Ruby Help:"));
            this.mc.player.sendMessage((ITextComponent)new TextComponentString(ChatFormatting.GRAY + "===================="));
            Ruby.commandManager.getCommands().forEach(command -> Ruby.chatManager.sendMessage(ChatFormatting.GRAY + "\u2022 " + ChatFormatting.WHITE + command.getFormat()));
            this.mc.player.sendMessage((ITextComponent)new TextComponentString(ChatFormatting.GRAY + "===================="));
        }
        catch (Exception ignored) {
            this.throwException(this.format);
        }
    }
}
