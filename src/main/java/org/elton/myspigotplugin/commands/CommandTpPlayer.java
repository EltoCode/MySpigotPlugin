package org.elton.myspigotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.elton.myspigotplugin.MySpigotPlugin;

import java.util.Objects;

public class CommandTpPlayer implements CommandExecutor {

    //main plugin ref
    MySpigotPlugin plugin;
    public CommandTpPlayer(MySpigotPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //if not player returns
        if(!(sender instanceof Player)) {
            plugin.getLogger().info("Only players may use this command");
            return true;
        }
        //if args != returns
        if(args.length > 0) {
            sender.sendMessage(ChatColor.RED + "Incorrect number of arguments, expected none");
            return true;
        }
        //if perm required and no permission returns
        if(!sender.hasPermission("myspigotplugin.tpplayer")
                && plugin.getConfig().getBoolean("tp-player-require-perm")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to run this command " +
                    "[myspigotplugin.tpplayer]");
            return true;
        }
        //if this code is here it means it's a player with 1 argument and proper permissions
        //creates a gui inv and has the player open it
        Player player = (Player) sender;
        player.openInventory(setupTpInventory());


        return true;
    }

    //creates an inventory gui
    public Inventory setupTpInventory() {

        //gets the number of online players
        int playerNum = Bukkit.getOnlinePlayers().size();
        int rowSize = 9;
        int invSize = (int) (Math.ceil((double)playerNum/(double) rowSize) * rowSize);
        Inventory gui = Bukkit.createInventory(null, invSize, "Teleport to?");
        //goes through every online player
        //better add a safeguard for size ig but it will never exceed 27
        //AtomicInteger maxItr = new AtomicInteger(27);
        Bukkit.getOnlinePlayers().forEach((Player p) -> {
            //gets the players head
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
            Objects.requireNonNull(skullMeta).setOwningPlayer(p);
            skullMeta.setDisplayName(p.getName());
            skullMeta.setCustomModelData(4242); //for differentiation
            skull.setItemMeta(skullMeta);
            //adds head to inventory
            gui.addItem(skull);
        });
        //returns the created gui
        return gui;
    }

}
