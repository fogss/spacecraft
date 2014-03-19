package nuclear.mods.atisot.space.tile;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import nuclear.mods.atisot.space.SLog;
import cpw.mods.fml.common.network.PacketDispatcher;

public class SCoreEntityTileWorkbench extends TileEntity implements IEnergySink, IInventory {
	
	
	double energy = 0;
	double maxenergy = 50000;
	boolean init;
	
	boolean work = false;
	public boolean preparetera = false;
	public boolean prepareterac = false;
	
	private ItemStack[] inv = new ItemStack[2];
	
	protected long ticks = 0;

	String status;
	
	public String button;
	
	@Override
	public void updateEntity() {
		 
		super.updateEntity();
		
		if (!this.init && this.worldObj != null) {
	    	
			if(!this.worldObj.isRemote)
			{
				EnergyTileLoadEvent e = new EnergyTileLoadEvent(this);
				MinecraftForge.EVENT_BUS.post(e);
				
				this.init = true;
			}
	    	
		}

		if(this.prepareterac && !this.preparetera && this.getEnergy() >= 25000)
		{
			this.checkpreparearea();
		}
		
		if (this.ticks == 0)
		{
			this.initiate();
		}
	
		if (this.ticks >= Long.MAX_VALUE)
		{
			this.ticks = 1;
		}
	
		this.ticks++;
		
		if (this.ticks % 3 == 0)
		{
			this.sendDescriptionPacket();
			this.getWorldObj().markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
	}
	
	private void checkpreparearea()
	{
		World world = this.worldObj;
		
		for(int x = 0; x < 1; x++)
		{
			for(int y = 0; y < 1; y++)
			{				
				for(int z = 0; z < 1; z++)
				{
					world.setBlock(this.xCoord, this.yCoord + y, this.zCoord, 57);
					
					world.setBlock(this.xCoord, this.yCoord + y, this.zCoord + z, 57);
					world.setBlock(this.xCoord, this.yCoord + y, this.zCoord - z, 57);
					
					world.setBlock(this.xCoord + x, this.yCoord + y, this.zCoord, 57);
					world.setBlock(this.xCoord - x, this.yCoord + y, this.zCoord, 57);
					
					world.setBlock(this.xCoord + x, this.yCoord + y, this.zCoord + y, 57);
					world.setBlock(this.xCoord + x, this.yCoord + y, this.zCoord - y, 57);
					
					world.setBlock(this.xCoord - x, this.yCoord + y, this.zCoord + y, 57);
					world.setBlock(this.xCoord + x, this.yCoord + y, this.zCoord - y, 57);
				}
			}
		}
		
		this.preparetera = true;
	}

	public void initiate(){}
	 
	 @Override
	 public void invalidate() {
		 

	        	EnergyTileUnloadEvent e = new EnergyTileUnloadEvent(this);
	        	
	            MinecraftForge.EVENT_BUS.post(e);
	 
	      
	 }
	 @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);    
 

            this.energy = nbt.getDouble("energy");
            
            final NBTTagList var2 = nbt.getTagList("Items");
            this.inv = new ItemStack[this.getSizeInventory()];
        
            for (int var3 = 0; var3 < var2.tagCount(); ++var3)
            {
                final NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
                final byte var5 = var4.getByte("Slot");

                if (var5 >= 0 && var5 < this.inv.length)
                {
                    this.inv[var5] = ItemStack.loadItemStackFromNBT(var4);
                }
            }
    }
	 @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);    
 
        nbt.setDouble("energy", this.energy);
        
        final NBTTagList list = new NBTTagList();

        for (int var3 = 0; var3 < this.inv.length; ++var3)
        {
            if (this.inv[var3] != null)
            {
                final NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte) var3);
                this.inv[var3].writeToNBT(var4);
                list.appendTag(var4);
            }
        }

        nbt.setTag("Items", list);
    }

	// IEnergySink
	 
	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
		
		if(direction == ForgeDirection.getOrientation(this.getBlockMetadata() + 2))
		{
			return true;
		}
		
		return false;
	}

	@Override
	public double demandedEnergyUnits() {
		return Math.max(0, this.getMaxEnergy() - this.getEnergy());
	}

	@Override
	public double injectEnergyUnits(ForgeDirection directionFrom, double amount)
	{
		if(amount > getMaxSafeInput())
		{
			this.getWorldObj().destroyBlock(this.xCoord, this.yCoord, this.zCoord, false);
			this.getWorldObj().createExplosion(new EntityTNTPrimed(this.getWorldObj()), this.xCoord, this.yCoord, this.zCoord, 2, true);
		}
		
		if(this.getEnergy() >= this.getMaxEnergy()) return amount;
		
		double openenergy = this.getMaxEnergy() - this.getEnergy();
		
		if(openenergy >= amount)
		{
			return this.energy += amount;
		}
		else if(amount >= openenergy)
		{
			this.energy = this.maxenergy;
			return amount - openenergy;
		}
		
		return 0;
	}

	@Override
	public int getMaxSafeInput() {
		return EnergyNet.instance.getPowerFromTier(2);
	}

	// IInventory
	
	@Override
    public int getSizeInventory()
    {
        return this.inv.length;
    }

    @Override
    public ItemStack getStackInSlot(int par1)
    {
        return this.inv[par1];
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.inv[par1] != null)
        {
            ItemStack var3;

            if (this.inv[par1].stackSize <= par2)
            {
                var3 = this.inv[par1];
                this.inv[par1] = null;
                return var3;
            }
            else
            {
                var3 = this.inv[par1].splitStack(par2);

                if (this.inv[par1].stackSize == 0)
                {
                    this.inv[par1] = null;
                }

                return var3;
            }
        }
        else
        {
            return null;
        }
    }
	@Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		this.inv[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }            
    }

	@Override
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (this.inv[par1] != null)
        {
            final ItemStack var2 = this.inv[par1];
            this.inv[par1] = null;
            return var2;
        }
        else
        {
            return null;
        }
    }
    
    @Override
    public int getInventoryStackLimit() {
            return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
            return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this &&
            player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
    }

    @Override
    public void openChest() {}

    @Override
    public void closeChest() {}

    @Override
    public String getInvName()
    {
            return "Space workbench";
    }

	@Override
	public boolean isInvNameLocalized()
	{
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return false;
	}
	
	// Other
	
	public int getScaledLevel(int i)
    {
		return (int) Math.floor(this.getEnergy() * i / (this.getMaxEnergy()));
    }
	
	public double getEnergy()
	{
		return this.energy;
	}
	
	public double getMaxEnergy()
	{
		return this.maxenergy;
	}
	
	public String getStatus()
	{
		return "";
	}
	
	public void setStatus(String i)
	{
		this.status = i;
	}
	
	// Packet
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound tag = new NBTTagCompound();
		
		tag.setDouble("energy", this.energy);
		
        writeToNBT(tag);
        
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, tag);
	}
		
	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt){

		readFromNBT(pkt.data);
		
	}

	public void sendDescriptionPacket() {

		PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 64D, worldObj.provider.dimensionId, getDescriptionPacket());
	}
	
	


	
}
