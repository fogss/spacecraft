package nuclear.mods.atisot.space;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import nuclear.mods.atisot.space.client.gui.SCoreWorkbenchGui;
import nuclear.mods.atisot.space.inventory.SCoreWorkbenchContainer;
import nuclear.mods.atisot.space.tile.SCoreEntityTileWorkbench;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxyCore implements IGuiHandler {

	public void preInit(FMLPreInitializationEvent event)
    {
        ;
    }

    public void init(FMLInitializationEvent event)
    {
        ;
    }

    public void postInit(FMLPostInitializationEvent event)
    {
        ;
    }

    public void registerRenderInformation()
    {
        ;
    }

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if (ID == SCoreConfigManager.idGuiWorkbench)
        {
			if(tileEntity instanceof SCoreEntityTileWorkbench)
			{
				return new SCoreWorkbenchContainer(player.inventory, (SCoreEntityTileWorkbench) tileEntity);
			}
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		return null;
	}
	
}
