//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.mixins.minecraft;

import net.minecraft.client.settings.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import dev.zprestige.ruby.events.*;
import dev.zprestige.ruby.*;
import dev.zprestige.ruby.eventbus.event.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ KeyBinding.class })
public class MixinKey
{
    @Shadow
    public boolean pressed;
    
    @Inject(method = { "isKeyDown" }, at = { @At("RETURN") }, cancellable = true)
    protected void isKeyDown(final CallbackInfoReturnable<Boolean> info) {
        final KeyEvent event = new KeyEvent((boolean)info.getReturnValue(), this.pressed);
        Ruby.eventBus.post((Event)event);
        info.setReturnValue((Object)event.info);
    }
}
