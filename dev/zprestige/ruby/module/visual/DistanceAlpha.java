//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.visual;

import dev.zprestige.ruby.module.*;
import net.minecraft.client.renderer.culling.*;
import net.minecraft.entity.*;
import java.util.*;
import dev.zprestige.ruby.events.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import dev.zprestige.ruby.eventbus.annotation.*;

public class DistanceAlpha extends Module
{
    public Frustum camera;
    
    public DistanceAlpha() {
        this.camera = new Frustum();
    }
    
    public void onFrame(final float partialTicks) {
        this.camera.setPosition(Objects.requireNonNull(this.mc.getRenderViewEntity()).posX, this.mc.getRenderViewEntity().posY, this.mc.getRenderViewEntity().posZ);
    }
    
    @RegisterListener
    public void onRenderLivingEntity(final RenderLivingEntityEvent event) {
        if (this.nullCheck() || !this.isEnabled() || event.getEntityLivingBase() == null || !(event.getEntityLivingBase() instanceof EntityPlayer) || this.mc.player.equals((Object)event.getEntityLivingBase()) || !this.camera.isBoundingBoxInFrustum(event.getEntityLivingBase().getEntityBoundingBox().grow(2.0))) {
            return;
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, Math.max(Math.min(this.mc.player.getDistance((Entity)event.getEntityLivingBase()) / 5.0f, 1.0f), 0.2f));
    }
}
