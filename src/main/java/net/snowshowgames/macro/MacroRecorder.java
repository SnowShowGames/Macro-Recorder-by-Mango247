package net.snowshowgames.macro;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MacroRecorder {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static final List<MacroFrame> recordedFrames = new ArrayList<>();
    private static boolean isRecording = false;
    private static long startTime = 0L;
    private static String currentName = "macro";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void init() {
        // nothing for now; kept for parity with original
    }

    public static void start(String name) {
        recordedFrames.clear();
        currentName = name;
        isRecording = true;
        startTime = System.currentTimeMillis();
        System.out.println("[MacroRecorder] Started recording: " + name);
    }

    public static void stop() {
        if (!isRecording) return;
        isRecording = false;
        saveToFile(currentName);
        System.out.println("[MacroRecorder] Stopped recording: " + currentName);
    }

    public static void onTick(MinecraftClient mc) {
        if (!isRecording || mc.player == null) return;
        recordFrame(mc);
    }

    private static void recordFrame(MinecraftClient mc) {
        float yaw = mc.player.getYaw();
        float pitch = mc.player.getPitch();

        // Read key states from options. Field names may vary across mappings; common names used below.
        Boolean forward = mc.options.keyForward.isPressed();
        Boolean left = mc.options.keyLeft.isPressed();
        Boolean back = mc.options.keyBack.isPressed();
        Boolean right = mc.options.keyRight.isPressed();
        Boolean jump = mc.options.keyJump.isPressed();
        Boolean sprint = mc.options.keySprint.isPressed();
        Boolean sneak = mc.options.keySneak.isPressed();
        Boolean attack = mc.options.keyAttack.isPressed();
        Boolean use = mc.options.keyUse.isPressed();
        Boolean dropItem = mc.options.keyDrop.isPressed();
        Boolean swapOffhand = mc.options.keySwapHands.isPressed();

        int selectedSlot = mc.player.getInventory().selectedSlot;

        MacroFrame frame = new MacroFrame(yaw, pitch, forward, left, back, right, jump, sprint, sneak, attack, use, dropItem, swapOffhand, selectedSlot, 1);

        if (!recordedFrames.isEmpty()) {
            MacroFrame last = recordedFrames.get(recordedFrames.size() - 1);
            if (last.equalsWithoutRepeats(frame)) {
                // increase repeats of last
                recordedFrames.set(recordedFrames.size() - 1, last.withIncreasedRepeats());
                return;
            }
        }
        recordedFrames.add(frame);
    }

    private static void saveToFile(String name) {
        Path dir = FabricLoader.getInstance().getConfigDir().resolve("macrorecorder");
        try {
            Files.createDirectories(dir);
            Path file = dir.resolve(name + ".json");
            try (Writer w = Files.newBufferedWriter(file)) {
                GSON.toJson(recordedFrames, w);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Playback implementation (basic). Plays frames on client by setting key states and selected slot.
    public static void play(String name) {
        Path dir = FabricLoader.getInstance().getConfigDir().resolve("macrorecorder");
        Path file = dir.resolve(name + ".json");
        if (!Files.exists(file)) {
            System.out.println("[MacroRecorder] Macro file not found: " + file);
            return;
        }
        try {
            MacroFrame[] frames = GSON.fromJson(Files.newBufferedReader(file), MacroFrame[].class);
            Thread playbackThread = new Thread(() -> {
                try {
                    for (MacroFrame f : frames) {
                        for (int r = 0; r < f.repeats; r++) {
                            applyFrame(f);
                            // Wait one tick (approx 50ms) - Minecraft tick is 50ms
                            Thread.sleep(50);
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "macro-playback");
            playbackThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void applyFrame(MacroFrame f) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;
        // Apply look
        mc.player.setYaw(f.yaw);
        mc.player.setPitch(f.pitch);

        // Apply selected hotbar slot
        mc.player.getInventory().selectedSlot = f.selectedSlot;

        // Set key states. Note: KeyBinding#setPressed or method name may vary with mappings.
        try {
            mc.options.keyForward.setPressed(Boolean.TRUE.equals(f.forward));
            mc.options.keyLeft.setPressed(Boolean.TRUE.equals(f.left));
            mc.options.keyBack.setPressed(Boolean.TRUE.equals(f.back));
            mc.options.keyRight.setPressed(Boolean.TRUE.equals(f.right));
            mc.options.keyJump.setPressed(Boolean.TRUE.equals(f.jump));
            mc.options.keySprint.setPressed(Boolean.TRUE.equals(f.sprint));
            mc.options.keySneak.setPressed(Boolean.TRUE.equals(f.sneak));
            mc.options.keyAttack.setPressed(Boolean.TRUE.equals(f.attack));
            mc.options.keyUse.setPressed(Boolean.TRUE.equals(f.use));
            mc.options.keyDrop.setPressed(Boolean.TRUE.equals(f.dropItem));
            mc.options.keySwapHands.setPressed(Boolean.TRUE.equals(f.swapOffhand));
        } catch (Throwable t) {
            // Some mappings may not expose setPressed; ignore if unavailable
        }
    }
}
