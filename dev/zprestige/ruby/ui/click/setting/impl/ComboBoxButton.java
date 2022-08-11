//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.ui.click.setting.impl;

import dev.zprestige.ruby.ui.click.setting.*;
import dev.zprestige.ruby.settings.impl.*;
import dev.zprestige.ruby.settings.*;
import dev.zprestige.ruby.*;
import java.awt.*;
import dev.zprestige.ruby.module.client.*;
import dev.zprestige.ruby.util.*;
import java.util.stream.*;

public class ComboBoxButton extends Button
{
    protected ComboBox comboBox;
    
    public ComboBoxButton(final ComboBox setting) {
        super((Setting)setting);
        this.comboBox = setting;
    }
    
    public void render(final int mouseX, final int mouseY) {
        super.render(mouseX, mouseY);
        final String name = this.comboBox.getName();
        final float middleHeight = this.getStringMiddle(name);
        Ruby.fontManager.drawStringWithShadow(name, (float)this.x, middleHeight, -1);
        final String value = this.comboBox.GetCombo();
        Ruby.fontManager.drawStringWithShadow(value, (float)(this.x + Ruby.fontManager.getStringWidth(name + "  ")), this.getStringMiddle(value), Color.GRAY.getRGB());
        float start = this.x + this.width / 2.0f;
        for (final String ignored : this.comboBox.getValues()) {
            start -= 3.0f;
        }
        float deltaX = start;
        for (final String string : this.comboBox.getValues()) {
            RenderUtil.drawRect(deltaX, (float)(this.y + this.height - 1), deltaX + 5.0f, (float)(this.y + this.height), ClickGui.Instance.color.GetColor().getRGB());
            if (this.comboBox.GetCombo().equals(string)) {
                RenderUtil.drawOutlineRect(deltaX, this.y + this.height - 1, deltaX + 5.0f, this.y + this.height, Color.WHITE, 1.0f);
            }
            deltaX += 6.0f;
        }
    }
    
    public int getIndex() {
        return IntStream.range(0, this.comboBox.getValues().length).filter(i -> this.comboBox.getValues()[i].equals(this.comboBox.GetCombo())).findFirst().orElse(-1);
    }
    
    public void click(final int mouseX, final int mouseY, final int clickedButton) {
        if (this.isInside(mouseX, mouseY)) {
            final int max = this.comboBox.getValues().length;
            int index = this.getIndex();
            switch (clickedButton) {
                case 0: {
                    if (index + 1 >= max) {
                        index = 0;
                    }
                    else {
                        ++index;
                    }
                    try {
                        this.comboBox.setValue(this.comboBox.getValues()[index]);
                    }
                    catch (Exception ex) {}
                    break;
                }
                case 1: {
                    if (index - 1 < 0) {
                        index = max - 1;
                    }
                    else {
                        --index;
                    }
                    try {
                        this.comboBox.setValue(this.comboBox.getValues()[index]);
                    }
                    catch (Exception ex2) {}
                    break;
                }
            }
        }
    }
}
