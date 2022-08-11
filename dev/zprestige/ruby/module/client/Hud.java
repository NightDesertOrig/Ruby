//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.client;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import java.math.*;
import dev.zprestige.ruby.events.*;
import dev.zprestige.ruby.eventbus.annotation.*;
import org.lwjgl.opengl.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.awt.*;
import dev.zprestige.ruby.util.*;
import dev.zprestige.ruby.*;
import java.io.*;
import java.util.*;
import net.minecraft.client.network.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import java.util.function.*;

public class Hud extends Module
{
    public static Hud Instance;
    public final ColorBox color;
    public final Switch watermark;
    public final Switch ping;
    public final Switch fps;
    public final Switch tps;
    public final Switch coords;
    public final Switch welcomer;
    public final Switch armor;
    public final Switch noRegularArmorHud;
    public final Switch totems;
    public final Switch packetGraph;
    public final Slider graphX;
    public final Slider graphY;
    public HashMap<Integer, Integer> placedCrystals;
    public GraphUtil receivedPackets;
    public List<Double> receivedPacketsList;
    public GraphUtil sentPackets;
    public List<Double> sentPacketsList;
    
    public Hud() {
        this.color = this.Menu.Color("Color");
        this.watermark = this.Menu.Switch("Watermark");
        this.ping = this.Menu.Switch("Ping");
        this.fps = this.Menu.Switch("Fps");
        this.tps = this.Menu.Switch("Tps");
        this.coords = this.Menu.Switch("Coords");
        this.welcomer = this.Menu.Switch("Welcomer");
        this.armor = this.Menu.Switch("Armor");
        this.noRegularArmorHud = this.Menu.Switch("No Regular Armor Hud");
        this.totems = this.Menu.Switch("Totems");
        this.packetGraph = this.Menu.Switch("Packet Graph");
        this.graphX = this.Menu.Slider("Graph X", 0, 1000);
        this.graphY = this.Menu.Slider("Graph Y", 0, 1000);
        this.placedCrystals = new HashMap<Integer, Integer>();
        this.receivedPackets = new GraphUtil();
        this.receivedPacketsList = new ArrayList<Double>();
        this.sentPackets = new GraphUtil();
        this.sentPacketsList = new ArrayList<Double>();
        Hud.Instance = this;
    }
    
    public static float roundNumber(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal decimal = BigDecimal.valueOf(value);
        decimal = decimal.setScale(places, RoundingMode.FLOOR);
        return decimal.floatValue();
    }
    
    @RegisterListener
    public void onPacketReceive(final PacketEvent.PacketReceiveEvent packetEvent) {
        if (this.nullCheck() || !this.isEnabled() || !this.packetGraph.GetSwitch()) {
            return;
        }
        this.receivedPackets.addItem();
    }
    
    @RegisterListener
    public void onPacketSend(final PacketEvent.PacketSendEvent packetEvent) {
        if (this.nullCheck() || !this.isEnabled() || !this.packetGraph.GetSwitch()) {
            return;
        }
        this.sentPackets.addItem();
    }
    
    public void makeGraph(final int x, int y, final List<Double> list, final double counter, final boolean sent) {
        y += 35;
        final double n7 = 17.5 / list.stream().max(Double::compareTo).orElse(1.0);
        list.add(counter + 1.0);
        while (list.size() > 200) {
            list.remove(0);
        }
        int n8 = 0;
        if (sent) {
            GL11.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
        }
        else {
            GL11.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        }
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glBegin(3);
        n8 += 3;
        final double n9 = (100 - n8) / (double)list.size();
        for (int i = 0; i < list.size(); ++i) {
            GL11.glVertex2d(n8 + i * n9 + x, y + n7 - n7 * list.get(i));
        }
        GL11.glEnd();
        GL11.glEnable(3553);
    }
    
    @Override
    public void onTick() {
        for (final Map.Entry<Integer, Integer> entry : this.placedCrystals.entrySet()) {
            this.placedCrystals.put(entry.getKey(), entry.getValue() - 1);
            if (entry.getValue() <= 0) {
                this.placedCrystals.remove(entry.getKey());
            }
        }
    }
    
    @SubscribeEvent
    public void onRenderHud(final RenderGameOverlayEvent event) {
        if (this.nullCheck() || !this.isEnabled()) {
            return;
        }
        if (event.getType().equals((Object)RenderGameOverlayEvent.ElementType.POTION_ICONS) || (this.noRegularArmorHud.GetSwitch() && event.getType().equals((Object)RenderGameOverlayEvent.ElementType.ARMOR))) {
            event.setCanceled(true);
        }
    }
    
