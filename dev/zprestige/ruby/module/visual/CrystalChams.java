//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.visual;

import dev.zprestige.ruby.module.*;
import net.minecraft.util.*;
import dev.zprestige.ruby.settings.impl.*;

public class CrystalChams extends Module
{
    public static CrystalChams Instance;
    public static ResourceLocation ENCHANTED_ITEM_GLINT_RES;
    public final Switch glint;
    public final Switch glintDepth;
    public final Slider glintSpeed;
    public final Slider glintScale;
    public final ColorBox glintColor;
    public final Switch fill;
    public final Switch fillDepth;
    public final Switch fillLighting;
    public final ColorBox fillColor;
    public final Switch outline;
    public final Switch outlineDepth;
    public final Slider outlineWidth;
    public final ColorBox outlineColor;
    public final Slider scale;
    public final Slider rotationSpeed;
    public final Slider verticalSpeed;
    
    public CrystalChams() {
        this.glint = this.Menu.Switch("Glint");
        this.glintDepth = this.Menu.Switch("Glint Depth");
        this.glintSpeed = this.Menu.Slider("Glint Speed", 0.1f, 20.0f);
        this.glintScale = this.Menu.Slider("Glint Scale", 0.1f, 10.0f);
        this.glintColor = this.Menu.Color("Glint Color");
        this.fill = this.Menu.Switch("Fill");
        this.fillDepth = this.Menu.Switch("Fill Depth");
        this.fillLighting = this.Menu.Switch("Fill Lighting");
        this.fillColor = this.Menu.Color("Fill Color");
        this.outline = this.Menu.Switch("Outline");
        this.outlineDepth = this.Menu.Switch("Outline Depth");
        this.outlineWidth = this.Menu.Slider("Outline Width", 0.1f, 10.0f);
        this.outlineColor = this.Menu.Color("Outline Color");
        this.scale = this.Menu.Slider("Scale", 0.1f, 10.0f);
        this.rotationSpeed = this.Menu.Slider("Rotation Speed", 0.0f, 10.0f);
        this.verticalSpeed = this.Menu.Slider("Vertical Speed", 0.0f, 10.0f);
        CrystalChams.Instance = this;
    }
    
    static {
        CrystalChams.ENCHANTED_ITEM_GLINT_RES = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    }
}
