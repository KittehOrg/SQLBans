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
package org.kitteh.sqlbans;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

@SuppressWarnings("unchecked")
final class Config {

    private Map<String, Object> map = new LinkedHashMap<>();

    Config(SQLBans plugin) {
        final File configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            try {
                plugin.saveResource("config.yml", false);
            } catch (final Exception e) {
                plugin.getLogger().log(Level.WARNING, "Could not save default config", e);
            }
        }
        try {
            this.map = (Map<String, Object>) new Yaml().load(new FileInputStream(configFile));
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    Object get(String string) {
        try {
            return this.get(string, this.map);
        } catch (final Exception e) {
            return null;
        }
    }

    int getInt(String string) {
        final String s = this.get(string).toString();
        try {
            return Integer.parseInt(s);
        } catch (final NumberFormatException e) {
            return 0;
        }
    }

    String getString(String string) {
        final Object o = this.get(string);
        if (o instanceof String) {
            return (String) o;
        }
        return null;
    }

    String getString(String string, String def) {
        final String result = this.getString(string);
        if (result == null) {
            return def;
        }
        return result;
    }

    private Object get(String string, Map<String, Object> map) {
        if (string.contains(".") && (string.indexOf(".") != (string.length() - 1))) {
            final Object o = map.get(string.substring(0, string.indexOf(".")));
            if (o instanceof Map) {
                return this.get(string.substring(string.indexOf(".") + 1), (Map<String, Object>) o);
            } else {
                return null;
            }
        }
        return map.get(string);
    }
}