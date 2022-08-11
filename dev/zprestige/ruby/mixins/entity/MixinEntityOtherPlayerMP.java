//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.mixins.entity;

import net.minecraft.client.entity.*;
import net.minecraft.world.*;
import com.mojang.authlib.*;
import dev.zprestige.ruby.module.misc.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ EntityOtherPlayerMP.class })
public class MixinEntityOtherPlayerMP extends AbstractClientPlayer
{
    @Shadow
    private int otherPlayerMPPosRotationIncrements;
    @Shadow
    private double otherPlayerMPX;
    @Shadow
    private double otherPlayerMPY;
    @Shadow
    private double otherPlayerMPZ;
    @Shadow
    private double otherPlayerMPYaw;
    @Shadow
    private double otherPlayerMPPitch;
    
    public MixinEntityOtherPlayerMP(final World worldIn, final GameProfile gameProfileIn) {
        super(worldIn, gameProfileIn);
    }
    
    @Overwrite
    public void onLivingUpdate() {
        if (this.otherPlayerMPPosRotationIncrements > 0) {
            double f1;
            double d1;
            double d2;
            if (NoInterpolation.Instance.isEnabled()) {
                f1 = this.serverPosX / 4096.0;
                d1 = this.serverPosY / 4096.0;
                d2 = this.serverPosZ / 4096.0;
            }
            else {
                f1 = this.posX + (this.otherPlayerMPX - this.posX) / this.otherPlayerMPPosRotationIncrements;
                d1 = this.posY + (this.otherPlayerMPY - this.posY) / this.otherPlayerMPPosRotationIncrements;
                d2 = this.posZ + (this.otherPlayerMPZ - this.posZ) / this.otherPlayerMPPosRotationIncrements;
            }
            double d3;
            for (d3 = this.otherPlayerMPYaw - this.rotationYaw; d3 < -180.0; d3 += 360.0) {}
            while (d3 >= 180.0) {
                d3 -= 360.0;
            }
            this.rotationYaw += (float)(d3 / this.otherPlayerMPPosRotationIncrements);
            this.rotationPitch += (float)((this.otherPlayerMPPitch - this.rotationPitch) / this.otherPlayerMPPosRotationIncrements);
            --this.otherPlayerMPPosRotationIncrements;
            this.setPosition(f1, d1, d2);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
        this.prevCameraYaw = this.cameraYaw;
        this.updateArmSwingProgress();
        float f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        float f3 = (float)Math.atan(-this.motionY * 0.20000000298023224) * 15.0f;
        if (f2 > 0.1f) {
            f2 = 0.1f;
        }
        if (!this.onGround || this.getHealth() <= 0.0f) {
            f2 = 0.0f;
        }
        if (this.onGround || this.getHealth() <= 0.0f) {
            f3 = 0.0f;
        }
        this.cameraYaw += (f2 - this.cameraYaw) * 0.4f;
        this.cameraPitch += (f3 - this.cameraPitch) * 0.8f;
        this.world.profiler.startSection("push");
        this.collideWithNearbyEntities();
        this.world.profiler.endSection();
    }
}
