package org.elton.myspigotplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.elton.myspigotplugin.MySpigotPlugin;

public class CommandDeathSave implements CommandExecutor {

    MySpigotPlugin plugin;
    public CommandDeathSave(MySpigotPlugin plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //needs to provide only 1 arg
        if(args.length > 1) {
            plugin.getLogger().info("Too many arguments, expected one");
            if(sender instanceof Player)
                sender.sendMessage(ChatColor.RED + "Too many arguments, expected one");
            return true;
        }

        //handles console
        if(!(sender instanceof Player)) {
            //console needs to provide player
            if(args.length == 0) {
                plugin.getLogger().info("You need to provide a player username for this command");
                return true;
            }
            //else sends name to be checked and toggled
            else
                toggleSaveFromDeath(args[0]);
        }
        //this means it's a player sending with one or 0 argument
        else {
            //if 0 args target self
            if(args.length == 0) {
                toggleSaveFromDeath(sender.getName());
            }
            //sends arg to target
            else {
                toggleSaveFromDeath(args[0]);
            }
        }
        return true;
    }

    private void toggleSaveFromDeath(String playerName) {

        //if player does not exist, returns
        Player target = plugin.getServer().getPlayer(playerName);
        if(target == null) {
            plugin.getLogger().info("Could not find player" + playerName);
            return;
        }
        //swaps bool value

        boolean prevValue = (plugin.saveFromDeath.get(playerName) == null) ? false : plugin.saveFromDeath.get(playerName);
        plugin.saveFromDeath.put(playerName, !prevValue);
        plugin.getLogger().info("Save from Death value of " + playerName + "is now " + !prevValue);
        target.sendMessage(ChatColor.YELLOW + "Save from Death value of " + playerName + "is now " + !prevValue);
    }
}
