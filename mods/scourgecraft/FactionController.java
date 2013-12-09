package mods.scourgecraft;

import java.util.Date;

import cpw.mods.fml.common.network.PacketDispatcher;
import mods.scourgecraft.network.packet.Packet5RoundInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.util.ChatMessageComponent;

public class FactionController {

	public static final int FACTION_COUNT = 4;
	
	private int roundId; 
	private String[] factionName = new String[FACTION_COUNT]; //This holds the name for the Factions, changed in many locations;
	private int[] playerCount = new int[FACTION_COUNT]; //This holds the current player count for each Faction;
	private int[] killCount = new int[FACTION_COUNT]; //This is how many kills in the current round to help determine balance of power;
	private int[] deathCount = new int[FACTION_COUNT]; //This is how many deaths in the current round to help determine balance of power;
	
	private byte roundType;
	
	public FactionController()
	{
	}
	
	public int getPlayerCountByTeamId(int teamId){
		if (teamId > FACTION_COUNT)
			return 0;
		System.currentTimeMillis();
		return playerCount[teamId - 1]; // We must minus one because the array is 0-3 not 1-4.
	}
	
	public void addPlayerToFaction(EntityPlayer ep, int teamId)
	{
		if (teamId > FACTION_COUNT)
			return;
		
		playerCount[teamId - 1]++;
	}
	
	public void onUpdate()
	{
		if (roundType != determineRoundType())
		{
			if (roundType == 3) // War Period is Ending
			{
				PacketDispatcher.sendPacketToAllPlayers(new Packet5RoundInfo((byte)0).makePacket());
			}
			else if (roundType == 2) // Balance Period is ending
			{
				PacketDispatcher.sendPacketToAllPlayers(new Packet5RoundInfo((byte)3).makePacket());
			}
			else if (roundType == 1) // Perp Period is Ending
			{
				PacketDispatcher.sendPacketToAllPlayers(new Packet5RoundInfo((byte)2).makePacket());
			}
			else if (roundType == 0) // Warmup Time is Ending
			{
				PacketDispatcher.sendPacketToAllPlayers(new Packet5RoundInfo((byte)1).makePacket());
			}
			
			roundType = determineRoundType();
		}
	}
	
	public int getPowerByFactionId(int factionId)
	{
		double totalPower = 0.0;
		double modifier = 1.0 - ((double)playerCount[factionId - 1] / (double)getTotalPlayerCount());
		totalPower += killCount[factionId - 1] * modifier;
		totalPower -= deathCount[factionId - 1] * (modifier / 5);
		
		if (totalPower <= 0)
			return 1;
		
		return (int)totalPower;
	}
	
	public int[] getGuiWidthLength(int totalWidth)
	{
		int[] guiWidth = new int[FACTION_COUNT];
		int[] powerRankings = new int[FACTION_COUNT];
		int totalPower = 0;
		for (int i = 0; i < FACTION_COUNT; i++)
		{
			int powerForThisFaction = getPowerByFactionId(i + 1);
			powerRankings[i] = powerForThisFaction;
			totalPower += powerForThisFaction;
		}
		for (int i = 0; i < FACTION_COUNT; i++)
		{
			guiWidth[i] = (int)(((double)powerRankings[i] / (double)totalPower) * totalWidth);
		}
		
		return guiWidth;
	}
	
	public void loadFactions()
	{
		//First determine if the server has been down long enough to reset the roundId
		//The Round ID is loaded by the config, but then checked live.
		
		roundType = determineRoundType();
		
		if (roundId == 0) //This should only appear true when a player runs a server for the first time.
			roundId = getCurrentRoundId();
		
		if (roundId != 0 && roundId != getCurrentRoundId()) //Server has been down during the reset, we need to create a new round. Reset all stats.
		{
			this.roundId = getCurrentRoundId();
			this.playerCount = new int[FACTION_COUNT]; 
			this.killCount = new int[FACTION_COUNT]; 
			this.deathCount = new int[FACTION_COUNT]; 
		}
	}
	
	private int getCurrentRoundId()
	{
		//Rounds are based on the 4 hour marks.
		Date now = new Date();
		
		int hourNeeded = 0;
		switch (now.getHours())
		{
			case 0:
			case 1:
			case 2:
			case 3:
				hourNeeded = 0;
				break;
			case 4:
			case 5:
			case 6:
			case 7:
				hourNeeded = 4;
				break;
			case 8:
			case 9:
			case 10:
			case 11:
				hourNeeded = 8;
				break;
			case 12:
			case 13:
			case 14:
			case 15:
				hourNeeded = 12;
				break;
			case 16:
			case 17:
			case 18:
			case 19:
				hourNeeded = 16;
				break;
			case 20:
			case 21:
			case 22:
			case 23:
				hourNeeded = 20;
				break;
				
		}
		return hourNeeded;
	}
	
	private int getTotalPlayerCount()
	{
		int toReturn = 0;
		for (int i = 0; i < FACTION_COUNT; i++)
		{
			toReturn += playerCount[i];
		}
		
		if (toReturn == 0)
			return 1;
		
		return toReturn;
	}
	
	//Type 0 - Warmup period, Type 1 Prep Period, Type 2 Balance Period, Type 3 War period
	private byte determineRoundType()
	{
		Date now = new Date();

		switch (now.getHours()) 
		{
			case 0:
			case 4:
			case 8:
			case 12:
			case 16:
			case 20:
			{
				if (now.getMinutes() <= 5)
					return 0;
				else
					return 1;
			}
		}
		switch (now.getHours())
		{
			case 1:
			case 2:
			case 5:
			case 6:
			case 9:
			case 10:
			case 13:
			case 14:
			case 17:
			case 18:
			case 21:
			case 22:
				return 2;
		}
		switch (now.getHours())
		{
			case 3:
			case 7:
			case 11:
			case 15:
			case 19:
			case 23:
				return 3;
		}
		
		return 0;
	}
	

	
	public int getRoundId()
	{
		return this.roundId;
	}
	
	public int[] getPlayerCount()
	{
		return playerCount;
	}
	
	public int[] getKillCount()
	{
		return killCount;
	}
	
	public int[] getDeathCount()
	{
		return deathCount;
	}
	
	public void setPlayerCount(int[] playerArray)
	{
		this.playerCount = playerArray;
	}
	
	public void setKillCount(int[] killArray)
	{
		this.killCount = killArray;
	}
	
	public void setDeathCount(int[] deathArray)
	{
		this.deathCount = deathArray;
	}

	public void setRoundId(int incomingRoundId) {
		this.roundId = incomingRoundId;
	}
	
	public byte getRoundType()
	{
		return roundType;
	}
	
	public static int getColorByFactionId(byte factionId)
	{
		switch (factionId)
		{
			case 1:
				return 0xFF0000;
			case 2:
				return 0x0000FF;
			case 3:
				return 0x009933;
			case 4: 
				return 0xFFFF00;
			default: 
				return 0xFFFFFF;
		}
	}

	public void addDeathToFaction(byte factionId) {
		if (factionId > 0 && factionId <= FACTION_COUNT)
				this.deathCount[factionId - 1]++;
	}
	public void addKillToFaction(byte factionId) {
		if (factionId > 0 && factionId <= FACTION_COUNT)
			this.killCount[factionId - 1]++;
	}
}
