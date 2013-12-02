package mods.scourgecraft.network.packet;

import java.util.logging.Level;

import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import mods.scourgecraft.ScourgeCraftCore;
import mods.scourgecraft.network.ScourgePacket;

public class Packet2RegisterFaction extends ScourgePacket {

	private byte factionId;
	
	public Packet2RegisterFaction() { } // Be sure to always have the default constructor in your class, or the reflection code will fail!
	
	
	public Packet2RegisterFaction(byte par1FactionId)
	{
		this.factionId = par1FactionId;
	}
	@Override
	protected void write(ByteArrayDataOutput out) {
		out.writeByte(factionId);
	}

	@Override
	protected void read(ByteArrayDataInput in) {
		this.factionId = in.readByte();
	}

	@Override
	protected void execute(EntityPlayer player, Side side) {
		if (side == Side.SERVER)
		{
			if (player.factionId == 0)
			{
				player.factionId = factionId;

				PacketDispatcher.sendPacketToAllPlayers(new Packet1UserInfo(player.username, player.factionId).makePacket());
				
				player.addChatMessage("You have successfully registered for the faction " + factionId);
				ScourgeCraftCore.instance.factionController.addPlayerToFaction(player, factionId);
				
				switch (factionId)
				{
					case 1:
					{
						player.setPositionAndUpdate(ScourgeCraftCore.configFactions.faction1_spawn_x, ScourgeCraftCore.configFactions.faction1_spawn_y, ScourgeCraftCore.configFactions.faction1_spawn_z);
						break;
					}
					case 2:
					{
						player.setPositionAndUpdate(ScourgeCraftCore.configFactions.faction2_spawn_x, ScourgeCraftCore.configFactions.faction2_spawn_y, ScourgeCraftCore.configFactions.faction2_spawn_z);
						break;
					}
					case 3:
					{
						player.setPositionAndUpdate(ScourgeCraftCore.configFactions.faction3_spawn_x, ScourgeCraftCore.configFactions.faction3_spawn_y, ScourgeCraftCore.configFactions.faction3_spawn_z);
						break;
					}
					case 4:
					{
						player.setPositionAndUpdate(ScourgeCraftCore.configFactions.faction4_spawn_x, ScourgeCraftCore.configFactions.faction4_spawn_y, ScourgeCraftCore.configFactions.faction4_spawn_z);
						break;
					}
				}
				
				player.addChatMessage("You have teleported your faction's spawn point.  Good luck!");
				
			}
			else
				FMLLog.log(ScourgeCraftCore.modid, Level.SEVERE,  "Player %s attempted to join a faction while already on one. Hacking!" + player.factionId, player.username);
		}
	}

}
