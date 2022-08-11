//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.mixins.render;

import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import dev.zprestige.ruby.events.*;
import dev.zprestige.ruby.*;
import dev.zprestige.ruby.eventbus.event.*;
import dev.zprestige.ruby.module.visual.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ EntityRenderer.class })
public abstract class MixinEntityRenderer implements IEntityRenderer
{
    @Shadow
    protected abstract void renderHand(final float p0, final int p1);
    
    public void invokeRenderHand(final float partialTicks, final int pass) {
        this.renderHand(partialTicks, pass);
    }
    
    @Shadow
    protected abstract void setupCameraTransform(final float p0, final int p1);
    
    public void invokeSetupCameraTransform(final float partialTicks, final int pass) {
        this.setupCameraTransform(partialTicks, pass);
    }
    
    @Inject(method = { "getMouseOver" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;") }, cancellable = true)
    protected void mouseOver(final float partialTicks, final CallbackInfo ci) {
        final MouseOverEvent event = new MouseOverEvent();
        Ruby.eventBus.post((Event)event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "hurtCameraEffect" }, at = { @At("HEAD") }, cancellable = true)
    protected void hurtCameraEffectHook(final float ticks, final CallbackInfo info) {
        if (!NoRender.Instance.nullCheck() && NoRender.Instance.isEnabled() && NoRender.Instance.hurtCam.GetSwitch()) {
            info.cancel();
        }
    }
    
    @ModifyVariable(method = { "updateLightmap" }, at = @At("STORE"), index = 20)
    protected int red(int red) {
        if (Ambience.Instance.isEnabled()) {
            red = Ambience.Instance.color.GetColor().getRed();
        }
        return red;
    }
    
    @ModifyVariable(method = { "updateLightmap" }, at = @At("STORE"), index = 21)
    protected int green(int green) {
        if (Ambience.Instance.isEnabled()) {
            green = Ambience.Instance.color.GetColor().getGreen();
        }
        return green;
    }
    
    @ModifyVariable(method = { "updateLightmap" }, at = @At("STORE"), index = 22)
    protected int blue(int blue) {
        if (Ambience.Instance.isEnabled()) {
            blue = Ambience.Instance.color.GetColor().getBlue();
        }
        return blue;
    }
}
