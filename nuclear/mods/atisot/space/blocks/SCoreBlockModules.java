package nuclear.mods.atisot.space.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import nuclear.mods.atisot.space.SpaceCore;

public class SCoreBlockModules extends Block {

	Icon[] iconBuffer;
	
	public SCoreBlockModules(int par1, String assetName)
	{
		super(par1, Material.rock);
		
		this.setHardness(1.0F);
        this.setTextureName(SpaceCore.ASSET_PREFIX + assetName);
        this.setUnlocalizedName(assetName);
	}

	@Override
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return SpaceCore.spaceTab;
    }
	
	@Override
    public void registerIcons(IconRegister iconRegister)
    {
        this.iconBuffer = new Icon[2];
        this.iconBuffer[0] = iconRegister.registerIcon(SpaceCore.ASSET_PREFIX + "launchpadstand");
        this.iconBuffer[1] = iconRegister.registerIcon(SpaceCore.ASSET_PREFIX + "launchpadfooter");
    }
	
	@Override
    public Icon getIcon(int side, int meta)
    {
        return iconBuffer[meta];
    }
		

    @Override
    public int damageDropped(int meta)
    {
    	return meta;
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < 2; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }
}
