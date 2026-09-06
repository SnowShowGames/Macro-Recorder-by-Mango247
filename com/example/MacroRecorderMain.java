/*
 * Decompiled with https://jar.tools
 */
package com.example;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MacroRecorderMain
implements ModInitializer {
    public static final String MOD_ID = "MacroRecorder";
    public static final Logger LOGGER = LoggerFactory.getLogger("MacroRecorder");

    public void onInitialize() {
        LOGGER.info("Macro recorder init");
    }
}
