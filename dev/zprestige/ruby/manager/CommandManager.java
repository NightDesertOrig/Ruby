//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.manager;

import java.util.*;
import dev.zprestige.ruby.command.*;
import net.minecraft.client.*;
import dev.zprestige.ruby.*;
import dev.zprestige.ruby.command.impl.*;
import dev.zprestige.ruby.events.*;
import net.minecraft.network.play.client.*;
import java.util.function.*;
import java.util.stream.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.util.text.*;
import dev.zprestige.ruby.eventbus.annotation.*;

public class CommandManager
{
    protected final ArrayList<Command> commands;
    protected final Minecraft mc;
    protected String prefix;
    
    public CommandManager() {
        this.commands = new ArrayList<Command>();
        this.mc = Ruby.mc;
        this.prefix = ".";
        Ruby.eventBus.register((Object)this);
        this.commands.add((Command)new ConfigCommand());
        this.commands.add((Command)new PrefixCommand());
        this.commands.add((Command)new NotifyColorCommand());
        this.commands.add((Command)new FriendCommand());
        this.commands.add((Command)new FakeHackerCommand());
        this.commands.add((Command)new HelpCommand());
    }
    
    public ArrayList<Command> getCommands() {
        return this.commands;
    }
    
    public String getPrefix() {
        return this.prefix;
    }
    
    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }
    
    @RegisterListener
    public void onPacketSend(final PacketEvent.PacketSendEvent event) {
        if (this.mc.world == null || this.mc.player == null || !(event.getPacket() instanceof CPacketChatMessage)) {
            return;
        }
        final CPacketChatMessage packet = (CPacketChatMessage)event.getPacket();
        if (!packet.getMessage().startsWith(this.prefix)) {
            return;
        }
        event.setCancelled(true);
        final String first = packet.getMessage().split(" ")[0];
        final String command2 = first.toLowerCase();
        final ArrayList<Command> commands1 = this.commands.stream().filter(command1 -> command1.getText().equals(command2.replace(this.prefix, ""))).collect((Collector<? super Object, ?, ArrayList<Command>>)Collectors.toCollection((Supplier<R>)ArrayList::new));
        if (!commands1.isEmpty()) {
            commands1.forEach(command1 -> command1.listener(packet.getMessage().toLowerCase()));
            return;
        }
        this.mc.player.sendMessage((ITextComponent)new TextComponentString(ChatFormatting.RED + "No such command found."));
    }
}
