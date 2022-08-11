//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.manager;

import dev.zprestige.ruby.*;
import net.minecraftforge.event.entity.living.*;
import java.util.function.*;
import net.minecraftforge.fml.common.eventhandler.*;
import dev.zprestige.ruby.events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import dev.zprestige.ruby.eventbus.event.*;
import net.minecraft.entity.*;
import dev.zprestige.ruby.eventbus.annotation.*;
import dev.zprestige.ruby.module.client.*;
import com.mojang.realmsclient.gui.*;
import java.util.*;

public class TotemPopManager
{
    public HashMap<String, Integer> popMap;
    
    public TotemPopManager() {
        this.popMap = new HashMap<String, Integer>();
        Ruby.eventBus.register((Object)this);
    }
    
    @SubscribeEvent
    public void onLivingUpdateEvent(final LivingEvent.LivingUpdateEvent event) {
        if (Ruby.mc.player != null && Ruby.mc.world != null) {
            Ruby.mc.world.playerEntities.stream().filter(entityPlayer -> entityPlayer != null && entityPlayer.getHealth() <= 0.0f).map(PlayerChangeEvent.Death::new).forEach(Ruby.eventBus::post);
        }
    }
    
    @RegisterListener
    public void onPacketReceive(final PacketEvent.PacketReceiveEvent event) {
        if (Ruby.mc.world == null || Ruby.mc.player == null) {
            return;
        }
        if (event.getPacket() instanceof SPacketEntityStatus) {
            final Entity entity = ((SPacketEntityStatus)event.getPacket()).getEntity((World)Ruby.mc.world);
            if (!(entity instanceof EntityPlayer)) {
                return;
            }
            if (((SPacketEntityStatus)event.getPacket()).getOpCode() == 35) {
                Ruby.eventBus.post((Event)new PlayerChangeEvent.TotemPop((EntityPlayer)entity));
            }
        }
    }
    
    @RegisterListener
    public void onDeath(final PlayerChangeEvent.Death event) {
        if (this.popMap.containsKey(event.entityPlayer.getName())) {
            final int pops = this.popMap.get(event.entityPlayer.getName());
            this.popMap.remove(event.entityPlayer.getName());
            int line = 0;
            for (final char character : event.entityPlayer.getName().toCharArray()) {
                line += character;
                line *= 10;
            }
            if (Notify.Instance.isEnabled() && Notify.Instance.totemPops.GetSwitch()) {
                Ruby.chatManager.sendRemovableMessage(ChatFormatting.WHITE + "" + ChatFormatting.BOLD + event.entityPlayer.getName() + ChatFormatting.WHITE + " has died after popping " + ChatFormatting.RED + pops + ChatFormatting.WHITE + ((pops == 1) ? " totem." : " totems."), line);
            }
        }
    }
    
    @RegisterListener
    public void onTotemPop(final PlayerChangeEvent.TotemPop event) {
        int pops = 1;
        if (this.popMap.containsKey(event.entityPlayer.getName())) {
            pops = this.popMap.get(event.entityPlayer.getName());
            this.popMap.put(event.entityPlayer.getName(), ++pops);
        }
        else {
            this.popMap.put(event.entityPlayer.getName(), pops);
        }
        if (this.popMap.containsKey(event.entityPlayer.getName())) {
            int line = 0;
            for (final char character : event.entityPlayer.getName().toCharArray()) {
                line += character;
                line *= 10;
            }
            if (Notify.Instance.isEnabled() && Notify.Instance.totemPops.GetSwitch()) {
                Ruby.chatManager.sendRemovableMessage(ChatFormatting.WHITE + "" + ChatFormatting.BOLD + event.entityPlayer.getName() + ChatFormatting.WHITE + " has popped " + ChatFormatting.RED + pops + ChatFormatting.WHITE + ((pops == 1) ? " totem." : " totems."), line);
            }
        }
    }
    
    public int getPopsByPlayer(final String name) {
        return this.popMap.entrySet().stream().filter(entry -> this.popMap.containsKey(name)).findFirst().map((Function<? super Object, ? extends Integer>)Map.Entry::getValue).orElse(0);
    }
}
