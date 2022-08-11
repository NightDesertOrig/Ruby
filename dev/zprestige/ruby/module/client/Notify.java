//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.client;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.network.play.server.*;
import dev.zprestige.ruby.*;
import dev.zprestige.ruby.eventbus.annotation.*;
import dev.zprestige.ruby.events.*;
import com.mojang.realmsclient.gui.*;

public class Notify extends Module
{
    public static Notify Instance;
    public final Switch modules;
    public final Switch totemPops;
    public final Switch zenovLolCounter;
    public int literalLOLS;
    public int containingLOLS;
    
    public Notify() {
        this.modules = this.Menu.Switch("Modules");
        this.totemPops = this.Menu.Switch("TotemPops");
        this.zenovLolCounter = this.Menu.Switch("Zenov LOL counter");
        this.literalLOLS = 0;
        this.containingLOLS = 0;
        Notify.Instance = this;
    }
    
    @RegisterListener
    public void onPacketReceive(final PacketEvent.PacketReceiveEvent event) {
        if (this.nullCheck() || !this.isEnabled() || !(event.getPacket() instanceof SPacketChat) || !this.zenovLolCounter.GetSwitch()) {
            return;
        }
        final SPacketChat sPacketChat = (SPacketChat)event.getPacket();
        final String chatMessage = sPacketChat.getChatComponent().getUnformattedText();
        boolean print = false;
        if (chatMessage.contains("ZenovJB")) {
            if (chatMessage.contains("LOL")) {
                ++this.containingLOLS;
                print = true;
            }
            if (chatMessage.equals("<ZenovJB> LOL")) {
                ++this.literalLOLS;
                print = true;
            }
            if (print) {
                Ruby.chatManager.sendMessage("ZenovJB LOL counter[Literal: " + this.literalLOLS + ", Containing: " + this.containingLOLS + "]");
            }
        }
    }
    
    @RegisterListener
    public void onModuleEnable(final ModuleToggleEvent.Enable event) {
        if (!this.isEnabled() || this.nullCheck() || !this.modules.GetSwitch()) {
            return;
        }
        Ruby.chatManager.sendRemovableMessage(ChatFormatting.WHITE + "" + ChatFormatting.BOLD + event.getModule().getName() + ChatFormatting.RESET + " has been toggled " + ChatFormatting.GREEN + "On" + ChatFormatting.RESET + ".", 1);
    }
    
    @RegisterListener
    public void onModuleDisable(final ModuleToggleEvent.Disable event) {
        if (!this.isEnabled() || this.nullCheck() || !this.modules.GetSwitch()) {
            return;
        }
        Ruby.chatManager.sendRemovableMessage(ChatFormatting.WHITE + "" + ChatFormatting.BOLD + event.getModule().getName() + ChatFormatting.RESET + " has been toggled " + ChatFormatting.RED + "Off" + ChatFormatting.RESET + ".", 1);
    }
}
