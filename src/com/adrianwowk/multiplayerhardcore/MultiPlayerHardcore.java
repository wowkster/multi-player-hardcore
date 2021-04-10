package com.adrianwowk.multiplayerhardcore;

import com.adrianwowk.multiplayerhardcore.commands.CommandHandler;
import com.adrianwowk.multiplayerhardcore.commands.MPHTabCompleter;
import com.adrianwowk.multiplayerhardcore.events.MultiPlayerHardcoreEvents;
import com.adrianwowk.multiplayerhardcore.items.MultiPlayerHardcoreCustomCrafting;
import org.bukkit.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.io.BukkitObjectInputStream;

import java.util.ArrayList;
import java.util.List;

public class MultiPlayerHardcore extends JavaPlugin {
    public static boolean hardcore;
    Server server;
    ConsoleCommandSender console;
    public static String prefix;
    public static int revivesLeft;
    public static Location startPlatform;
    FileConfiguration config = getConfig();


    public MultiPlayerHardcore() {
        this.server = Bukkit.getServer();
        this.console = this.server.getConsoleSender();
        this.prefix = ChatColor.GRAY + "[" + ChatColor.RED + "MultiPlayerHardcore" + ChatColor.GRAY + "] ";
    }

    public void onEnable() {
        config.addDefault("revives", 2);
        config.options().copyDefaults(true);
        saveConfig();

        config.addDefault("hardcore", false);
        config.options().copyDefaults(true);
        saveConfig();

        config.addDefault("platform.x", 0);
        config.options().copyDefaults(true);
        saveConfig();

        config.addDefault("platform.y", 150);
        config.options().copyDefaults(true);
        saveConfig();

        config.addDefault("platform.z", 0);
        config.options().copyDefaults(true);
        saveConfig();

        startPlatform = new Location(Bukkit.getWorld("world"), config.getInt("platform.x"),config.getInt("platform.y"),config.getInt("platform.z"));
        revivesLeft = config.getInt("revives");
        hardcore = config.getBoolean("hardcore");

        System.out.println(String.valueOf(startPlatform.getBlockX()) + ", " + String.valueOf(startPlatform.getBlockY()) + ", " + String.valueOf(startPlatform.getBlockZ()));

        Bukkit.getWorld("world").setSpawnLocation(startPlatform);
        Bukkit.getWorld("world").save();

        // Initialize Name Tag Crafting Recipe
        MultiPlayerHardcoreCustomCrafting.initNameTag();
        // Initialize Saddle Crafting Recipe
        MultiPlayerHardcoreCustomCrafting.initSaddle();
        // Initialize Notch Apple Crafting Recipe
        MultiPlayerHardcoreCustomCrafting.initNotchApple();

        // Register command tab completer and executer

        getCommand("revive").setTabCompleter(new MPHTabCompleter());
        getCommand("revive").setExecutor(new CommandHandler());

        getCommand("hardcore").setTabCompleter(new MPHTabCompleter());
        getCommand("hardcore").setExecutor(new CommandHandler());

//        getCommand("spawnplatform").setTabCompleter(new MPHTabCompleter());
//        getCommand("spawnplatform").setExecutor(new CommandHandler());

        // Register Event Listeners
        Bukkit.getServer().getPluginManager().registerEvents(new MultiPlayerHardcoreEvents(), (Plugin) this);

        // Server Console Message
        this.getLogger().info(ChatColor.GREEN + "=================================");
        this.getLogger().info(ChatColor.GREEN + "      [MultiPlayerHardcore]      ");
        this.getLogger().info(ChatColor.GREEN + "  Has been successfuly enabled!  ");
        this.getLogger().info(ChatColor.GREEN + "     Author - Adrian Wowk        ");
        this.getLogger().info(ChatColor.GREEN + "=================================");
//        this.getLogger().info(ChatColor.GREEN + String.valueOf(config.getInt("revives")));

    }

    public void onDisable() {
        config.set("revives", revivesLeft);
        config.set("hardcore", hardcore);
        config.set("platform.x", startPlatform.getBlockX());
        config.set("platform.y", startPlatform.getBlockY());
        config.set("platform.z", startPlatform.getBlockZ());
        saveConfig();
        System.out.println(String.valueOf(startPlatform.getBlockX()) + ", " + String.valueOf(startPlatform.getBlockY()) + ", " + String.valueOf(startPlatform.getBlockZ()));
        this.getLogger().info(prefix + ChatColor.GREEN + "The Plugin was successfully disabled.");
    }

//
}