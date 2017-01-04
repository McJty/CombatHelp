package mcjty.combathelp.compat.intwheel;

import mcjty.combathelp.CombatHelp;
import mcjty.intwheel.api.IInteractionWheel;
import mcjty.intwheel.api.IWheelActionProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public class WheelCompatibility {

    private static boolean registered;

    public static void register() {
        if (registered)
            return;
        registered = true;
        FMLInterModComms.sendFunctionMessage("intwheel", "getTheWheel", "mcjty.combathelp.compat.intwheel.WheelCompatibility$GetTheWheel");
    }


    public static class GetTheWheel implements com.google.common.base.Function<IInteractionWheel, Void> {

        public static IInteractionWheel wheel;

        @Nullable
        @Override
        public Void apply(IInteractionWheel theWheel) {
            wheel = theWheel;
            CombatHelp.logger.log(Level.INFO, "Enabled support for The Interaction Wheel");
            wheel.registerProvider(new IWheelActionProvider() {
                @Override
                public String getID() {
                    return CombatHelp.MODID + ".wheel";
                }

                @Override
                public void updateWheelActions(@Nonnull Set<String> actions, @Nonnull EntityPlayer player, World world, @Nullable BlockPos pos) {
                    actions.add(HandleWieldShieldAction.ACTION_WIELDSHIELD);
                }
            });
            wheel.getRegistry().register(new HandleWieldShieldAction());
            return null;
        }
    }
}
