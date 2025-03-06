package me.swishhyy.industrial_age.init;

import me.swishhyy.industrial_age.Industrial_age;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CreativeTabInit {
    // 1) Create an Identifier safely via tryParse
    private static final Identifier TAB_ID;
    static {
        TAB_ID = Identifier.tryParse(Industrial_age.MOD_ID + ":industrial_age_group");
        if (TAB_ID == null) {
            throw new IllegalArgumentException("Failed to create Identifier for custom creative tab.");
        }
    }

    // 2) Create a RegistryKey<ItemGroup> using that Identifier
    public static final RegistryKey<ItemGroup> INDUSTRIAL_AGE_GROUP_KEY = RegistryKey.of(
            RegistryKeys.ITEM_GROUP,
            TAB_ID
    );

    // 3) Register the custom creative tab in the global registry
    public static final ItemGroup INDUSTRIAL_AGE_GROUP = Registry.register(
            Registries.ITEM_GROUP,
            INDUSTRIAL_AGE_GROUP_KEY,
            FabricItemGroup.builder()
                    // Icon for the tab
                    .icon(() -> new ItemStack(ItemInit.MY_COOL_ITEM))
                    // The translation key used in lang files
                    .displayName(Text.translatable("itemGroup.industrial_age.industrial_age_group"))
                    // Add items to the tab
                    .entries((enabledFeatures, entries) -> {
                        entries.add(ItemInit.MY_COOL_ITEM);
                        // Add any other items you want here
                    })
                    .build()
    );
}