    @Override
    public void onFrame2D() {
        if (this.packetGraph.GetSwitch()) {
            RenderUtil.drawRect(this.graphX.GetSlider(), this.graphY.GetSlider(), this.graphX.GetSlider() + 100.0f, this.graphY.GetSlider() + 35.0f, new Color(0, 0, 0, 100).getRGB());
            RenderUtil.drawOutlineRect(this.graphX.GetSlider(), this.graphY.GetSlider(), this.graphX.GetSlider() + 100.0f, this.graphY.GetSlider() + 35.0f, this.color.GetColor(), 1.0f);
            Ruby.fontManager.drawStringWithShadow("Packets", this.graphX.GetSlider() + 2.0f, this.graphY.GetSlider(), -1);
            this.makeGraph((int)this.graphX.GetSlider(), (int)this.graphY.GetSlider(), this.receivedPacketsList, this.receivedPackets.getCount(), false);
            this.makeGraph((int)this.graphX.GetSlider(), (int)this.graphY.GetSlider(), this.sentPacketsList, this.sentPackets.getCount(), true);
        }
        int i = -10;
        if (this.watermark.GetSwitch()) {
            i += 10;
            final File path = new File(Ruby.mc.gameDir + File.separator + "mods");
            String string = "";
            for (final String file : Objects.requireNonNull(path.list())) {
                if (file.contains("ruby")) {
                    string = file.replace("-main.jar", "");
                }
            }
            RenderUtil.drawRect(1.0f, (float)i, (float)(1 + Ruby.fontManager.getStringWidth(string) + 2), (float)(i + 10), new Color(0, 0, 0, 100).getRGB());
            Ruby.fontManager.drawStringWithShadow("Ruby", 1.0f, (float)i, new Color(6118749).getRGB());
            Ruby.fontManager.drawStringWithShadow(string.replace("ruby", ""), (float)(1 + Ruby.fontManager.getStringWidth("Ruby")), (float)i, this.color.GetColor().getRGB());
            RenderUtil.drawRect(0.0f, (float)i, 1.0f, (float)(i + 10), this.color.GetColor().getRGB());
        }
        if (this.ping.GetSwitch()) {
            i += 10;
            try {
                RenderUtil.drawRect(1.0f, (float)i, (float)(1 + Ruby.fontManager.getStringWidth("Ping " + Objects.requireNonNull(this.mc.getConnection()).getPlayerInfo(this.mc.getConnection().getGameProfile().getId()).getResponseTime()) + 2), (float)(i + 10), new Color(0, 0, 0, 100).getRGB());
                Ruby.fontManager.drawStringWithShadow("Ping", 1.0f, (float)i, new Color(6118749).getRGB());
                Ruby.fontManager.drawStringWithShadow(" " + this.mc.getConnection().getPlayerInfo(this.mc.getConnection().getGameProfile().getId()).getResponseTime(), (float)(1 + Ruby.fontManager.getStringWidth("Ping")), (float)i, this.color.GetColor().getRGB());
                RenderUtil.drawRect(0.0f, (float)i, 1.0f, (float)(i + 10), this.color.GetColor().getRGB());
            }
            catch (Exception ex) {}
        }
        if (this.tps.GetSwitch()) {
            i += 10;
            try {
                RenderUtil.drawRect(1.0f, (float)i, (float)(1 + Ruby.fontManager.getStringWidth("Tps " + Ruby.tickManager.getTPS()) + 2), (float)(i + 10), new Color(0, 0, 0, 100).getRGB());
                Ruby.fontManager.drawStringWithShadow("Tps", 1.0f, (float)i, new Color(6118749).getRGB());
                Ruby.fontManager.drawStringWithShadow(" " + Ruby.tickManager.getTPS(), (float)(1 + Ruby.fontManager.getStringWidth("Tps")), (float)i, this.color.GetColor().getRGB());
                RenderUtil.drawRect(0.0f, (float)i, 1.0f, (float)(i + 10), this.color.GetColor().getRGB());
            }
            catch (Exception ex2) {}
        }
        if (this.fps.GetSwitch()) {
            i += 10;
            RenderUtil.drawRect(1.0f, (float)i, (float)(1 + Ruby.fontManager.getStringWidth("Fps " + Minecraft.getDebugFPS()) + 2), (float)(i + 10), new Color(0, 0, 0, 100).getRGB());
            Ruby.fontManager.drawStringWithShadow("Fps", 1.0f, (float)i, new Color(6118749).getRGB());
            Ruby.fontManager.drawStringWithShadow(" " + Minecraft.getDebugFPS(), (float)(1 + Ruby.fontManager.getStringWidth("Fps")), (float)i, this.color.GetColor().getRGB());
            RenderUtil.drawRect(0.0f, (float)i, 1.0f, (float)(i + 10), this.color.GetColor().getRGB());
        }
        if (this.coords.GetSwitch()) {
            final boolean inNether = this.mc.world.getBiome(this.mc.player.getPosition()).getBiomeName().equals("Hell");
            final int screenHeight = new ScaledResolution(this.mc).getScaledHeight() - ((this.mc.currentScreen instanceof GuiChat) ? 14 : 0);
            RenderUtil.drawRect(1.0f, (float)(screenHeight - 10), (float)(1 + Ruby.fontManager.getStringWidth("XYZ " + roundNumber(this.mc.player.posX, 1) + ", " + roundNumber(this.mc.player.posY, 1) + ", " + roundNumber(this.mc.player.posZ, 1) + " [" + roundNumber(inNether ? (this.mc.player.posX * 8.0) : (this.mc.player.posX / 8.0), 1) + ", " + roundNumber(inNether ? (this.mc.player.posZ * 8.0) : (this.mc.player.posZ / 8.0), 1) + "]")), (float)screenHeight, new Color(0, 0, 0, 100).getRGB());
            Ruby.fontManager.drawStringWithShadow("XYZ", 1.0f, (float)(screenHeight - 10), new Color(6118749).getRGB());
            Ruby.fontManager.drawStringWithShadow(String.valueOf(roundNumber(this.mc.player.posX, 1)), (float)(1 + Ruby.fontManager.getStringWidth("XYZ ")), (float)(screenHeight - 10), this.color.GetColor().getRGB());
            Ruby.fontManager.drawStringWithShadow(",", (float)(1 + Ruby.fontManager.getStringWidth("XYZ " + roundNumber(this.mc.player.posX, 1))), (float)(screenHeight - 10), new Color(6118749).getRGB());
            Ruby.fontManager.drawStringWithShadow(String.valueOf(roundNumber(this.mc.player.posY, 1)), (float)(1 + Ruby.fontManager.getStringWidth("XYZ " + roundNumber(this.mc.player.posX, 1) + ", ")), (float)(screenHeight - 10), this.color.GetColor().getRGB());
            Ruby.fontManager.drawStringWithShadow(",", (float)(1 + Ruby.fontManager.getStringWidth("XYZ " + roundNumber(this.mc.player.posX, 1) + ", " + roundNumber(this.mc.player.posY, 1))), (float)(screenHeight - 10), new Color(6118749).getRGB());
            Ruby.fontManager.drawStringWithShadow(String.valueOf(roundNumber(this.mc.player.posZ, 1)), (float)(1 + Ruby.fontManager.getStringWidth("XYZ " + roundNumber(this.mc.player.posX, 1) + ", " + roundNumber(this.mc.player.posY, 1) + ", ")), (float)(screenHeight - 10), this.color.GetColor().getRGB());
            Ruby.fontManager.drawStringWithShadow("[", (float)(1 + Ruby.fontManager.getStringWidth("XYZ " + roundNumber(this.mc.player.posX, 1) + ", " + roundNumber(this.mc.player.posY, 1) + ", " + roundNumber(this.mc.player.posZ, 1) + " ")), (float)(screenHeight - 10), new Color(6118749).getRGB());
            Ruby.fontManager.drawStringWithShadow(String.valueOf(roundNumber(inNether ? (this.mc.player.posX * 8.0) : (this.mc.player.posX / 8.0), 1)), (float)(1 + Ruby.fontManager.getStringWidth("XYZ " + roundNumber(this.mc.player.posX, 1) + ", " + roundNumber(this.mc.player.posY, 1) + ", " + roundNumber(this.mc.player.posZ, 1) + " [")), (float)(screenHeight - 10), this.color.GetColor().getRGB());
            Ruby.fontManager.drawStringWithShadow(",", (float)(1 + Ruby.fontManager.getStringWidth("XYZ " + roundNumber(this.mc.player.posX, 1) + ", " + roundNumber(this.mc.player.posY, 1) + ", " + roundNumber(this.mc.player.posZ, 1) + " [" + roundNumber(inNether ? (this.mc.player.posX * 8.0) : (this.mc.player.posX / 8.0), 1))), (float)(screenHeight - 10), new Color(6118749).getRGB());
            Ruby.fontManager.drawStringWithShadow(String.valueOf(roundNumber(inNether ? (this.mc.player.posZ * 8.0) : (this.mc.player.posZ / 8.0), 1)), (float)(1 + Ruby.fontManager.getStringWidth("XYZ " + roundNumber(this.mc.player.posX, 1) + ", " + roundNumber(this.mc.player.posY, 1) + ", " + roundNumber(this.mc.player.posZ, 1) + " [" + roundNumber(inNether ? (this.mc.player.posX * 8.0) : (this.mc.player.posX / 8.0), 1) + ", ")), (float)(screenHeight - 10), this.color.GetColor().getRGB());
            Ruby.fontManager.drawStringWithShadow("]", (float)(1 + Ruby.fontManager.getStringWidth("XYZ " + roundNumber(this.mc.player.posX, 1) + ", " + roundNumber(this.mc.player.posY, 1) + ", " + roundNumber(this.mc.player.posZ, 1) + " [" + roundNumber(inNether ? (this.mc.player.posX * 8.0) : (this.mc.player.posX / 8.0), 1) + ", " + roundNumber(inNether ? (this.mc.player.posZ * 8.0) : (this.mc.player.posZ / 8.0), 1))), (float)(screenHeight - 10), new Color(6118749).getRGB());
            RenderUtil.drawRect(1.0f, (float)(screenHeight - 20), (float)(1 + Ruby.fontManager.getStringWidth("Facing " + (this.mc.player.getHorizontalFacing().equals((Object)EnumFacing.NORTH) ? "North" : (this.mc.player.getHorizontalFacing().equals((Object)EnumFacing.EAST) ? "East" : (this.mc.player.getHorizontalFacing().equals((Object)EnumFacing.SOUTH) ? "South" : "West"))) + "  [" + (this.mc.player.getHorizontalFacing().equals((Object)EnumFacing.NORTH) ? "-Z" : (this.mc.player.getHorizontalFacing().equals((Object)EnumFacing.EAST) ? "+X" : (this.mc.player.getHorizontalFacing().equals((Object)EnumFacing.SOUTH) ? "+Z" : "-X"))) + "]")), (float)(screenHeight - 10), new Color(0, 0, 0, 100).getRGB());
            Ruby.fontManager.drawStringWithShadow("Facing", 1.0f, (float)(screenHeight - 20), new Color(6118749).getRGB());
            Ruby.fontManager.drawStringWithShadow(this.mc.player.getHorizontalFacing().equals((Object)EnumFacing.NORTH) ? "North" : (this.mc.player.getHorizontalFacing().equals((Object)EnumFacing.EAST) ? "East" : (this.mc.player.getHorizontalFacing().equals((Object)EnumFacing.SOUTH) ? "South" : "West")), (float)(1 + Ruby.fontManager.getStringWidth("Facing ")), (float)(screenHeight - 20), this.color.GetColor().getRGB());
            Ruby.fontManager.drawStringWithShadow("[", (float)(1 + Ruby.fontManager.getStringWidth("Facing " + (this.mc.player.getHorizontalFacing().equals((Object)EnumFacing.NORTH) ? "North" : (this.mc.player.getHorizontalFacing().equals((Object)EnumFacing.EAST) ? "East" : (this.mc.player.getHorizontalFacing().equals((Object)EnumFacing.SOUTH) ? "South" : "West"))) + " ")), (float)(screenHeight - 20), new Color(6118749).getRGB());
            Ruby.fontManager.drawStringWithShadow(this.mc.player.getHorizontalFacing().equals((Object)EnumFacing.NORTH) ? "-Z" : (this.mc.player.getHorizontalFacing().equals((Object)EnumFacing.EAST) ? "+X" : (this.mc.player.getHorizontalFacing().equals((Object)EnumFacing.SOUTH) ? "+Z" : "-X")), (float)(1 + Ruby.fontManager.getStringWidth("Facing " + (this.mc.player.getHorizontalFacing().equals((Object)EnumFacing.NORTH) ? "North" : (this.mc.player.getHorizontalFacing().equals((Object)EnumFacing.EAST) ? "East" : (this.mc.player.getHorizontalFacing().equals((Object)EnumFacing.SOUTH) ? "South" : "West"))) + "  [")), (float)(screenHeight - 20), this.color.GetColor().getRGB());
            Ruby.fontManager.drawStringWithShadow("]", (float)(1 + Ruby.fontManager.getStringWidth("Facing " + (this.mc.player.getHorizontalFacing().equals((Object)EnumFacing.NORTH) ? "North" : (this.mc.player.getHorizontalFacing().equals((Object)EnumFacing.EAST) ? "East" : (this.mc.player.getHorizontalFacing().equals((Object)EnumFacing.SOUTH) ? "South" : "West"))) + "  [" + (this.mc.player.getHorizontalFacing().equals((Object)EnumFacing.NORTH) ? "-Z" : (this.mc.player.getHorizontalFacing().equals((Object)EnumFacing.EAST) ? "+X" : (this.mc.player.getHorizontalFacing().equals((Object)EnumFacing.SOUTH) ? "+Z" : "-X"))))), (float)(screenHeight - 20), new Color(6118749).getRGB());
            RenderUtil.drawRect(0.0f, (float)(screenHeight - 20), 1.0f, (float)screenHeight, this.color.GetColor().getRGB());
        }
        if (this.welcomer.GetSwitch()) {
            final int screenWidth = new ScaledResolution(this.mc).getScaledWidth();
            Ruby.fontManager.drawStringWithShadow("Welcome, ", screenWidth / 2.0f - Ruby.fontManager.getStringWidth("Welcome, " + this.mc.player.getName()) / 2.0f, 0.0f, new Color(6118749).getRGB());
            Ruby.fontManager.drawStringWithShadow(this.mc.player.getName(), screenWidth / 2.0f - Ruby.fontManager.getStringWidth("Welcome, " + this.mc.player.getName()) / 2.0f + Ruby.fontManager.getStringWidth("Welcome, "), 0.0f, this.color.GetColor().getRGB());
        }
        if (this.armor.GetSwitch()) {
            this.renderArmorHUD();
        }
        if (this.totems.GetSwitch()) {
            this.renderTotemHUD();
        }
    }
    
