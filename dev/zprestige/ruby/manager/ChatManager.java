//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.manager;

import net.minecraft.client.*;
import com.mojang.realmsclient.gui.*;
import dev.zprestige.ruby.*;
import net.minecraft.util.text.*;

public class ChatManager
{
    protected final Minecraft mc;
    public ChatFormatting prefixColor;
    public String prefix;
    
    public ChatManager() {
        this.mc = Ruby.mc;
        this.prefixColor = ChatFormatting.RED;
        this.prefix = this.prefixColor + "[Ruby] " + ChatFormatting.GRAY;
    }
    
    public void sendRawMessage(final String message) {
        if (this.mc.player != null) {
            this.mc.player.sendMessage((ITextComponent)new TextComponentString(message));
        }
    }
    
    public void sendMessage(final String message) {
        this.sendRawMessage(this.prefix + message);
    }
    
    public void sendRemovableMessage(final String message, final int id) {
        this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((ITextComponent)new TextComponentString(this.prefix + message), id);
    }
}
