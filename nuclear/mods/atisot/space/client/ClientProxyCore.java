package nuclear.mods.atisot.space.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import nuclear.mods.atisot.space.CommonProxyCore;
import nuclear.mods.atisot.space.SCoreConfigManager;
import nuclear.mods.atisot.space.SLog;
import nuclear.mods.atisot.space.client.gui.SCoreWorkbenchGui;
import nuclear.mods.atisot.space.inventory.SCoreWorkbenchContainer;
import nuclear.mods.atisot.space.tile.SCoreEntityTileWorkbench;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxyCore extends CommonProxyCore {

	@Override
	public void preInit(FMLPreInitializationEvent event)
    {
        ;
    }

	@Override
    public void init(FMLInitializationEvent event)
    {
        ;
    }

	@Override
    public void postInit(FMLPostInitializationEvent event)
    {
        ;
    }

	@Override
    public void registerRenderInformation()
    {
        ;
    }
	
	@Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		if (ID == SCoreConfigManager.idGuiWorkbench)
        {
			if (tile != null && tile instanceof SCoreEntityTileWorkbench)
			{
				SCoreEntityTileWorkbench tiles = (SCoreEntityTileWorkbench) world.getBlockTileEntity(x, y, z);
				
				return new SCoreWorkbenchGui(player.inventory, (SCoreEntityTileWorkbench) tiles);
			}
			else
			{
				return null;
			}
		}
		
		return null;
    }
}
