package mcjty.combathelp;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class KeyBindings {

    public static KeyBinding shieldHotSwap;
    public static KeyBinding restoreHotbar1;
    public static KeyBinding restoreHotbar2;
    public static KeyBinding restoreHotbar3;

    public static void init() {
        shieldHotSwap = new KeyBinding("key.shieldwield", KeyConflictContext.IN_GAME, Keyboard.KEY_BACK, "key.categories.combathelp");
        restoreHotbar1 = new KeyBinding("key.restoreHotbar1", KeyConflictContext.IN_GAME, Keyboard.KEY_NUMPAD7, "key.categories.combathelp");
        restoreHotbar2 = new KeyBinding("key.restoreHotbar2", KeyConflictContext.IN_GAME, Keyboard.KEY_NUMPAD8, "key.categories.combathelp");
        restoreHotbar3 = new KeyBinding("key.restoreHotbar3", KeyConflictContext.IN_GAME, Keyboard.KEY_NUMPAD9, "key.categories.combathelp");
        ClientRegistry.registerKeyBinding(shieldHotSwap);
        ClientRegistry.registerKeyBinding(restoreHotbar1);
        ClientRegistry.registerKeyBinding(restoreHotbar2);
        ClientRegistry.registerKeyBinding(restoreHotbar3);
    }
}
