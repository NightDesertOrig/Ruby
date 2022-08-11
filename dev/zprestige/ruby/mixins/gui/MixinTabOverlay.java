//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.mixins.gui;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.network.*;
import dev.zprestige.ruby.module.misc.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.scoreboard.*;
import dev.zprestige.ruby.*;
import com.mojang.realmsclient.gui.*;
import dev.zprestige.ruby.module.client.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiPlayerTabOverlay.class })
public class MixinTabOverlay extends Gui
{
    @Redirect(method = { "renderPlayerlist" }, at = @At(value = "INVOKE", target = "Ljava/util/List;subList(II)Ljava/util/List;", remap = false))
    protected List<NetworkPlayerInfo> subListHook(final List<NetworkPlayerInfo> list, final int fromIndex, final int toIndex) {
        if (TabList.Instance.isEnabled()) {
            final String getCombo = TabList.Instance.order.GetCombo();
            switch (getCombo) {
                case "Ping": {
                    return list.stream().sorted(Comparator.comparing((Function<? super Object, ? extends Comparable>)NetworkPlayerInfo::getResponseTime)).limit((long)TabList.Instance.maxSize.GetSlider()).collect((Collector<? super Object, ?, List<NetworkPlayerInfo>>)Collectors.toList());
                }
                case "Alphabet": {
                    return list.stream().sorted(Comparator.comparing(playerInfo -> playerInfo.getGameProfile().getName())).limit((long)TabList.Instance.maxSize.GetSlider()).collect((Collector<? super Object, ?, List<NetworkPlayerInfo>>)Collectors.toList());
                }
                case "Length": {
                    return list.stream().sorted(Comparator.comparing(playerInfo -> playerInfo.getGameProfile().getName().length())).limit((long)TabList.Instance.maxSize.GetSlider()).collect((Collector<? super Object, ?, List<NetworkPlayerInfo>>)Collectors.toList());
                }
                case "Normal": {
                    return list.stream().limit((long)TabList.Instance.maxSize.GetSlider()).collect((Collector<? super Object, ?, List<NetworkPlayerInfo>>)Collectors.toList());
                }
            }
        }
        return list.subList(fromIndex, toIndex);
    }
    
    @Inject(method = { "getPlayerName" }, at = { @At("HEAD") }, cancellable = true)
    public void getPlayerNameHook(final NetworkPlayerInfo networkPlayerInfoIn, final CallbackInfoReturnable<String> callbackInfoReturnable) {
        final String name = (networkPlayerInfoIn.getDisplayName() != null) ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName((Team)networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
        if (TabList.Instance.isEnabled() && TabList.Instance.showPing.GetSwitch()) {
            callbackInfoReturnable.setReturnValue((Object)(name + " [" + networkPlayerInfoIn.getResponseTime() + "]"));
        }
        if (Friends.Instance.isEnabled() && Friends.Instance.tabHighlight.GetSwitch() && Ruby.friendManager.isFriend(name)) {
            if (Friends.Instance.tabPrefix.GetSwitch()) {
                callbackInfoReturnable.setReturnValue((Object)(ChatFormatting.AQUA + "[Friend] " + name + ((TabList.Instance.isEnabled() && TabList.Instance.showPing.GetSwitch()) ? (" [" + networkPlayerInfoIn.getResponseTime() + "]") : "")));
            }
            else {
                callbackInfoReturnable.setReturnValue((Object)(ChatFormatting.AQUA + "" + name + ((TabList.Instance.isEnabled() && TabList.Instance.showPing.GetSwitch()) ? (" [" + networkPlayerInfoIn.getResponseTime() + "]") : "")));
            }
        }
        if (Enemies.Instance.isEnabled() && Enemies.Instance.tabHighlight.GetSwitch() && Ruby.enemyManager.isEnemy(name)) {
            if (Enemies.Instance.tabPrefix.GetSwitch()) {
                callbackInfoReturnable.setReturnValue((Object)(ChatFormatting.RED + "[Enemy] " + name + ((TabList.Instance.isEnabled() && TabList.Instance.showPing.GetSwitch()) ? (" [" + networkPlayerInfoIn.getResponseTime() + "]") : "")));
            }
            else {
                callbackInfoReturnable.setReturnValue((Object)(ChatFormatting.RED + "" + name + ((TabList.Instance.isEnabled() && TabList.Instance.showPing.GetSwitch()) ? (" [" + networkPlayerInfoIn.getResponseTime() + "]") : "")));
            }
        }
    }
}
