package com.github.glowingpotato.fastredstone.world;

import com.github.glowingpotato.fastredstone.FastRedstoneMod;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class WireGraphData extends WorldSavedData {

	private static final String DATA_NAME = FastRedstoneMod.MODID + "_WireGraphData";

	public WireGraphData() {
		super(DATA_NAME);
	}

	@Override
	public void readFromNBT(NBTTagCompound arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public static WireGraphData get(World world) {
		MapStorage storage = world.getMapStorage();
		WireGraphData instance = (WireGraphData) storage.getOrLoadData(WireGraphData.class, DATA_NAME);

		if (instance == null) {
			instance = new WireGraphData();
			storage.setData(DATA_NAME, instance);
		}
		return instance;
	}

}
