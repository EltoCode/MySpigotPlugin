package org.elton.myspigotplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.elton.myspigotplugin.MySpigotPlugin;
import org.apache.commons.lang3.EnumUtils;

public class CommandEnderPearlFlair implements CommandExecutor {

    //main plugin ref
    MySpigotPlugin plugin;

    //constructor
    public  CommandEnderPearlFlair(MySpigotPlugin plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //checks if sender is player
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 0) {
                player.sendMessage(ChatColor.RED + "No flair provided. If you want to reset your enderpearl flair to default do" +
                        "\n/enderpearlflair none");
                return true;
            }

            //Sets args to String Upper
            String flair = args[0].toUpperCase();

            if(flair.contentEquals("NONE")) {
                player.sendMessage(ChatColor.DARK_AQUA + "You enderpearl flair has been disabled");
                plugin.enderpearlFlair.remove(player.getName());
            }

            if (!(EnumUtils.isValidEnum(Particle.class, flair))) {
                player.sendMessage(ChatColor.RED + args[0] + " is not a valid particle name.\n" +
                        "For a full list of particle names please refer to the Minecraft Wiki");
                return true;
            }

            plugin.enderpearlFlair.put(player.getName(),flair);
            player.sendMessage(ChatColor.DARK_AQUA + "Your enderpearl flair has been set to " + args[0]);

        }

        return  true;
    }
}
