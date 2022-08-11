//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.mixins.render;

import net.minecraft.entity.item.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import dev.zprestige.ruby.module.visual.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import net.minecraft.util.math.*;
import org.lwjgl.opengl.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ RenderEnderCrystal.class })
public abstract class MixinRenderEnderCrystal extends Render<EntityEnderCrystal>
{
    @Final
    @Shadow
    private static ResourceLocation ENDER_CRYSTAL_TEXTURES;
    @Final
    @Shadow
    private ModelBase modelEnderCrystal;
    @Final
    @Shadow
    private ModelBase modelEnderCrystalNoBase;
    
    protected MixinRenderEnderCrystal(final RenderManager renderManager) {
        super(renderManager);
    }
    
    @Shadow
    public abstract void doRender(final EntityEnderCrystal p0, final double p1, final double p2, final double p3, final float p4, final float p5);
    
    @Redirect(method = { "doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    protected void bottomRenderRedirect(final ModelBase modelBase, final Entity entityIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        if (!CrystalChams.Instance.isEnabled()) {
            modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }
    
    @Inject(method = { "doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V" }, at = { @At("RETURN") })
    protected void doRenderCrystal(final EntityEnderCrystal entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final CallbackInfo ci) {
        if (CrystalChams.Instance.isEnabled()) {
            if (CrystalChams.Instance.fill.GetSwitch()) {
                final float f3 = entity.innerRotation + partialTicks;
                GlStateManager.pushMatrix();
                GlStateManager.translate(x, y, z);
                Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(MixinRenderEnderCrystal.ENDER_CRYSTAL_TEXTURES);
                float f4 = MathHelper.sin(f3 * 0.2f) / 2.0f + 0.5f;
                f4 += f4 * f4;
                GL11.glPushAttrib(1048575);
                GL11.glPolygonMode(1032, 6914);
                GL11.glDisable(3553);
                if (CrystalChams.Instance.fillLighting.GetSwitch()) {
                    GL11.glEnable(2896);
                }
                else {
                    GL11.glDisable(2896);
                }
                GL11.glDisable(2929);
                GL11.glEnable(2848);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glColor4f(CrystalChams.Instance.fillColor.GetColor().getRed() / 255.0f, CrystalChams.Instance.fillColor.GetColor().getGreen() / 255.0f, CrystalChams.Instance.fillColor.GetColor().getBlue() / 255.0f, CrystalChams.Instance.fillColor.GetColor().getAlpha() / 255.0f);
                GL11.glScalef(CrystalChams.Instance.scale.GetSlider(), CrystalChams.Instance.scale.GetSlider(), CrystalChams.Instance.scale.GetSlider());
                if (CrystalChams.Instance.fillDepth.GetSwitch()) {
                    GL11.glDepthMask(true);
                    GL11.glEnable(2929);
                }
                if (entity.shouldShowBottom()) {
                    this.modelEnderCrystal.render((Entity)entity, 0.0f, f3 * 3.0f * CrystalChams.Instance.rotationSpeed.GetSlider(), f4 * 0.2f * CrystalChams.Instance.verticalSpeed.GetSlider(), 0.0f, 0.0f, 0.0625f);
                }
                else {
                    this.modelEnderCrystalNoBase.render((Entity)entity, 0.0f, f3 * 3.0f * CrystalChams.Instance.rotationSpeed.GetSlider(), f4 * 0.2f * CrystalChams.Instance.verticalSpeed.GetSlider(), 0.0f, 0.0f, 0.0625f);
                }
                if (CrystalChams.Instance.fillDepth.GetSwitch()) {
                    GL11.glDisable(2929);
                    GL11.glDepthMask(false);
                }
                GL11.glScalef(1.0f / CrystalChams.Instance.scale.GetSlider(), 1.0f / CrystalChams.Instance.scale.GetSlider(), 1.0f / CrystalChams.Instance.scale.GetSlider());
                GL11.glPopAttrib();
                GL11.glPopMatrix();
            }
            if (CrystalChams.Instance.outline.GetSwitch()) {
                final float f3 = entity.innerRotation + partialTicks;
                GlStateManager.pushMatrix();
                GlStateManager.translate(x, y, z);
                Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(MixinRenderEnderCrystal.ENDER_CRYSTAL_TEXTURES);
                float f4 = MathHelper.sin(f3 * 0.2f) / 2.0f + 0.5f;
                f4 += f4 * f4;
                GL11.glPushAttrib(1048575);
                GL11.glPolygonMode(1032, 6913);
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                GL11.glDisable(2929);
                GL11.glEnable(2848);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glLineWidth(CrystalChams.Instance.outlineWidth.GetSlider());
                GL11.glColor4f(CrystalChams.Instance.outlineColor.GetColor().getRed() / 255.0f, CrystalChams.Instance.outlineColor.GetColor().getGreen() / 255.0f, CrystalChams.Instance.outlineColor.GetColor().getBlue() / 255.0f, CrystalChams.Instance.outlineColor.GetColor().getAlpha() / 255.0f);
                GL11.glScalef(CrystalChams.Instance.scale.GetSlider(), CrystalChams.Instance.scale.GetSlider(), CrystalChams.Instance.scale.GetSlider());
                if (CrystalChams.Instance.outlineDepth.GetSwitch()) {
                    GL11.glDepthMask(true);
                    GL11.glEnable(2929);
                }
                if (entity.shouldShowBottom()) {
                    this.modelEnderCrystal.render((Entity)entity, 0.0f, f3 * 3.0f * CrystalChams.Instance.rotationSpeed.GetSlider(), f4 * 0.2f * CrystalChams.Instance.verticalSpeed.GetSlider(), 0.0f, 0.0f, 0.0625f);
                }
                else {
                    this.modelEnderCrystalNoBase.render((Entity)entity, 0.0f, f3 * 3.0f * CrystalChams.Instance.rotationSpeed.getMin(), f4 * 0.2f * CrystalChams.Instance.verticalSpeed.GetSlider(), 0.0f, 0.0f, 0.0625f);
                }
                if (CrystalChams.Instance.outlineDepth.GetSwitch()) {
                    GL11.glDisable(2929);
                    GL11.glDepthMask(false);
                }
                GL11.glScalef(1.0f / CrystalChams.Instance.scale.GetSlider(), 1.0f / CrystalChams.Instance.scale.GetSlider(), 1.0f / CrystalChams.Instance.scale.GetSlider());
                GL11.glPopAttrib();
                GL11.glPopMatrix();
            }
            if (CrystalChams.Instance.glint.GetSwitch()) {
                final float f3 = entity.innerRotation + partialTicks;
                GlStateManager.pushMatrix();
                GlStateManager.translate(x, y, z);
                Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(CrystalChams.ENCHANTED_ITEM_GLINT_RES);
                float f4 = MathHelper.sin(f3 * 0.2f) / 2.0f + 0.5f;
                f4 += f4 * f4;
                GL11.glPushAttrib(1048575);
                GL11.glPolygonMode(1032, 6914);
                GL11.glDisable(2896);
                GL11.glDisable(2929);
                GL11.glEnable(3042);
                GL11.glColor4f(CrystalChams.Instance.glintColor.GetColor().getRed() / 255.0f, CrystalChams.Instance.glintColor.GetColor().getGreen() / 255.0f, CrystalChams.Instance.glintColor.GetColor().getBlue() / 255.0f, CrystalChams.Instance.glintColor.GetColor().getAlpha() / 255.0f);
                GL11.glScalef(CrystalChams.Instance.scale.GetSlider(), CrystalChams.Instance.scale.GetSlider(), CrystalChams.Instance.scale.GetSlider());
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
                for (int i = 0; i < 2; ++i) {
                    GlStateManager.matrixMode(5890);
                    GlStateManager.loadIdentity();
                    final float tScale = 0.33333334f * CrystalChams.Instance.glintScale.GetSlider();
                    GlStateManager.scale(tScale, tScale, tScale);
                    GlStateManager.rotate(30.0f - i * 60.0f, 0.0f, 0.0f, 1.0f);
                    GlStateManager.translate(0.0f, (entity.ticksExisted + partialTicks) * (0.001f + i * 0.003f) * CrystalChams.Instance.glintSpeed.GetSlider(), 0.0f);
                    GlStateManager.matrixMode(5888);
                    if (CrystalChams.Instance.glintDepth.GetSwitch()) {
                        GL11.glDepthMask(true);
                        GL11.glEnable(2929);
                    }
                    if (entity.shouldShowBottom()) {
                        this.modelEnderCrystal.render((Entity)entity, 0.0f, f3 * 3.0f * CrystalChams.Instance.rotationSpeed.GetSlider(), f4 * 0.2f * CrystalChams.Instance.verticalSpeed.GetSlider(), 0.0f, 0.0f, 0.0625f);
                    }
                    else {
                        this.modelEnderCrystalNoBase.render((Entity)entity, 0.0f, f3 * 3.0f * CrystalChams.Instance.rotationSpeed.GetSlider(), f4 * 0.2f * CrystalChams.Instance.verticalSpeed.GetSlider(), 0.0f, 0.0f, 0.0625f);
                    }
                    if (CrystalChams.Instance.glintDepth.GetSwitch()) {
                        GL11.glDisable(2929);
                        GL11.glDepthMask(false);
                    }
                }
                GlStateManager.matrixMode(5890);
                GlStateManager.loadIdentity();
                GlStateManager.matrixMode(5888);
                GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                GL11.glScalef(1.0f / CrystalChams.Instance.scale.GetSlider(), 1.0f / CrystalChams.Instance.scale.GetSlider(), 1.0f / CrystalChams.Instance.scale.GetSlider());
                GL11.glPopAttrib();
                GL11.glPopMatrix();
            }
        }
    }
}
