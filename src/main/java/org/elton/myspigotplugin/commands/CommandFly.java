package org.elton.myspigotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.elton.myspigotplugin.MySpigotPlugin;

import java.util.Objects;

public class CommandFly implements CommandExecutor {

    //main plugin ref
    private final MySpigotPlugin plugin;

    public CommandFly(MySpigotPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player)sender;

            //checks if another player is added as arguments
            if(args.length == 0)
                if(player.hasPermission("myspigotplugin.fly"))
                    toggleFly(player);
            if(args.length == 1)
                if(player.hasPermission("myspigotplugin.flyothers"))
                    try {
                        Player playerTarget = Bukkit.getPlayer(args[0]);
                        toggleFly(Objects.requireNonNull(playerTarget));
                    }catch (NullPointerException ex) {
                        player.sendMessage(ChatColor.RED + "Could not find player " + args[0]);
                    }
        }
        return true;
    }

    private void toggleFly (Player player) {
        if(!player.getAllowFlight()) {
            player.setAllowFlight(true);
            player.sendMessage(ChatColor.AQUA + plugin.getConfig().getString("fly-enable-msg"));
        }
        else {
            player.setAllowFlight(false);
            player.sendMessage(ChatColor.AQUA + plugin.getConfig().getString("fly-disable-msg"));
        }
    }
}
