//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.visual;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.renderer.culling.*;
import java.math.*;
import dev.zprestige.ruby.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import java.util.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.client.network.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import dev.zprestige.ruby.util.*;

public class Nametags extends Module
{
    public static Nametags Instance;
    public final Switch multiThreaded;
    public final Switch health;
    public final Switch ping;
    public final Switch totemPops;
    public final Switch inFrustum;
    public List<EntityPlayer> entityPlayers;
    public ICamera camera;
    
    public Nametags() {
        this.multiThreaded = this.Menu.Switch("Multi Threaded");
        this.health = this.Menu.Switch("Health");
        this.ping = this.Menu.Switch("Ping");
        this.totemPops = this.Menu.Switch("Totem Pops");
        this.inFrustum = this.Menu.Switch("In Frustum");
        this.entityPlayers = new ArrayList<EntityPlayer>();
        this.camera = (ICamera)new Frustum();
        Nametags.Instance = this;
    }
    
    public static float roundNumber(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal decimal = BigDecimal.valueOf(value);
        decimal = decimal.setScale(places, RoundingMode.FLOOR);
        return decimal.floatValue();
    }
    
    public void onFrame(final float partialTicks) {
        if (this.multiThreaded.GetSwitch()) {
            Ruby.threadManager.run(() -> this.entityPlayers = (List<EntityPlayer>)this.mc.world.playerEntities);
        }
        else {
            this.entityPlayers = (List<EntityPlayer>)this.mc.world.playerEntities;
        }
        if (!this.entityPlayers.isEmpty()) {
            if (this.inFrustum.GetSwitch()) {
                this.camera.setPosition(Objects.requireNonNull(this.mc.getRenderViewEntity()).posX, this.mc.getRenderViewEntity().posY, this.mc.getRenderViewEntity().posZ);
            }
            for (final EntityPlayer entity : this.entityPlayers) {
                if (entity.isSpectator()) {
                    continue;
                }
                if (this.inFrustum.GetSwitch() && !this.camera.isBoundingBoxInFrustum(entity.getEntityBoundingBox().grow(2.0))) {
                    continue;
                }
                if (entity.getName().equals("FakePlayer")) {
                    continue;
                }
                if (entity.equals((Object)this.mc.player)) {
                    continue;
                }
                GL11.glPushMatrix();
                try {
                    this.renderFullNametag(entity, this.interpolate(entity.lastTickPosX, entity.posX, partialTicks) - this.mc.getRenderManager().renderPosX, this.interpolate(entity.lastTickPosY, entity.posY, partialTicks) - this.mc.getRenderManager().renderPosY, this.interpolate(entity.lastTickPosZ, entity.posZ, partialTicks) - this.mc.getRenderManager().renderPosZ, partialTicks);
                }
                catch (Exception ex) {}
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glPopMatrix();
            }
        }
    }
    
    public ChatFormatting getPopsColor(final int i) {
        if (i > 8) {
            return ChatFormatting.DARK_RED;
        }
        if (i > 6) {
            return ChatFormatting.RED;
        }
        if (i > 4) {
            return ChatFormatting.GOLD;
        }
        if (i > 2) {
            return ChatFormatting.YELLOW;
        }
        if (i > 0) {
            return ChatFormatting.GREEN;
        }
        return ChatFormatting.GRAY;
    }
    
    public ChatFormatting getHealthColor(final EntityPlayer entityPlayer) {
        final float health = entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount();
        if (health > 20.0f) {
            return ChatFormatting.GREEN;
        }
        if (health > 16.0f) {
            return ChatFormatting.GOLD;
        }
        if (health > 10.0f) {
            return ChatFormatting.YELLOW;
        }
        if (health > 5.0f) {
            return ChatFormatting.RED;
        }
        if (health > 0.0f) {
            return ChatFormatting.DARK_RED;
        }
        return null;
    }
    
    public ChatFormatting getPingColor(final EntityPlayer entityPlayer) {
        try {
            final float ping = (float)Objects.requireNonNull(this.mc.getConnection()).getPlayerInfo(entityPlayer.getGameProfile().getId()).getResponseTime();
            if (ping > 200.0f) {
                return ChatFormatting.DARK_RED;
            }
            if (ping > 150.0f) {
                return ChatFormatting.RED;
            }
            if (ping > 100.0f) {
                return ChatFormatting.YELLOW;
            }
            if (ping > 50.0f) {
                return ChatFormatting.GOLD;
            }
            if (ping > 0.0f) {
                return ChatFormatting.GREEN;
            }
        }
        catch (Exception ex) {}
        return null;
    }
    
