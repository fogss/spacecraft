package nuclear.mods.atisot.space;

import java.util.logging.Level;

import cpw.mods.fml.relauncher.FMLRelaunchLog;

public class SLog {

	public static void info(String message)
    {
        FMLRelaunchLog.log("Space", Level.INFO, message);
    }

    public static void severe(String message)
    {
        FMLRelaunchLog.log("Space", Level.SEVERE, message);
    }
	
}
