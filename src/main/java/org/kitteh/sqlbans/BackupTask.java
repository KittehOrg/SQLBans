/*
 * SQLBans
 * Copyright 2012 Matt Baxter
 *
 * Google Gson
 * Copyright 2008-2011 Google Inc.
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
package org.kitteh.sqlbans;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.logging.Level;

public class BackupTask implements Runnable {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
    private final SQLBans sqlbans;

    public BackupTask(SQLBans sqlbans) {
        this.sqlbans = sqlbans;
    }

    @Override
    public void run() {
        Set<BanItem> set = null;
        try {
            set = SQLHandler.getAllBans(0);
        } catch (final Exception e) {
            this.sqlbans.getLogger().log(Level.SEVERE, "Could not acquire bans for backup", e);
        }
        if ((set == null) || set.isEmpty()) {
            return;
        }
        try {
            final PrintWriter writer = new PrintWriter(new FileWriter(new File(this.sqlbans.getDataFolder(), "backup.txt"), false));

            writer.println("# Updated " + (new SimpleDateFormat()).format(new Date()) + " by SQLBans " + this.sqlbans.getVersion());
            writer.println("# victim name | ban date | banned by | banned until | reason");
            writer.println();

            for (final BanItem item : set) {
                final Date created = item.getCreated();
                final int length = item.getLength();
                final Date expires = length == 0 ? null : new Date(created.getTime() + (item.getLength() * 60000));

                final StringBuilder builder = new StringBuilder();

                builder.append(item.getInfo());
                builder.append("|");
                builder.append(BackupTask.format.format(created));
                builder.append("|");
                builder.append(item.getAdmin());
                builder.append("|");
                builder.append(expires == null ? "Forever" : BackupTask.format.format(expires));
                builder.append("|");
                builder.append(item.getReason());
                writer.println(builder.toString());
            }
            writer.close();
        } catch (final Exception e) {
            this.sqlbans.getLogger().log(Level.SEVERE, "Could not save ban list", e);
        }
    }
}