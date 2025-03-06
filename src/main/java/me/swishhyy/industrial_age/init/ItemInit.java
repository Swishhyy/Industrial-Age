package me.swishhyy.industrial_age.init;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.swishhyy.industrial_age.Industrial_age;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ItemInit {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * We'll track each registered item’s Identifier, so we can generate translations later.
     */
    private static final List<Identifier> REGISTERED_ITEM_IDS = new ArrayList<>();

    // Example item
    public static final Item MY_COOL_ITEM = register(
            "my_cool_item",
            Item::new,
            new Item.Settings()
    );

    /**
     * Helper method for registering an item.
     */
    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        // Construct the Identifier via tryParse
        Identifier id = Identifier.tryParse(Industrial_age.MOD_ID + ":" + name);
        if (id == null) {
            throw new IllegalArgumentException("Invalid Identifier for " + name);
        }

        // Create a RegistryKey for the Item
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, id);

        // Create the Item instance
        Item item = itemFactory.apply(settings.registryKey(itemKey));

        // Register the Item in the global registry
        Registry.register(Registries.ITEM, itemKey, item);

        // Keep track of the Identifier so we can generate default translations
        REGISTERED_ITEM_IDS.add(id);

        return item;
    }

    /**
     * Called from your main mod class (Industrial_age) during onInitialize().
     * This ensures items are statically loaded (registered).
     */
    public static void initialize() {
        // Force the static field(s) to load
        Item dummyReference = MY_COOL_ITEM;
        // If you have more items, referencing them here (or in the creative tab) ensures they're loaded.
    }

    // --------------------------------------------------------------------------------
    // DATA GENERATION SECTION
    // --------------------------------------------------------------------------------

    /**
     * Generates a basic en_us.json language file for all items registered by this class.
     * It auto-converts "my_cool_item" -> "My Cool Item" as a naive approach.
     */
    public static void generateLangFile() throws IOException {
        // 1) Build a map of translation keys -> default display names
        //    e.g. "item.industrial_age.my_cool_item" : "My Cool Item"
        Map<String, String> translations = new HashMap<>();

        for (Identifier id : REGISTERED_ITEM_IDS) {
            String translationKey = "item." + Industrial_age.MOD_ID + "." + id.getPath();
            String displayName = toDisplayName(id.getPath());
            translations.put(translationKey, displayName);
        }

        // 2) Choose where to write the generated file (e.g., build/generated/resources/...)
        Path outPath = Paths.get("build/generated/resources/assets", Industrial_age.MOD_ID, "lang", "en_us.json");
        Files.createDirectories(outPath.getParent());

        // 3) Write the JSON
        String json = GSON.toJson(translations);
        Files.writeString(outPath, json);

        System.out.println("Generated language file at: " + outPath.toAbsolutePath());
    }

    /**
     * Simple helper that converts "my_cool_item" -> "My Cool Item".
     */
    private static String toDisplayName(String path) {
        String[] words = path.split("_");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                sb.append(" ");
            }
            String word = words[i];
            sb.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1));
        }
        return sb.toString();
    }

    /**
     * If you want to run this as a standalone process (via your IDE or Gradle),
     * you can create a 'main' method that calls 'generateLangFile()'.
     */
    public static void main(String[] args) throws IOException {
        generateLangFile();
    }
}
