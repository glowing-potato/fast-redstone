package com.github.glowingpotato.fastredstone;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = FastRedstoneMod.MODID, name = FastRedstoneMod.NAME, version = FastRedstoneMod.VERSION)
public class FastRedstoneMod {
	public static final String MODID = "fastredstone";
	public static final String NAME = "Fast Redstone";
	public static final String VERSION = "1.0";

	private static Logger logger;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {

	}

}
