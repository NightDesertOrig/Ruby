//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.misc;

import dev.zprestige.ruby.module.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import org.lwjgl.input.*;
import dev.zprestige.ruby.ui.middleclick.*;
import net.minecraft.client.gui.*;
import dev.zprestige.ruby.events.*;
import net.minecraft.network.play.server.*;
import java.util.function.*;
import dev.zprestige.ruby.eventbus.annotation.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class MiddleClick extends Module
{
    public static MiddleClick Instance;
    public ArrayList<EntityPlayer> blockedList;
    
    public MiddleClick() {
        this.blockedList = new ArrayList<EntityPlayer>();
        MiddleClick.Instance = this;
    }
    
    @Override
    public void onTick() {
        final RayTraceResult result = this.mc.objectMouseOver;
        if (result != null && result.typeOfHit == RayTraceResult.Type.ENTITY && result.entityHit instanceof EntityPlayer && Mouse.isButtonDown(2)) {
            this.mc.displayGuiScreen((GuiScreen)new MiddleClickInterface(new ScaledResolution(this.mc), result.entityHit));
        }
        if (this.mc.currentScreen instanceof MiddleClickInterface && !Mouse.isButtonDown(2)) {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
    }
    
    @RegisterListener
    public void onPacketReceive(final PacketEvent.PacketReceiveEvent event) {
        if (this.nullCheck() || !this.isEnabled() || !(event.getPacket() instanceof SPacketChat)) {
            return;
        }
        this.blockedList.stream().filter(player -> ((SPacketChat)event.getPacket()).getChatComponent().getUnformattedText().contains(player.getName())).map(player -> true).forEach((Consumer<? super Object>)event::setCancelled);
    }
    
    @SubscribeEvent
    public void onRenderOverlay(final RenderGameOverlayEvent event) {
        if (this.isEnabled() && this.mc.currentScreen instanceof MiddleClickInterface && event.getType().equals((Object)RenderGameOverlayEvent.ElementType.ALL)) {
            event.setCanceled(true);
        }
    }
}
