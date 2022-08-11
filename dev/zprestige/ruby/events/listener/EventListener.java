//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.events.listener;

import net.minecraft.client.*;
import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.network.*;
import dev.zprestige.ruby.eventbus.event.*;
import net.minecraftforge.client.event.*;
import dev.zprestige.ruby.module.visual.*;
import dev.zprestige.ruby.module.client.*;
import dev.zprestige.ruby.ui.hudeditor.*;
import java.awt.*;
import net.minecraftforge.fml.common.gameevent.*;
import org.lwjgl.input.*;
import java.util.concurrent.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.init.*;
import net.minecraft.network.play.server.*;
import com.google.common.base.*;
import dev.zprestige.ruby.events.*;
import java.util.*;
import net.minecraft.entity.player.*;
import dev.zprestige.ruby.eventbus.annotation.*;

public class EventListener
{
    protected final Minecraft mc;
    protected final ArrayList<Module> moduleList;
    
    public EventListener() {
        this.mc = Ruby.mc;
        this.moduleList = new ArrayList<Module>(Ruby.moduleManager.moduleList);
        MinecraftForge.EVENT_BUS.register((Object)this);
        Ruby.eventBus.register((Object)this);
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onLivingUpdateEvent(final LivingEvent.LivingUpdateEvent event) {
        if (this.checkNull() && event.getEntity().getEntityWorld().isRemote && event.getEntityLiving().equals((Object)this.mc.player)) {
            this.moduleList.stream().filter(Module::isEnabled).forEach(Module::onTick);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderGameOverlayTextEvent(final RenderGameOverlayEvent.Text event) {
        if (this.checkNull()) {
            this.moduleList.stream().filter(Module::isEnabled).forEach(Module::onFrame2D);
        }
    }
    
    @SubscribeEvent
    public void onClientConnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        Ruby.eventBus.post((Event)new SelfLogoutEvent());
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderWorldLastEvent(final RenderWorldLastEvent event) {
        this.mc.profiler.startSection("ruby");
        if (this.checkNull()) {
            final Render3DEvent render3DEvent = new Render3DEvent(event.getPartialTicks());
            Ruby.eventBus.post((Event)render3DEvent);
            if (Nametags.Instance.isEnabled()) {
                Nametags.Instance.onFrame(event.getPartialTicks());
            }
            this.moduleList.stream().filter(module -> module.isEnabled() && !(module instanceof Nametags)).forEach(module -> module.onFrame(event.getPartialTicks()));
        }
        this.mc.profiler.endSection();
    }
    
    @SubscribeEvent
    public void onRenderOverlay(final RenderGameOverlayEvent event) {
        if (ClickGui.Instance.isEnabled() && !(this.mc.currentScreen instanceof HudEditorScreen)) {
            event.setCanceled(true);
        }
        new Color(3421236);
    }
    
    @SubscribeEvent
    public void onKeyInput(final InputEvent.KeyInputEvent event) {
        if (this.checkNull()) {
            Ruby.moduleManager.moduleList.stream().filter(module -> Keyboard.getEventKeyState() && module.getKeybind().equals(Keyboard.getEventKey())).forEach(module -> {
                if (module.isEnabled()) {
                    module.disableModule();
                }
                else {
                    module.enableModule();
                }
            });
        }
    }
    
    @SubscribeEvent
    public void onDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        Ruby.threadManager.setExecutorService(Executors.newFixedThreadPool(2));
    }
    
    @SubscribeEvent
    public void onDeath(final LivingDeathEvent event) {
        if (event.getEntity().equals((Object)this.mc.player)) {
            Ruby.threadManager.setExecutorService(Executors.newFixedThreadPool(2));
        }
    }
    
    @RegisterListener
    public void onPacketReceive(final PacketEvent.PacketReceiveEvent event) {
        if (!this.checkNull()) {
            return;
        }
        if (event.getPacket() instanceof SPacketSoundEffect && ((SPacketSoundEffect)event.getPacket()).getSound() == SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT) {
            Ruby.eventBus.post((Event)new ChorusEvent(((SPacketSoundEffect)event.getPacket()).getX(), ((SPacketSoundEffect)event.getPacket()).getY(), ((SPacketSoundEffect)event.getPacket()).getZ()));
        }
        if (event.getPacket() instanceof SPacketPlayerListItem) {
            for (final SPacketPlayerListItem.AddPlayerData data : ((SPacketPlayerListItem)event.getPacket()).getEntries()) {
                if (data != null && (!Strings.isNullOrEmpty(data.getProfile().getName()) || data.getProfile().getId() != null)) {
                    final EntityPlayer entity = this.mc.world.getPlayerEntityByUUID(data.getProfile().getId());
                    if (((SPacketPlayerListItem)event.getPacket()).getAction().equals((Object)SPacketPlayerListItem.Action.ADD_PLAYER)) {
                        Ruby.eventBus.post((Event)new LogoutEvent.LoginEvent(entity));
                    }
                    else {
                        if (!((SPacketPlayerListItem)event.getPacket()).getAction().equals((Object)SPacketPlayerListItem.Action.REMOVE_PLAYER) || entity == null) {
                            continue;
                        }
                        Ruby.eventBus.post((Event)new LogoutEvent(entity, entity.getPosition(), System.currentTimeMillis(), entity.getEntityId()));
                    }
                }
            }
        }
    }
    
    protected boolean checkNull() {
        return this.mc.player != null && this.mc.world != null;
    }
}
