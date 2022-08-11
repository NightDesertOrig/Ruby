//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.mixins.loader;

import net.minecraftforge.fml.relauncher.*;
import org.spongepowered.asm.launch.*;
import org.spongepowered.asm.mixin.*;
import java.util.*;

public class MixinLoader implements IFMLLoadingPlugin
{
    public MixinLoader() {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.ruby.json");
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
    }
    
    public String[] getASMTransformerClass() {
        return new String[0];
    }
    
    public String getModContainerClass() {
        return null;
    }
    
    public String getSetupClass() {
        return null;
    }
    
    public String getAccessTransformerClass() {
        return null;
    }
    
    public void injectData(final Map<String, Object> data) {
    }
}
