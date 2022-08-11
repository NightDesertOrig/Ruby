//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.command;

import net.minecraft.client.*;
import dev.zprestige.ruby.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.util.text.*;

public class Command
{
    protected final Minecraft mc;
    protected String text;
    protected String format;
    
    public Command(final String text, final String format) {
        this.mc = Ruby.mc;
        this.text = text;
        this.format = format;
    }
    
    public String getText() {
        return this.text;
    }
    
    public String getFormat() {
        return this.format;
    }
    
    public void listener(final String string) {
    }
    
    public void completeMessage(final String format) {
        this.mc.player.sendMessage((ITextComponent)new TextComponentString(ChatFormatting.GREEN + "Successfully " + format + "."));
    }
    
    public void throwException(final String format) {
        this.mc.player.sendMessage((ITextComponent)new TextComponentString(ChatFormatting.RED + "Invalid command, try " + format + "."));
    }
}
