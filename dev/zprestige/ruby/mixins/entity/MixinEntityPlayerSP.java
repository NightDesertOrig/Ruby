//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.mixins.entity;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.entity.*;
import net.minecraft.world.*;
import com.mojang.authlib.*;
import net.minecraft.entity.*;
import dev.zprestige.ruby.*;
import dev.zprestige.ruby.eventbus.event.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import dev.zprestige.ruby.events.*;

@Mixin(value = { EntityPlayerSP.class }, priority = 999)
public class MixinEntityPlayerSP extends AbstractClientPlayer
{
    protected MotionUpdateEvent motionEvent;
    
    public MixinEntityPlayerSP(final World worldIn, final GameProfile playerProfile) {
        super(worldIn, playerProfile);
    }
    
    public void move(final MoverType type, final double x, final double y, final double z) {
        final MoveEvent event = new MoveEvent(type, x, y, z);
        Ruby.eventBus.post((Event)event);
        super.move(type, event.getMotionX(), event.getMotionY(), event.getMotionZ());
    }
    
    @Inject(method = { "move" }, at = { @At("HEAD") }, cancellable = true)
    protected void move(final MoverType type, final double x, final double y, final double z, final CallbackInfo ci) {
        final MoveEvent event = new MoveEvent(type, x, y, z);
        Ruby.eventBus.post((Event)event);
        if (event.motionX != x || event.motionY != y || event.motionZ != z) {
            super.move(type, event.motionX, event.motionY, event.motionZ);
            ci.cancel();
        }
    }
    
    @Inject(method = { "pushOutOfBlocks" }, at = { @At("HEAD") }, cancellable = true)
    protected void pushOutOfBlocksHook(final double x, final double y, final double z, final CallbackInfoReturnable<Boolean> info) {
        final BlockPushEvent event = new BlockPushEvent();
        Ruby.eventBus.post((Event)event);
        if (event.isCancelled()) {
            info.setReturnValue((Object)false);
        }
    }
    
    @Inject(method = { "onUpdateWalkingPlayer" }, at = { @At("HEAD") }, cancellable = true)
    protected void onUpdateWalkingPlayer_Head(final CallbackInfo callbackInfo) {
        this.motionEvent = new MotionUpdateEvent(1, this.posX, this.getEntityBoundingBox().minY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround);
        Ruby.eventBus.post((Event)this.motionEvent);
        if (this.motionEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "onUpdateWalkingPlayer" }, at = { @At("RETURN") })
    protected void onUpdateWalkingPlayer_Return(final CallbackInfo callbackInfo) {
        final MotionUpdateEvent event = new MotionUpdateEvent(2, this.motionEvent);
        event.setCancelled(this.motionEvent.isCancelled());
        Ruby.eventBus.post((Event)event);
    }
}
