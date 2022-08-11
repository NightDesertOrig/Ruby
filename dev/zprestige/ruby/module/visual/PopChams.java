//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.visual;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import dev.zprestige.ruby.events.*;
import dev.zprestige.ruby.eventbus.annotation.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;
import java.awt.*;
import java.util.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.entity.player.*;

public class PopChams extends Module
{
    public static PopChams Instance;
    public static double HEAD_X;
    public static double HEAD_Y;
    public static double HEAD_Z;
    public static double HEAD_X1;
    public static double HEAD_Y1;
    public static double HEAD_Z1;
    public static double CHEST_X;
    public static double CHEST_Y;
    public static double CHEST_Z;
    public static double CHEST_X1;
    public static double CHEST_Y1;
    public static double CHEST_Z1;
    public static double ARM1_X;
    public static double ARM1_Y;
    public static double ARM1_Z;
    public static double ARM1_X1;
    public static double ARM1_Y1;
    public static double ARM1_Z1;
    public static double ARM2_X;
    public static double ARM2_Y;
    public static double ARM2_Z;
    public static double ARM2_X1;
    public static double ARM2_Y1;
    public static double ARM2_Z1;
    public static double LEG1_X;
    public static double LEG1_Y;
    public static double LEG1_Z;
    public static double LEG1_X1;
    public static double LEG1_Y1;
    public static double LEG1_Z1;
    public static double LEG2_X;
    public static double LEG2_Y;
    public static double LEG2_Z;
    public static double LEG2_X1;
    public static double LEG2_Y1;
    public static double LEG2_Z1;
    public final Parent misc;
    public final Slider fadeTime;
    public final Switch selfPop;
    public final Switch onDeath;
    public final Switch travel;
    public final Slider travelSpeed;
    public final Parent rendering;
    public final ColorBox solidColor;
    public final ColorBox outlineColor;
    public final Slider outlineWidth;
    public final Switch differDeaths;
    public final ColorBox deathSolidColor;
    public final ColorBox deathOutlineColor;
    public final Slider deathOutlineWidth;
    public HashMap<String, PopData> popDataHashMap;
    
    public PopChams() {
        this.misc = this.Menu.Parent("Misc");
        this.fadeTime = this.Menu.Slider("Fade Time", 0, 5000).parent(this.misc);
        this.selfPop = this.Menu.Switch("Self Pop").parent(this.misc);
        this.onDeath = this.Menu.Switch("On Death").parent(this.misc);
        this.travel = this.Menu.Switch("Travel").parent(this.misc);
        this.travelSpeed = this.Menu.Slider("Travel Speed", -10.0f, 10.0f).parent(this.misc);
        this.rendering = this.Menu.Parent("Rendering");
        this.solidColor = this.Menu.Color("Solid Color").parent(this.rendering);
        this.outlineColor = this.Menu.Color("Outline Color").parent(this.rendering);
        this.outlineWidth = this.Menu.Slider("Outline Width", 0.1f, 5.0f).parent(this.rendering);
        this.differDeaths = this.Menu.Switch("Differ Deaths").parent(this.rendering);
        this.deathSolidColor = this.Menu.Color("Death Solid Color").parent(this.rendering);
        this.deathOutlineColor = this.Menu.Color("Death Outline Color").parent(this.rendering);
        this.deathOutlineWidth = this.Menu.Slider("Death Outline Width", 0.1f, 5.0f).parent(this.rendering);
        this.popDataHashMap = new HashMap<String, PopData>();
        PopChams.Instance = this;
    }
    
    @RegisterListener
    public void onTotemPop(final PlayerChangeEvent.TotemPop event) {
        if (!this.isEnabled() || this.nullCheck()) {
            return;
        }
        if (event.entityPlayer.equals((Object)this.mc.player) && !this.selfPop.GetSwitch()) {
            return;
        }
        this.popDataHashMap.put(event.entityPlayer.getName(), new PopData(event.entityPlayer, System.currentTimeMillis(), event.entityPlayer.rotationYaw, event.entityPlayer.rotationPitch, event.entityPlayer.posX, event.entityPlayer.posY, event.entityPlayer.posZ, false));
    }
    
