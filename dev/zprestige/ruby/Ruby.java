//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby;

import net.minecraftforge.fml.common.*;
import net.minecraft.client.*;
import dev.zprestige.ruby.eventbus.*;
import dev.zprestige.ruby.events.listener.*;
import dev.zprestige.ruby.ui.hudeditor.*;
import dev.zprestige.ruby.manager.*;
import net.minecraftforge.fml.common.event.*;

@Mod(modid = "ruby", name = "Ruby", version = "0.1")
public class Ruby
{
    public static Minecraft mc;
    public static EventBus eventBus;
    public static ThreadManager threadManager;
    public static HoleManager holeManager;
    public static ModuleManager moduleManager;
    public static EventListener eventListener;
    public static FontManager fontManager;
    public static HudManager hudManager;
    public static FriendManager friendManager;
    public static EnemyManager enemyManager;
    public static TickManager tickManager;
    public static ChatManager chatManager;
    public static TotemPopManager totemPopManager;
    public static CommandManager commandManager;
    public static ConfigManager configManager;
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        Ruby.mc = Minecraft.getMinecraft();
        Ruby.eventBus = new EventBus();
        Ruby.threadManager = new ThreadManager();
        Ruby.holeManager = new HoleManager();
        Ruby.moduleManager = new ModuleManager();
        Ruby.eventListener = new EventListener();
        Ruby.fontManager = new FontManager();
        Ruby.hudManager = new HudManager();
        Ruby.friendManager = new FriendManager();
        Ruby.enemyManager = new EnemyManager();
        Ruby.tickManager = new TickManager();
        Ruby.chatManager = new ChatManager();
        Ruby.totemPopManager = new TotemPopManager();
        Ruby.commandManager = new CommandManager();
        Ruby.configManager = new ConfigManager().loadFromActiveConfig().readAndSetSocials();
    }
}