    public void renderFullNametag(final EntityPlayer entityPlayer, final double x, final double y, final double z, final float delta) {
        double tempY = y;
        tempY += (entityPlayer.isSneaking() ? 0.5 : 0.7);
        final Entity camera = this.mc.getRenderViewEntity();
        assert camera != null;
        final double originalPositionX = camera.posX;
        final double originalPositionY = camera.posY;
        final double originalPositionZ = camera.posZ;
        camera.posX = this.interpolate(camera.prevPosX, camera.posX, delta);
        camera.posY = this.interpolate(camera.prevPosY, camera.posY, delta);
        camera.posZ = this.interpolate(camera.prevPosZ, camera.posZ, delta);
        final double distance = camera.getDistance(x + this.mc.getRenderManager().viewerPosX, y + this.mc.getRenderManager().viewerPosY, z + this.mc.getRenderManager().viewerPosZ);
        final double scale = (distance <= 8.0) ? 0.0245 : ((0.0018 + 10.0 * (distance * 0.3)) / 1000.0);
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
        GlStateManager.disableLighting();
        GlStateManager.translate((float)x, (float)tempY + 1.4f, (float)z);
        GlStateManager.rotate(-this.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(this.mc.getRenderManager().playerViewX, (this.mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scale, -scale, scale);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.enableBlend();
        GlStateManager.disableBlend();
        final ItemStack renderMainHand = entityPlayer.getHeldItemMainhand().copy();
        if (renderMainHand.hasEffect() && (renderMainHand.getItem() instanceof ItemTool || renderMainHand.getItem() instanceof ItemArmor)) {
            renderMainHand.stackSize = 1;
        }
        GL11.glPushMatrix();
        GL11.glScalef(0.75f, 0.75f, 0.0f);
        GL11.glScalef(1.5f, 1.5f, 1.0f);
        GL11.glPopMatrix();
        GlStateManager.pushMatrix();
        int xOffset = -8;
        for (final ItemStack stack : entityPlayer.inventory.armorInventory) {
            if (stack != null) {
                xOffset -= 8;
            }
        }
        xOffset -= 8;
        final ItemStack renderOffhand = entityPlayer.getHeldItemOffhand().copy();
        if (renderOffhand.hasEffect() && (renderOffhand.getItem() instanceof ItemTool || renderOffhand.getItem() instanceof ItemArmor)) {
            renderOffhand.stackSize = 1;
        }
        this.renderItemStack(renderOffhand, xOffset);
        xOffset += 16;
        for (final ItemStack stack2 : entityPlayer.inventory.armorInventory) {
            if (stack2 == null) {
                continue;
            }
            final ItemStack armourStack = stack2.copy();
            if (armourStack.hasEffect() && (armourStack.getItem() instanceof ItemTool || armourStack.getItem() instanceof ItemArmor)) {
                armourStack.stackSize = 1;
            }
            this.renderItemStack(armourStack, xOffset);
            xOffset += 16;
        }
        this.renderItemStack(renderMainHand, xOffset);
        GlStateManager.popMatrix();
        final int pops = Ruby.totemPopManager.getPopsByPlayer(entityPlayer.getName());
        Ruby.mc.fontRenderer.drawStringWithShadow((Ruby.friendManager.isFriend(entityPlayer.getName()) ? ChatFormatting.AQUA : (Ruby.enemyManager.isEnemy(entityPlayer.getName()) ? ChatFormatting.RED : "")) + entityPlayer.getName() + (this.health.GetSwitch() ? (" " + this.getHealthColor(entityPlayer) + roundNumber(entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount(), 1)) : "") + (this.ping.GetSwitch() ? (this.getPingColor(entityPlayer) + " " + Objects.requireNonNull(this.mc.getConnection()).getPlayerInfo(entityPlayer.getGameProfile().getId()).getResponseTime() + "ms") : "") + (this.totemPops.GetSwitch() ? (" " + this.getPopsColor(pops) + pops) : ""), (float)(-Ruby.fontManager.getStringWidth(entityPlayer.getName() + (this.health.GetSwitch() ? (" " + this.getHealthColor(entityPlayer) + roundNumber(entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount(), 1)) : "") + (this.ping.GetSwitch() ? (this.getPingColor(entityPlayer) + " " + roundNumber(Objects.requireNonNull(this.mc.getConnection()).getPlayerInfo(entityPlayer.getGameProfile().getId()).getResponseTime(), 0) + "ms") : "") + (this.totemPops.GetSwitch() ? (" " + this.getPopsColor(pops) + pops) : "")) / 2), -8.0f, -1);
        camera.posX = originalPositionX;
        camera.posY = originalPositionY;
        camera.posZ = originalPositionZ;
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
        GlStateManager.popMatrix();
    }
    
    private void renderItemStack(final ItemStack stack, final int x) {
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.clear(256);
        RenderHelper.enableStandardItemLighting();
        this.mc.getRenderItem().zLevel = -150.0f;
        GlStateManager.disableAlpha();
        GlStateManager.enableDepth();
        GlStateManager.disableCull();
        this.mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, -26);
        this.mc.getRenderItem().renderItemOverlays(this.mc.fontRenderer, stack, x, -26);
        this.mc.getRenderItem().zLevel = 0.0f;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableCull();
        GlStateManager.enableAlpha();
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        GlStateManager.disableDepth();
        this.renderEnchantmentText(stack, x);
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GlStateManager.popMatrix();
    }
    
    private void renderEnchantmentText(final ItemStack stack, final int x) {
        if (EntityUtil.hasDurability(stack)) {
            final int percent = EntityUtil.getRoundedDamage(stack);
            final String color = (percent >= 60) ? "§a" : ((percent >= 25) ? "§e" : "§c");
            this.mc.fontRenderer.drawStringWithShadow(color + percent + "%", (float)(x * 2), -26.0f, -1);
        }
    }
    
    private double interpolate(final double previous, final double current, final float delta) {
        return previous + (current - previous) * delta;
    }
}
