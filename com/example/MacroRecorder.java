/*
 * Decompiled with https://jar.tools
 */
package com.example;

import com.example.MacroRecorderConfigManager;
import com.google.gson.Gson;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.class_1661;
import net.minecraft.class_1799;
import net.minecraft.class_304;
import net.minecraft.class_310;
import net.minecraft.class_746;

@Environment(value=EnvType.CLIENT)
public class MacroRecorder {
    private static final class_310 client = class_310.method_1551();
    private static final List<MacroFrame> recordedFrames = new ArrayList();
    private static final List<MacroFrame> playbackFrames = new ArrayList();
    private static boolean isRecording = false;
    private static boolean isPlaying = false;
    private static int playbackIndex = 0;
    private static int currentRepeat = 0;
    private static final Gson gson = new Gson();
    private static final File macroDir = new File(System.getProperty("user.home") + "/AppData/Roaming/.minecraft/macros");
    private static boolean waitingForInput = false;
    private static boolean showIndicator = MacroRecorderConfigManager.config.showIndicator;
    private static boolean loop = MacroRecorderConfigManager.config.loop;
    private static int startRecordKey = MacroRecorderConfigManager.config.startRecordKey;
    private static int stopRecordKey = MacroRecorderConfigManager.config.stopRecordKey;
    private static Map<String, Integer> macroKeybinds = MacroRecorderConfigManager.config.macroKeybinds;
    private static Map<String, Boolean> waitingForMacroBind = MacroRecorderConfigManager.config.waitingForMacroBind;
    private static boolean waitingForStartKeybind = false;
    private static boolean waitingForStopKeybind = false;
    private static boolean startKeyPressedLastTick = false;
    private static boolean stopKeyPressedLastTick = false;
    private static int skipTicks = 0;
    private static Map<String, Boolean> macroKeyPressedLastTick = new HashMap();
    private static boolean recordOnFirstInput = MacroRecorderConfigManager.config.recordOnFirstInput;
    private static boolean recordCamera = MacroRecorderConfigManager.config.recordCamera;
    private static boolean recordCrouch = MacroRecorderConfigManager.config.recordCrouch;
    private static boolean recordJump = MacroRecorderConfigManager.config.recordJump;
    private static boolean recordMovement = MacroRecorderConfigManager.config.recordMovement;
    private static boolean recordSprint = MacroRecorderConfigManager.config.recordSprint;
    private static boolean recordPlace = MacroRecorderConfigManager.config.recordPlace;
    private static boolean recordAttack = MacroRecorderConfigManager.config.recordAttack;
    private static boolean recordUse = MacroRecorderConfigManager.config.recordUse;
    private static boolean recordDrop = MacroRecorderConfigManager.config.recordDrop;
    private static boolean recordSwapHands = MacroRecorderConfigManager.config.recordSwapHands;
    private static boolean playbackCamera = MacroRecorderConfigManager.config.playbackCamera;
    private static boolean playbackCrouch = MacroRecorderConfigManager.config.playbackCrouch;
    private static boolean playbackJump = MacroRecorderConfigManager.config.playbackJump;
    private static boolean playbackMovement = MacroRecorderConfigManager.config.playbackMovement;
    private static boolean playbackSprint = MacroRecorderConfigManager.config.playbackSprint;
    private static boolean playbackPlace = MacroRecorderConfigManager.config.playbackPlace;
    private static boolean playbackAttack = MacroRecorderConfigManager.config.playbackAttack;
    private static boolean playbackUse = MacroRecorderConfigManager.config.playbackUse;
    private static boolean playbackDrop = MacroRecorderConfigManager.config.playbackDrop;
    private static boolean playbackSwapHands = MacroRecorderConfigManager.config.playbackSwapHands;

