package mods.scourgecraft.config;

import java.io.File;
import java.io.IOException;

import mods.scourgecraft.ScourgeCraftCore;
import mods.scourgecraft.permission.SGRank;
import net.minecraftforge.common.Configuration;

public class ConfigJobs {

	public void initConfig()
    {
        File var0 = new File(ScourgeCraftCore.proxy.getMinecraftDir() + "/config/ScourgeCraft");
        var0.mkdir();
        File var1 = new File(ScourgeCraftCore.proxy.getMinecraftDir() + "/config/ScourgeCraft/Jobs.cfg");

        try
        {
            var1.createNewFile();
        }
        catch (IOException var5)
        {
            System.out.println(var5);
        }
        Configuration config = new Configuration(var1);
        
        ScourgeCraftCore.instance.ranks.add(new SGRank("Builder", 1, config.get("Builder", "Level 1 Cost", 20).getInt()));
        ScourgeCraftCore.instance.ranks.add(new SGRank("Builder", 2, config.get("Builder", "Level 2 Cost", 200).getInt()));
        ScourgeCraftCore.instance.ranks.add(new SGRank("Builder", 3, config.get("Builder", "Level 3 Cost", 2000).getInt()));
        
        ScourgeCraftCore.instance.ranks.add(new SGRank("Warrior", 1, config.get("Warrior", "Level 1 Cost", 20).getInt()));
        ScourgeCraftCore.instance.ranks.add(new SGRank("Warrior", 2, config.get("Warrior", "Level 2 Cost", 200).getInt()));
        ScourgeCraftCore.instance.ranks.add(new SGRank("Warrior", 3, config.get("Warrior", "Level 3 Cost", 2000).getInt()));
        
        ScourgeCraftCore.instance.ranks.add(new SGRank("Enchanter", 1, config.get("Enchanter", "Level 1 Cost", 20).getInt()));
        ScourgeCraftCore.instance.ranks.add(new SGRank("Enchanter", 2, config.get("Enchanter", "Level 2 Cost", 200).getInt()));
        ScourgeCraftCore.instance.ranks.add(new SGRank("Enchanter", 3, config.get("Enchanter", "Level 3 Cost", 2000).getInt()));
        
        config.save();
    }
	
}
