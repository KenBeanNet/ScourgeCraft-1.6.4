package mods.scourgecraft;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.minecraft.entity.player.EntityPlayer;
import mods.scourgecraft.network.packet.Packet7JobInfo;
import mods.scourgecraft.permission.SGJob;

public class JobController {

	public static String getNextLevelCost(String jobName, byte currentLevel)
	{
		for (SGJob r : ScourgeCraftCore.instance.ranks)
		{
			if (r.title.equals(jobName))
			{
				if (r.level == currentLevel + 1)
				{
					return Integer.toString(r.cost);
				}
			}
		}
		return "MAX";
	}
	
	public static int getNextLevelCostByInt(String jobName, byte currentLevel)
	{
		for (SGJob r : ScourgeCraftCore.instance.ranks)
		{
			if (r.title.equals(jobName))
			{
				if (r.level == currentLevel + 1)
				{
					return r.cost;
				}
			}
		}
		return Integer.MAX_VALUE;
	}
	
	public static void increaseVitality(EntityPlayer player, int amount, String reason)
	{
		if (player != null)
		{
			player.vitalityPoints += amount;
			if (!reason.equals(""))
			{
				player.addChatMessage(reason);
			}
			PacketDispatcher.sendPacketToPlayer(new Packet7JobInfo(player.vitalityPoints, player.builderLevel, player.warriorLevel, player.enchanterLevel).makePacket(), (Player)player);
		}
	}
	
	public static void increaseVitality(EntityPlayer player, int amount)
	{
		increaseVitality(player, amount, "");
	}
}
