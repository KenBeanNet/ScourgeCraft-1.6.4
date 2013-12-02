package mods.scourgecraft;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

public class FactionSavedData extends WorldSavedData {
	
	public FactionSavedData(String par1String) {
		super(par1String);
		key = par1String;
	}
	
	public FactionSavedData() {
		super(key);
		// TODO Auto-generated constructor stub
	}

	public static String key = "Factions";

	// Fields containing your data here
	
	public static FactionSavedData forWorld(World world) {
                // Retrieves the MyWorldData instance for the given world, creating it if necessary
		MapStorage storage = world.perWorldStorage;
		FactionSavedData result = (FactionSavedData)storage.loadData(FactionSavedData.class, key);
		if (result == null) {
			result = new FactionSavedData(key);
			storage.setData(key, result);
		}
		return result;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		ScourgeCraftCore.instance.factionController.setRoundId(nbttagcompound.getInteger("RoundId"));
		ScourgeCraftCore.instance.factionController.setPlayerCount(nbttagcompound.getIntArray("FactionPlayerCount"));
		ScourgeCraftCore.instance.factionController.setKillCount(nbttagcompound.getIntArray("FactionKillCount"));
		ScourgeCraftCore.instance.factionController.setDeathCount(nbttagcompound.getIntArray("FactionDeathCount"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setInteger("RoundId", ScourgeCraftCore.instance.factionController.getRoundId());
		nbttagcompound.setIntArray("FactionPlayerCount", ScourgeCraftCore.instance.factionController.getPlayerCount());
		nbttagcompound.setIntArray("FactionKillCount", ScourgeCraftCore.instance.factionController.getKillCount());
		nbttagcompound.setIntArray("FactionDeathCount", ScourgeCraftCore.instance.factionController.getDeathCount());
	}

}