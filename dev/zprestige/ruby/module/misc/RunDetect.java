//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.misc;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import dev.zprestige.ruby.util.*;
import java.util.*;
import net.minecraft.util.math.*;

public class RunDetect extends Module
{
    public static RunDetect Instance;
    public final Slider radius;
    public ArrayList<EntityPlayer> potentialRunnersList;
    public ArrayList<EntityPlayer> swordedPotentialRunnersList;
    public ArrayList<EntityPlayer> gappledPreviouslySwordedPotentialRunnerList;
    
    public RunDetect() {
        this.radius = this.Menu.Slider("Radius", 0.1f, 15.0f);
        this.potentialRunnersList = new ArrayList<EntityPlayer>();
        this.swordedPotentialRunnersList = new ArrayList<EntityPlayer>();
        this.gappledPreviouslySwordedPotentialRunnerList = new ArrayList<EntityPlayer>();
        RunDetect.Instance = this;
    }
    
    @Override
    public void onTick() {
        this.mc.world.playerEntities.stream().filter(player -> !player.equals((Object)this.mc.player) && !this.potentialRunnersList.contains(player) && this.mc.player.getDistanceSq(EntityUtil.getPlayerPos(player)) < this.radius.GetSlider() * this.radius.GetSlider()).forEach(player -> this.potentialRunnersList.add(player));
        this.potentialRunnersList.stream().filter(entityPlayer -> !this.swordedPotentialRunnersList.contains(entityPlayer) && entityPlayer.getHeldItemMainhand().getItem().equals(Items.DIAMOND_SWORD)).forEach(entityPlayer -> this.swordedPotentialRunnersList.add(entityPlayer));
        this.swordedPotentialRunnersList.stream().filter(entityPlayer -> !this.gappledPreviouslySwordedPotentialRunnerList.contains(entityPlayer) && entityPlayer.getHeldItemMainhand().getItem().equals(Items.GOLDEN_APPLE)).forEach(entityPlayer -> this.gappledPreviouslySwordedPotentialRunnerList.add(entityPlayer));
        this.potentialRunnersList.stream().filter(entityPlayer -> this.mc.player.getDistanceSq(EntityUtil.getPlayerPos(entityPlayer)) > this.radius.GetSlider() * this.radius.GetSlider()).findFirst().ifPresent(entityPlayer -> this.potentialRunnersList.remove(entityPlayer));
        this.swordedPotentialRunnersList.stream().filter(entityPlayer -> this.mc.player.getDistanceSq(EntityUtil.getPlayerPos(entityPlayer)) > this.radius.GetSlider() * this.radius.GetSlider()).findFirst().ifPresent(entityPlayer -> this.potentialRunnersList.remove(entityPlayer));
    }
    
    @Override
    public void onFrame(final float partialTicks) {
        for (final EntityPlayer entityPlayer : this.gappledPreviouslySwordedPotentialRunnerList) {
            GL11.glPushMatrix();
            final Vec3d i = RenderUtil.interpolateEntity((Entity)entityPlayer);
            RenderUtil.drawNametag("Potentially running.", i.x, i.y + 1.0, i.z, 0.005, -1);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glPopMatrix();
            if (!BlockUtil.isPlayerSafe(entityPlayer) || !entityPlayer.getHeldItemMainhand().getItem().equals(Items.GOLDEN_APPLE) || this.mc.player.getDistanceSq(EntityUtil.getPlayerPos(entityPlayer)) > this.radius.GetSlider() * this.radius.GetSlider()) {
                this.potentialRunnersList.remove(entityPlayer);
                this.swordedPotentialRunnersList.remove(entityPlayer);
                this.gappledPreviouslySwordedPotentialRunnerList.remove(entityPlayer);
            }
        }
    }
}
