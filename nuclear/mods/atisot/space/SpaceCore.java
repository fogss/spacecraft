package nuclear.mods.atisot.space;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import nuclear.mods.atisot.space.blocks.SCoreBlocks;
import nuclear.mods.atisot.space.generator.SCoreOverworldGenerator;
import nuclear.mods.atisot.space.items.SCoreItems;
import nuclear.mods.atisot.space.tile.SCoreEntityTileWorkbench;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(name = SpaceCore.NAME, version = SpaceCore.LOCALMAJVERSION + "." + SpaceCore.LOCALMINVERSION + "." + SpaceCore.LOCALBUILDVERSION, useMetadata = true, modid = SpaceCore.MODID, dependencies = "required-after:Forge@[9.10.0.837,); after:ICBM|Explosion; after:IC2; after:BuildCraft|Core; after:BuildCraft|Energy; after:IC2")
@NetworkMod(channels = { SpaceCore.CHANNEL }, clientSideRequired = true, serverSideRequired = false)
public class SpaceCore {

	public static final String NAME = "Space Core";
    public static final String MODID = "SpaceCore";
    public static final String CHANNEL = "SpaceCore";
    public static final String CHANNELENTITIES = "SCoreEntity";

    public static final int LOCALMAJVERSION = 0;
    public static final int LOCALMINVERSION = 1;
    public static final int LOCALBUILDVERSION = 0;
    public static int remoteMajVer;
    public static int remoteMinVer;
    public static int remoteBuildVer;
	
	@SidedProxy(clientSide = "nuclear.mods.atisot.space.client.ClientProxyCore", serverSide = "nuclear.mods.atisot.space.CommonProxyCore")
    public static CommonProxyCore proxy;

    @Instance(SpaceCore.MODID)
    public static SpaceCore instance;
	
    public static String ASSET_DOMAIN = "spacecore";
    public static String ASSET_PREFIX = SpaceCore.ASSET_DOMAIN + ":";
    
    public static CreativeTabs spaceTab;
    
    public static final String CONFIG_FILE = "Space/core.conf";
    
	@EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
		SCoreConfigManager.setDefaultValues(new File(event.getModConfigurationDirectory(), SpaceCore.CONFIG_FILE));
		
		SCoreBlocks.initBlocks();
		SCoreItems.initItems();
		
		SCoreBlocks.registerOreDictionary();
		SCoreItems.registerOreDictionary();
		
		registeryTileEntity();
		
		if(SCoreConfigManager.enableCopperOreGen)
		{
			GameRegistry.registerWorldGenerator(new SCoreOverworldGenerator(SCoreBlocks.SCoreOre, 0, 24, 20, 75, 7));
		}
		
		if (SCoreConfigManager.enableTinOreGen)
        {
            GameRegistry.registerWorldGenerator(new SCoreOverworldGenerator(SCoreBlocks.SCoreOre, 1, 22, 20, 60, 7));
        }
		
		if (SCoreConfigManager.enableSilverOreGen)
        {
            GameRegistry.registerWorldGenerator(new SCoreOverworldGenerator(SCoreBlocks.SCoreOre, 2, 4, 0, 40, 4));
        }
		
		
    }
	
	@EventHandler
    public void Init(FMLInitializationEvent event)
    {
		SpaceCore.spaceTab = new SCoreCreativeTab(CreativeTabs.getNextID(), SpaceCore.CHANNEL, SCoreBlocks.SCoreModules.blockID, 0);
    }
	
	@EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
		NetworkRegistry.instance().registerGuiHandler(this, SpaceCore.proxy);
    }
	
	public void registeryTileEntity()
	{
		GameRegistry.registerTileEntity(SCoreEntityTileWorkbench.class, "SpaceWorkbench");
	}
	
}
