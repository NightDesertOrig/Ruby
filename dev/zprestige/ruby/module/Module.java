//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module;

import dev.zprestige.ruby.settings.*;
import net.minecraft.client.*;
import dev.zprestige.ruby.settings.impl.*;
import java.util.*;
import dev.zprestige.ruby.*;
import dev.zprestige.ruby.events.*;
import dev.zprestige.ruby.eventbus.event.*;
import net.minecraftforge.common.*;

public class Module
{
    public final Menu Menu;
    protected final List<Setting> newSettings;
    protected final Minecraft mc;
    protected final Key keybind;
    protected final Switch enabled;
    public boolean drawn;
    public int scrollY;
    protected String name;
    protected Category category;
    
    public Module() {
        this.Menu = new Menu(this);
        this.newSettings = new ArrayList<Setting>();
        this.mc = Minecraft.getMinecraft();
        this.keybind = this.Menu.Key("Keybind", 0);
        this.enabled = this.Menu.Switch("Enabled");
        this.drawn = true;
    }
    
    public void onEnable() {
    }
    
    public void onDisable() {
    }
    
    public void onTick() {
    }
    
    public void onFrame2D() {
    }
    
    public void onFrame(final float partialTicks) {
    }
    
    public void enableModule() {
        this.setEnabled(true);
        this.onEnable();
        Ruby.eventBus.post((Event)new ModuleToggleEvent.Enable(this));
        MinecraftForge.EVENT_BUS.register((Object)this);
        Ruby.eventBus.register((Object)this);
    }
    
    public void disableModule() {
        this.setEnabled(false);
        this.onDisable();
        Ruby.eventBus.post((Event)new ModuleToggleEvent.Disable(this));
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        Ruby.eventBus.unregister((Object)this);
    }
    
    public void disableModule(final String message) {
        this.disableModule();
        Ruby.chatManager.sendMessage(message);
    }
    
    public boolean isEnabled() {
        return this.enabled.GetSwitch();
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled.setValue(enabled);
    }
    
    public String getName() {
        return this.name;
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public Integer getKeybind() {
        return this.keybind.GetKey();
    }
    
    public void setKeybind(final Integer keybind) {
        this.keybind.setValue(keybind);
    }
    
    public boolean nullCheck() {
        return this.mc.world == null || this.mc.player == null;
    }
    
    public List<Setting> getSettings() {
        return this.newSettings;
    }
    
    public Module withSuper(final String name, final Category category) {
        this.name = name;
        this.category = category;
        return this;
    }
}
