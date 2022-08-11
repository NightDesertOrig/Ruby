//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.visual;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.renderer.culling.*;
import java.awt.*;
import net.minecraft.client.renderer.*;
import dev.zprestige.ruby.*;
import net.minecraft.client.shader.*;
import org.lwjgl.opengl.*;
import java.util.*;
import java.util.function.*;
import net.minecraft.entity.item.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.util.math.*;
import dev.zprestige.ruby.events.*;
import dev.zprestige.ruby.eventbus.annotation.*;

public class ESP extends Module
{
    public static ESP Instance;
    public final Parent items;
    public final Switch itemNames;
    public final Parent player;
    public final Switch players;
    public final Switch playerMoveCancel;
    public final ColorBox playerColor;
    public final Slider playerLineWidth;
    public ArrayList<Entity> entityList;
    public List<EntityPlayer> playerList;
    public ArrayList<BlockPos> obsidianHoles;
    public ArrayList<BlockPos> bedrockHoles;
    public ICamera camera;
    public Thread thread3;
    
    public ESP() {
        this.items = this.Menu.Parent("Items");
        this.itemNames = this.Menu.Switch("Item Names").parent(this.items);
        this.player = this.Menu.Parent("Player");
        this.players = this.Menu.Switch("Players").parent(this.player);
        this.playerMoveCancel = this.Menu.Switch("Move Cancel").parent(this.player);
        this.playerColor = this.Menu.Color("Player Color").parent(this.player);
        this.playerLineWidth = this.Menu.Slider("Player Line Width", 0.1f, 5.0f).parent(this.player);
        this.entityList = new ArrayList<Entity>();
        this.playerList = new ArrayList<EntityPlayer>();
        this.obsidianHoles = new ArrayList<BlockPos>();
        this.bedrockHoles = new ArrayList<BlockPos>();
        this.camera = (ICamera)new Frustum();
        this.thread3 = new Thread(() -> {
            while (true) {
                if (!this.nullCheck()) {
                    if (!this.isEnabled()) {
                        continue;
                    }
                    else {
                        try {
                            this.playerList = (List<EntityPlayer>)this.mc.world.playerEntities;
                        }
                        catch (Exception ex) {}
                    }
                }
            }
        });
        ESP.Instance = this;
    }
    
    public static void renderOne(final float width) {
        checkSetupFBO();
        GL11.glPushAttrib(1048575);
        GL11.glDisable(3008);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(width);
        GL11.glEnable(2848);
        GL11.glEnable(2960);
        GL11.glClear(1024);
        GL11.glClearStencil(15);
        GL11.glStencilFunc(512, 1, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6913);
    }
    
    public static void renderTwo() {
        GL11.glStencilFunc(512, 0, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6914);
    }
    
    public static void renderThree() {
        GL11.glStencilFunc(514, 1, 15);
        GL11.glStencilOp(7680, 7680, 7680);
        GL11.glPolygonMode(1032, 6913);
    }
    
    public static void renderFour() {
        setColor(new Color(255, 255, 255));
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glEnable(10754);
        GL11.glPolygonOffset(1.0f, -2000000.0f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
    }
    
    public static void renderFive() {
        GL11.glPolygonOffset(1.0f, 2000000.0f);
        GL11.glDisable(10754);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(2960);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glEnable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
        GL11.glEnable(3008);
        GL11.glPopAttrib();
    }
    
    public static void checkSetupFBO() {
        final Framebuffer fbo = Ruby.mc.getFramebuffer();
        if (fbo.depthBuffer > -1) {
            setupFBO(fbo);
            fbo.depthBuffer = -1;
        }
    }
    
    public static void setupFBO(final Framebuffer fbo) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
        final int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(36161, stencil_depth_buffer_ID);
        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, Ruby.mc.displayWidth, Ruby.mc.displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencil_depth_buffer_ID);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencil_depth_buffer_ID);
    }
    
    public static void setColor(final Color c) {
        GL11.glColor4d((double)(c.getRed() / 255.0f), (double)(c.getGreen() / 255.0f), (double)(c.getBlue() / 255.0f), (double)(c.getAlpha() / 255.0f));
    }
    
    public void onFrame(final float partialTicks) {
        final Runnable runnable = () -> this.playerList = (List<EntityPlayer>)this.mc.world.playerEntities;
        Ruby.threadManager.run(runnable);
        this.camera.setPosition(Objects.requireNonNull(this.mc.getRenderViewEntity()).posX, this.mc.getRenderViewEntity().posY, this.mc.getRenderViewEntity().posZ);
        if (this.players.GetSwitch() && this.playerMoveCancel.GetSwitch()) {
            this.mc.world.playerEntities.stream().filter(entityPlayer -> !entityPlayer.equals((Object)this.mc.player)).forEach(entityPlayer -> {
                entityPlayer.limbSwing = 0.0f;
                entityPlayer.limbSwingAmount = 0.0f;
                entityPlayer.prevLimbSwingAmount = 0.0f;
                entityPlayer.rotationYawHead = 0.0f;
                entityPlayer.rotationPitch = 0.0f;
                entityPlayer.rotationYaw = 0.0f;
                return;
            });
        }
        if (this.itemNames.GetSwitch()) {
            final boolean fancyGraphics = this.mc.gameSettings.fancyGraphics;
            this.mc.gameSettings.fancyGraphics = false;
            final float gammaSetting = this.mc.gameSettings.gammaSetting;
            this.mc.gameSettings.gammaSetting = 100.0f;
            this.entityList.clear();
            this.mc.world.loadedEntityList.stream().filter(Objects::nonNull).forEach(entity -> this.entityList.add(entity));
            final Vec3d i;
            this.entityList.stream().filter(entity -> entity instanceof EntityItem && !entity.getItem().equals(this.mc.player.getHeldItemMainhand()) && this.camera.isBoundingBoxInFrustum(((Entity)entity).getEntityBoundingBox().grow(2.0))).filter(entity -> this.mc.player.getDistanceSq(entity.getPosition()) < 1000.0 && !entity.isDead).forEach(entity -> {
                GL11.glPushMatrix();
                i = RenderUtil.interpolateEntity((Entity)entity);
                RenderUtil.drawNametag(entity.getItem().getDisplayName() + " x" + entity.getItem().getCount(), i.x, i.y, i.z, 0.005, -1);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glPopMatrix();
                return;
            });
            this.mc.gameSettings.gammaSetting = gammaSetting;
            this.mc.gameSettings.fancyGraphics = fancyGraphics;
        }
    }
    
    @RegisterListener
    public void onRenderLivingEntity(final RenderLivingEntityEvent event) {
        if (this.nullCheck() || !this.isEnabled() || !(event.getEntityLivingBase() instanceof EntityPlayer) || !this.players.GetSwitch() || !this.camera.isBoundingBoxInFrustum(event.getEntityLivingBase().getEntityBoundingBox().grow(2.0))) {
            return;
        }
        if (!this.thread3.isAlive() || this.thread3.isInterrupted()) {
            this.thread3.start();
        }
        event.getEntityLivingBase().hurtTime = 0;
        event.getModelBase().render((Entity)event.getEntityLivingBase(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScaleFactor());
        renderOne(this.playerLineWidth.GetSlider());
        event.getModelBase().render((Entity)event.getEntityLivingBase(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScaleFactor());
        renderTwo();
        event.getModelBase().render((Entity)event.getEntityLivingBase(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScaleFactor());
        renderThree();
        renderFour();
        setColor(this.playerColor.GetColor());
        event.getModelBase().render((Entity)event.getEntityLivingBase(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScaleFactor());
        renderFive();
        setColor(Color.WHITE);
    }
}
