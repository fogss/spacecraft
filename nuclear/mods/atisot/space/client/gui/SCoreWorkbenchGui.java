package nuclear.mods.atisot.space.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import nuclear.mods.atisot.space.SLog;
import nuclear.mods.atisot.space.SpaceCore;
import nuclear.mods.atisot.space.inventory.SCoreWorkbenchContainer;
import nuclear.mods.atisot.space.tile.SCoreEntityTileWorkbench;

import org.lwjgl.opengl.GL11;

public class SCoreWorkbenchGui extends SCoreGuiContainer {

	private static final ResourceLocation distributorTexture = new ResourceLocation(SpaceCore.ASSET_DOMAIN, "textures/gui/spaceworkbench.png");
	
	private SCoreInfoRegion electricInfoRegion = new SCoreInfoRegion((this.width - this.xSize) / 2 + 4, (this.height - this.ySize) / 2 + 55, 4, 60, new ArrayList<String>(), this.width, this.height);
	
	SCoreEntityTileWorkbench workbench;
	
	GuiButton b;
	
	String button;
	
    public SCoreWorkbenchGui(InventoryPlayer inventoryPlayer, SCoreEntityTileWorkbench tileEntity){
    	super(new SCoreWorkbenchContainer(inventoryPlayer, tileEntity));
    	this.workbench = tileEntity;
    	this.ySize = 160;
	}
    
    @Override
    public void initGui()
    {
        super.initGui();
        
        this.buttonList.add(this.b = new GuiButton(1, this.width / 2 - 30, this.height / 2 - 23, 60, 20, "Запустить"));
    }
    
    @Override
    protected void actionPerformed(GuiButton guibutton) {

        switch(guibutton.id)
        {
	        case 1:
	        	this.workbench.prepareterac = true;
	            break;
        }

    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {
            //draw text and stuff here
            //the parameters for drawString are: string, x, y, color
            fontRenderer.drawString("Space Workbench", 30, 10, 4210752);
            //draws "Inventory" or your regional equivalent
            //fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 30, ySize - 90 + 2, 4210752);
            
            this.button = this.workbench.button;

            List<String> electricityDesc = new ArrayList<String>();
            electricityDesc.add(StatCollector.translateToLocal("container.inventory"));
            electricityDesc.add("Energy: " + ((int) Math.floor(this.workbench.getEnergy()) + " / " + (int) Math.floor(this.workbench.getMaxEnergy())));
            this.electricInfoRegion.tooltipStrings = electricityDesc;
            this.electricInfoRegion.xPosition = (this.width - this.xSize) / 2 + 13;
            this.electricInfoRegion.yPosition = (this.height - this.ySize) / 2 + 16;
            this.electricInfoRegion.parentWidth = this.width;
            this.electricInfoRegion.parentHeight = this.height;
            this.infoRegions.add(this.electricInfoRegion);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {	
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(distributorTexture);
        final int var5 = (this.width - this.xSize) / 2;
        final int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6 + 5, 0, 0, this.xSize, 181);
        
        if (this.workbench != null)
        {
        	
        	
        	int scale = this.workbench.getScaledLevel(55);
        	
//        	SLog.info("" + scale);
//        	SLog.info("" + this.workbench.getEnergy());
        	
            this.drawTexturedModalRect(var5 + 12, var6 + 20, 176, 0, 4, scale);
            
            //this.drawTexturedModalRect(var5 + 12, var6 + 20, 176, 55, 4, 20);
            
//            if (this.workbench.getEnergy() > 0)
//            {
//            	this.drawTexturedModalRect(var5 + 12, var6 + 20, 176, 0, 4, 55);
//            }
//            
            
        }
    } 

}
