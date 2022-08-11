//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.mixins.world;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.particle.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import dev.zprestige.ruby.events.*;
import dev.zprestige.ruby.*;
import dev.zprestige.ruby.eventbus.event.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ ParticleManager.class })
public class MixinParticleManager
{
    @Inject(method = { "addEffect" }, at = { @At("HEAD") }, cancellable = true)
    public void addEffect(final Particle effect, final CallbackInfo ci) {
        final ParticleEvent event = new ParticleEvent();
        Ruby.eventBus.post((Event)event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
}
