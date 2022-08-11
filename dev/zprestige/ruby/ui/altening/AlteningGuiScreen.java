//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.ui.altening;

import java.util.*;
import net.minecraft.client.gui.*;
import java.util.stream.*;
import com.mojang.realmsclient.gui.*;
import java.io.*;
import net.minecraft.util.text.*;
import java.awt.*;
import dev.zprestige.ruby.ui.altening.switcher.*;
import java.net.*;
import com.mojang.authlib.yggdrasil.*;
import com.mojang.authlib.*;
import com.mojang.authlib.exceptions.*;
import net.minecraft.util.*;
import dev.zprestige.ruby.mixins.minecraft.*;

public class AlteningGuiScreen extends GuiScreen
{
    public static ArrayList<String> altInformation;
    public static ServiceSwitcher serviceSwitcher;
    public GuiScreen guiScreen;
    public String responseMessage;
    public GuiTextField freeTokenField;
    public GuiButton useButton;
    public GuiButton generator;
    
    public AlteningGuiScreen(final GuiScreen guiScreen) {
        this.guiScreen = guiScreen;
    }
    
    public void initGui() {
        final int widthOfComponents = 200;
        this.freeTokenField = new GuiTextField(1, this.fontRenderer, this.width / 2 - widthOfComponents / 2, this.height / 2 + this.height / 6 - 40, widthOfComponents, 20);
        this.generator = new GuiButton(2, this.width / 2 - widthOfComponents / 2, this.height / 2 + this.height / 6, "Generator");
        this.useButton = new GuiButton(2, this.width / 2 - widthOfComponents / 2, this.height / 2 + this.height / 6 + 22, "Use free token");
        this.buttonList.add(new GuiButton(3, this.width / 2 - widthOfComponents / 2, this.height / 2 + this.height / 6 + 44, "Back"));
        super.initGui();
    }
    
    public void updateScreen() {
        this.freeTokenField.updateCursorCounter();
        super.updateScreen();
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        this.fontRenderer.drawStringWithShadow("Altening Manager", this.width / 2.0f - this.fontRenderer.getStringWidth("Altening Manager") / 2.0f, this.height / 2.0f + this.height / 6.0f - 70.0f, -1);
        this.freeTokenField.drawTextBox();
        this.useButton.drawButton(this.mc, mouseX, mouseY, partialTicks);
        this.generator.drawButton(this.mc, mouseX, mouseY, partialTicks);
        if (this.responseMessage != null) {
            this.fontRenderer.drawStringWithShadow(this.responseMessage, (float)(this.width / 2 - this.fontRenderer.getStringWidth(this.responseMessage) / 2), this.height / 2.0f + this.height / 6.0f - 60.0f, -1);
        }
        if (!AlteningGuiScreen.altInformation.isEmpty()) {
            final String string;
            IntStream.range(0, AlteningGuiScreen.altInformation.size()).forEach(i -> {
                string = AlteningGuiScreen.altInformation.get(i);
                this.fontRenderer.drawStringWithShadow(ChatFormatting.GREEN + string, this.width / 2.0f - this.fontRenderer.getStringWidth(string) / 2.0f, this.height / 2.0f + this.height / 6.0f - 60.0f, -1);
                return;
            });
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1) {
            this.mc.displayGuiScreen(this.guiScreen);
            return;
        }
        this.freeTokenField.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }
    
    protected void actionPerformed(final GuiButton button) throws IOException {
        if (button.id == 3) {
            this.mc.displayGuiScreen(this.guiScreen);
            return;
        }
        super.actionPerformed(button);
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        this.freeTokenField.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.useButton.isMouseOver()) {
            this.useButton.playPressSound(this.mc.getSoundHandler());
            if (this.freeTokenField.getText().isEmpty() || this.freeTokenField.getText().length() == 0) {
                this.responseMessage = TextFormatting.RED + "Field is empty.";
            }
            else {
                this.responseMessage = TextFormatting.YELLOW + "Attempting login.";
                new Thread(this::checkFreeToken).start();
            }
        }
        if (this.generator.isMouseOver()) {
            this.useButton.playPressSound(this.mc.getSoundHandler());
            try {
                Desktop.getDesktop().browse(URI.create("https://thealtening.com/free/free-minecraft-alt"));
            }
            catch (Exception ex) {}
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    public void checkFreeToken() {
        AlteningGuiScreen.serviceSwitcher.switchToService(AlteningServiceType.THEALTENING);
        final YggdrasilAuthenticationService yggdrasilAuthenticationService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        final YggdrasilUserAuthentication yggdrasilUserAuthentication = (YggdrasilUserAuthentication)yggdrasilAuthenticationService.createUserAuthentication(Agent.MINECRAFT);
        yggdrasilUserAuthentication.setUsername(this.freeTokenField.getText());
        yggdrasilUserAuthentication.setPassword("zprestigeontop");
        try {
            yggdrasilUserAuthentication.logIn();
        }
        catch (AuthenticationException e) {
            e.printStackTrace();
            this.responseMessage = TextFormatting.RED + "Login attempt failed.";
            return;
        }
        final Session session = new Session(yggdrasilUserAuthentication.getSelectedProfile().getName(), yggdrasilUserAuthentication.getSelectedProfile().getId().toString(), yggdrasilUserAuthentication.getAuthenticatedToken(), "LEGACY");
        ((IMixinMinecraft)this.mc).setSession(session);
        AlteningGuiScreen.altInformation.clear();
        AlteningGuiScreen.altInformation.add("Logged in as " + session.getUsername() + ".");
        this.responseMessage = null;
    }
    
    static {
        AlteningGuiScreen.altInformation = new ArrayList<String>();
        AlteningGuiScreen.serviceSwitcher = new ServiceSwitcher();
    }
}
