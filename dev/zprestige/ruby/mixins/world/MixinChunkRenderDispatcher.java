//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.mixins.world;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.renderer.chunk.*;
import dev.zprestige.ruby.module.visual.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ ChunkRenderDispatcher.class })
public class MixinChunkRenderDispatcher
{
    @Inject(method = { "getNextChunkUpdate" }, at = { @At("HEAD") })
    protected void limitChunkUpdates(final CallbackInfoReturnable<ChunkCompileTaskGenerator> cir) throws InterruptedException {
        if (WorldTweaks.Instance.isEnabled()) {
            Thread.sleep((long)WorldTweaks.Instance.chunkLoadDelay.GetSlider());
        }
    }
}
