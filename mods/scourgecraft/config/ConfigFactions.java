package mods.scourgecraft.config;

import java.io.File;
import java.io.IOException;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import mods.scourgecraft.ScourgeCraftCore;
import mods.scourgecraft.blocks.BlockFactionSelector;
import mods.scourgecraft.permission.SGRank;
import net.minecraft.block.Block;
import net.minecraftforge.common.Configuration;

public class ConfigFactions {
	
	public static int faction1_spawn_x;
	public static int faction1_spawn_y;
	public static int faction1_spawn_z;
	public static int faction2_spawn_x;
	public static int faction2_spawn_y;
	public static int faction2_spawn_z;
	public static int faction3_spawn_x;
	public static int faction3_spawn_y;
	public static int faction3_spawn_z;
	public static int faction4_spawn_x;
	public static int faction4_spawn_y;
	public static int faction4_spawn_z;
	
	public void initConfig()
    {
        File var0 = new File(ScourgeCraftCore.proxy.getMinecraftDir() + "/config/ScourgeCraft");
        var0.mkdir();
        File var1 = new File(ScourgeCraftCore.proxy.getMinecraftDir() + "/config/ScourgeCraft/Factions.cfg");

        try
        {
            var1.createNewFile();
        }
        catch (IOException var5)
        {
            System.out.println(var5);
        }
        Configuration config = new Configuration(var1);
            
        faction1_spawn_x = config.get("Faction 1", "Spawn X", 0).getInt();
        faction1_spawn_y = config.get("Faction 1", "Spawn Y", 0).getInt();
        faction1_spawn_z = config.get("Faction 1", "Spawn Z", 0).getInt();
        faction2_spawn_x = config.get("Faction 2", "Spawn X", 50).getInt();
        faction2_spawn_y = config.get("Faction 2", "Spawn Y", 0).getInt();
        faction2_spawn_z = config.get("Faction 2", "Spawn Z", 0).getInt();
        faction3_spawn_x = config.get("Faction 3", "Spawn X", 50).getInt();
        faction3_spawn_y = config.get("Faction 3", "Spawn Y", 50).getInt();
        faction3_spawn_z = config.get("Faction 3", "Spawn Z", 0).getInt();
        faction4_spawn_x = config.get("Faction 4", "Spawn X", 0).getInt();
        faction4_spawn_y = config.get("Faction 4", "Spawn Y", 50).getInt();
        faction4_spawn_z = config.get("Faction 4", "Spawn Z", 0).getInt();
        
        config.save();
    }
}
