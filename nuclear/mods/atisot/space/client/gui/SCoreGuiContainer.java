package nuclear.mods.atisot.space.client.gui;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

public abstract class SCoreGuiContainer extends GuiContainer
{
    public List<SCoreInfoRegion> infoRegions = new ArrayList<SCoreInfoRegion>();

    public SCoreGuiContainer(Container container)
    {
        super(container);
    }

    @Override
    public void drawScreen(int par1, int par2, float par3)
    {
        super.drawScreen(par1, par2, par3);

        for (int k = 0; k < this.infoRegions.size(); ++k)
        {
            SCoreInfoRegion guibutton = this.infoRegions.get(k);
            guibutton.drawRegion(par1, par2);
        }
    }

    @Override
    public void setWorldAndResolution(Minecraft par1Minecraft, int par2, int par3)
    {
        this.infoRegions.clear();
        super.setWorldAndResolution(par1Minecraft, par2, par3);
    }
}
