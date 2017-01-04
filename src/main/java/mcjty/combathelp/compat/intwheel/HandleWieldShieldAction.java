package mcjty.combathelp.compat.intwheel;

import mcjty.combathelp.varia.Tools;
import mcjty.intwheel.api.IWheelAction;
import mcjty.intwheel.api.WheelActionElement;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class HandleWieldShieldAction implements IWheelAction {

    public static final String ACTION_WIELDSHIELD = "combathelp.wieldshield";

    @Override
    public String getId() {
        return ACTION_WIELDSHIELD;
    }

    @Override
    public WheelActionElement createElement() {
        return new WheelActionElement(ACTION_WIELDSHIELD).description("Wield your shield", null).texture("combathelp:textures/gui/wheel_actions.png", 0, 0, 0, 0+32, 128, 128);
    }

    @Override
    public boolean performClient(EntityPlayer player, World world, @Nullable BlockPos pos, boolean extended) {
        return true;
    }

    @Override
    public void performServer(EntityPlayer player, World world, @Nullable BlockPos pos, boolean extended) {
        Tools.wieldShield((EntityPlayerMP) player);
    }
}
