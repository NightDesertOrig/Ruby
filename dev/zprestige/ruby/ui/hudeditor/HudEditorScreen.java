//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.ui.hudeditor;

import dev.zprestige.ruby.*;
import java.util.*;
import net.minecraft.client.gui.*;
import dev.zprestige.ruby.module.client.*;
import dev.zprestige.ruby.util.*;
import dev.zprestige.ruby.ui.hudeditor.components.*;

public class HudEditorScreen extends GuiScreen
{
    protected final ArrayList<HudComponentScreen> hudComponentScreens;
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected float dragX;
    protected float dragY;
    protected boolean corrected;
    protected boolean dragging;
    
    public HudEditorScreen() {
        this.hudComponentScreens = new ArrayList<HudComponentScreen>();
        this.width = 100.0f;
        this.height = 13.0f;
        Ruby.hudManager.getHudComponents().forEach(hudComponent -> this.hudComponentScreens.add(new HudComponentScreen(hudComponent, 0.0f, 0.0f, 100.0f, 13.0f)));
    }
    
    protected void drag(final int mouseX, final int mouseY) {
        this.x = this.dragX + mouseX;
        this.y = this.dragY + mouseY;
        float deltaY = this.y;
        for (final HudComponentScreen hudComponentScreen : this.hudComponentScreens) {
            hudComponentScreen.setY(deltaY += 14.0f);
            hudComponentScreen.setX(this.x);
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.dragging) {
            this.drag(mouseX, mouseY);
        }
        if (!this.corrected) {
            final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
            this.x = scaledResolution.getScaledWidth() / 2.0f - 50.0f;
            this.y = scaledResolution.getScaledHeight() / 2.0f - this.hudComponentScreens.size() * 7.5f - 7.5f;
            float deltaY = this.y;
            for (final HudComponentScreen hudComponentScreen2 : this.hudComponentScreens) {
                hudComponentScreen2.setY(deltaY += 14.0f);
                hudComponentScreen2.setX(this.x);
            }
            this.corrected = true;
        }
        RenderUtil.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, ClickGui.Instance.color.GetColor().getRGB());
        float deltaY2 = this.y;
        for (final HudComponentScreen ignored : this.hudComponentScreens) {
            deltaY2 += 14.0f;
        }
        RenderUtil.drawOutlineRect(this.x, this.y, this.x + this.width, deltaY2, ClickGui.Instance.backgroundColor.GetColor(), 1.0f);
        RenderUtil.drawRect(this.x, this.y, this.x + this.width, deltaY2, ClickGui.Instance.backgroundColor.GetColor().getRGB());
        RenderUtil.drawOutlineRect(this.x, this.y, this.x + this.width, this.y + this.height, ClickGui.Instance.backgroundColor.GetColor(), 1.0f);
        final String text = "HudEditor";
        Ruby.fontManager.drawStringWithShadow("HudEditor", this.x + this.width / 2.0f - Ruby.fontManager.getStringWidth("HudEditor") / 2.0f, this.y + this.height / 2.0f - Ruby.fontManager.getFontHeight() / 2.0f, -1);
        this.hudComponentScreens.forEach(hudComponentScreen -> hudComponentScreen.draw(mouseX, mouseY));
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.inside(mouseX, mouseY) && mouseButton == 0) {
            this.dragX = this.x - mouseX;
            this.dragY = this.y - mouseY;
            this.dragging = true;
        }
        this.hudComponentScreens.forEach(hudComponentScreen -> hudComponentScreen.click(mouseX, mouseY, mouseButton));
    }
    
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        if (state == 0) {
            this.dragging = false;
        }
        this.hudComponentScreens.forEach(hudComponentScreen -> hudComponentScreen.release(state));
    }
    
    protected boolean inside(final int mouseX, final int mouseY) {
        return mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height;
    }
}
