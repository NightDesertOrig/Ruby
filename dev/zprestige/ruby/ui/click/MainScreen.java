//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.ui.click;

import dev.zprestige.ruby.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import dev.zprestige.ruby.util.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import dev.zprestige.ruby.module.client.*;
import dev.zprestige.ruby.module.*;

public class MainScreen extends GuiScreen
{
    public static Timer timer;
    public ArrayList<GuiCategory> guiCategories;
    public int deltaX;
    public HashMap<Float, String> copyPasteMap;
    
    public MainScreen() {
        this.guiCategories = new ArrayList<GuiCategory>();
        this.copyPasteMap = new HashMap<Float, String>();
        this.deltaX = 26;
        final ArrayList<GuiCategory> guiCategories;
        final int deltaX;
        final GuiCategory e;
        Ruby.moduleManager.getCategories().forEach(category -> {
            guiCategories = this.guiCategories;
            // new(dev.zprestige.ruby.ui.click.GuiCategory.class)
            deltaX = this.deltaX + 101;
            new GuiCategory(category, this.deltaX = deltaX, 2, 100, 13);
            guiCategories.add(e);
        });
    }
    
    public static String idleSign() {
        if (MainScreen.timer.getTime(1000L)) {
            MainScreen.timer.setTime(0);
        }
        if (MainScreen.timer.getTime(500L)) {
            return "_";
        }
        return "";
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        RenderUtil.image(new ResourceLocation("textures/icons/ruby.png"), scaledResolution.getScaledWidth() / 2 - 34, 511, 68, 28);
        GlStateManager.disableAlpha();
        this.guiCategories.forEach(newCategory -> newCategory.drawScreen(mouseX, mouseY));
        if (!this.copyPasteMap.isEmpty()) {
            final float centerX = scaledResolution.getScaledWidth() / 2.0f;
            final float height = (float)scaledResolution.getScaledHeight();
            float deltaY = height - height / 4.0f;
            for (final Map.Entry<Float, String> entry : new HashMap<Float, String>(this.copyPasteMap).entrySet()) {
                if (entry.getKey() <= 50.0f) {
                    this.copyPasteMap.remove(entry.getKey());
                }
                else {
                    final String entryValue = entry.getValue();
                    Ruby.fontManager.drawStringWithShadow(entryValue, centerX - Ruby.fontManager.getStringWidth(entryValue) / 2.0f, deltaY -= Math.min(10.0f, entry.getKey() / 5.0f), new Color(1.0f, 1.0f, 1.0f, entry.getKey() / 255.0f).getRGB());
                    this.copyPasteMap.put(entry.getKey() - entry.getKey() / 100.0f, entryValue);
                    this.copyPasteMap.remove(entry.getKey());
                }
            }
        }
    }
    
    public void keyTyped(final char typedChar, final int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        this.guiCategories.forEach(newCategory -> newCategory.keyTyped(typedChar, keyCode));
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        this.guiCategories.forEach(newCategory -> newCategory.mouseClicked(mouseX, mouseY, mouseButton));
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        this.guiCategories.forEach(newCategory -> newCategory.mouseReleased(mouseX, mouseY, state));
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public static float getAnimDelta(final float target, final float current) {
        final float delta = target - current;
        final float change = 100.0f / (20.0f - ClickGui.Instance.animationSpeed.GetSlider() / 5.0f);
        if (delta > 0.0f) {
            return (target - current) / change;
        }
        return current / change;
    }
    
    static {
        MainScreen.timer = new Timer();
    }
}
