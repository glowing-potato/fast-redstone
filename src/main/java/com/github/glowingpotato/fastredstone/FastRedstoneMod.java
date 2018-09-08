package com.github.glowingpotato.fastredstone;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = FastRedstoneMod.MODID, name = FastRedstoneMod.NAME, version = FastRedstoneMod.VERSION)
public class FastRedstoneMod {
	public static final String MODID = "fastredstone";
	public static final String NAME = "Fast Redstone";
	public static final String VERSION = "1.0";

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
	}
}
