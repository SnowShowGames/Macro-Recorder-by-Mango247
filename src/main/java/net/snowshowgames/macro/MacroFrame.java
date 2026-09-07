package net.snowshowgames.macro;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public class MacroFrame {
    public final float yaw;
    public final float pitch;
    public final Boolean forward;
    public final Boolean left;
    public final Boolean back;
    public final Boolean right;
    public final Boolean jump;
    public final Boolean sprint;
    public final Boolean sneak;
    public final Boolean attack;
    public final Boolean use;
    public final Boolean dropItem;
    public final Boolean swapOffhand;
    public final int selectedSlot;
    public final int repeats;

    public MacroFrame(float yaw, float pitch, Boolean forward, Boolean left, Boolean back, Boolean right, Boolean jump, Boolean sprint, Boolean sneak, Boolean attack, Boolean use, Boolean dropItem, Boolean swapOffhand, int selectedSlot, int repeats) {
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
        this.dropItem = dropItem;
        this.swapOffhand = swapOffhand;
        this.selectedSlot = selectedSlot;
        this.repeats = repeats;
    }

    private boolean isEqualNullableBool(Boolean a, Boolean b) {
        if (a == null || b == null) return true;
        return a.equals(b);
    }

    private boolean isEqualFloat(float a, float b) {
        if (Float.isNaN(a) || Float.isNaN(b)) return true;
        return Float.compare(a, b) == 0;
    }

    public boolean equalsWithoutRepeats(MacroFrame other) {
        if (other == null) return false;
        return isEqualFloat(this.yaw, other.yaw) && isEqualFloat(this.pitch, other.pitch)
                && isEqualNullableBool(this.forward, other.forward) && isEqualNullableBool(this.left, other.left)
                && isEqualNullableBool(this.back, other.back) && isEqualNullableBool(this.right, other.right)
                && isEqualNullableBool(this.jump, other.jump) && isEqualNullableBool(this.sprint, other.sprint)
                && isEqualNullableBool(this.sneak, other.sneak) && isEqualNullableBool(this.attack, other.attack)
                && isEqualNullableBool(this.use, other.use) && this.selectedSlot == other.selectedSlot
                && isEqualNullableBool(this.dropItem, other.dropItem) && isEqualNullableBool(this.swapOffhand, other.swapOffhand);
    }

    public MacroFrame withIncreasedRepeats() {
        return new MacroFrame(this.yaw, this.pitch, this.forward, this.left, this.back, this.right, this.jump, this.sprint, this.sneak, this.attack, this.use, this.dropItem, this.swapOffhand, this.selectedSlot, this.repeats + 1);
    }
}
