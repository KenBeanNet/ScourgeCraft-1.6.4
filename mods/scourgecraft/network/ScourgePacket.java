package mods.scourgecraft.network;

import java.util.logging.Level;

import mods.scourgecraft.network.packet.Packet1UserInfo;
import mods.scourgecraft.network.packet.Packet2RegisterFaction;
import mods.scourgecraft.network.packet.Packet3LoginSuccessful;
import mods.scourgecraft.network.packet.Packet4FactionInfo;
import mods.scourgecraft.network.packet.Packet5RoundInfo;
import mods.scourgecraft.network.packet.Packet6OnRespawn;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

public abstract class ScourgePacket {

    public static final String CHANNEL = "scourgecraft";
    private static final BiMap<Integer, Class<? extends ScourgePacket>> idMap;
           
    static {
            ImmutableBiMap.Builder<Integer, Class<? extends ScourgePacket>> builder = ImmutableBiMap.builder();
           
            builder.put(Integer.valueOf(1), Packet1UserInfo.class);
            builder.put(Integer.valueOf(2), Packet2RegisterFaction.class);
            builder.put(Integer.valueOf(3), Packet3LoginSuccessful.class);
            builder.put(Integer.valueOf(4), Packet4FactionInfo.class);
            builder.put(Integer.valueOf(5), Packet5RoundInfo.class);
            builder.put(Integer.valueOf(6), Packet6OnRespawn.class);
            // we add all our packets here later
           
            idMap = builder.build();
    }

    public static ScourgePacket constructPacket(int packetId)  {
            Class<? extends ScourgePacket> clazz = idMap.get(Integer.valueOf(packetId));
            if (clazz == null) {
                    FMLLog.log(Level.SEVERE, "Unknown Packet ID : " + packetId);
            } else {
                    try {
						return clazz.newInstance();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            }
			return null;
    }

    public static class ProtocolException extends Exception {

            public ProtocolException() {
            }

            public ProtocolException(String message, Throwable cause) {
                    super(message, cause);
            }

            public ProtocolException(String message) {
                    super(message);
            }

            public ProtocolException(Throwable cause) {
                    super(cause);
            }
    }
    
    public final int getPacketId() {
        if (idMap.inverse().containsKey(getClass())) {
                return idMap.inverse().get(getClass()).intValue();
        } else {
                throw new RuntimeException("Packet " + getClass().getSimpleName() + " is missing a mapping!");
        }
    }

    public final Packet makePacket() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeByte(getPacketId());
        write(out);
        return PacketDispatcher.getPacket(CHANNEL, out.toByteArray());
    }

    protected abstract void write(ByteArrayDataOutput out);
   
    protected abstract void read(ByteArrayDataInput in);
   
    protected abstract void execute(EntityPlayer player, Side side); 
}