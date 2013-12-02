package mods.scourgecraft;

import java.util.HashMap;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import mods.scourgecraft.network.packet.Packet1UserInfo;
import mods.scourgecraft.network.packet.Packet3LoginSuccessful;
import mods.scourgecraft.network.packet.Packet5RoundInfo;
import mods.scourgecraft.network.packet.Packet6OnRespawn;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class PlayerEventListener {
	
	protected HashMap playerKeepsFaction = new HashMap();

	@ForgeSubscribe
    public void livingDies(LivingDeathEvent event)
    {
            if ((event.entityLiving instanceof EntityPlayer))
            {
                    EntityPlayer player = (EntityPlayer)event.entityLiving;
                    
                    if (player.factionId != 0)
                    {
                    	ScourgeCraftCore.instance.factionController.addDeathToFaction(player.factionId);
                    }
                    playerKeepsFaction.put(player.username, player.factionId);
            }
    }
	
	@ForgeSubscribe
	public void onEntityLivingDeath(LivingDeathEvent event)
	{
		if (event.source.getEntity() instanceof EntityPlayer && event.entity instanceof EntityPlayer)
		{
			EntityPlayer attacker = (EntityPlayer)event.source.getEntity();
			EntityPlayer reciever = (EntityPlayer)event.entity;
			if (attacker.factionId != 0 && reciever.factionId != 0)
            {
				ScourgeCraftCore.instance.factionController.addKillToFaction(attacker.factionId);
            }
		}
	}
	
	@ForgeSubscribe
	public void entityAttacked(LivingAttackEvent event)
	{
		Entity attackedEnt = event.entity;
		DamageSource attackSource = event.source;
		
		if (attackedEnt != null && attackedEnt instanceof EntityPlayer)
		{
			if (attackSource.getEntity() != null && attackSource.getEntity() instanceof EntityPlayer)
			{
				EntityPlayer attacked = (EntityPlayer)attackedEnt;
				EntityPlayer attacker = (EntityPlayer)attackSource.getEntity();
				
				if (attacked.factionId == attacker.factionId)
				{
					if (event.isCancelable())
						event.setCanceled(true);
				}
			}
		}
		
	}

	public void onPlayerRespawn(EntityPlayer player) {
		PacketDispatcher.sendPacketToPlayer(new Packet6OnRespawn().makePacket(), (Player)player);
		if (playerKeepsFaction.containsKey(player.username))
		{
			player.addChatMessage("You have been added back to faction : " + playerKeepsFaction.get(player.username));
			player.factionId = Byte.parseByte(playerKeepsFaction.get(player.username).toString());
			playerKeepsFaction.remove(player.username);
		}
	}
}
