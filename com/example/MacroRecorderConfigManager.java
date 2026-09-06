/*
 * Decompiled with https://jar.tools
 */
package com.example;

import com.example.MacroRecorderConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

@Environment(value=EnvType.CLIENT)
public class MacroRecorderConfigManager {
    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "macrorecorderconfig.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static MacroRecorderConfig config = new MacroRecorderConfig();

    public static void load() {
        try {
            FileReader reader = new FileReader(CONFIG_FILE);
            try {
                config = (MacroRecorderConfig)GSON.fromJson(reader, MacroRecorderConfig.class);
            }
            catch (Throwable e1) {
                reader.close();
                throw e1;
            }
            reader.close();
            try {
                reader.close();
            }
            catch (Throwable e2) {
                var1.addSuppressed(e2);
                throw var1;
            }
            throw var1;
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public static void save() {
        try {
            FileWriter writer = new FileWriter(CONFIG_FILE);
            try {
                GSON.toJson(config, writer);
            }
            catch (Throwable e1) {
                writer.close();
                throw e1;
            }
            writer.close();
            try {
                writer.close();
            }
            catch (Throwable e2) {
                var1.addSuppressed(e2);
                throw var1;
            }
            throw var1;
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}
