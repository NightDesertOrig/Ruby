//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.mixins.render;

import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.block.model.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import dev.zprestige.ruby.*;
import dev.zprestige.ruby.events.*;
import dev.zprestige.ruby.eventbus.event.*;
import dev.zprestige.ruby.module.visual.*;
import java.awt.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { RenderItem.class }, priority = 1001)
public class MixinRenderItem
{
    @Shadow
    private void renderModel(final IBakedModel model, final int color, final ItemStack stack) {
    }
    
    @Inject(method = { "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;Z)V" }, at = { @At("INVOKE") })
    public void renderItem(final ItemStack stack, final EntityLivingBase entityLivingBaseIn, final ItemCameraTransforms.TransformType transform, final boolean leftHanded, final CallbackInfo ci) {
        if (transform == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND || transform == ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND) {
            if (transform.equals((Object)ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND)) {
                Ruby.eventBus.post((Event)new RenderItemEvent.Offhand(stack, entityLivingBaseIn));
            }
            else {
                Ruby.eventBus.post((Event)new RenderItemEvent.MainHand(stack, entityLivingBaseIn));
            }
        }
    }
    
    @Redirect(method = { "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/IBakedModel;)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderItem;renderModel(Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/item/ItemStack;)V"))
    public void renderModelColor(final RenderItem renderItem, final IBakedModel model, final ItemStack stack) {
        this.renderModel(model, ItemModification.Instance.isEnabled() ? ItemModification.Instance.color.GetColor().getRGB() : new Color(1.0f, 1.0f, 1.0f, 1.0f).getRGB(), stack);
    }
}
