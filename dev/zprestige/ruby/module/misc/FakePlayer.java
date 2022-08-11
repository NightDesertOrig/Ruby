//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.misc;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.client.entity.*;
import java.util.*;
import com.google.gson.*;
import java.io.*;
import java.net.*;
import com.mojang.authlib.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;

public class FakePlayer extends Module
{
    public static FakePlayer Instance;
    public final Switch copyInventory;
    EntityOtherPlayerMP fakePlayer;
    
    public FakePlayer() {
        this.copyInventory = this.Menu.Switch("Copy Inventory");
        FakePlayer.Instance = this;
    }
    
    public static UUID getUUIDByName(final String name) {
        try {
            final URLConnection request = new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openConnection();
            request.connect();
            final String id = UUID.fromString(new JsonParser().parse((Reader)new InputStreamReader((InputStream)request.getContent())).getAsJsonObject().get("id").getAsString().replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5")).toString();
            return UUID.fromString(id);
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    @Override
    public void onEnable() {
        if (this.nullCheck()) {
            return;
        }
        (this.fakePlayer = new EntityOtherPlayerMP((World)this.mc.world, new GameProfile(getUUIDByName("FakePlayer"), "FakePlayer"))).copyLocationAndAnglesFrom((Entity)this.mc.player);
        if (this.copyInventory.GetSwitch()) {
            this.fakePlayer.inventory = this.mc.player.inventory;
        }
        this.fakePlayer.setHealth(36.0f);
        this.mc.world.addEntityToWorld(-100, (Entity)this.fakePlayer);
    }
    
    @Override
    public void onTick() {
        if (this.fakePlayer != null && this.fakePlayer.getDistanceSq((Entity)this.mc.player) > 10000.0) {
            this.mc.world.removeEntityFromWorld(-100);
            this.disableModule("FakePlayer too far away.");
        }
    }
    
    @Override
    public void onDisable() {
        if (this.fakePlayer != null) {
            this.mc.world.removeEntityFromWorld(-100);
        }
    }
}
