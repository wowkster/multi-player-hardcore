package com.adrianwowk.multiplayerhardcore.events;

import com.adrianwowk.multiplayerhardcore.MultiPlayerHardcore;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.awt.*;

public class MultiPlayerHardcoreEvents implements Listener {

    @EventHandler
    public void onButtonPress(final PlayerInteractEvent event){
        if (event.isCancelled()){
            return;
        }
        if(event.getClickedBlock().getType() == Material.WARPED_BUTTON && !MultiPlayerHardcore.hardcore){
            event.getPlayer().sendMessage(MultiPlayerHardcore.prefix + ChatColor.YELLOW + "Hardcore Mode is off");
            event.setCancelled(true);
        }
        return;
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        // put code here
        long ticks = 1;
        Bukkit.getScheduler().runTaskLater(MultiPlayerHardcore.getPlugin(MultiPlayerHardcore.class), new Runnable() {
            @Override
            public void run() {
                //some long annoying text here
                Player player = event.getPlayer();
                if(!player.hasPlayedBefore()){
                    player.teleport(MultiPlayerHardcore.startPlatform);
                    player.setGameMode(GameMode.ADVENTURE);
                }
                // send custom message
                player.sendMessage(MultiPlayerHardcore.prefix + ChatColor.YELLOW + "Welcome to MultiPlayer Hardcore survival. Between all players, there are only 2 respawns. This means that collectively you can only have 2 deaths. After 2 deaths, Everyone will be set to spectator mode.");

                if (MultiPlayerHardcore.revivesLeft <= -1 && MultiPlayerHardcore.hardcore){
                    player.setGameMode(GameMode.SPECTATOR);
                }
            }
        }, ticks);

        return;
    }

    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent event) {
        Player p = event.getEntity();
        for (Player player : Bukkit.getOnlinePlayers()){
            player.sendMessage(MultiPlayerHardcore.prefix + ChatColor.YELLOW + event.getDeathMessage());
        }
    }

    @EventHandler
    public void onFall(final EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();
            Location loc = p.getLocation();
            loc.setY(loc.getBlockY() -1);
            if (loc.getBlock().getType() == Material.BARRIER){
                Location start = MultiPlayerHardcore.startPlatform.clone();
                start.setX(start.getBlockX() + 0.5);
                start.setZ(start.getBlockZ() + 0.5);
                p.teleport(start);
            }
        }
    }

    @EventHandler
    public void onPlayerRespawn(final PlayerRespawnEvent event) {
        // put code here
        if (MultiPlayerHardcore.hardcore){
            Player p = event.getPlayer();
            if (MultiPlayerHardcore.revivesLeft > 0) {
                // respawn
                MultiPlayerHardcore.revivesLeft--;
                Bukkit.getServer().getLogger().info(ChatColor.GREEN + String.valueOf(MultiPlayerHardcore.revivesLeft));
                p.sendTitle("You Died!",   String.valueOf(MultiPlayerHardcore.revivesLeft) + " Respawn(s) Left");

            } else {
                // perm-die
                MultiPlayerHardcore.revivesLeft = -1;
            }


            long ticks = 1;
            Bukkit.getScheduler().runTaskLater(MultiPlayerHardcore.getPlugin(MultiPlayerHardcore.class), new Runnable() {
                @Override
                public void run() {
                    //some long annoying text here
                    if (!(MultiPlayerHardcore.revivesLeft >= 0)) {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.setGameMode(GameMode.SPECTATOR);
                            player.sendTitle(p.getDisplayName() + " Died!", "No Respawns Left", 10, 70 ,20);
                        }
                        p.sendMessage(MultiPlayerHardcore.prefix + ChatColor.YELLOW + "Type "
                                + ChatColor.AQUA + "/back" + ChatColor.YELLOW + " to teleport to your death location");
                    } else if (p.getBedSpawnLocation() == null) {
                        p.teleport(MultiPlayerHardcore.startPlatform);
                        p.setGameMode(GameMode.ADVENTURE);
                        p.getInventory().clear();
                        p.setHealth(20);
                        p.setFoodLevel(20);
                        p.setSaturation(20);
                    }
                }
            }, ticks);
        }
        // gamemode spectator



        return;
    }
}