    public void renderArmorHUD() {
        final int width = new ScaledResolution(this.mc).getScaledWidth();
        final int height = new ScaledResolution(this.mc).getScaledHeight();
        GlStateManager.enableTexture2D();
        final int i = width / 2;
        int iteration = 0;
        final int y = height - 55 - ((this.mc.player.isInWater() && this.mc.playerController.gameIsSurvivalOrAdventure()) ? 10 : 0);
        for (final ItemStack is : this.mc.player.inventory.armorInventory) {
            ++iteration;
            if (is.isEmpty()) {
                continue;
            }
            final int x = i - 90 + (9 - iteration) * 20 + 2;
            GlStateManager.enableDepth();
            RenderUtil.itemRender.zLevel = 200.0f;
            RenderUtil.itemRender.renderItemAndEffectIntoGUI(is, x, y);
            RenderUtil.itemRender.renderItemOverlayIntoGUI(this.mc.fontRenderer, is, x, y, "");
            RenderUtil.itemRender.zLevel = 0.0f;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            final String s = (is.getCount() > 1) ? (is.getCount() + "") : "";
            this.mc.fontRenderer.drawStringWithShadow(s, (float)(x + 19 - 2 - this.mc.fontRenderer.getStringWidth(s)), (float)(y + 9), 16777215);
            final float green = (is.getMaxDamage() - (float)is.getItemDamage()) / is.getMaxDamage();
            final float red = 1.0f - green;
            final int dmg = 100 - (int)(red * 100.0f);
            this.mc.fontRenderer.drawStringWithShadow(dmg + "", x + 8 - this.mc.fontRenderer.getStringWidth(dmg + "") / 2.0f, (float)(y - 11), new Color((int)(red * 255.0f), (int)(green * 255.0f), 0).getRGB());
        }
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
    }
    
