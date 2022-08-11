//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.mixins.gui;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.*;
import dev.zprestige.ruby.ui.altening.*;

@Mixin({ GuiMainMenu.class })
public class MixinGuiMainMenu extends GuiScreen
{
    @Inject(method = { "initGui" }, at = { @At("RETURN") })
    protected void initGui(final CallbackInfo callbackInfo) {
        this.buttonList.add(new GuiButton(36, this.width / 2 - 100, this.height / 4 + 48 + 118, "Altening Manager"));
    }
    
    @Inject(method = { "actionPerformed" }, at = { @At("RETURN") })
    protected void actionPerformed(final GuiButton button, final CallbackInfo callbackInfo) {
        if (button.id == 36) {
            this.mc.displayGuiScreen((GuiScreen)new AlteningGuiScreen(this));
        }
    }
}
