//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.ui.click;

import dev.zprestige.ruby.module.*;
import java.util.*;
import dev.zprestige.ruby.ui.click.setting.*;
import dev.zprestige.ruby.settings.impl.*;
import dev.zprestige.ruby.ui.click.setting.impl.*;
import dev.zprestige.ruby.module.client.*;
import dev.zprestige.ruby.util.*;
import dev.zprestige.ruby.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import com.mojang.realmsclient.gui.*;
import dev.zprestige.ruby.settings.*;

public class GuiModule
{
    public Module module;
    public int x;
    public int y;
    public int width;
    public int height;
    public int deltaY;
    public float animDeltaY;
    public boolean isOpened;
    public float animWidth;
    public float hoverAnimWidth;
    public ArrayList<Button> settings;
    protected float publicAnimHeight;
    
    public GuiModule(final Module module, final int x, final int y, final int width, final int height) {
        this.isOpened = false;
        this.settings = new ArrayList<Button>();
        this.module = module;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.animWidth = (module.isEnabled() ? ((float)width) : 0.0f);
        this.hoverAnimWidth = 0.0f;
        module.getSettings().stream().filter(setting -> !setting.getName().equals("Enabled")).forEach(setting -> {
            if (setting instanceof Switch) {
                this.settings.add(new SwitchButton((Switch)setting));
            }
            if (setting instanceof Slider) {
                this.settings.add(new SliderButton((Slider)setting));
            }
            if (setting instanceof Key) {
                this.settings.add(new KeyButton((Key)setting));
            }
            if (setting instanceof ColorBox) {
                this.settings.add(new ColorButton((ColorBox)setting));
            }
            if (setting instanceof ComboBox) {
                this.settings.add(new ComboBoxButton((ComboBox)setting));
            }
            if (setting instanceof ColorSwitch) {
                this.settings.add(new ColorSwitchButton((ColorSwitch)setting));
            }
            if (setting instanceof Parent) {
                this.settings.add(new ParentButton(setting));
            }
            return;
        });
        this.animDeltaY = (float)(height + 1);
    }
    
    public void drawScreen(final int mouseX, final int mouseY) {
        RenderUtil.drawRect((float)this.x, (float)this.y, (float)(this.x + this.width), (float)(this.y + this.height), ClickGui.Instance.backgroundColor.GetColor().getRGB());
        RenderUtil.drawOutlineRect(this.x, this.y, this.x + this.width, this.y + this.height, ClickGui.Instance.backgroundColor.GetColor(), 1.0f);
        if (this.module.isEnabled()) {
            this.animWidth = AnimationUtil.increaseNumber(this.animWidth, (float)this.width, MainScreen.getAnimDelta((float)this.width, this.animWidth));
        }
        else {
            this.animWidth = AnimationUtil.decreaseNumber(this.animWidth, 0.0f, MainScreen.getAnimDelta(0.0f, this.animWidth));
        }
        if (this.animWidth > 0.0f) {
            RenderUtil.drawRect((float)this.x, (float)this.y, this.x + this.animWidth, (float)(this.y + this.height), ClickGui.Instance.color.GetColor().getRGB());
        }
        Ruby.fontManager.drawStringWithShadow(this.module.getName(), (float)(this.x + (this.isInside(mouseX, mouseY) ? 2 : 1)), this.y + this.height / 2.0f - Ruby.fontManager.getFontHeight() / 2.0f, -1);
        if (this.isInside(mouseX, mouseY)) {
            this.hoverAnimWidth = AnimationUtil.increaseNumber(this.hoverAnimWidth, (float)this.width, MainScreen.getAnimDelta((float)this.width, this.hoverAnimWidth));
        }
        else {
            this.hoverAnimWidth = AnimationUtil.decreaseNumber(this.hoverAnimWidth, 0.0f, MainScreen.getAnimDelta(0.0f, this.hoverAnimWidth));
        }
        RenderUtil.drawRect((float)this.x, (float)this.y, this.x + this.hoverAnimWidth, (float)(this.y + this.height), new Color(0, 0, 0, 50).getRGB());
        this.deltaY = 0;
        if (this.isOpened) {
            final int y;
            final int deltaY;
            this.settings.stream().filter(setting -> setting.getSetting().openedParent()).forEach(setting -> {
                setting.setX(this.x + (setting.getSetting().hasParent() ? 3 : 2));
                y = this.y;
                deltaY = this.deltaY + (this.height + 1);
                setting.setY(y + (this.deltaY = deltaY));
                setting.setWidth(this.width - (setting.getSetting().hasParent() ? 6 : 4));
                setting.setHeight(this.height);
                return;
            });
            this.deltaY += this.height + 1;
            if (this.animDeltaY < this.deltaY) {
                this.animDeltaY = AnimationUtil.increaseNumber(this.animDeltaY, (float)this.deltaY, MainScreen.getAnimDelta((float)this.deltaY, this.animDeltaY));
            }
            else if (this.animDeltaY > this.deltaY) {
                this.animDeltaY = AnimationUtil.decreaseNumber(this.animDeltaY, (float)this.deltaY, MainScreen.getAnimDelta((float)this.deltaY, this.animDeltaY));
            }
        }
        else {
            this.animDeltaY = AnimationUtil.decreaseNumber(this.animDeltaY, (float)(this.height + 1), MainScreen.getAnimDelta((float)(this.height + 1), this.animDeltaY));
        }
        this.publicAnimHeight = this.animDeltaY;
        if (this.y + this.animDeltaY > this.y + this.height + 1) {
            GL11.glPushMatrix();
            GL11.glPushAttrib(524288);
            RenderUtil.scissor(this.x, this.y + this.height, this.x + 1000, this.y + (int)this.animDeltaY);
            GL11.glEnable(3089);
            RenderUtil.drawRect((float)this.x, (float)(this.y + this.height + 1), (float)(this.x + 1), this.y + this.animDeltaY, ClickGui.Instance.color.GetColor().getRGB());
            this.settings.stream().filter(setting -> setting.getSetting().openedParent()).forEach(setting -> setting.render(mouseX, mouseY));
            GL11.glDisable(3089);
            GL11.glPopAttrib();
            GL11.glPopMatrix();
        }
    }
    
    public void keyTyped(final char typedChar, final int keyCode) {
        if (this.isOpened) {
            this.settings.stream().filter(setting -> setting.getSetting().openedParent()).forEach(setting -> setting.type(typedChar, keyCode));
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isInside(mouseX, mouseY)) {
            switch (mouseButton) {
                case 0: {
                    if (this.module.isEnabled()) {
                        this.module.disableModule();
                        break;
                    }
                    this.module.enableModule();
                    break;
                }
                case 1: {
                    this.isOpened = !this.isOpened;
                    break;
                }
                case 2: {
                    this.module.drawn = !this.module.drawn;
                    Ruby.chatManager.sendMessage(ChatFormatting.WHITE + "" + ChatFormatting.BOLD + this.module.getName() + ChatFormatting.WHITE + " drawn: " + this.module.drawn + ".");
                    break;
                }
            }
        }
        if (this.isOpened) {
            this.settings.stream().filter(setting -> setting.getSetting().openedParent()).forEach(setting -> setting.click(mouseX, mouseY, mouseButton));
        }
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        if (this.isOpened) {
            this.settings.stream().filter(setting -> setting.getSetting().openedParent()).forEach(setting -> setting.release(mouseX, mouseY, state));
        }
    }
    
    public boolean isInside(final int mouseX, final int mouseY) {
        return mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height;
    }
    
    public float getAnimHeight() {
        return this.publicAnimHeight;
    }
}
