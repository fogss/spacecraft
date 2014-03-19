package nuclear.mods.atisot.space.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import nuclear.mods.atisot.space.SCoreConfigManager;
import nuclear.mods.atisot.space.SpaceCore;
import nuclear.mods.atisot.space.items.SCoreItemBlockModules;
import cpw.mods.fml.common.registry.GameRegistry;

public class SCoreBlocks {

	public static Block SCoreModules;
	//public static Block SCoreOre;
	public static Block SCoreWorkbench;
	
	public static ArrayList<Integer> hiddenBlocks = new ArrayList<Integer>();
	
	public static void initBlocks()
	{
		SCoreModules = new SCoreBlockModules(SCoreConfigManager.idBlockModules, "rocketModules");
		//SCoreOre = new SCoreBlockOre(SCoreConfigManager.idBlockOre, "ore");
		SCoreWorkbench = new SCoreBlockWorkbench(SCoreConfigManager.idBlockWorkbench, "workbench");
		
		hideBlocks();
		setHarvestLevels();
		registerBlocks();
		
	}
	
	public static void hideBlocks()
	{
		
	}
	
	public static void setHarvestLevels()
    {
		
    }
	
	public static void registerBlocks()
    {
		//GameRegistry.registerBlock(SCoreBlocks.SCoreOre, SCoreItemBlockOre.class, SCoreBlocks.SCoreOre.getUnlocalizedName(), SpaceCore.MODID);
		GameRegistry.registerBlock(SCoreBlocks.SCoreModules, SCoreItemBlockModules.class, SCoreBlocks.SCoreModules.getUnlocalizedName(), SpaceCore.MODID);
		GameRegistry.registerBlock(SCoreBlocks.SCoreWorkbench, ItemBlock.class, SCoreBlocks.SCoreWorkbench.getUnlocalizedName(), SpaceCore.MODID);
    }
	
	public static void registerOreDictionary()
	{
		//OreDictionary.registerOre("tinore", new ItemStack(SCoreBlocks.SCoreOre, 1, 1));
		
	}
}
