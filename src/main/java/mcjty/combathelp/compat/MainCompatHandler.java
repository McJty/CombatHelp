package mcjty.combathelp.compat;

import mcjty.combathelp.compat.intwheel.WheelCompatibility;
import net.minecraftforge.fml.common.Loader;

public class MainCompatHandler {

    public static void registerWheel() {
        if (Loader.isModLoaded("intwheel")) {
            WheelCompatibility.register();
        }
    }

}
