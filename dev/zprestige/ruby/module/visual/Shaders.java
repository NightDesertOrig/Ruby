//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.visual;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import dev.zprestige.ruby.events.*;
import dev.zprestige.ruby.eventbus.annotation.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import dev.zprestige.ruby.util.shader.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import dev.zprestige.ruby.mixins.render.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.entity.*;

public class Shaders extends Module
{
    public final Parent targets;
    public final Switch players;
    public final Switch crystals;
    public final Switch experienceBottles;
    public final Switch items;
    public final Parent shader;
    public final ColorBox color;
    public final Slider radius;
    public final Slider opacity;
    public boolean forceRender;
    
    public Shaders() {
        this.targets = this.Menu.Parent("Targets");
        this.players = this.Menu.Switch("Players").parent(this.targets);
        this.crystals = this.Menu.Switch("Crystals").parent(this.targets);
        this.experienceBottles = this.Menu.Switch("Experience Bottles").parent(this.targets);
        this.items = this.Menu.Switch("Items").parent(this.targets);
        this.shader = this.Menu.Parent("Shader");
        this.color = this.Menu.Color("Color").parent(this.shader);
        this.radius = this.Menu.Slider("Radius", 0.1f, 10.0f).parent(this.shader);
        this.opacity = this.Menu.Slider("Opacity", 0.0f, 255.0f).parent(this.shader);
        this.forceRender = false;
    }
    
    @RegisterListener
    public void renderItemInFirstPerson(final RenderItemInFirstPersonEvent event) {
        if (this.nullCheck() || !this.isEnabled() || !event.isPre || this.forceRender || !this.items.GetSwitch()) {
            return;
        }
        event.setCancelled(true);
    }
    
    public void onFrame(final float partialTicks) {
        if (this.nullCheck()) {
            return;
        }
        if ((Display.isActive() || Display.isVisible()) && this.mc.gameSettings.thirdPersonView == 0 && !(this.mc.currentScreen instanceof GuiDownloadTerrain)) {
            GlStateManager.pushMatrix();
            GlStateManager.pushAttrib();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.enableAlpha();
            final ItemShader shader = ItemShader.Instance;
            shader.mix = this.opacity.GetSlider() / 255.0f;
            shader.alpha = this.color.GetColor().getAlpha() / 255.0f;
            shader.startDraw(this.mc.getRenderPartialTicks());
            this.forceRender = true;
            final Vec3d vector;
            final Render render;
            this.mc.world.loadedEntityList.stream().filter(entity -> entity != null && (entity != this.mc.player || entity != this.mc.getRenderViewEntity()) && this.mc.getRenderManager().getEntityRenderObject((Entity)entity) != null && ((entity instanceof EntityPlayer && this.players.GetSwitch() && !entity.isSpectator()) || (entity instanceof EntityEnderCrystal && this.crystals.GetSwitch()) || (entity instanceof EntityExpBottle && this.experienceBottles.GetSwitch()))).forEach(entity -> {
                vector = this.getInterpolatedRenderPos(entity, partialTicks);
                if (entity instanceof EntityPlayer) {
                    ((EntityPlayer)entity).hurtTime = 0;
                }
                render = this.mc.getRenderManager().getEntityRenderObject(entity);
                if (render != null) {
                    try {
                        render.doRender(entity, vector.x, vector.y, vector.z, entity.rotationYaw, partialTicks);
                    }
                    catch (Exception ex) {}
                }
                return;
            });
            if (this.items.GetSwitch()) {
                ((IEntityRenderer)this.mc.entityRenderer).invokeRenderHand(this.mc.getRenderPartialTicks(), 2);
            }
            this.forceRender = false;
            shader.stopDraw(this.color.GetColor(), this.radius.GetSlider(), 1.0f);
            GlStateManager.disableBlend();
            GlStateManager.disableAlpha();
            GlStateManager.disableDepth();
            GlStateManager.popAttrib();
            GlStateManager.popMatrix();
        }
    }
    
    public Vec3d getInterpolatedRenderPos(final Entity entity, final float ticks) {
        return this.interpolateEntity(entity, ticks).subtract(this.mc.getRenderManager().renderPosX, this.mc.getRenderManager().renderPosY, this.mc.getRenderManager().renderPosZ);
    }
    
    public Vec3d interpolateEntity(final Entity entity, final float time) {
        return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * time, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * time, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * time);
    }
}
