//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.ui.hudeditor;

import net.minecraft.client.*;
import java.util.*;
import dev.zprestige.ruby.ui.hudeditor.components.*;
import dev.zprestige.ruby.*;
import net.minecraftforge.common.*;
import dev.zprestige.ruby.ui.hudeditor.components.impl.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class HudManager
{
    protected final Minecraft mc;
    protected final ArrayList<HudComponent> hudComponents;
    
    public HudManager() {
        this.mc = Ruby.mc;
        this.hudComponents = new ArrayList<HudComponent>();
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.hudComponents.add((HudComponent)new Watermark());
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderGameOverlayTextEvent(final RenderGameOverlayEvent.Text event) {
        if (this.safe()) {
            this.hudComponents.stream().filter(HudComponent::isEnabled).forEach(HudComponent::render);
        }
    }
    
    public ArrayList<HudComponent> getHudComponents() {
        return this.hudComponents;
    }
    
    protected boolean safe() {
        return this.mc.world != null && this.mc.player != null;
    }
}