    public void renderTotemHUD() {
        final int width = new ScaledResolution(this.mc).getScaledWidth();
        final int height = new ScaledResolution(this.mc).getScaledHeight();
        int totems = this.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        if (this.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            totems += this.mc.player.getHeldItemOffhand().getCount();
        }
        if (totems > 0) {
            GlStateManager.enableTexture2D();
            final int i = width / 2;
            final int y = height - 55 - ((this.mc.player.isInWater() && this.mc.playerController.gameIsSurvivalOrAdventure()) ? 10 : 0);
            final int x = i - 189 + 180 + 2;
            GlStateManager.enableDepth();
            RenderUtil.itemRender.zLevel = 200.0f;
            RenderUtil.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.TOTEM_OF_UNDYING), x, y);
            RenderUtil.itemRender.renderItemOverlayIntoGUI(this.mc.fontRenderer, new ItemStack(Items.TOTEM_OF_UNDYING), x, y, "");
            RenderUtil.itemRender.zLevel = 0.0f;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            this.mc.fontRenderer.drawStringWithShadow(totems + "", (float)(x + 19 - 2 - this.mc.fontRenderer.getStringWidth(totems + "")), (float)(y + 9), 16777215);
            GlStateManager.enableDepth();
            GlStateManager.disableLighting();
        }
    }
}
