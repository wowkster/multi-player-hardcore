package com.adrianwowk.multiplayerhardcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MPHTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        /* If command is revive */
            if (cmd.getName().equalsIgnoreCase("revive")) {
                List<String> list = new ArrayList<>();
                if (checkForPerm(sender, cmd)){
                    // allow command
                    if (args.length <= 1){
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            list.add(player.getDisplayName());
                        }
//                        list.add("player");
                        return list;
                    }
//                    list.add("args.length is not <= 1");
                    return list;
                }
                List<String> test = new ArrayList<>();
//                test.add("test");
                return test;
            }
        /* If command is hardcore */
            else if (cmd.getName().equalsIgnoreCase("hardcore")){
                List<String> list = new ArrayList<>();
                if (checkForPerm(sender, cmd)){
                    // allow command
                    if (args.length <= 1) {
                        list.add("start");
                        list.add("end");
                        list.add("revives");
                        list.add("spawn");
                    } else if (args.length <= 2 && args[0].equalsIgnoreCase("revives")){
                        list.add("get");
                        list.add("set");
                    } else if (args.length <= 2 && args[0].equalsIgnoreCase("spawn")){
                        list.add("get");
                        list.add("set");
                    }
//                    list.add("args.length is not <= 1");
                    return list;
                }
                List<String> test = new ArrayList<>();
//                test.add("test");
                return test;
            }

        return null;
    }
    /**
     * Check is the command sender has permission to execute the command
     * */
    public static boolean checkForPerm(CommandSender sender, Command cmd){
        boolean hasPermission = false;
        if (sender instanceof Player) {
            // is player
            Player p = (Player) sender;
            // check all possible commands
            if (cmd.getName().equalsIgnoreCase("revive")) {
                if (p.hasPermission("mph.revive")) {
                    return true;
                }
            } else if (cmd.getName().equalsIgnoreCase("hardcore")) {
                if (p.hasPermission("mph.hardcore")) {
                    return true;
                }
            }
        } else {
            // is server
            return true;
        }
        return false;
    }
}
