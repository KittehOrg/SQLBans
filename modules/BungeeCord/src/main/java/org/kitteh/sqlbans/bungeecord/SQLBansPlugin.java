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
package org.kitteh.sqlbans.bungeecord;

import com.google.common.eventbus.Subscribe;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import org.kitteh.sqlbans.Perm;
import org.kitteh.sqlbans.SQLBans;
import org.kitteh.sqlbans.SQLBansUserData;
import org.kitteh.sqlbans.api.Player;
import org.kitteh.sqlbans.api.SQLBansCommand;
import org.kitteh.sqlbans.api.SQLBansImplementation;
import org.kitteh.sqlbans.api.Scheduler;

import java.util.logging.Logger;

public final class SQLBansPlugin extends Plugin implements Listener, SQLBansImplementation {

    private SQLBans sqlbans;
    private final Scheduler scheduler = new BungeeScheduler();

    @Override
    public Logger getLogger() {
        return ProxyServer.getInstance().getLogger();
    }

    @Override
    public Player[] getOnlinePlayers() {
        final ProxiedPlayer[] bplayers = ProxyServer.getInstance().getPlayers().toArray(new ProxiedPlayer[0]);
        final Player[] players = new Player[bplayers.length];
        for (int x = 0; x < players.length; x++) {
            players[x] = new BungeePlayer(bplayers[x]);
        }
        return players;
    }

    @Override
    public Player getPlayer(String name) {
        final ProxiedPlayer player = ProxyServer.getInstance().getPlayer(name);
        if (player == null) {
            return null;
        }
        return new BungeePlayer(player);
    }

    @Override
    public Scheduler getScheduler() {
        return this.scheduler;
    }

    @Override
    public String getVersion() {
        return this.getDescription().getVersion();
    }

    @Override
    public void onEnable() {
        this.sqlbans = new SQLBans(this);
    }

    @Subscribe
    public void onLogin(LoginEvent event) {
        final SQLBansUserData data = new SQLBansUserData(event.getConnection().getName(), event.getConnection().getUniqueId(), event.getConnection().getAddress().getAddress());
        this.sqlbans.processUserData(data, true);
        if (data.getResult() != SQLBansUserData.Result.UNCHANGED) {
            event.setCancelled(true);
            event.setCancelReason(data.getReason());
        }
    }

    @Override
    public void registerCommand(SQLBansCommand command) {
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new BungeeCommand(command));
    }

    @Override
    public void registerLoginAttemptListening() {
        ProxyServer.getInstance().getPluginManager().registerListener(this, this);
    }

    @Override
    public void sendMessage(Perm permission, String message) {
        BaseComponent[] mess = TextComponent.fromLegacyText(message);
        for (final ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
            if (player.hasPermission(permission.toString())) {
                player.sendMessage(mess);
            }
        }
    }

    @Override
    public void shutdown() {
        // Boggle!
    }
}