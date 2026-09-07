package net.snowshowgames.macro;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public class MacroConfig {
    public boolean showIndicator = true;
    public boolean loop = false;
    public boolean recordOnFirstInput = false;
    public int startRecordKey = -1;
    public int stopRecordKey = -1;
    // additional config options may be added later
}
