package nuclear.mods.atisot.space.items;

import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import nuclear.mods.atisot.space.client.ClientProxyCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SCoreItemBlockFurnace extends ItemBlock
{
    public SCoreItemBlockFurnace(int i)
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
        return Block.blocksList[this.getBlockID()].getUnlocalizedName() + "." + "furnaceplus";
    }

    @Override
    public String getUnlocalizedName()
    {
        return Block.blocksList[this.getBlockID()].getUnlocalizedName() + ".0";
    }
}
