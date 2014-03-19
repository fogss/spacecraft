package nuclear.mods.atisot.space.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import nuclear.mods.atisot.space.SpaceCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SCoreItemBasic extends Item {

	public static final String[] names = {
		"Uranium225",
		"Uranium228"
	};
	
	protected Icon[] icons = new Icon[SCoreItemBasic.names.length];
	
	public SCoreItemBasic(int par1, String assetName) {
		super(par1);
		this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setUnlocalizedName(assetName);
        this.setTextureName(SpaceCore.ASSET_PREFIX + assetName);
	}

	@Override
    public CreativeTabs getCreativeTab()
    {
        return SpaceCore.spaceTab;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
        int i = 0;

        for (final String name : SCoreItemBasic.names)
        {
            this.icons[i++] = iconRegister.registerIcon(this.getIconString() + "." + name);
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return this.getUnlocalizedName() + "." + SCoreItemBasic.names[itemStack.getItemDamage()];
    }
    
    @Override
    public Icon getIconFromDamage(int damage)
    {
        if (this.icons.length > damage)
        {
            return this.icons[damage];
        }

        return super.getIconFromDamage(damage);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < SCoreItemBasic.names.length; i++)
        {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }

    @Override
    public int getMetadata(int par1)
    {
        return par1;
    }
	
}
