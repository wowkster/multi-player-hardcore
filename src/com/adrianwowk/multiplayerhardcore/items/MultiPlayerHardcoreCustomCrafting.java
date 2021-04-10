package com.adrianwowk.multiplayerhardcore.items;

import com.adrianwowk.multiplayerhardcore.MultiPlayerHardcore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class MultiPlayerHardcoreCustomCrafting {
    // Name Tag
    public static ItemStack nameTag;
    public static ItemMeta nameTagLabel;
    public static ShapedRecipe nameTagRecipe;
    //Saddle
    public static ItemStack saddle;
    public static ItemMeta saddleLabel;
    public static ShapedRecipe saddleRecipe;
    // Notch Apple
    public static ItemStack notchApple;
    public static ItemMeta notchAppleLabel;
    public static ShapedRecipe notchAppleRecipe;


    public static void initNameTag() {
        nameTag = new ItemStack(Material.NAME_TAG);
        nameTagLabel = nameTag.getItemMeta();
        assert nameTagLabel != null;
        nameTagLabel.setDisplayName(ChatColor.GOLD + "Name Tag");
        //nameTagLabel.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        //List<String> lore = new ArrayList<>();
        //lore.add("Can Break Bedrock");
        //nameTagLabel.setLore(lore);
        nameTag.setItemMeta(nameTagLabel);

        NamespacedKey nameTagKey = NamespacedKey.minecraft("name_tag");//new (MultiPlayerHardcore, "name_tag");
        nameTagRecipe = new ShapedRecipe(nameTagKey, nameTag);

        nameTagRecipe.shape(
                " II",
                " II",
                "S  ");
        nameTagRecipe.setIngredient('I', Material.IRON_INGOT);
        nameTagRecipe.setIngredient('S', Material.STRING);
        Bukkit.addRecipe(nameTagRecipe);
    }

    public static void initSaddle() {
        saddle = new ItemStack(Material.SADDLE);
        saddleLabel = saddle.getItemMeta();
        assert saddleLabel != null;
        saddleLabel.setDisplayName(ChatColor.GOLD + "Saddle");
        //nameTagLabel.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        //List<String> lore = new ArrayList<>();
        //lore.add("Can Break Bedrock");
        //nameTagLabel.setLore(lore);
        saddle.setItemMeta(saddleLabel);

        NamespacedKey saddleKey = NamespacedKey.minecraft("saddle");//new (MultiPlayerHardcore, "name_tag");
        saddleRecipe = new ShapedRecipe(saddleKey, saddle);

        saddleRecipe.shape(
                "LLL",
                "S S",
                "I I");
        saddleRecipe.setIngredient('I', Material.IRON_INGOT);
        saddleRecipe.setIngredient('S', Material.STRING);
        saddleRecipe.setIngredient('L', Material.LEATHER);
        Bukkit.addRecipe(saddleRecipe);
    }

    public static void initNotchApple() {
        notchApple = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
        notchAppleLabel = notchApple.getItemMeta();
        assert notchAppleLabel != null;
        notchAppleLabel.setDisplayName(ChatColor.GOLD + "Notch Apple");
        //nameTagLabel.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        List<String> lore = new ArrayList<>();
        lore.add("Ooh! Shiny!");
        notchAppleLabel.setLore(lore);
        notchApple.setItemMeta(notchAppleLabel);

        NamespacedKey notchAppleKey = NamespacedKey.minecraft("enchanted_golden_apple");//new (MultiPlayerHardcore, "name_tag");
        notchAppleRecipe = new ShapedRecipe(notchAppleKey, notchApple);

        notchAppleRecipe.shape(
                "GGG",
                "GAG",
                "GGG");
        notchAppleRecipe.setIngredient('G', Material.GOLD_BLOCK);
        notchAppleRecipe.setIngredient('A', Material.APPLE);
        Bukkit.addRecipe(notchAppleRecipe);
    }
}
