//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.movement;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.entity.player.*;
import dev.zprestige.ruby.*;
import java.util.stream.*;
import dev.zprestige.ruby.manager.*;
import java.util.*;
import net.minecraft.util.math.*;

public class HoleDrag extends Module
{
    public final Slider holeRange;
    public final ComboBox dragMode;
    public final Slider smoothSpeed;
    public final Switch onGroundOnly;
    public Timer timer;
    
    public HoleDrag() {
        this.holeRange = this.Menu.Slider("Hole Range", 0.1f, 3.0f);
        this.dragMode = this.Menu.ComboBox("Drag Mode", new String[] { "Smooth", "Teleport" });
        this.smoothSpeed = this.Menu.Slider("Smooth Speed", 0.0f, 2.0f);
        this.onGroundOnly = this.Menu.Switch("On Ground Only");
        this.timer = new Timer();
    }
    
    public void onTick() {
        if (BlockUtil.isPlayerSafe((EntityPlayer)this.mc.player)) {
            this.timer.setTime(0);
            return;
        }
        if (!this.timer.getTime(1000L)) {
            return;
        }
        if (this.onGroundOnly.GetSwitch() && !this.mc.player.onGround) {
            return;
        }
        final ArrayList<HoleManager.HolePos> holes = (ArrayList<HoleManager.HolePos>)Ruby.holeManager.holes.stream().filter(holePos -> this.mc.player.getDistanceSq(holePos.pos) / 2.0 < this.holeRange.GetSlider()).collect(Collectors.toCollection(ArrayList::new));
        for (final HoleManager.HolePos holePos2 : holes) {
            final BlockPos pos = holePos2.pos;
            final String getCombo = this.dragMode.GetCombo();
            switch (getCombo) {
                case "Smooth": {
                    if (this.mc.player.posX > pos.up().x + 0.5) {
                        this.mc.player.motionX = -this.smoothSpeed.GetSlider() / 10.0f;
                    }
                    else if (this.mc.player.posX < pos.up().x + 0.5) {
                        this.mc.player.motionX = this.smoothSpeed.GetSlider() / 10.0f;
                    }
                    if (this.mc.player.posZ > pos.up().z + 0.5) {
                        this.mc.player.motionZ = -this.smoothSpeed.GetSlider() / 10.0f;
                        continue;
                    }
                    if (this.mc.player.posZ < pos.up().z + 0.5) {
                        this.mc.player.motionZ = this.smoothSpeed.GetSlider() / 10.0f;
                        continue;
                    }
                    continue;
                }
                case "Teleport": {
                    this.mc.player.setPosition(pos.up().x + 0.5, (double)pos.up().y, pos.up().z + 0.5);
                    continue;
                }
            }
        }
    }
}
