package mods.scourgecraft;

import java.util.Map;

import mods.scourgecraft.client.gui.GuiHandler;
import mods.scourgecraft.config.ConfigBlocks;
import mods.scourgecraft.config.ConfigFactions;
import mods.scourgecraft.creative.CreativeTabBlock;
import mods.scourgecraft.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.src.ModLoader;
import net.minecraft.world.storage.SaveHandler;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(
        modid = ScourgeCraftCore.modid,
        name = "ScourgeCraft",
        version = "0.1.0"
)
@NetworkMod(
        channels = {"scourgecraft"},
        clientSideRequired = true,
        serverSideRequired = false,
        packetHandler = PacketHandler.class,
        connectionHandler = SGConnectionHandler.class
)
public class ScourgeCraftCore
{
    @Mod.Instance("ScourgeCraft")
    public static ScourgeCraftCore instance;
    public static final String modid = "ScourgeCraft";
    
    @SidedProxy(clientSide = "mods.scourgecraft.ClientProxy", serverSide = "mods.scourgecraft.CommonProxy")
    public static CommonProxy proxy;
    
    public static CreativeTabBlock tabBlocks;
    
    public static ConfigBlocks configBlocks;
    public static ConfigFactions configFactions;
    
    public FactionController factionController;
    
    public static PlayerEventListener playerEventListener;
    public static PermissionEventListener permissionEventListener;

    public static PlayerTracker playerTracker;

    @EventHandler
    public void loadConfigurationValues(FMLPreInitializationEvent event) {
    	tabBlocks = new CreativeTabBlock("SG : Blocks");
    	
    	configBlocks = new ConfigBlocks();
    	configFactions = new ConfigFactions();
    	factionController = new FactionController();
    	playerEventListener = new PlayerEventListener();
    	permissionEventListener = new PermissionEventListener();
    	playerTracker = new PlayerTracker();
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) 
    {
    }
    
    @EventHandler
    public void load(FMLInitializationEvent var1)
    {
        configBlocks.initConfig();
        configFactions.initConfig();
        
    	MinecraftForge.EVENT_BUS.register(playerEventListener);
    	MinecraftForge.EVENT_BUS.register(permissionEventListener);
    	 
    	new GuiHandler();
    	
    	configBlocks.load();
    	
    	configBlocks.register();
    	
    	configBlocks.languageRegister();
    	
    	LanguageRegistry.instance().addStringLocalization("itemGroup.ScourgeCraft : Blocks", "ScourgeCraft: Blocks");
    	
    	proxy.registerHandlers();

    	GameRegistry.registerPlayerTracker(playerTracker);
    }
    
}