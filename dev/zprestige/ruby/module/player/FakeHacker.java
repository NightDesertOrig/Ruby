//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.player;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.util.*;
import java.util.*;

public class FakeHacker extends Module
{
    public static String target;
    public final Switch rotate;
    public final Switch swing;
    public EntityPlayer targetPlayer;
    
    public FakeHacker() {
        this.rotate = this.Menu.Switch("Rotate");
        this.swing = this.Menu.Switch("Swing");
        this.targetPlayer = null;
    }
    
    public void onEnable() {
        this.targetPlayer = null;
    }
    
    public void onTick() {
        if (FakeHacker.target.equals("zPrestige_")) {
            this.disableModule("Fuck u piece of shit nice try bitch");
            return;
        }
        EntityPlayer targetPlayer = null;
        for (final EntityPlayer entityPlayer : this.mc.world.playerEntities) {
            if (entityPlayer.getName().equals(FakeHacker.target)) {
                targetPlayer = entityPlayer;
            }
        }
        if (targetPlayer != null && this.mc.player.getDistance((Entity)targetPlayer) < this.mc.playerController.getBlockReachDistance()) {
            if (this.rotate.GetSwitch()) {
                final float[] angle = BlockUtil.calcAngle(targetPlayer.getPositionEyes(this.mc.getRenderPartialTicks()), this.mc.player.getPositionVector());
                targetPlayer.rotationYaw = angle[0];
                targetPlayer.rotationYawHead = angle[0];
                targetPlayer.rotationPitch = angle[1];
            }
            if (this.swing.GetSwitch()) {
                targetPlayer.swingArm(EnumHand.MAIN_HAND);
            }
        }
    }
    
    static {
        FakeHacker.target = "";
    }
}
