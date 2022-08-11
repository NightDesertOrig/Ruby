//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.visual;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.client.renderer.culling.*;
import net.minecraft.entity.*;
import dev.zprestige.ruby.*;
import dev.zprestige.ruby.manager.*;
import java.util.stream.*;
import net.minecraft.util.math.*;
import java.awt.*;
import dev.zprestige.ruby.util.*;
import java.util.*;

public class HoleESP extends Module
{
    protected final Slider radius;
    protected final Slider height;
    protected final Slider lineWidth;
    protected final Switch doubles;
    protected final ComboBox animation;
    protected final Slider growSpeed;
    protected final Slider distanceDivision;
    protected final ColorSwitch bedrockBox;
    protected final ColorSwitch bedrockOutline;
    protected final ColorSwitch obsidianBox;
    protected final ColorSwitch obsidianOutline;
    protected final HashMap<BlockPos, Long> holePosLongHashMap;
    protected final ICamera camera;
    
    public HoleESP() {
        this.radius = this.Menu.Slider("Radius", 1.0f, 50.0f);
        this.height = this.Menu.Slider("Height", 0.0f, 2.0f);
        this.lineWidth = this.Menu.Slider("Line Width", 0.1f, 5.0f);
        this.doubles = this.Menu.Switch("Doubles");
        this.animation = this.Menu.ComboBox("Animation", new String[] { "None", "Grow", "Fade" });
        this.growSpeed = this.Menu.Slider("Grow Speed", 0.0f, 1000.0f);
        this.distanceDivision = this.Menu.Slider("Distance Division", 0.1f, 50.0f);
        this.bedrockBox = this.Menu.ColorSwitch("Bedrock Box");
        this.bedrockOutline = this.Menu.ColorSwitch("Bedrock Outline");
        this.obsidianBox = this.Menu.ColorSwitch("Obsidian Box");
        this.obsidianOutline = this.Menu.ColorSwitch("Obsidian Outline");
        this.holePosLongHashMap = new HashMap<BlockPos, Long>();
        this.camera = (ICamera)new Frustum();
    }
    
