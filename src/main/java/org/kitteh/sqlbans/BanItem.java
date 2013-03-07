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

import java.util.Date;

public class BanItem {

    private final String reason;
    private final String admin;
    private final Date created;
    private final int length;
    private final String info;

    public BanItem(String info, String admin, Date created, int length, String reason) {
        this.info = info;
        this.admin = admin;
        this.created = created;
        this.length = length;
        this.reason = reason;
    }

    public String getAdmin() {
        return this.admin;
    }

    public Date getCreated() {
        return this.created;
    }

    public String getInfo() {
        return this.info;
    }

    public int getLength() {
        return this.length;
    }

    public String getReason() {
        return this.reason;
    }
}