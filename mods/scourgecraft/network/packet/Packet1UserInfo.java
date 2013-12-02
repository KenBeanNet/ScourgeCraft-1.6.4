package mods.scourgecraft.network.packet;

import java.util.logging.Level;

import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import mods.scourgecraft.network.ScourgePacket;

public class Packet1UserInfo extends ScourgePacket {

	private String entityName;
    private byte factionId;
   
    public Packet1UserInfo(String par1EntityName, byte par1FactionId) {
    		this.entityName = par1EntityName;
            this.factionId = par1FactionId;
    }

    public Packet1UserInfo() { } // Be sure to always have the default constructor in your class, or the reflection code will fail!

    @Override
    protected void write(ByteArrayDataOutput out) {
    		out.writeUTF(entityName);
            out.writeByte(factionId);
    }

    @Override
    protected void read(ByteArrayDataInput in) {
    	entityName = in.readUTF();
    	factionId = in.readByte();
    }

    @Override
    protected void execute(EntityPlayer player, Side side) {
            if (side.isClient()) {
                    EntityPlayer remotePlayer = player.worldObj.getPlayerEntityByName(entityName);
                    if (remotePlayer != null)
                    {
                    	remotePlayer.factionId = factionId;
                    }
                    else
                    	FMLLog.log(Level.SEVERE, "Unable to find player on Packet1UserInfo Name : " + entityName);
            } 
    }
}
