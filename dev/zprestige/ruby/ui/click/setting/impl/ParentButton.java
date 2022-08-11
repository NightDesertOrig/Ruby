//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.ui.click.setting.impl;

import dev.zprestige.ruby.ui.click.setting.*;
import dev.zprestige.ruby.settings.impl.*;
import dev.zprestige.ruby.settings.*;
import dev.zprestige.ruby.module.client.*;
import dev.zprestige.ruby.util.*;
import dev.zprestige.ruby.*;

public class ParentButton extends Button
{
    protected Parent parent;
    
    public ParentButton(final Parent setting) {
        super((Setting)setting);
        this.parent = setting;
    }
    
    public void render(final int mouseX, final int mouseY) {
        super.render(mouseX, mouseY);
        RenderUtil.drawRect((float)this.x, (float)this.y, (float)(this.x + this.width), (float)(this.y + this.height), ClickGui.Instance.color.GetColor().getRGB());
        Ruby.fontManager.drawStringWithShadow(this.parent.getName(), this.x + this.width / 2.0f - Ruby.fontManager.getStringWidth(this.parent.getName()) / 2.0f, this.getStringMiddle(this.parent.getName()), -1);
        Ruby.fontManager.drawStringWithShadow("...", (float)(this.x + this.width - 6), this.y + this.height - Ruby.fontManager.getFontHeight() - 3.0f, -1);
        final int i = this.parent.getChildren().stream().mapToInt(setting -> this.height + 1).sum();
        RenderUtil.drawOutlineRect(this.x, this.y, this.x + this.width, this.y + this.height + (this.parent.GetParent() ? (i + 1) : 0), ClickGui.Instance.color.GetColor(), 2.0f);
        this.hover(mouseX, mouseY);
    }
    
    public void click(final int mouseX, final int mouseY, final int clickedButton) {
        if (this.isInside(mouseX, mouseY) && clickedButton == 1) {
            this.parent.setValue(!this.parent.GetParent());
        }
    }
}
