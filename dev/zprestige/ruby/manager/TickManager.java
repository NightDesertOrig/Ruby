//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.manager;

import dev.zprestige.ruby.*;
import net.minecraft.util.math.*;
import dev.zprestige.ruby.module.client.*;
import dev.zprestige.ruby.events.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class TickManager
{
    public final float[] TPS;
    public long prevTime;
    public int currentTick;
    
    public TickManager() {
        this.TPS = new float[20];
        this.prevTime = -1L;
        for (int i = 0, len = this.TPS.length; i < len; ++i) {
            this.TPS[i] = 0.0f;
        }
        Ruby.eventBus.register((Object)this);
    }
    
    public float getTPS() {
        int tickCount = 0;
        float tickRate = 0.0f;
        for (final float tick : this.TPS) {
            if (tick > 0.0f) {
                tickRate += tick;
                ++tickCount;
            }
        }
        return Ruby.mc.isSingleplayer() ? 20.0f : Hud.roundNumber(MathHelper.clamp(tickRate / tickCount, 0.0f, 20.0f), 2);
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.PacketReceiveEvent event) {
        if (event.getPacket() instanceof SPacketTimeUpdate) {
            if (this.prevTime != -1L) {
                this.TPS[this.currentTick % this.TPS.length] = MathHelper.clamp(20.0f / ((System.currentTimeMillis() - this.prevTime) / 1000.0f), 0.0f, 20.0f);
                ++this.currentTick;
            }
            this.prevTime = System.currentTimeMillis();
        }
    }
}
