package nuclear.mods.atisot.space.blocks;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import nuclear.mods.atisot.space.SCoreConfigManager;
import nuclear.mods.atisot.space.SpaceCore;
import nuclear.mods.atisot.space.tile.SCoreEntityTileWorkbench;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SCoreBlockWorkbench extends BlockContainer {

	private Icon iconMachineSide;
    private Icon iconWorkbench;
    private Icon iconInput;
	
	public SCoreBlockWorkbench(int par1, String assetName) {
		super(par1, Material.rock);
		
		this.setUnlocalizedName(assetName);
        this.setTextureName(SpaceCore.ASSET_PREFIX + assetName);
        this.setHardness(2.0F);
	}

	@Override
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return SpaceCore.spaceTab;
    }
		
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.iconMachineSide = par1IconRegister.registerIcon(SpaceCore.ASSET_PREFIX + "workbench_0");
        this.iconWorkbench = par1IconRegister.registerIcon(SpaceCore.ASSET_PREFIX + "workbench_1");
        this.iconInput = par1IconRegister.registerIcon(SpaceCore.ASSET_PREFIX + "workbench_3");
    }
	
	@Override
	public Icon getIcon(int side, int metadata)
	{
	    if (side == 0 || side == 1)
	    {
	        return this.iconMachineSide;
	    }
	    else if (side == ForgeDirection.getOrientation(metadata + 2).ordinal())
	    {
	        return this.iconInput;
	    }
	    else
	    {
	        return this.iconWorkbench;
	    }
	}
	
	@Override
	public TileEntity createTileEntity(World world, int meta) {
		return new SCoreEntityTileWorkbench();
	}
	
	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack)
    {
        final int angle = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        int change = 0;

        switch (angle)
        {
        case 0:
            change = 3;
            break;
        case 1:
            change = 1;
            break;
        case 2:
            change = 2;
            break;
        case 3:
            change = 0;
            break;
        }

        world.setBlockMetadataWithNotify(x, y, z, change, 3);
    }
	
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
		if(par5EntityPlayer.isSneaking())
		{
			return false;
		}
		
		par5EntityPlayer.openGui(SpaceCore.instance, SCoreConfigManager.idGuiWorkbench, par1World, par2, par3, par4);
		
        return true;
    }

	@Override
    public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
            dropItems(world, x, y, z);world.markBlockForUpdate(x, y, z);
            super.breakBlock(world, x, y, z, par5, par6);
    }

    private void dropItems(World world, int x, int y, int z){
            Random rand = new Random();

            TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
            if (!(tileEntity instanceof IInventory)) {
                    return;
            }
            IInventory inventory = (IInventory) tileEntity;

            for (int i = 0; i < inventory.getSizeInventory(); i++) {
                    ItemStack item = inventory.getStackInSlot(i);

                    if (item != null && item.stackSize > 0) {
                            float rx = rand.nextFloat() * 0.8F + 0.1F;
                            float ry = rand.nextFloat() * 0.8F + 0.1F;
                            float rz = rand.nextFloat() * 0.8F + 0.1F;

                            EntityItem entityItem = new EntityItem(world,
                                            x + rx, y + ry, z + rz,
                                            new ItemStack(item.itemID, item.stackSize, item.getItemDamage()));

                            if (item.hasTagCompound()) {
                                    entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
                            }

                            float factor = 0.05F;
                            entityItem.motionX = rand.nextGaussian() * factor;
                            entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                            entityItem.motionZ = rand.nextGaussian() * factor;
                            world.spawnEntityInWorld(entityItem);
                            item.stackSize = 0;
                    }
            }
    }
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new SCoreEntityTileWorkbench();
	}

}
