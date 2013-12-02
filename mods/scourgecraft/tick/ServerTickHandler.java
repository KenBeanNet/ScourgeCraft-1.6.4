package mods.scourgecraft.tick;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.EnumSet;

import mods.scourgecraft.FactionSavedData;
import mods.scourgecraft.ScourgeCraftCore;
import mods.scourgecraft.network.packet.Packet4FactionInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;

public class ServerTickHandler implements ITickHandler {
	private int tickCount = 0;

	public static FactionSavedData worldData = null;
	private boolean hasLoaded = false;
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) 
	{
		if (!hasLoaded && type.equals(EnumSet.of(TickType.WORLD)))
		{
			World world = null;
			for(int i = 0; i < tickData.length; i++)
			{
				if(tickData[i] instanceof World)
					world = (World)tickData[i];
				if(world == null)
				{
					return;
				}
			}
         
			worldData = FactionSavedData.forWorld(world);
			//worldData.markDirty();
			
			ScourgeCraftCore.instance.factionController.loadFactions();
			
			hasLoaded = true;
		}
		
		tickCount++;
		
		if (tickCount % 20 == 0)
		{
			ScourgeCraftCore.instance.factionController.onUpdate();
		}
		
		if (tickCount == 100)
		{
			if(type.equals(EnumSet.of(TickType.WORLD)))
			{
				World world = null;
				for(int i = 0; i < tickData.length; i++)
				{
					if(tickData[i] instanceof World)
						world = (World)tickData[i];
					if(world == null)
					{
						return;
					}
				}
	         
				worldData.markDirty();

				PacketDispatcher.sendPacketToAllPlayers(new Packet4FactionInfo(ScourgeCraftCore.instance.factionController).makePacket());
				tickCount = 0;
			}
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		
	}

	@Override
	public EnumSet<TickType> ticks() {
		// TODO Auto-generated method stub
		return EnumSet.of(TickType.SERVER, TickType.WORLD);
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
