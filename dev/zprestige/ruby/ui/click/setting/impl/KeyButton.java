//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.ui.click.setting.impl;

import dev.zprestige.ruby.ui.click.setting.*;
import dev.zprestige.ruby.settings.impl.*;
import dev.zprestige.ruby.settings.*;
import dev.zprestige.ruby.*;
import dev.zprestige.ruby.ui.click.*;
import org.lwjgl.input.*;
import java.awt.*;

public class KeyButton extends Button
{
    protected Key key;
    protected boolean opened;
    
    public KeyButton(final Key setting) {
        super((Setting)setting);
        this.key = setting;
    }
    
    public void render(final int mouseX, final int mouseY) {
        super.render(mouseX, mouseY);
        final String name = this.key.getName();
        Ruby.fontManager.drawStringWithShadow(name, (float)(this.x + 2), this.getStringMiddle(name), -1);
        final String closedText = this.opened ? (" " + MainScreen.idleSign()) : ((this.key.GetKey() == -1) ? "None" : Keyboard.getKeyName(this.key.GetKey()));
        final float nameWidth = (float)Ruby.fontManager.getStringWidth(name + " ");
        Ruby.fontManager.drawStringWithShadow(closedText, this.x + 2 + nameWidth, this.getStringMiddle(closedText), Color.GRAY.getRGB());
        this.hover(mouseX, mouseY);
    }
    
    public void click(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0 && this.isInside(mouseX, mouseY)) {
            this.opened = !this.opened;
        }
    }
    
    public void type(final char typedChar, final int keyCode) {
        if (this.opened) {
            this.key.setValue((keyCode == 211 || keyCode == 1) ? -1 : keyCode);
            this.opened = !this.opened;
        }
    }
}
