//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.util.shader;

import org.lwjgl.opengl.*;
import dev.zprestige.ruby.*;

public class ItemShader extends FramebufferShader
{
    public static ItemShader Instance;
    public float mix;
    public float alpha;
    
    public ItemShader() {
        super("itemglow.frag");
        this.mix = 0.0f;
        this.alpha = 1.0f;
    }
    
    public void setupUniforms() {
        this.setupUniform("texture");
        this.setupUniform("texelSize");
        this.setupUniform("color");
        this.setupUniform("divider");
        this.setupUniform("radius");
        this.setupUniform("maxSample");
        this.setupUniform("dimensions");
        this.setupUniform("mixFactor");
        this.setupUniform("minAlpha");
    }
    
    public void updateUniforms() {
        GL20.glUniform1i(this.getUniform("texture"), 0);
        GL20.glUniform2f(this.getUniform("texelSize"), 1.0f / Ruby.mc.displayWidth * (this.radius * this.quality), 1.0f / Ruby.mc.displayHeight * (this.radius * this.quality));
        GL20.glUniform3f(this.getUniform("color"), this.red, this.green, this.blue);
        GL20.glUniform1f(this.getUniform("divider"), 140.0f);
        GL20.glUniform1f(this.getUniform("radius"), this.radius);
        GL20.glUniform1f(this.getUniform("maxSample"), 10.0f);
        GL20.glUniform2f(this.getUniform("dimensions"), (float)Ruby.mc.displayWidth, (float)Ruby.mc.displayHeight);
        GL20.glUniform1f(this.getUniform("mixFactor"), this.mix);
        GL20.glUniform1f(this.getUniform("minAlpha"), this.alpha);
    }
    
    static {
        ItemShader.Instance = new ItemShader();
    }
}