    @RegisterListener
    public void onDeath(final PlayerChangeEvent.Death event) {
        if (!this.isEnabled() || this.nullCheck() || !this.onDeath.GetSwitch()) {
            return;
        }
        if (event.entityPlayer.equals((Object)this.mc.player) && !this.selfPop.GetSwitch()) {
            return;
        }
        this.popDataHashMap.put(event.entityPlayer.getName(), new PopData(event.entityPlayer, System.currentTimeMillis(), event.entityPlayer.rotationYaw, event.entityPlayer.rotationPitch, event.entityPlayer.posX, event.entityPlayer.posY, event.entityPlayer.posZ, true));
    }
    
    public void onFrame(final float partialTicks) {
        try {
            final HashMap<String, PopData> save = new HashMap<String, PopData>(this.popDataHashMap);
            for (final Map.Entry<String, PopData> entry : save.entrySet()) {
                final PopData data = entry.getValue();
                if (this.travel.GetSwitch()) {
                    final PopData popData = data;
                    popData.y += this.travelSpeed.GetSlider() / 100.0f;
                }
                final double x = data.getX() - this.mc.getRenderManager().viewerPosX;
                final double y = data.getY() - this.mc.getRenderManager().viewerPosY;
                final double z = data.getZ() - this.mc.getRenderManager().viewerPosZ;
                final float yaw = data.getYaw();
                final float pitch = data.getPitch();
                final AxisAlignedBB head = new AxisAlignedBB(x + PopChams.HEAD_X, y + PopChams.HEAD_Y, z + PopChams.HEAD_Z, x + PopChams.HEAD_X1, y + PopChams.HEAD_Y1, z + PopChams.HEAD_Z1);
                final AxisAlignedBB chest = new AxisAlignedBB(x + PopChams.CHEST_X, y + PopChams.CHEST_Y, z + PopChams.CHEST_Z, x + PopChams.CHEST_X1, y + PopChams.CHEST_Y1, z + PopChams.CHEST_Z1);
                final AxisAlignedBB arm1 = new AxisAlignedBB(x + PopChams.ARM1_X, y + PopChams.ARM1_Y, z + PopChams.ARM1_Z, x + PopChams.ARM1_X1, y + PopChams.ARM1_Y1, z + PopChams.ARM1_Z1);
                final AxisAlignedBB arm2 = new AxisAlignedBB(x + PopChams.ARM2_X, y + PopChams.ARM2_Y, z + PopChams.ARM2_Z, x + PopChams.ARM2_X1, y + PopChams.ARM2_Y1, z + PopChams.ARM2_Z1);
                final AxisAlignedBB leg1 = new AxisAlignedBB(x + PopChams.LEG1_X, y + PopChams.LEG1_Y, z + PopChams.LEG1_Z, x + PopChams.LEG1_X1, y + PopChams.LEG1_Y1, z + PopChams.LEG1_Z1);
                final AxisAlignedBB leg2 = new AxisAlignedBB(x + PopChams.LEG2_X, y + PopChams.LEG2_Y, z + PopChams.LEG2_Z, x + PopChams.LEG2_X1, y + PopChams.LEG2_Y1, z + PopChams.LEG2_Z1);
                GlStateManager.pushMatrix();
                GlStateManager.translate(x, y, z);
                GlStateManager.rotate(180.0f + -(yaw + 90.0f), 0.0f, 1.0f, 0.0f);
                GlStateManager.translate(-x, -y, -z);
                final Color boxColor = (this.differDeaths.GetSwitch() && entry.getValue().isDeath) ? this.deathSolidColor.GetColor() : this.solidColor.GetColor();
                final Color outlineColor = (this.differDeaths.GetSwitch() && entry.getValue().isDeath) ? this.deathOutlineColor.GetColor() : this.outlineColor.GetColor();
                final float maxBoxAlpha = (float)boxColor.getAlpha();
                final float maxOutlineAlpha = (float)outlineColor.getAlpha();
                final float alphaBoxAmount = maxBoxAlpha / this.fadeTime.GetSlider();
                final float alphaOutlineAmount = maxOutlineAlpha / this.fadeTime.GetSlider();
                final int fadeBoxAlpha = MathHelper.clamp((int)(alphaBoxAmount * (data.getTime() + this.fadeTime.GetSlider() - System.currentTimeMillis())), 0, (int)maxBoxAlpha);
                final int fadeOutlineAlpha = MathHelper.clamp((int)(alphaOutlineAmount * (data.getTime() + this.fadeTime.GetSlider() - System.currentTimeMillis())), 0, (int)maxOutlineAlpha);
                final Color box = new Color(boxColor.getRed(), boxColor.getGreen(), boxColor.getBlue(), fadeBoxAlpha);
                final Color out = new Color(outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue(), fadeOutlineAlpha);
                this.renderAxis(chest, box, out, entry.getValue().isDeath);
                this.renderAxis(arm1, box, out, entry.getValue().isDeath);
                this.renderAxis(arm2, box, out, entry.getValue().isDeath);
                this.renderAxis(leg1, box, out, entry.getValue().isDeath);
                this.renderAxis(leg2, box, out, entry.getValue().isDeath);
                GlStateManager.translate(x, y + 1.5, z);
                GlStateManager.rotate(pitch, 0.0f, 0.0f, 1.0f);
                GlStateManager.translate(-x, -y - 1.5, -z);
                this.renderAxis(head, box, out, entry.getValue().isDeath);
                GlStateManager.popMatrix();
            }
        }
        catch (Exception ex) {}
        this.popDataHashMap.entrySet().removeIf(e -> e.getValue().getTime() + this.fadeTime.GetSlider() < System.currentTimeMillis());
    }
    