    public void onFrame(final float partialTicks) {
        this.camera.setPosition(Objects.requireNonNull(this.mc.getRenderViewEntity()).posX, this.mc.getRenderViewEntity().posY, this.mc.getRenderViewEntity().posZ);
        final List<HoleManager.HolePos> holes = (List<HoleManager.HolePos>)Ruby.holeManager.holes.stream().filter(holePos -> this.mc.player.getDistanceSq(holePos.pos) < this.radius.GetSlider() * this.radius.GetSlider() && (this.doubles.GetSwitch() || holePos.holeType.equals((Object)HoleManager.Type.Bedrock) || holePos.holeType.equals((Object)HoleManager.Type.Obsidian))).collect(Collectors.toList());
        final Long n;
        new HashMap(this.holePosLongHashMap).entrySet().stream().filter(entry -> holes.stream().noneMatch(holePos -> holePos.pos.equals(entry.getKey()))).forEach(entry -> n = this.holePosLongHashMap.remove(entry.getKey()));
        for (final HoleManager.HolePos holePos2 : holes) {
            AxisAlignedBB bb = this.animation.GetCombo().equals("Grow") ? new AxisAlignedBB(holePos2.pos).shrink(0.5) : new AxisAlignedBB(holePos2.pos);
            if (this.animation.GetCombo().equals("Grow")) {
                for (final Map.Entry<BlockPos, Long> entry2 : this.holePosLongHashMap.entrySet()) {
                    if (entry2.getKey().equals((Object)holePos2.pos)) {
                        bb = bb.grow(Math.min((System.currentTimeMillis() - entry2.getValue()) / (1001.0f - this.growSpeed.GetSlider()), 0.5));
                    }
                }
            }
            final int bedrockAlpha = (int)Math.min(this.bedrockBox.GetColor().getAlpha() / Math.max(1.0, this.mc.player.getDistanceSq(holePos2.pos) / this.distanceDivision.GetSlider()), this.bedrockBox.GetColor().getAlpha());
            final int obsidianAlpha = (int)Math.min(this.obsidianBox.GetColor().getAlpha() / Math.max(1.0, this.mc.player.getDistanceSq(holePos2.pos) / this.distanceDivision.GetSlider()), this.obsidianBox.GetColor().getAlpha());
            final int bedrockOutlineAlpha = (int)Math.min(this.bedrockOutline.GetColor().getAlpha() / Math.max(1.0, this.mc.player.getDistanceSq(holePos2.pos) / this.distanceDivision.GetSlider()), this.bedrockOutline.GetColor().getAlpha());
            final int obsidianOutlineAlpha = (int)Math.min(this.obsidianOutline.GetColor().getAlpha() / Math.max(1.0, this.mc.player.getDistanceSq(holePos2.pos) / this.distanceDivision.GetSlider()), this.obsidianOutline.GetColor().getAlpha());
            final Color bedrockBoxColor = this.animation.GetCombo().equals("Fade") ? new Color(this.bedrockBox.GetColor().getRed() / 255.0f, this.bedrockBox.GetColor().getGreen() / 255.0f, this.bedrockBox.GetColor().getBlue() / 255.0f, bedrockAlpha / 255.0f) : this.bedrockBox.GetColor();
            final Color obsidianBoxColor = this.animation.GetCombo().equals("Fade") ? new Color(this.obsidianBox.GetColor().getRed() / 255.0f, this.obsidianBox.GetColor().getGreen() / 255.0f, this.obsidianBox.GetColor().getBlue() / 255.0f, obsidianAlpha / 255.0f) : this.obsidianBox.GetColor();
            final Color bedrockOutlineColor = this.animation.GetCombo().equals("Fade") ? new Color(this.bedrockOutline.GetColor().getRed() / 255.0f, this.bedrockOutline.GetColor().getGreen() / 255.0f, this.bedrockOutline.GetColor().getBlue() / 255.0f, bedrockOutlineAlpha / 255.0f) : this.bedrockOutline.GetColor();
            final Color obsidianOutlineColor = this.animation.GetCombo().equals("Fade") ? new Color(this.obsidianOutline.GetColor().getRed() / 255.0f, this.obsidianOutline.GetColor().getGreen() / 255.0f, this.obsidianOutline.GetColor().getBlue() / 255.0f, obsidianOutlineAlpha / 255.0f) : this.obsidianOutline.GetColor();
            if (this.camera.isBoundingBoxInFrustum(bb.grow(2.0))) {
                switch (holePos2.holeType) {
                    case Bedrock: {
                        RenderUtil.drawBoxWithHeight(bb, bedrockBoxColor, this.height.GetSlider());
                        RenderUtil.drawBlockOutlineBBWithHeight(bb, bedrockOutlineColor, this.lineWidth.GetSlider(), this.height.GetSlider());
                        break;
                    }
                    case Obsidian: {
                        RenderUtil.drawBoxWithHeight(bb, obsidianBoxColor, this.height.GetSlider());
                        RenderUtil.drawBlockOutlineBBWithHeight(bb, obsidianOutlineColor, this.lineWidth.GetSlider(), this.height.GetSlider());
                        break;
                    }
                    case DoubleBedrockNorth: {
                        RenderUtil.drawCustomBB(bedrockBoxColor, bb.minX, bb.minY, bb.minZ - 1.0, bb.maxX, bb.maxY - 1.0 + this.height.GetSlider(), bb.maxZ);
                        RenderUtil.drawBlockOutlineBBWithHeight(new AxisAlignedBB(bb.minX, bb.minY, bb.minZ - 1.0, bb.maxX, bb.maxY, bb.maxZ), bedrockOutlineColor, this.lineWidth.GetSlider(), this.height.GetSlider());
                        break;
                    }
                    case DoubleObsidianNorth: {
                        RenderUtil.drawCustomBB(obsidianBoxColor, bb.minX, bb.minY, bb.minZ - 1.0, bb.maxX, bb.maxY - 1.0 + this.height.GetSlider(), bb.maxZ);
                        RenderUtil.drawBlockOutlineBBWithHeight(new AxisAlignedBB(bb.minX, bb.minY, bb.minZ - 1.0, bb.maxX, bb.maxY, bb.maxZ), obsidianOutlineColor, this.lineWidth.GetSlider(), this.height.GetSlider());
                        break;
                    }
                    case DoubleBedrockWest: {
                        RenderUtil.drawCustomBB(bedrockBoxColor, bb.minX - 1.0, bb.minY, bb.minZ, bb.maxX, bb.maxY - 1.0 + this.height.GetSlider(), bb.maxZ);
                        RenderUtil.drawBlockOutlineBBWithHeight(new AxisAlignedBB(bb.minX - 1.0, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ), bedrockOutlineColor, this.lineWidth.GetSlider(), this.height.GetSlider());
                        break;
                    }
                    case DoubleObsidianWest: {
                        RenderUtil.drawCustomBB(obsidianBoxColor, bb.minX - 1.0, bb.minY, bb.minZ, bb.maxX, bb.maxY - 1.0 + this.height.GetSlider(), bb.maxZ);
                        RenderUtil.drawBlockOutlineBBWithHeight(new AxisAlignedBB(bb.minX - 1.0, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ), obsidianOutlineColor, this.lineWidth.GetSlider(), this.height.GetSlider());
                        break;
                    }
                }
            }
            if (!this.holePosLongHashMap.containsKey(holePos2.pos)) {
                this.holePosLongHashMap.put(holePos2.pos, System.currentTimeMillis());
            }
        }
    }
}
