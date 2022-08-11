//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.command.impl;

import dev.zprestige.ruby.command.*;
import com.mojang.realmsclient.gui.*;
import java.util.*;
import dev.zprestige.ruby.*;

public class NotifyColorCommand extends Command
{
    public NotifyColorCommand() {
        super("notifycolor", "NotifyColor <ColorCode>");
    }
    
    public void listener(final String string) {
        try {
            final String[] split = string.split(" ");
            final Object o;
            Arrays.stream(ChatFormatting.values()).filter(chatFormatting -> split[1].charAt(0) == chatFormatting.getChar()).forEach(chatFormatting -> {
                Ruby.chatManager.prefixColor = chatFormatting;
                this.completeMessage("set Notify Color to " + o[1]);
            });
        }
        catch (Exception ignored) {
            this.throwException(this.format);
        }
    }
}
