//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.mixins.render;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import dev.zprestige.ruby.events.*;
import dev.zprestige.ruby.*;
import dev.zprestige.ruby.eventbus.event.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ RenderLivingBase.class })
public class MixinRenderLivingBase
{
    @Shadow
    protected ModelBase mainModel;
    
    @Inject(method = { "renderModel" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V") }, cancellable = true)
    private void renderModel(final EntityLivingBase entityLivingBase, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor, final CallbackInfo info) {
        final RenderLivingEntityEvent event = new RenderLivingEntityEvent(this.mainModel, entityLivingBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        Ruby.eventBus.post((Event)event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
}
