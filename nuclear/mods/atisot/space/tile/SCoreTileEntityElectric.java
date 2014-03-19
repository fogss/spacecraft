package nuclear.mods.atisot.space.tile;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.network.PacketDispatcher;

public class SCoreTileEntityElectric extends TileEntity implements IEnergySink
{

	double energy = 0;
	double maxenergy;
	int tier;
	
	boolean init;
	
	boolean isenable;
	
	protected long ticks = 0;
	
	public SCoreTileEntityElectric(double maxenergy, int tier)
	{
		this.maxenergy = maxenergy;
		this.tier = tier;
	}
	
	@Override
    public void updateEntity() {
		
		super.updateEntity();

		if (this.ticks == 0)
		{
			this.initiate();
		}

		if (this.ticks >= Long.MAX_VALUE)
		{
			this.ticks = 1;
		}

		this.ticks++;
		
		if (!this.worldObj.isRemote)
        {
			if (!this.init)
			{
				this.initIC();
			}
			
			if (this.ticks % 3 == 0)
            {
				this.sendDescriptionPacket();
				this.getWorldObj().markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            }
			
        }
	}

	/**
	 * Called on the TileEntity's first tick.
	 */
	public void initiate()
	{
	}
	
	@Override
	public void invalidate()
	{
		this.unloadTileIC2();
		super.invalidate();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);    
		
		this.energy = nbt.getDouble("energy");
		this.isenable = nbt.getBoolean("isenable");
	}
	 
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);    
		
		nbt.setDouble("energy", this.energy);
	    nbt.setBoolean("isenable", this.isenable);
	}
	
	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
		return true;
	}

	@Override
	public double demandedEnergyUnits() {
		
		return Math.max(0, this.getMaxEnergy() - this.getEnergy());
	}

	@Override
	public double injectEnergyUnits(ForgeDirection directionFrom, double amount) {
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
		return tier;
	}
	
	public double getEnergy()
	{
		return this.energy;
	}
	
	public double getMaxEnergy()
	{
		return this.maxenergy;
	}
	
	public boolean isEnable()
	{
		return this.isenable;
	}
	
	public void setEnable(boolean enable)
	{
		this.isenable = enable;
	}
	
	protected void initIC()
	{
		if (!this.init && this.worldObj != null)
		{
			
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			
			this.init = true;
		}
	}
	
	private void unloadTileIC2()
	{
		if (this.init && this.worldObj != null)
		{
			
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			
			this.init = false;
		}
	}
	
	public int getScaledLevel(int i)
    {
		return (int) Math.floor(this.getEnergy() * i / (this.getMaxEnergy()));
    }

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound tag = new NBTTagCompound();
		
		tag.setDouble("energy", this.energy);
		tag.setBoolean("isenable", this.isenable);
		
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
