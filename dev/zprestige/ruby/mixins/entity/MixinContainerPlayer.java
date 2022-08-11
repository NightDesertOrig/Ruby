//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.mixins.entity;

import org.spongepowered.asm.mixin.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import dev.zprestige.ruby.events.*;
import dev.zprestige.ruby.*;
import dev.zprestige.ruby.eventbus.event.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ ContainerPlayer.class })
public class MixinContainerPlayer
{
    @Inject(method = { "onContainerClosed" }, at = { @At("HEAD") }, cancellable = true)
    protected void onContainerClosed(final EntityPlayer entityPlayer, final CallbackInfo callbackInfo) {
        final CloseInventoryEvent event = new CloseInventoryEvent();
        Ruby.eventBus.post((Event)event);
        if (event.isCancelled()) {
            callbackInfo.cancel();
        }
    }
}
