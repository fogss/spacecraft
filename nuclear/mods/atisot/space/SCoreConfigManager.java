package nuclear.mods.atisot.space;

import java.io.File;

import net.minecraftforge.common.Configuration;

public class SCoreConfigManager {

	public static boolean loaded;

    static Configuration configuration;
    
    // Blocks
    public static int idBlockModules;
    public static int idBlockOre;
    public static int idBlockWorkbench;
    
    // Items
	public static int idItemIngot;
    
	// Other
	public static int idGuiWorkbench;
	
	// General
	public static boolean enableCopperOreGen;
    public static boolean enableTinOreGen;
    public static boolean enableSilverOreGen;

    public static void setDefaultValues(File file)
    {
        if (!SCoreConfigManager.loaded)
        {
            SCoreConfigManager.configuration = new Configuration(file);
        }

        try
        {
            SCoreConfigManager.configuration.load();
            
            // Block
            SCoreConfigManager.idBlockModules = SCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockCoreModules", 3350).getInt(3350);
            SCoreConfigManager.idBlockOre = SCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockCoreOre", 3352).getInt(3352);
            SCoreConfigManager.idBlockWorkbench = SCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockCoreWorkbench", 3353).getInt(3353);
            
            // Other
            SCoreConfigManager.idGuiWorkbench = SCoreConfigManager.configuration.get("GUI", "idGuiCoreWorkbench", 128).getInt(128);
            
            // Item
            SCoreConfigManager.idItemIngot = SCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemCoreIngot", 3351).getInt(3351);
            
            // Other
            SCoreConfigManager.enableCopperOreGen = SCoreConfigManager.configuration.get(Configuration.CATEGORY_GENERAL, "Enable Copper Ore Gen", true, "If this is enabled, copper ore will generate on the overworld.").getBoolean(true);
            SCoreConfigManager.enableTinOreGen = SCoreConfigManager.configuration.get(Configuration.CATEGORY_GENERAL, "Enable Tin Ore Gen", true, "If this is enabled, tin ore will generate on the overworld.").getBoolean(true);
            SCoreConfigManager.enableSilverOreGen = SCoreConfigManager.configuration.get(Configuration.CATEGORY_GENERAL, "Enable Silver Ore Gen", true, "If this is enabled, tin ore will generate on the overworld.").getBoolean(true);
        }
        catch (final Exception e)
        {
            SLog.severe("Problem loading core config (\"core.conf\")");
        }
        finally
        {
            if (SCoreConfigManager.configuration.hasChanged())
            {
                SCoreConfigManager.configuration.save();
            }

            SCoreConfigManager.loaded = true;
        }
    }
	
}
