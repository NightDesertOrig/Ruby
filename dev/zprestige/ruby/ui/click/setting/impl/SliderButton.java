//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.ui.click.setting.impl;

import dev.zprestige.ruby.ui.click.setting.*;
import dev.zprestige.ruby.settings.impl.*;
import dev.zprestige.ruby.settings.*;
import dev.zprestige.ruby.module.client.*;
import dev.zprestige.ruby.util.*;
import dev.zprestige.ruby.*;
import java.awt.*;
import org.lwjgl.input.*;
import java.math.*;

public class SliderButton extends Button
{
    protected Slider slider;
    protected int extension;
    
    public SliderButton(final Slider setting) {
        super((Setting)setting);
        this.slider = setting;
    }
    
    public void render(final int mouseX, final int mouseY) {
        super.render(mouseX, mouseY);
        this.dragSlider(mouseX, mouseY);
        final float sliderWidth = this.width * this.sliderWidthValue();
        RenderUtil.drawRect((float)this.x, (float)this.y, this.x + sliderWidth, (float)(this.y + this.height), ClickGui.Instance.color.GetColor().getRGB());
        final String name = this.slider.getName();
        Ruby.fontManager.drawStringWithShadow(name, (float)(this.x + 2), this.getStringMiddle(name), -1);
        Ruby.fontManager.drawStringWithShadow(this.slider.GetSlider() + "", (float)(this.x + 2 + Ruby.fontManager.getStringWidth(name + " ")), this.getStringMiddle(name), Color.GRAY.getRGB());
        this.hover(mouseX, mouseY);
    }
    
    protected float sliderWidthValue() {
        return (this.slider.GetSlider() - this.slider.getMin()) / (this.slider.getMax() - this.slider.getMin());
    }
    
    protected void dragSlider(final int mouseX, final int mouseY) {
        if (this.isInsideExtended(mouseX, mouseY) && Mouse.isButtonDown(0)) {
            this.setSliderValue(mouseX);
            this.extension = 400;
        }
        else {
            this.extension = 0;
        }
    }
    
    protected void setSliderValue(final int mouseX) {
        this.slider.setValue(this.slider.getMin());
        final float diff = (float)Math.min(this.width, Math.max(0, mouseX - this.x));
        final float min = this.slider.getMin();
        final float max = this.slider.getMax();
        this.slider.setValue((diff == 0.0f) ? this.slider.getMin() : this.roundNumber(diff / this.width * (max - min) + min));
    }
    
    protected float roundNumber(final double value) {
        BigDecimal decimal = BigDecimal.valueOf(value);
        decimal = decimal.setScale(1, RoundingMode.FLOOR);
        return decimal.floatValue();
    }
    
    protected boolean isInsideExtended(final int mouseX, final int mouseY) {
        return mouseX > this.x - this.extension && mouseX < this.x + this.width + this.extension && mouseY > this.y - this.extension && mouseY < this.y + this.height + this.extension;
    }
}
