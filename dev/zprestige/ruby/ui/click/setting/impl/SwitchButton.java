//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.ui.click.setting.impl;

import dev.zprestige.ruby.ui.click.setting.*;
import dev.zprestige.ruby.settings.impl.*;
import dev.zprestige.ruby.settings.*;
import dev.zprestige.ruby.module.client.*;
import dev.zprestige.ruby.util.*;
import dev.zprestige.ruby.*;

public class SwitchButton extends Button
{
    protected Switch aSwitch;
    
    public SwitchButton(final Switch setting) {
        super((Setting)setting);
        this.aSwitch = setting;
    }
    
    public void render(final int mouseX, final int mouseY) {
        super.render(mouseX, mouseY);
        RenderUtil.drawOutlineRect(this.x + this.width - 11, this.y + 2, this.x + this.width - 2, this.y + this.height - 2, ClickGui.Instance.color.GetColor(), 1.0f);
        if (this.aSwitch.GetSwitch()) {
            RenderUtil.drawRect((float)(this.x + this.width - 10), (float)(this.y + 3), (float)(this.x + this.width - 3), (float)(this.y + this.height - 3), ClickGui.Instance.color.GetColor().getRGB());
            RenderUtil.drawOutlineRect(this.x + this.width - 10, this.y + 3, this.x + this.width - 3, this.y + this.height - 3, ClickGui.Instance.backgroundColor.GetColor(), 1.0f);
        }
        final String name = this.aSwitch.getName();
        Ruby.fontManager.drawStringWithShadow(name, (float)(this.x + 2), this.y + this.height / 2.0f - Ruby.fontManager.getFontHeight() / 2.0f, -1);
        this.hover(mouseX, mouseY);
    }
    
    public void click(final int mouseX, final int mouseY, final int clickedButton) {
        if (clickedButton == 0 && this.insideBox(mouseX, mouseY)) {
            this.aSwitch.setValue(!this.aSwitch.GetSwitch());
        }
    }
    
    protected boolean insideBox(final int mouseX, final int mouseY) {
        return mouseX > this.x + this.width - 12 && mouseX < this.x + this.width - 2 && mouseY > this.y + 2 && mouseY < this.y + this.height - 2;
    }
}
