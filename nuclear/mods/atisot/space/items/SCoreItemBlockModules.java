package nuclear.mods.atisot.space.items;

import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import nuclear.mods.atisot.space.client.ClientProxyCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SCoreItemBlockModules extends ItemBlock
{
    public SCoreItemBlockModules(int i)
    {
        super(i);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int meta)
    {
        return meta;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        String name = "";

        switch (itemstack.getItemDamage())
        {
        case 0:
        {
            name = "launchpadstand";
            break;
        }
        case 1:
        {
            name = "launchpadfooter";
            break;
        }
        default:
            name = "null";
        }

        return Block.blocksList[this.getBlockID()].getUnlocalizedName() + "." + name;
    }

    @Override
    public String getUnlocalizedName()
    {
        return Block.blocksList[this.getBlockID()].getUnlocalizedName() + ".0";
    }
}
