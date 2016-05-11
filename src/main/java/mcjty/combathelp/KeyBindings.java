package mcjty.combathelp;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class KeyBindings {

    public static KeyBinding shieldHotSwap;

    public static void init() {
        shieldHotSwap = new KeyBinding("key.shieldwield", Keyboard.KEY_BACKSLASH, "key.categories.combathelp");
        ClientRegistry.registerKeyBinding(shieldHotSwap);
    }
}
