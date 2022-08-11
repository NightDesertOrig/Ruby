//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.mixins.entity;

import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import dev.zprestige.ruby.events.*;
import dev.zprestige.ruby.*;
import dev.zprestige.ruby.eventbus.event.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ Entity.class })
public class MixinEntity
{
    @Inject(method = { "turn" }, at = { @At("HEAD") }, cancellable = true)
    protected void onTurn(final float yaw, final float pitch, final CallbackInfo ci) {
        final TurnEvent event = new TurnEvent(yaw, pitch);
        Ruby.eventBus.post((Event)event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
}
