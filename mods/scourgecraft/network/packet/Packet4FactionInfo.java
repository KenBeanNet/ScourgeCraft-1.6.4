package mods.scourgecraft.network.packet;

import mods.scourgecraft.FactionController;
import mods.scourgecraft.ScourgeCraftCore;
import mods.scourgecraft.network.ScourgePacket;
import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class Packet4FactionInfo extends ScourgePacket {

	private int[] playerCount = new int[FactionController.FACTION_COUNT];
	private int[] killCount = new int[FactionController.FACTION_COUNT];
	private int[] deathCount = new int[FactionController.FACTION_COUNT];
   
    //This packet's primary role is after you have logged in, you need to be notify of information of all players around you.  
    public Packet4FactionInfo(FactionController controller) {
    		this.playerCount = controller.getPlayerCount();
    		this.killCount = controller.getKillCount();
    		this.deathCount = controller.getDeathCount();
    }

    public Packet4FactionInfo() { } // Be sure to always have the default constructor in your class, or the reflection code will fail!

    @Override
    protected void write(ByteArrayDataOutput out) {
    		for (int i = 0; i < FactionController.FACTION_COUNT; i++)
    		{
    			out.writeInt(this.playerCount[i]);
    		}
    		for (int i = 0; i < FactionController.FACTION_COUNT; i++)
    		{
    			out.writeInt(this.killCount[i]);
    		}
    		for (int i = 0; i < FactionController.FACTION_COUNT; i++)
    		{
    			out.writeInt(this.deathCount[i]);
    		}
    }

    @Override
    protected void read(ByteArrayDataInput in) {
    	for (int i = 0; i < FactionController.FACTION_COUNT; i++)
		{
    		playerCount[i] = in.readInt();
		}
		for (int i = 0; i < FactionController.FACTION_COUNT; i++)
		{
			killCount[i] = in.readInt();
		}
		for (int i = 0; i < FactionController.FACTION_COUNT; i++)
		{
			deathCount[i] = in.readInt();
		}
    }

    @Override
    protected void execute(EntityPlayer player, Side side) {
            if (side.isClient()) {
            	ScourgeCraftCore.instance.factionController.setPlayerCount(playerCount);
            	ScourgeCraftCore.instance.factionController.setKillCount(killCount);
            	ScourgeCraftCore.instance.factionController.setDeathCount(deathCount);
            } 
    }
}
