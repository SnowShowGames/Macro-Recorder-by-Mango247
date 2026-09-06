/*
 * Decompiled with https://jar.tools
 */
package com.example;

import com.example.MacroRecorder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
class MacroRecorder$MacroFrame {
    final float yaw;
    final float pitch;
    final Boolean forward;
    final Boolean left;
    final Boolean back;
    final Boolean right;
    final Boolean jump;
    final Boolean sprint;
    final Boolean sneak;
    final Boolean attack;
    final Boolean use;
    final Boolean dropItem;
    final Boolean swapOffhand;
    final int selectedSlot;
    final int repeats;

    MacroRecorder$MacroFrame(float yaw, float pitch, Boolean forward, Boolean left, Boolean back, Boolean right, Boolean jump, Boolean sprint, Boolean sneak, Boolean attack, Boolean use, int selectedSlot, Boolean dropItem, Boolean swapOffhand, int repeats) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.forward = forward;
        this.left = left;
        this.back = back;
        this.right = right;
        this.jump = jump;
        this.sprint = sprint;
        this.sneak = sneak;
        this.attack = attack;
        this.use = use;
        this.selectedSlot = selectedSlot;
        this.dropItem = dropItem;
        this.swapOffhand = swapOffhand;
        this.repeats = repeats;
    }

    private boolean isEqualNullableBool(Boolean a, Boolean b) {
        if (a == null || b == null) {
            return true;
        }
        return a.equals(b);
    }

    private boolean isEqualFloat(float a, float b) {
        if (Float.isNaN(a) || Float.isNaN(b)) {
            return true;
        }
        return Float.compare(a, b) == 0;
    }

    boolean equalsWithoutRepeats(MacroRecorder.MacroFrame other) {
        if (other == null) {
            return false;
        }
        return this.isEqualFloat(this.yaw, other.yaw) && this.isEqualFloat(this.pitch, other.pitch) && this.isEqualNullableBool(this.forward, other.forward) && this.isEqualNullableBool(this.left, other.left) && this.isEqualNullableBool(this.back, other.back) && this.isEqualNullableBool(this.right, other.right) && this.isEqualNullableBool(this.jump, other.jump) && this.isEqualNullableBool(this.sprint, other.sprint) && this.isEqualNullableBool(this.sneak, other.sneak) && this.isEqualNullableBool(this.attack, other.attack) && this.isEqualNullableBool(this.use, other.use) && this.selectedSlot == other.selectedSlot && this.isEqualNullableBool(this.dropItem, other.dropItem) && this.isEqualNullableBool(this.swapOffhand, other.swapOffhand);
    }

    MacroRecorder.MacroFrame withIncreasedRepeats() {
        return new MacroRecorder.MacroFrame(this.yaw, this.pitch, this.forward, this.left, this.back, this.right, this.jump, this.sprint, this.sneak, this.attack, this.use, this.selectedSlot, this.dropItem, this.swapOffhand, this.repeats + 1);
    }
}
