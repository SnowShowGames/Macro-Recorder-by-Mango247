package net.snowshowgames.macro;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class MacroRecorderMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MacroConfigManager.load();
        MacroRecorder.init();
        ClientTickEvents.END_CLIENT_TICK.register(client -> MacroRecorder.onTick(client));
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> MacroCommands.register(dispatcher));
    }
}
