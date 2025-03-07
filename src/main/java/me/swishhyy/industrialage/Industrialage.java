package me.swishhyy.industrialage;

import me.swishhyy.industrialage.init.ItemInit;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Industrialage implements ModInitializer {
    public static final String MOD_ID = "industrialage";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
        //registers Items using ItemInit Class
        ItemInit.RegisterItems();
    }
}
