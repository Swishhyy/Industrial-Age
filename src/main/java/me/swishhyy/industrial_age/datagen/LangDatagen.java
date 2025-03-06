package me.swishhyy.industrial_age.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.swishhyy.industrial_age.Industrial_age;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple data generator to create a default en_us.json language file
 * for all items registered under your mod's namespace.
 */
public class LangDatagen {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) throws IOException {
        // Where to write the generated lang file.
        // Typically, you'd place this in build/generated/... so it doesn't overwrite your source files directly.
        Path outPath = Paths.get("build/generated/resources/assets", Industrial_age.MOD_ID, "lang", "en_us.json");

        // Gather translations in a map: { "item.industrial_age.my_cool_item" : "My Cool Item" }
        Map<String, String> translations = new HashMap<>();

        // 1) Collect all items from your mod namespace
        for (Item item : Registries.ITEM) {
            Identifier id = Registries.ITEM.getId(item);
            if (id.getNamespace().equals(Industrial_age.MOD_ID)) {
                // Build the translation key: "item.industrial_age.my_cool_item"
                String translationKey = "item." + Industrial_age.MOD_ID + "." + id.getPath();
                // Convert "my_cool_item" => "My Cool Item"
                String displayName = toDisplayName(id.getPath());
                translations.put(translationKey, displayName);
            }
        }

        // 2) (Optional) Collect blocks, entities, etc. the same way if you want to auto-generate those names:
        // for (Block block : Registries.BLOCK) { ... }

        // Ensure the parent directories exist
        Files.createDirectories(outPath.getParent());

        // Convert the map to a JSON string
        String json = GSON.toJson(translations);

        // Write out the JSON
        Files.writeString(outPath, json);

        System.out.println("Generated language file at: " + outPath.toAbsolutePath());
    }

    /**
     * Converts a registry path (e.g. "my_cool_item") into a more human-friendly name ("My Cool Item").
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
}
