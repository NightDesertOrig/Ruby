//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.misc;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.events.*;
import dev.zprestige.ruby.eventbus.annotation.*;
import org.apache.logging.log4j.*;

public class PacketLogger extends Module
{
    public static final Logger Logger;
    
    @Override
    public void onEnable() {
        PacketLogger.Logger.info("Logger Started.");
    }
    
    @Override
    public void onDisable() {
        PacketLogger.Logger.info("Logger Finished.");
    }
    
    @RegisterListener
    public void onPacketSend(final PacketEvent.PacketSendEvent event) {
        if (!this.isEnabled()) {
            return;
        }
        PacketLogger.Logger.info("[Send] " + event.getPacket());
    }
    
    @RegisterListener
    public void onPacketReceive(final PacketEvent.PacketReceiveEvent event) {
        if (!this.isEnabled()) {
            return;
        }
        PacketLogger.Logger.info("[Receive] " + event.getPacket());
    }
    
    static {
        Logger = LogManager.getLogger("[PacketLogger] ");
    }
}
