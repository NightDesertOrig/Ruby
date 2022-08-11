//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.mixins.entity;

import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import dev.zprestige.ruby.events.*;
import dev.zprestige.ruby.*;
import dev.zprestige.ruby.eventbus.event.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ EntityPlayer.class })
public abstract class MixinEntityPlayer extends EntityLivingBase
{
    public MixinEntityPlayer(final World worldIn) {
        super(worldIn);
    }
    
    @Inject(method = { "applyEntityCollision" }, at = { @At("HEAD") }, cancellable = true)
    protected void applyEntityCollision(final Entity entity, final CallbackInfo info) {
        final EntityPushEvent event = new EntityPushEvent();
        Ruby.eventBus.post((Event)event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
}
