package com.adrianwowk.multiplayerhardcore.commands;

import com.adrianwowk.multiplayerhardcore.MultiPlayerHardcore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.swing.plaf.multi.MultiInternalFrameUI;

public class CommandHandler implements CommandExecutor {
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("revive")){
            if (MPHTabCompleter.checkForPerm(sender, cmd)) {
                try {
                    reviveCommand(sender, args);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
            sender.sendMessage(MultiPlayerHardcore.prefix + ChatColor.YELLOW
            + "You do not have permission to use this command");
            }
        } else if (cmd.getName().equalsIgnoreCase("hardcore")){
            if (MPHTabCompleter.checkForPerm(sender, cmd)) {
                try {
                    hardcoreCommand(sender, args);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                sender.sendMessage(MultiPlayerHardcore.prefix + ChatColor.YELLOW
                        + "You do not have permission to use this command");
            }
        }
        return true;
    }
    public boolean reviveCommand(CommandSender sender, final String[] args) throws InstantiationException, IllegalAccessException {
        // Try to get target from args --> execute
        if (args.length == 0){
            sender.sendMessage(MultiPlayerHardcore.prefix + ChatColor.YELLOW + "This command revives a player that has died. Usage: /revive <target>");
        } else if (args.length >= 1) {
            Player target = Bukkit.getPlayerExact(args[0]);
            if (target != null) {
                target.teleport(MultiPlayerHardcore.startPlatform);
                target.sendTitle("You Have Been Revived!", "Don't waste it!", 10, 70, 20);
                target.setGameMode(GameMode.ADVENTURE);
                target.getInventory().clear();
                MultiPlayerHardcore.revivesLeft = 0;
                target.setHealth(20);
                target.setFoodLevel(20);
                target.setSaturation(20);
                if (!target.getActivePotionEffects().isEmpty()){
                    for (PotionEffect effect : target.getActivePotionEffects()){
                        target.removePotionEffect(effect.getType());
                    }
                }
                sender.sendMessage(MultiPlayerHardcore.prefix + ChatColor.AQUA + target.getDisplayName() + ChatColor.YELLOW + " has been revived.");
            } else {
                sender.sendMessage(MultiPlayerHardcore.prefix + ChatColor.YELLOW + "The specified target could not be found.");
            }
        }
        return false;
    }
    public boolean hardcoreCommand(CommandSender sender, final String[] args) throws InstantiationException, IllegalAccessException {
        // Try to get start/end from args --> execute
        if (args.length == 0){
            //explain
            sender.sendMessage(MultiPlayerHardcore.prefix + ChatColor.YELLOW
                    + "This command starts or ends the hardcore period, or sets the number of revives");
        } else if (args.length >= 1){
            if (args[0].equalsIgnoreCase("start")){
                MultiPlayerHardcore.hardcore = true;
                sender.sendMessage(MultiPlayerHardcore.prefix + ChatColor.YELLOW
                        + "Hardcore Mode is "+ ChatColor.AQUA + "On");
                return false;
            } else if (args[0].equalsIgnoreCase("end")){
                MultiPlayerHardcore.hardcore = false;
                sender.sendMessage(MultiPlayerHardcore.prefix + ChatColor.YELLOW
                        + "Hardcore Mode is "+ ChatColor.AQUA + "Off");
                return false;
            } else if (args[0].equalsIgnoreCase("revives")){
                if (args.length >= 2){
                    if (args[1].equalsIgnoreCase("get")){
                        sender.sendMessage(MultiPlayerHardcore.prefix + ChatColor.YELLOW +
                                "There are " + MultiPlayerHardcore.revivesLeft + " revives left.");
                    } else if (args[1].equalsIgnoreCase("set")){
                        if (args.length >= 3){
                            //try to parse Int
                            try{
                                int toSet = Integer.parseInt(args[2]);
                                MultiPlayerHardcore.revivesLeft = toSet;
                                sender.sendMessage(MultiPlayerHardcore.prefix + ChatColor.YELLOW +
                                        "Set revives left to " + String.valueOf(toSet));
                            } catch (NumberFormatException e){
                                hardcoreCommandInvalid(sender);
                            }
                        } else{
                            hardcoreCommandInvalid(sender);
                        }
                    } else {
                        hardcoreCommandInvalid(sender);
                    }
                } else{
                    hardcoreCommandInvalid(sender);
                }
            } else if (args[0].equalsIgnoreCase("spawn")){
                if (args.length >= 2){
                    if (args[1].equalsIgnoreCase("get")){
                        sender.sendMessage(MultiPlayerHardcore.prefix + ChatColor.YELLOW +
                                "The location of the spawn platform is: " + ChatColor.AQUA +
                                MultiPlayerHardcore.startPlatform.getBlockX() + ", " +
                                MultiPlayerHardcore.startPlatform.getBlockY() + ", " +
                                MultiPlayerHardcore.startPlatform.getBlockZ());
                        System.out.println(String.valueOf(MultiPlayerHardcore.startPlatform.getBlockX()) + ", " + String.valueOf(MultiPlayerHardcore.startPlatform.getBlockY()) + ", " + String.valueOf(MultiPlayerHardcore.startPlatform.getBlockZ()));
                    } else if (args[1].equalsIgnoreCase("set")){
                        if (args.length >= 5){
                            //try to parse Int
                            try{
                                int toSetX = Integer.parseInt(args[2]);
                                int toSetY = Integer.parseInt(args[3]);
                                int toSetZ = Integer.parseInt(args[4]);
                                MultiPlayerHardcore.startPlatform = new Location(Bukkit.getWorld("world"),
                                        toSetX, toSetY, toSetZ);
                                sender.sendMessage(MultiPlayerHardcore.prefix + ChatColor.YELLOW +
                                        "Set spawn location to " +
                                        MultiPlayerHardcore.startPlatform.getBlockX() + ", " +
                                        MultiPlayerHardcore.startPlatform.getBlockY() + ", " +
                                        MultiPlayerHardcore.startPlatform.getBlockY() + ", ");
                                Bukkit.getWorld("world").setSpawnLocation(MultiPlayerHardcore.startPlatform);
                                Bukkit.getWorld("world").save();

                                System.out.println(String.valueOf(MultiPlayerHardcore.startPlatform.getBlockX()) + ", " + String.valueOf(MultiPlayerHardcore.startPlatform.getBlockY()) + ", " + String.valueOf(MultiPlayerHardcore.startPlatform.getBlockZ()));
                            } catch (NumberFormatException e){
                                hardcoreCommandInvalid(sender);
                            }
                        } else{
                            hardcoreCommandInvalid(sender);
                        }
                    } else {
                        hardcoreCommandInvalid(sender);
                    }
                } else{
                    hardcoreCommandInvalid(sender);
                }
            } else{
                // invalid arg
                hardcoreCommandInvalid(sender);
            }
        }

        return false;
    }

    private void hardcoreCommandInvalid(CommandSender sender){
        sender.sendMessage(MultiPlayerHardcore.prefix + ChatColor.YELLOW +
                "Invalid usage of command hardcore. Usage: /hardcore <start|end> or /hardcore revives <get|set> [value]");
    }
}

