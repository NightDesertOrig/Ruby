//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.command.impl;

import dev.zprestige.ruby.command.*;
import dev.zprestige.ruby.*;

public class FriendCommand extends Command
{
    public FriendCommand() {
        super("friend", "Friend <Add/Del> <Name>");
    }
    
    public void listener(final String string) {
        try {
            final String[] split = string.split(" ");
            if (split[1].equals("add")) {
                Ruby.friendManager.addFriend(split[2]);
                this.completeMessage("added " + split[2] + " to your friends list");
            }
            else if (split[1].equals("del")) {
                Ruby.friendManager.removeFriend(split[2]);
                this.completeMessage("deleted " + split[2] + " from your friends list");
            }
        }
        catch (Exception ignored) {
            this.throwException(this.format);
        }
    }
}
