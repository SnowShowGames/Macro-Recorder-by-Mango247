/*
 * Decompiled with https://jar.tools
 */
package com.example;

import com.example.MacroRecorder;
import com.example.MacroRecorderConfigManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;

@Environment(value=EnvType.CLIENT)
public class MacroRecorderClient
implements ClientModInitializer {
    public void onInitializeClient() {
        MacroRecorderConfigManager.load();
        ClientLifecycleEvents.CLIENT_STOPPING.register(MacroRecorderClient::lambda$onInitializeClient$0);
        MacroRecorder.init();
    }
}
