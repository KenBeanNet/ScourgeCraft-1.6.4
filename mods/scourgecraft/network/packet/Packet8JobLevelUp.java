package mods.scourgecraft.network.packet;

import mods.scourgecraft.ClientProxy;
import mods.scourgecraft.JobController;
import mods.scourgecraft.ScourgeCraftCore;
import mods.scourgecraft.network.ScourgePacket;
import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class Packet8JobLevelUp extends ScourgePacket {

	private String jobType;
   
    public Packet8JobLevelUp(String par1JobType) {
    		this.jobType = par1JobType;
    }

    public Packet8JobLevelUp() { } // Be sure to always have the default constructor in your class, or the reflection code will fail!

    @Override
    protected void write(ByteArrayDataOutput out) {
    		out.writeUTF(this.jobType);
    }

    @Override
    protected void read(ByteArrayDataInput in) {
    	jobType = in.readUTF();
    }

    @Override
    protected void execute(EntityPlayer player, Side side) {
            if (side.isServer()) {
            	byte jobCurrentLevel = 0;
            	if (jobType.equals("Builder"))
            		jobCurrentLevel = player.builderLevel;
            	else if (jobType.equals("Warrior"))
            		jobCurrentLevel = player.warriorLevel;
            	else if (jobType.equals("Enchanter"))
            		jobCurrentLevel = player.enchanterLevel;
            	
            	if (player.vitalityPoints >= JobController.getNextLevelCostByInt(jobType, jobCurrentLevel))
            	{
                	player.vitalityPoints -= JobController.getNextLevelCostByInt(jobType, jobCurrentLevel);
                	if (jobType.equals("Builder"))
                		player.builderLevel++;
                	else if (jobType.equals("Warrior"))
                		player.warriorLevel++;
                	else if (jobType.equals("Enchanter"))
                		player.enchanterLevel++;
                	
                	player.addChatMessage("You have successfully upgraded the job " + jobType + " to level " + jobCurrentLevel++);
                	
                	PacketDispatcher.sendPacketToPlayer(new Packet7JobInfo(player.vitalityPoints, player.builderLevel, player.warriorLevel, player.enchanterLevel).makePacket(), (Player)player);
            	}
            } 
    }
}
