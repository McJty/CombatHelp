package mcjty.combathelp;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import mcjty.combathelp.proxy.CommonProxy;

import java.io.File;

@Mod(modid = CombatHelp.MODID, name="CombatHelp", dependencies =
        "required-after:Forge@["+ CombatHelp.MIN_FORGE_VER+",)",
        version = CombatHelp.VERSION)
public class CombatHelp {
    public static final String MODID = "combathelp";
    public static final String VERSION = "0.1.0";
    public static final String MIN_FORGE_VER = "12.16.1.1896";

    @SidedProxy(clientSide="mcjty.combathelp.proxy.ClientProxy", serverSide="mcjty.combathelp.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance("CombatHelp")
    public static CombatHelp instance;
    public static Logger logger;
    public static File mainConfigDir;
    public static File modConfigDir;
    public static Configuration config;

    /**
     * Run before anything else. Read your config, create blocks, items, etc, and
     * register them with the GameRegistry.
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        logger = e.getModLog();
        mainConfigDir = e.getModConfigurationDirectory();
        modConfigDir = new File(mainConfigDir.getPath());
        config = new Configuration(new File(modConfigDir, "combathelp.cfg"));
        proxy.preInit(e);

//        FMLInterModComms.sendMessage("Waila", "register", "mcjty.wailasupport.WailaCompatibility.load");
    }

    /**
     * Do your mod setup. Build whatever data structures you care about. Register recipes.
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    /**
     * Handle interaction with other mods, complete your setup based on this.
     */
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
}
