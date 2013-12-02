package mods.scourgecraft.tick;

import java.io.IOException;
import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import mods.scourgecraft.ScourgeCraftCore;
import mods.scourgecraft.network.packet.Packet3LoginSuccessful;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;

public class PlayerTickHandler implements ITickHandler {
	private int tickCount = 0;

	public boolean sentLoginUpdate = false; // Our first tick in-game.  We need to learn about others, It is reset also when a player connects to the server to false.
	private byte alertTimer = 0;
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) 
	{
		tickCount++;
		if (tickCount >= 100)
		{
			if (!sentLoginUpdate) 
			{
				for(Object tick : tickData) {
					if (tick instanceof EntityPlayer) 
					{
						sentLoginUpdate = true;
						PacketDispatcher.sendPacketToServer(new Packet3LoginSuccessful(((EntityPlayer) tick).getEntityName()).makePacket());
					}
				}
			}
			tickCount = 0;
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		
	}

	@Override
	public EnumSet<TickType> ticks() {
		// TODO Auto-generated method stub
		return EnumSet.of(TickType.PLAYER);
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