    private void renderAxis(final AxisAlignedBB bb, final Color color, final Color outline, final boolean isDeath) {
        RenderUtil.renderBox(bb, color, outline, (this.differDeaths.GetSwitch() && isDeath) ? this.deathOutlineWidth.GetSlider() : this.outlineWidth.GetSlider());
    }
    
    static {
        PopChams.HEAD_X = -0.2;
        PopChams.HEAD_Y = 1.5;
        PopChams.HEAD_Z = -0.25;
        PopChams.HEAD_X1 = 0.2;
        PopChams.HEAD_Y1 = 1.95;
        PopChams.HEAD_Z1 = 0.25;
        PopChams.CHEST_X = -0.18;
        PopChams.CHEST_Y = 0.8;
        PopChams.CHEST_Z = -0.275;
        PopChams.CHEST_X1 = 0.18;
        PopChams.CHEST_Y1 = 1.5;
        PopChams.CHEST_Z1 = 0.275;
        PopChams.ARM1_X = -0.1;
        PopChams.ARM1_Y = 0.75;
        PopChams.ARM1_Z = 0.275;
        PopChams.ARM1_X1 = 0.1;
        PopChams.ARM1_Y1 = 1.5;
        PopChams.ARM1_Z1 = 0.5;
        PopChams.ARM2_X = -0.1;
        PopChams.ARM2_Y = 0.75;
        PopChams.ARM2_Z = -0.275;
        PopChams.ARM2_X1 = 0.1;
        PopChams.ARM2_Y1 = 1.5;
        PopChams.ARM2_Z1 = -0.5;
        PopChams.LEG1_X = -0.15;
        PopChams.LEG1_Y = 0.0;
        PopChams.LEG1_Z = 0.0;
        PopChams.LEG1_X1 = 0.15;
        PopChams.LEG1_Y1 = 0.8;
        PopChams.LEG1_Z1 = 0.25;
        PopChams.LEG2_X = -0.15;
        PopChams.LEG2_Y = 0.0;
        PopChams.LEG2_Z = 0.0;
        PopChams.LEG2_X1 = 0.15;
        PopChams.LEG2_Y1 = 0.8;
        PopChams.LEG2_Z1 = -0.25;
    }
    
    public static class PopData
    {
        public EntityPlayer player;
        public long time;
        public float yaw;
        public float pitch;
        public double x;
        public double y;
        public double z;
        public boolean isDeath;
        
        public PopData(final EntityPlayer player, final long time, final float yaw, final float pitch, final double x, final double y, final double z, final boolean isDeath) {
            this.player = player;
            this.time = time;
            this.yaw = yaw;
            this.pitch = pitch;
            this.x = x;
            this.y = y;
            this.z = z;
            this.isDeath = isDeath;
        }
        
        public EntityPlayer getPlayer() {
            return this.player;
        }
        
        public long getTime() {
            return this.time;
        }
        
        public float getYaw() {
            return this.yaw;
        }
        
        public float getPitch() {
            return this.pitch;
        }
        
        public double getX() {
            return this.x;
        }
        
        public double getY() {
            return this.y;
        }
        
        public double getZ() {
            return this.z;
        }
    }
}
