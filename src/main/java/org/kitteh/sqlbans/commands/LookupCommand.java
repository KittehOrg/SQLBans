/*
 * SQLBans
 * Copyright 2012-2014 Matt Baxter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kitteh.sqlbans.commands;

import org.kitteh.sqlbans.ChatColor;
import org.kitteh.sqlbans.Perm;
import org.kitteh.sqlbans.SQLBans;
import org.kitteh.sqlbans.SQLBans.Messages;
import org.kitteh.sqlbans.api.CommandSender;
import org.kitteh.sqlbans.api.SQLBansCommand;

public final class LookupCommand extends SQLBansCommand {

    private final SQLBans plugin;

    public LookupCommand(SQLBans plugin) {
        super("lookup", null);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        final boolean bans = sender.hasPermission(Perm.COMMAND_LOOKUP_BANS);
        final boolean ips = sender.hasPermission(Perm.COMMAND_LOOKUP_IPS);
        if (args.length == 0) { // Usage
            if (!bans && !ips) {
                sender.sendMessage(Messages.getCommandNoPermission());
            } else {
                final StringBuilder builder = new StringBuilder();
                builder.append(ChatColor.AQUA).append("Usage: ").append(this.getName());
                if (bans) {
                    builder.append("[bans");
                    builder.append(ips ? "/ips]" : "]");
                } else {
                    builder.append("[ips]");
                }
                sender.sendMessage(builder.toString());
            }
            return true;
        }
        if (bans && args[0].equalsIgnoreCase("bans")) {
            if (args.length == 1) {
                // TODO
            }
        } else if (ips && args[0].equalsIgnoreCase("ips")) {
            // TODO
        } else {
            sender.sendMessage(Messages.getCommandNoPermission());
        }
        return true;
    }
}