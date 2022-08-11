//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.ui.click;

import dev.zprestige.ruby.*;
import java.util.*;
import dev.zprestige.ruby.module.client.*;
import dev.zprestige.ruby.util.*;
import org.lwjgl.opengl.*;
import org.lwjgl.input.*;
import dev.zprestige.ruby.module.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;

public class GuiCategory
{
    public Category category;
    public int x;
    public int y;
    public int width;
    public int height;
    public int dragX;
    public int dragY;
    public int deltaY;
    public int targetAnim;
    public float animHeight;
    public boolean isDragging;
    public boolean isOpened;
    public ArrayList<GuiModule> guiModules;
    
    public GuiCategory(final Category category, final int x, final int y, final int width, final int height) {
        this.isOpened = true;
        this.guiModules = new ArrayList<GuiModule>();
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.deltaY = y;
        final ArrayList<GuiModule> guiModules;
        final int deltaY;
        final GuiModule e;
        final int x2;
        Ruby.moduleManager.getModulesInCategory(category).forEach(module -> {
            guiModules = this.guiModules;
            // new(dev.zprestige.ruby.ui.click.GuiModule.class)
            deltaY = this.deltaY + (height + 1);
            new GuiModule(module, x2, this.deltaY = deltaY, width - 2, height);
            guiModules.add(e);
            return;
        });
        Ruby.moduleManager.getModulesInCategory(category).forEach(module -> module.scrollY = 0);
    }
    
    public void dragScreen(final int mouseX, final int mouseY) {
        if (!this.isDragging) {
            return;
        }
        this.x = this.dragX + mouseX;
        this.y = this.dragY + mouseY;
        this.deltaY = this.y;
        for (final GuiModule newModule : this.guiModules) {
            newModule.x = this.x + 1;
            final GuiModule guiModule = newModule;
            final int scrollY = newModule.module.scrollY;
            final int deltaY = this.deltaY + (this.height + 1);
            this.deltaY = deltaY;
            guiModule.y = scrollY + deltaY;
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY) {
        this.dragScreen(mouseX, mouseY);
        if (this.isInsideFull(mouseX, mouseY)) {
            this.setScroll();
        }
        this.deltaY = this.y + this.height + 1;
        this.guiModules.forEach(newModule -> {
            newModule.y = newModule.module.scrollY + this.deltaY;
            this.deltaY += (int)newModule.getAnimHeight();
            return;
        });
        RenderUtil.drawRect((float)this.x, (float)this.y, (float)(this.x + this.width), (float)(this.y + this.height), ClickGui.Instance.color.GetColor().getRGB());
        Ruby.fontManager.drawStringWithShadow(this.category.toString(), this.x + this.width / 2.0f - Ruby.fontManager.getStringWidth(this.category.toString()) / 2.0f, this.y + this.height / 2.0f - Ruby.fontManager.getFontHeight() / 2.0f, -1);
        RenderUtil.drawRect((float)this.x, (float)(this.y + this.height), (float)(this.x + this.width), this.animHeight, ClickGui.Instance.backgroundColor.GetColor().getRGB());
        RenderUtil.drawOutlineRect(this.x, this.y, this.x + this.width, this.animHeight, ClickGui.Instance.backgroundColor.GetColor(), 1.0f);
        if (ClickGui.Instance.icons.GetSwitch()) {
            this.drawCategoryIcon();
        }
        if (this.isOpened) {
            this.targetAnim = this.deltaY;
            if (this.animHeight < this.targetAnim) {
                this.animHeight = AnimationUtil.increaseNumber(this.animHeight, (float)this.targetAnim, MainScreen.getAnimDelta((float)this.targetAnim, this.animHeight));
            }
            else if (this.animHeight > this.targetAnim) {
                this.animHeight = AnimationUtil.decreaseNumber(this.animHeight, (float)this.targetAnim, MainScreen.getAnimDelta((float)this.targetAnim, this.animHeight));
            }
        }
        else {
            this.targetAnim = this.y + this.height;
            this.animHeight = AnimationUtil.decreaseNumber(this.animHeight, (float)this.targetAnim, MainScreen.getAnimDelta((float)this.targetAnim, this.animHeight));
        }
        GL11.glPushMatrix();
        GL11.glPushAttrib(524288);
        RenderUtil.scissor(this.x, this.y + this.height, this.x + 1000, (int)this.animHeight);
        GL11.glEnable(3089);
        this.guiModules.forEach(newModule -> newModule.drawScreen(mouseX, mouseY));
        GL11.glDisable(3089);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }
    
    public void setScroll() {
        final int dWheel = Mouse.getDWheel();
        for (final Module module : Ruby.moduleManager.getModulesInCategory(this.category)) {
            if (dWheel < 0) {
                final Module module2 = module;
                module2.scrollY -= (int)ClickGui.Instance.scrollSpeed.GetSlider();
            }
            else {
                if (dWheel <= 0) {
                    continue;
                }
                final Module module3 = module;
                module3.scrollY += (int)ClickGui.Instance.scrollSpeed.GetSlider();
            }
        }
    }
    
    public boolean isInsideFull(final int mouseX, final int mouseY) {
        return mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y + this.height && mouseY < this.animHeight;
    }
    
    public void keyTyped(final char typedChar, final int keyCode) {
        if (this.isOpened) {
            this.guiModules.forEach(newModule -> newModule.keyTyped(typedChar, keyCode));
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isInside(mouseX, mouseY)) {
            switch (mouseButton) {
                case 0: {
                    this.dragX = this.x - mouseX;
                    this.dragY = this.y - mouseY;
                    this.isDragging = true;
                    break;
                }
                case 1: {
                    this.isOpened = !this.isOpened;
                    break;
                }
            }
        }
        if (this.isOpened) {
            this.guiModules.forEach(newModule -> newModule.mouseClicked(mouseX, mouseY, mouseButton));
        }
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        if (state == 0) {
            this.isDragging = false;
        }
        if (this.isOpened) {
            this.guiModules.forEach(newModule -> newModule.mouseReleased(mouseX, mouseY, state));
        }
    }
    
    public boolean isInside(final int mouseX, final int mouseY) {
        return mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height;
    }
    
    public void drawCategoryIcon() {
        GlStateManager.enableAlpha();
        Ruby.mc.getTextureManager().bindTexture(new ResourceLocation("textures/icons/" + this.category.toString().toLowerCase() + ".png"));
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        GL11.glPushMatrix();
        GuiScreen.drawScaledCustomSizeModalRect((int)(this.x + this.width / 2.0f - Ruby.fontManager.getStringWidth(this.category.toString()) / 2.0f - 14.0f), this.y + 1, 0.0f, 0.0f, 13, 12, 13, 12, 13.0f, 12.0f);
        GlStateManager.disableAlpha();
        GL11.glPopMatrix();
    }
}
