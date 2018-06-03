package timaxa007.no_drop;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = NoDropsMod.MODID, name = NoDropsMod.NAME, version = NoDropsMod.VERSION)
public final class NoDropsMod {

	public static final String
	MODID = "nodrops",
	NAME = "No Drops",
	VERSION = "0.3";

	@Mod.Instance(MODID) public static NoDropsMod instance;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(new EventsFML());
		MinecraftForge.EVENT_BUS.register(new EventsForge());
	}

	@Mod.EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		event.registerServerCommand(new NoDropCommand());
	}

}
