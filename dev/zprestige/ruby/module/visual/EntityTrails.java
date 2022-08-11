//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.visual;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import org.lwjgl.opengl.*;
import java.util.stream.*;
import java.awt.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import dev.zprestige.ruby.util.*;
import java.util.*;

public class EntityTrails extends Module
{
    public final Switch self;
    public final Slider lineWidth;
    public final Switch fade;
    public final Slider removeDelay;
    public final ColorBox startColor;
    public final ColorBox endColor;
    public final Switch entities;
    public final Switch pearls;
    public final Switch exp;
    public final ColorBox pearlColor;
    public final Slider pearlLineWidth;
    public HashMap<UUID, List<Vec3d>> pearlPos;
    public HashMap<UUID, Double> removeWait;
    public Map<UUID, ItemTrail> trails;
    
    public EntityTrails() {
        this.self = this.Menu.Switch("Self");
        this.lineWidth = this.Menu.Slider("LineWidth", 0.1f, 5.0f);
        this.fade = this.Menu.Switch("Fade");
        this.removeDelay = this.Menu.Slider("RemoveDelay", 0, 2000);
        this.startColor = this.Menu.Color("StartColor");
        this.endColor = this.Menu.Color("EndColor");
        this.entities = this.Menu.Switch("Entities");
        this.pearls = this.Menu.Switch("Pearls");
        this.exp = this.Menu.Switch("Exp");
        this.pearlColor = this.Menu.Color("EntityColor");
        this.pearlLineWidth = this.Menu.Slider("PearlLineWidth", 0.0f, 10.0f);
        this.pearlPos = new HashMap<UUID, List<Vec3d>>();
        this.removeWait = new HashMap<UUID, Double>();
        this.trails = new HashMap<UUID, ItemTrail>();
    }
    
    public void onTick() {
        if (this.entities.GetSwitch()) {
            UUID pearlPos = null;
            for (final UUID uuid : this.removeWait.keySet()) {
                if (this.removeWait.get(uuid) <= 0.0) {
                    this.pearlPos.remove(uuid);
                    pearlPos = uuid;
                }
                else {
                    this.removeWait.replace(uuid, this.removeWait.get(uuid) - 0.05);
                }
            }
            if (pearlPos != null) {
                this.removeWait.remove(pearlPos);
            }
            for (final Entity entity : this.mc.world.getLoadedEntityList()) {
                if ((entity instanceof EntityEnderPearl && this.pearls.GetSwitch()) || (entity instanceof EntityExpBottle && this.exp.GetSwitch())) {
                    if (!this.pearlPos.containsKey(entity.getUniqueID())) {
                        this.pearlPos.put(entity.getUniqueID(), new ArrayList<Vec3d>(Collections.singletonList(entity.getPositionVector())));
                        this.removeWait.put(entity.getUniqueID(), 0.1);
                    }
                    else {
                        this.removeWait.replace(entity.getUniqueID(), 0.1);
                        final List<Vec3d> v = this.pearlPos.get(entity.getUniqueID());
                        v.add(entity.getPositionVector());
                    }
                }
            }
        }
        if (this.self.GetSwitch()) {
            if (this.trails.containsKey(this.mc.player.getUniqueID())) {
                final ItemTrail playerTrail = this.trails.get(this.mc.player.getUniqueID());
                playerTrail.timer.setTime(0);
                final List<Position> toRemove = playerTrail.positions.stream().filter(position -> System.currentTimeMillis() - position.time > this.removeDelay.GetSlider()).collect((Collector<? super Object, ?, List<Position>>)Collectors.toList());
                playerTrail.positions.removeAll(toRemove);
                playerTrail.positions.add(new Position(this.mc.player.getPositionVector()));
            }
            else {
                this.trails.put(this.mc.player.getUniqueID(), new ItemTrail((Entity)this.mc.player));
            }
        }
    }
    
    public void onFrame(final float partialTicks) {
        if (this.self.GetSwitch()) {
            this.trails.forEach((key, value) -> {
                if (value.entity.isDead || this.mc.world.getEntityByID(value.entity.getEntityId()) == null) {
                    if (value.timer.isPaused()) {
                        value.timer.setTime(0);
                    }
                    value.timer.setPaused(false);
                }
                if (!value.timer.isPassed()) {
                    this.drawTrail(value);
                }
                return;
            });
        }
        if (this.pearlPos.isEmpty() || !this.entities.GetSwitch()) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(this.pearlLineWidth.GetSlider());
        final Color color;
        final List<Vec3d> pos;
        this.pearlPos.keySet().stream().filter(uuid -> this.pearlPos.get(uuid).size() > 2).forEach(uuid -> {
            GL11.glBegin(1);
            IntStream.range(1, this.pearlPos.get(uuid).size()).forEach(i -> {
                color = this.pearlColor.GetColor();
                GL11.glColor3d((double)(color.getRed() / 255.0f), (double)(color.getGreen() / 255.0f), (double)(color.getBlue() / 255.0f));
                pos = this.pearlPos.get(uuid);
                GL11.glVertex3d(pos.get(i).x - this.mc.getRenderManager().viewerPosX, pos.get(i).y - this.mc.getRenderManager().viewerPosY, pos.get(i).z - this.mc.getRenderManager().viewerPosZ);
                GL11.glVertex3d(pos.get(i - 1).x - this.mc.getRenderManager().viewerPosX, pos.get(i - 1).y - this.mc.getRenderManager().viewerPosY, pos.get(i - 1).z - this.mc.getRenderManager().viewerPosZ);
                return;
            });
            GL11.glEnd();
            return;
        });
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    void drawTrail(final ItemTrail trail) {
        final Color fadeColor = this.endColor.GetColor();
        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableAlpha();
        GlStateManager.disableCull();
        GlStateManager.enableBlend();
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(this.lineWidth.GetSlider());
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        (RenderUtil.builder = RenderUtil.tessellator.getBuffer()).begin(3, DefaultVertexFormats.POSITION_COLOR);
        this.buildBuffer(RenderUtil.builder, trail, this.startColor.GetColor(), this.fade.GetSwitch() ? fadeColor : this.startColor.GetColor());
        RenderUtil.tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.popMatrix();
        GL11.glEnable(3553);
        GL11.glPolygonMode(1032, 6914);
    }
    
    void buildBuffer(final BufferBuilder builder, final ItemTrail trail, final Color start, final Color end) {
        for (final Position p : trail.positions) {
            final Vec3d pos = RenderUtil.updateToCamera(p.pos);
            final double value = this.normalize(trail.positions.indexOf(p), trail.positions.size());
            RenderUtil.addBuilderVertex(builder, pos.x, pos.y, pos.z, RenderUtil.interpolateColor((float)value, start, end));
        }
    }
    
    double normalize(final double value, final double max) {
        return (value - 0.0) / (max - 0.0);
    }
    
    static class ItemTrail
    {
        public Entity entity;
        public List<Position> positions;
        public Timer timer;
        
        ItemTrail(final Entity entity) {
            this.entity = entity;
            this.positions = new ArrayList<Position>();
            (this.timer = new Timer()).setDelay(1000L);
            this.timer.setPaused(true);
        }
    }
    
    static class Position
    {
        public Vec3d pos;
        public long time;
        
        public Position(final Vec3d pos) {
            this.pos = pos;
            this.time = System.currentTimeMillis();
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            final Position position = (Position)o;
            return this.time == position.time && Objects.equals(this.pos, position.pos);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(this.pos, this.time);
        }
    }
}
