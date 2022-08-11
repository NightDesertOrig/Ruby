//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.mixins.entity;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import dev.zprestige.ruby.*;
import dev.zprestige.ruby.events.*;
import dev.zprestige.ruby.eventbus.event.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ PlayerControllerMP.class })
public class MixinPlayerControllerMP
{
    @Inject(method = { "clickBlock" }, at = { @At("HEAD") }, cancellable = true)
    protected void clickBlockHook(final BlockPos pos, final EnumFacing face, final CallbackInfoReturnable<Boolean> info) {
        Ruby.eventBus.post((Event)new BlockInteractEvent.ClickBlock(pos, face));
    }
    
    @Inject(method = { "onPlayerDamageBlock" }, at = { @At("HEAD") }, cancellable = true)
    private void onPlayerDamageBlockHook(final BlockPos pos, final EnumFacing face, final CallbackInfoReturnable<Boolean> info) {
        Ruby.eventBus.post((Event)new BlockInteractEvent.DamageBlock(pos, face));
    }
}
