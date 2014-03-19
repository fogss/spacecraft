package nuclear.mods.atisot.space.tile;

import ic2.api.energy.tile.IEnergySink;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.io.ByteArrayDataInput;

public abstract class SCoreTileEntityElectric extends TileEntity implements IEnergySink//, IPacketReceiver
{

	double energy = 0;
	double maxenergy = 10000;
	
	int tier;
	
	protected long ticks = 0;
	
	public abstract void readPacket(ByteArrayDataInput data);

    public abstract Packet getPacket();
	
	public SCoreTileEntityElectric()
	{
		
	}
	
	@Override
    public void updateEntity() {
		
		if (!this.worldObj.isRemote)
        {
//            if (this.ticks % 3 == 0)
//            {
//                PacketManager.sendPacketToClients(this.getPacket(), this.worldObj, new Vector3(this), this.getPacketRange());
//            }
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
	}

	/**
	 * Called on the TileEntity's first tick.
	 */
	public void initiate()
	{
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
	
	public int getScaledLevel(int i)
    {
		return (int) Math.floor(this.getEnergy() * i / (this.getMaxEnergy()));
    }

	protected double getPacketRange()
    {
        return 12.0D;
    }
//	
//	@Override
//    public void handlePacketData(INetworkManager network, int type, Packet250CustomPayload packet, EntityPlayer player, ByteArrayDataInput dataStream)
//    {
//        try
//        {
//            this.readPacket(dataStream);
//        }
//        catch (final Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
}
