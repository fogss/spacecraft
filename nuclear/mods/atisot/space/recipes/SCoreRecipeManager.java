package nuclear.mods.atisot.space.recipes;

import nuclear.mods.atisot.space.SCoreCompatibilityManager;

public class SCoreRecipeManager {

	public static void loadRecipes()
    {
        if (SCoreCompatibilityManager.isIc2Loaded())
        {
            SCoreRecipeManager.addIndustrialCraft2Recipes();
        }

    }

	private static void addIndustrialCraft2Recipes() {
		
		
		
	}
	
}
