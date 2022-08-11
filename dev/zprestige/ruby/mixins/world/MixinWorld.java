//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.mixins.world;

import org.spongepowered.asm.mixin.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import dev.zprestige.ruby.*;
import dev.zprestige.ruby.events.*;
import dev.zprestige.ruby.eventbus.event.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ World.class })
public class MixinWorld
{
    @Inject(method = { "onEntityAdded" }, at = { @At("HEAD") })
    public void onEntityAdded(final Entity entity, final CallbackInfo ci) {
        Ruby.eventBus.post((Event)new EntityAddedEvent(entity));
    }
}
