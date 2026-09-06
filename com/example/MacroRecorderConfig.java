/*
 * Decompiled with https://jar.tools
 */
package com.example;

import java.util.HashMap;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public class MacroRecorderConfig {
    public boolean showIndicator = true;
    public boolean loop = false;
    public boolean recordOnFirstInput = false;
    public int startRecordKey = -1;
    public int stopRecordKey = -1;
    public Map<String, Integer> macroKeybinds = new HashMap();
    public Map<String, Boolean> waitingForMacroBind = new HashMap();
    public boolean recordCamera = true;
    public boolean recordCrouch = true;
    public boolean recordJump = true;
    public boolean recordMovement = true;
    public boolean recordSprint = true;
    public boolean recordPlace = true;
    public boolean recordAttack = true;
    public boolean recordUse = true;
    public boolean recordDrop = false;
    public boolean recordSwapHands = true;
    public boolean playbackCamera = true;
    public boolean playbackCrouch = true;
    public boolean playbackJump = true;
    public boolean playbackMovement = true;
    public boolean playbackSprint = true;
    public boolean playbackPlace = true;
    public boolean playbackAttack = true;
    public boolean playbackUse = true;
    public boolean playbackDrop = true;
    public boolean playbackSwapHands = true;
}
