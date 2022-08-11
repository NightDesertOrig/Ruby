//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.ui.middleclick;

import net.minecraft.client.gui.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import dev.zprestige.ruby.*;
import org.lwjgl.opengl.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.util.text.*;
import dev.zprestige.ruby.module.misc.*;
import net.minecraft.entity.player.*;

public class MiddleClickInterface extends GuiScreen
{
    ScaledResolution resolution;
    ArrayList<Quadrant> quadrants;
    Entity entity;
    
    public MiddleClickInterface(final ScaledResolution resolution, final Entity entity) {
        this.quadrants = new ArrayList<Quadrant>();
        this.resolution = resolution;
        this.entity = entity;
        this.quadrants.add(new Quadrant(resolution.getScaledWidth() / 2 - 64, resolution.getScaledHeight() / 2 - 64, 64, 64, new ResourceLocation("textures/icons/target.png"), QuadrantType.AddEnemy, entity));
        this.quadrants.add(new Quadrant(resolution.getScaledWidth() / 2 - 10, resolution.getScaledHeight() / 2 - 64, 64, 64, new ResourceLocation("textures/icons/addfriend.png"), QuadrantType.AddFriend, entity));
        this.quadrants.add(new Quadrant(resolution.getScaledWidth() / 2 - 64, resolution.getScaledHeight() / 2 - 10, 64, 64, new ResourceLocation("textures/icons/block.png"), QuadrantType.Block, entity));
        this.quadrants.add(new Quadrant(resolution.getScaledWidth() / 2 - 10, resolution.getScaledHeight() / 2 - 10, 64, 64, new ResourceLocation("textures/icons/talk.png"), QuadrantType.Whisper, entity));
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        GlStateManager.enableAlpha();
        Ruby.mc.getTextureManager().bindTexture(new ResourceLocation("textures/icons/circle.png"));
        GL11.glPushMatrix();
        GuiScreen.drawScaledCustomSizeModalRect(this.resolution.getScaledWidth() / 2 - 64, this.resolution.getScaledHeight() / 2 - 64, 0.0f, 128.0f, 128, 128, 128, 128, 128.0f, 128.0f);
        GL11.glScaled(2.0, 2.0, 0.0);
        Ruby.fontManager.drawStringWithShadow(this.entity.getName(), (this.resolution.getScaledWidth() / 2.0f - Ruby.fontManager.getStringWidth(this.entity.getName())) / 2.0f, (this.resolution.getScaledHeight() / 2.0f - 64.0f - Ruby.fontManager.getFontHeight()) / 2.0f - 10.0f, -1);
        GL11.glPopMatrix();
        GlStateManager.disableAlpha();
        this.quadrants.forEach(quadrant -> quadrant.drawScreen(mouseX, mouseY, partialTicks));
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int releasedButton) {
        if (releasedButton == 2) {
            this.quadrants.forEach(quadrant -> quadrant.mouseReleased(mouseX, mouseY));
        }
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public enum QuadrantType
    {
        AddFriend("Add Friend"), 
        Whisper("Whisper"), 
        Block("Block"), 
        AddEnemy("Add Enemy");
        
        String name;
        
        private QuadrantType(final String name) {
            this.name = name;
        }
        
        public String getName() {
            return this.name;
        }
    }
    
    public static class Quadrant
    {
        int x;
        int y;
        int width;
        int height;
        ResourceLocation resourceLocation;
        QuadrantType quadrantType;
        Entity entity;
        
        public Quadrant(final int x, final int y, final int width, final int height, final ResourceLocation resourceLocation, final QuadrantType quadrantType, final Entity entity) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.resourceLocation = resourceLocation;
            this.quadrantType = quadrantType;
            this.entity = entity;
        }
        
        public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
            Ruby.mc.getTextureManager().bindTexture(this.resourceLocation);
            GlStateManager.color(1.0f, 1.0f, 1.0f);
            GL11.glPushMatrix();
            if (this.isHovering(mouseX, mouseY)) {
                GuiScreen.drawScaledCustomSizeModalRect(this.x + 20, this.y + 20, 0.0f, 0.0f, 34, 34, 34, 34, 34.0f, 34.0f);
                Ruby.fontManager.drawStringWithShadow(this.quadrantType.getName(), this.x + 37 - Ruby.fontManager.getStringWidth(this.quadrantType.getName()) / 2.0f, (float)(this.y + 54), -1);
            }
            else {
                GuiScreen.drawScaledCustomSizeModalRect(this.x + 22, this.y + 22, 0.0f, 0.0f, 32, 32, 32, 32, 32.0f, 32.0f);
            }
            GL11.glPopMatrix();
            GlStateManager.disableAlpha();
        }
        
        public void mouseReleased(final int mouseX, final int mouseY) {
            if (this.isHovering(mouseX, mouseY)) {
                final String prefix = ChatFormatting.RED + "[Ruby] ";
                switch (this.quadrantType) {
                    case AddFriend: {
                        if (Ruby.friendManager.isFriend(this.entity.getName())) {
                            Ruby.friendManager.removeFriend(this.entity.getName());
                            Ruby.mc.player.sendMessage((ITextComponent)new TextComponentString(prefix + ChatFormatting.RESET + "Successfully removed " + this.entity.getName() + " as friend."));
                            break;
                        }
                        Ruby.friendManager.addFriend(this.entity.getName());
                        Ruby.mc.player.sendMessage((ITextComponent)new TextComponentString(prefix + ChatFormatting.RESET + "Successfully added " + this.entity.getName() + " as friend."));
                        break;
                    }
                    case AddEnemy: {
                        if (Ruby.enemyManager.isEnemy(this.entity.getName())) {
                            Ruby.enemyManager.removeEnemy(this.entity.getName());
                            Ruby.mc.player.sendMessage((ITextComponent)new TextComponentString(prefix + ChatFormatting.RESET + "Successfully removed " + this.entity.getName() + " as enemy."));
                            break;
                        }
                        Ruby.enemyManager.addEnemy(this.entity.getName());
                        Ruby.mc.player.sendMessage((ITextComponent)new TextComponentString(prefix + ChatFormatting.RESET + "Successfully added " + this.entity.getName() + " as enemy."));
                        break;
                    }
                    case Block: {
                        if (MiddleClick.Instance.blockedList.contains(this.entity)) {
                            MiddleClick.Instance.blockedList.remove(this.entity);
                            Ruby.mc.player.sendMessage((ITextComponent)new TextComponentString(prefix + ChatFormatting.RESET + "Successfully unblocked " + this.entity.getName() + "."));
                            break;
                        }
                        MiddleClick.Instance.blockedList.add(this.entity);
                        Ruby.mc.player.sendMessage((ITextComponent)new TextComponentString(prefix + ChatFormatting.RESET + "Successfully blocked " + this.entity.getName() + "."));
                        break;
                    }
                    case Whisper: {
                        Ruby.mc.player.sendChatMessage("/msg " + this.entity.getName() + " yo");
                        break;
                    }
                }
            }
        }
        
        public boolean isHovering(final int mouseX, final int mouseY) {
            return mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height;
        }
    }
}