    private static void updateConfig() {
        MacroRecorderConfigManager.config.showIndicator = showIndicator;
        MacroRecorderConfigManager.config.loop = loop;
        MacroRecorderConfigManager.config.startRecordKey = startRecordKey;
        MacroRecorderConfigManager.config.stopRecordKey = stopRecordKey;
        MacroRecorderConfigManager.config.macroKeybinds = macroKeybinds;
        MacroRecorderConfigManager.config.waitingForMacroBind = waitingForMacroBind;
        MacroRecorderConfigManager.config.recordOnFirstInput = recordOnFirstInput;
        MacroRecorderConfigManager.config.recordCamera = recordCamera;
        MacroRecorderConfigManager.config.recordCrouch = recordCrouch;
        MacroRecorderConfigManager.config.recordJump = recordJump;
        MacroRecorderConfigManager.config.recordMovement = recordMovement;
        MacroRecorderConfigManager.config.recordSprint = recordSprint;
        MacroRecorderConfigManager.config.recordPlace = recordPlace;
        MacroRecorderConfigManager.config.recordAttack = recordAttack;
        MacroRecorderConfigManager.config.recordUse = recordUse;
        MacroRecorderConfigManager.config.recordDrop = recordDrop;
        MacroRecorderConfigManager.config.recordSwapHands = recordSwapHands;
        MacroRecorderConfigManager.config.playbackCamera = playbackCamera;
        MacroRecorderConfigManager.config.playbackCrouch = playbackCrouch;
        MacroRecorderConfigManager.config.playbackJump = playbackJump;
        MacroRecorderConfigManager.config.playbackMovement = playbackMovement;
        MacroRecorderConfigManager.config.playbackSprint = playbackSprint;
        MacroRecorderConfigManager.config.playbackPlace = playbackPlace;
        MacroRecorderConfigManager.config.playbackAttack = playbackAttack;
        MacroRecorderConfigManager.config.playbackUse = playbackUse;
        MacroRecorderConfigManager.config.playbackDrop = playbackDrop;
        MacroRecorderConfigManager.config.playbackSwapHands = playbackSwapHands;
        MacroRecorderConfigManager.save();
    }

    public static void releaseAllKeys() {
        class_310 client = class_310.method_1551();
        client.field_1690.field_1894.method_23481(false);
        client.field_1690.field_1913.method_23481(false);
        client.field_1690.field_1881.method_23481(false);
        client.field_1690.field_1849.method_23481(false);
        client.field_1690.field_1903.method_23481(false);
        client.field_1690.field_1867.method_23481(false);
        client.field_1690.field_1832.method_23481(false);
        client.field_1690.field_1886.method_23481(false);
        client.field_1690.field_1904.method_23481(false);
        client.field_1690.field_1869.method_23481(false);
        client.field_1690.field_1831.method_23481(false);
    }

    public static void init() {
        if (!macroDir.exists()) {
            macroDir.mkdirs();
        }
        ClientTickEvents.END_CLIENT_TICK.register(MacroRecorder::lambda$init$0);
        HudRenderCallback.EVENT.register(MacroRecorder::lambda$init$1);
        MacroRecorder.registerCommands();
    }

    private static void simulateKeyPress(class_304 key, boolean pressed) {
        key.method_23481(pressed);
    }

    private static int getSelectedSlotReflect(class_746 player) {
        try {
            Field field = player.method_31548().getClass().getDeclaredField("field_7545");
            field.setAccessible(true);
            return field.getInt(player.method_31548());
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static void moveItemToSlot(class_1799 desired, int targetSlot) {
        class_1661 inv = client.field_1724.method_31548();
        class_1799 current = inv.method_5438(targetSlot);
        if (class_1799.method_31577(desired, current)) {
            return;
        }
        int i = 0;
        while (true) {
            if (i >= inv.method_5439()) break;
            if (i != targetSlot) {
                class_1799 found = inv.method_5438(i);
                if (class_1799.method_31577(desired, found)) {
                    inv.method_5447(i, current);
                    inv.method_5447(targetSlot, found);
                    return;
                }
            }
            i++;
        }
    }

    private static void registerCommands() {
        ClientCommandRegistrationCallback.EVENT.register(MacroRecorder::lambda$registerCommands$28);
    }
}
