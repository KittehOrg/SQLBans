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
package org.kitteh.sqlbans.bukkit;

import org.kitteh.sqlbans.api.Scheduler;

final class BukkitScheduler implements Scheduler {

    private final SQLBansPlugin plugin;

    public BukkitScheduler(SQLBansPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void delayedTask(Runnable runnable, int delay) {
        this.plugin.getServer().getScheduler().runTaskLaterAsynchronously(this.plugin, runnable, delay);
    }

    @Override
    public void repeatingTask(Runnable runnable, int delay, int period) {
        this.plugin.getServer().getScheduler().runTaskTimerAsynchronously(this.plugin, runnable, delay, period);
    }

    @Override
    public void run(Runnable runnable) {
        this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, runnable);
    }
}