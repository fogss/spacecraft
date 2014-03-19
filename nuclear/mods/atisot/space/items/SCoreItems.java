package nuclear.mods.atisot.space.items;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import nuclear.mods.atisot.space.SCoreConfigManager;

public class SCoreItems {

	public static Item SCCoreItemIngot;
	
	public static ArrayList<Integer> hiddenItems = new ArrayList<Integer>();
	
	public static void initItems()
	{
		SCCoreItemIngot = new SCoreItemIngot(SCoreConfigManager.idItemIngot, "itemingot");
		
		hideItems();
		setHarvestLevels();
	}
	
	public static void hideItems()
	{
		
	}
	
	public static void setHarvestLevels()
    {
		
    }

	public static void registerOreDictionary()
	{
		OreDictionary.registerOre("ingotTin", new ItemStack(SCoreItems.SCCoreItemIngot, 1, 1));

		OreDictionary.registerOre("ingotCopper", new ItemStack(SCoreItems.SCCoreItemIngot, 1, 0));

		OreDictionary.registerOre("ingotSilver", new ItemStack(SCoreItems.SCCoreItemIngot, 1, 2));

		OreDictionary.registerOre("ingotSteel", new ItemStack(SCoreItems.SCCoreItemIngot, 1, 3));
	}
	
}
