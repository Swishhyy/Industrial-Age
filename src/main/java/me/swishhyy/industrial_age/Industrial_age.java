package me.swishhyy.industrial_age;

import net.fabricmc.api.ModInitializer;
import me.swishhyy.industrial_age.init.ItemInit;
import me.swishhyy.industrial_age.init.CreativeTabInit;


public class Industrial_age implements ModInitializer {
    public static final String MOD_ID = "industrial_age";

    @Override
    public void onInitialize() {
        // 1) Register items
        ItemInit.initialize();

        // 2) Reference items so they're actually loaded
        //    (assigning them to a variable removes the "ignored result" warning)
        var myCoolItem = ItemInit.MY_COOL_ITEM;

        // 3) Reference the custom creative tab so it registers
        var myCreativeTab = CreativeTabInit.INDUSTRIAL_AGE_GROUP;
    }
}
