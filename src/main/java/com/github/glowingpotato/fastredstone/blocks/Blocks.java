package com.github.glowingpotato.fastredstone.blocks;

import com.github.glowingpotato.fastredstone.FastRedstoneMod;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = FastRedstoneMod.MODID)
public class Blocks {

	// Blocks
	public static final Block WIRE = new Wire().setUnlocalizedName("wire").setHardness(1.0f).setRegistryName("wire");
	public static final Block DELAYER = new Delayer().setUnlocalizedName("delayer").setHardness(1.0f).setRegistryName("delayer");
	public static final Block INVERTER = new Delayer().setUnlocalizedName("inverter").setHardness(1.0f).setRegistryName("inverter");
	public static final Block INPUT = new Delayer().setUnlocalizedName("input").setHardness(1.0f).setRegistryName("input");
	public static final Block OUTPUT = new Delayer().setUnlocalizedName("output").setHardness(1.0f).setRegistryName("output");

	// ItemBlocks
	public static final Item WIRE_ITEM = new ItemBlock(Blocks.WIRE).setRegistryName(Blocks.WIRE.getRegistryName());
	public static final Item DELAYER_ITEM = new ItemBlock(Blocks.DELAYER).setRegistryName(Blocks.DELAYER.getRegistryName());
	public static final Item INVERTER_ITEM = new ItemBlock(Blocks.INVERTER).setRegistryName(Blocks.INVERTER.getRegistryName());
	public static final Item INPUT_ITEM = new ItemBlock(Blocks.INPUT).setRegistryName(Blocks.INPUT.getRegistryName());
	public static final Item OUTPUT_ITEM = new ItemBlock(Blocks.OUTPUT).setRegistryName(Blocks.OUTPUT.getRegistryName());

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().register(Blocks.WIRE);
		event.getRegistry().register(Blocks.DELAYER);
		event.getRegistry().register(Blocks.INVERTER);
		event.getRegistry().register(Blocks.INPUT);
		event.getRegistry().register(Blocks.OUTPUT);
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(WIRE_ITEM);
		event.getRegistry().register(DELAYER_ITEM);
		event.getRegistry().register(INVERTER_ITEM);
		event.getRegistry().register(INPUT_ITEM);
		event.getRegistry().register(OUTPUT_ITEM);
	}

	@SubscribeEvent
	public static void registerRenders(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(WIRE_ITEM, 0, new ModelResourceLocation(WIRE_ITEM.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(DELAYER_ITEM, 0, new ModelResourceLocation(DELAYER_ITEM.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(INVERTER_ITEM, 0, new ModelResourceLocation(INVERTER_ITEM.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(INPUT_ITEM, 0, new ModelResourceLocation(INPUT_ITEM.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(OUTPUT_ITEM, 0, new ModelResourceLocation(OUTPUT_ITEM.getRegistryName(), "inventory"));
	}

}
