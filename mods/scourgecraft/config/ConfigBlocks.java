package mods.scourgecraft.config;

import java.io.File;
import java.io.IOException;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import mods.scourgecraft.ScourgeCraftCore;
import mods.scourgecraft.blocks.BlockFactionHQ;
import mods.scourgecraft.blocks.BlockFactionSelector;
import net.minecraft.block.Block;
import net.minecraftforge.common.Configuration;

public class ConfigBlocks {
	
	public static int factionSelectorID;
	public static Block factionSelector;
	
	public static int factionHQID;
	public static Block factionHQ;
	
	public void initConfig()
    {
        File var0 = new File(ScourgeCraftCore.proxy.getMinecraftDir() + "/config/ScourgeCraft");
        var0.mkdir();
        File var1 = new File(ScourgeCraftCore.proxy.getMinecraftDir() + "/config/ScourgeCraft/Blocks.cfg");

        try
        {
            var1.createNewFile();
        }
        catch (IOException var5)
        {
            System.out.println(var5);
        }
        Configuration config = new Configuration(var1);
            
        factionSelectorID = config.get("Blocks", "factionSelector", 500).getInt();
        factionHQID = config.get("Blocks", "factionHQ", 501).getInt();
        
        config.save();
    }
	
	public void load()
    {
		factionSelector = (new BlockFactionSelector(factionSelectorID)).setCreativeTab(ScourgeCraftCore.tabBlocks).setUnlocalizedName("factionSelector");
		factionHQ = (new BlockFactionHQ(factionHQID)).setCreativeTab(ScourgeCraftCore.tabBlocks).setUnlocalizedName("factionHQ");
		
    }
	
	public void register()
    {
        GameRegistry.registerBlock(factionSelector, ScourgeCraftCore.modid + "factionSelector");
        GameRegistry.registerBlock(factionHQ, ScourgeCraftCore.modid + "factionHQ");
    }
	
	public void languageRegister()
	{
	    LanguageRegistry.addName(factionSelector, "Faction Selector");
	    LanguageRegistry.addName(factionHQ, "Faction Head Quarters");
	}
}
