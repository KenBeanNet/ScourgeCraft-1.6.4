package mods.scourgecraft;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class PermissionEventListener {

	 @ForgeSubscribe
	  public void PlayerInteract(PlayerInteractEvent event)
	  {
		 if (event.entityPlayer.worldObj.isRemote)
			 return;
		 
	    String cancelMessage = "";

	    if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK)
	    {
	      boolean cancelEvent = false;

	      if (!PlayerIsOnFaction(event.entityPlayer))
	      {
	        cancelMessage = "[ScourgeCraft] You must sign up for a faction.";
	        cancelEvent = true;
	      }
	      else if (IsProtectedAgainstPlayer(event.entityPlayer, event.x, event.z))
	      {
	        cancelMessage = "[ScourgeCraft] You are not allowed to demolish here. This area is protected.";
	        cancelEvent = true;
	      }

	      if (!PlayerIsOP(event.entityPlayer.username) && cancelEvent)
	      {
	        if (event.isCancelable())
	        {
	          event.entityPlayer.addChatMessage(cancelMessage);
	          event.setCanceled(true);
	        }
	      }
	    }
	    
	    if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)
	    {
	      boolean cancelEvent = false;

	      if (!PlayerIsOnFaction(event.entityPlayer))
	      {
	        cancelMessage = "[ScourgeCraft] You must sign up for a faction.";
	        cancelEvent = true;
	      }
	      
	      int blockID = event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z);
	      
	      if (blockID == ScourgeCraftCore.instance.configBlocks.factionSelectorID)
	      {
	    	  cancelEvent = false;
	      }
	      
	      if (!PlayerIsOP(event.entityPlayer.username) && cancelEvent)
	      {
	    	  event.entityPlayer.addChatMessage(cancelMessage);
	          event.setCanceled(true);
	      }
	    }
	  }
	 
	 
	 
	 private boolean IsProtectedAgainstPlayer(EntityPlayer ep, int x, int z) 
	 {
		if (ScourgeCraftCore.instance.factionController.getRoundType() == 2 || ScourgeCraftCore.instance.factionController.getRoundType() == 3)
		{
			if (ep.factionId != 1)
			{
				if ((x > ScourgeCraftCore.instance.configFactions.faction1_spawn_x + 20 || x < ScourgeCraftCore.instance.configFactions.faction1_spawn_x - 20)
						&& (z > ScourgeCraftCore.instance.configFactions.faction1_spawn_z + 20 || z < ScourgeCraftCore.instance.configFactions.faction1_spawn_z - 20))
					return true;
			}
			else if (ep.factionId != 2)
			{
				if ((x > ScourgeCraftCore.instance.configFactions.faction2_spawn_x + 20 || x < ScourgeCraftCore.instance.configFactions.faction2_spawn_x - 20)
						&& (z > ScourgeCraftCore.instance.configFactions.faction2_spawn_z + 20 || z < ScourgeCraftCore.instance.configFactions.faction2_spawn_z - 20))
					return true;
			}
			else if (ep.factionId != 3)
			{
				if ((x > ScourgeCraftCore.instance.configFactions.faction3_spawn_x + 20 || x < ScourgeCraftCore.instance.configFactions.faction3_spawn_x - 20)
						&& (z > ScourgeCraftCore.instance.configFactions.faction3_spawn_z + 20 || z < ScourgeCraftCore.instance.configFactions.faction3_spawn_z - 20))
					return true;
			}
			else if (ep.factionId != 4)
			{
				if ((x > ScourgeCraftCore.instance.configFactions.faction4_spawn_x + 20 || x < ScourgeCraftCore.instance.configFactions.faction4_spawn_x - 20)
						&& (z > ScourgeCraftCore.instance.configFactions.faction4_spawn_z + 20 || z < ScourgeCraftCore.instance.configFactions.faction4_spawn_z - 20))
					return true;
			}
		}
		return false;
	}

	public ArrayList<String> GetAvailablePerms()
	  {
	    ArrayList perms = new ArrayList();
	    perms.add("kick");
	    perms.add("ban");
	    perms.add("mute");
	    perms.add("build");
	    perms.add("demolish");
	    perms.add("home");
	    perms.add("tp");
	    perms.add("tpme");
	    perms.add("give");
	    perms.add("giveme");
	    perms.add("setspawn");
	    perms.add("tpspawn");
	    perms.add("protect");

	    return perms;
	  }

	  public boolean PlayerIsOP(String username)
	  {
	    if (MinecraftServer.getServerConfigurationManager(MinecraftServer.getServer()).isPlayerOpped(username)) {
	      return true;
	    }
	    return false;
	  }
	  
	  public boolean PlayerIsOnFaction(EntityPlayer ep)
	  {
			if (ep.factionId != 0)
				return true;
			return false;
	  }
}
