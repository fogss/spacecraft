package nuclear.mods.atisot.space;

import cpw.mods.fml.common.Loader;

public class SCoreCompatibilityManager
{
    private static boolean modIc2Loaded;
    
    public static void checkForCompatibleMods()
    {

        if (Loader.isModLoaded("IC2"))
        {
            SCoreCompatibilityManager.modIc2Loaded = true;
        }

    }

    public static boolean isIc2Loaded()
    {
        return SCoreCompatibilityManager.modIc2Loaded;
    }

}
