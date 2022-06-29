package org.elton.myspigotplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.elton.myspigotplugin.MySpigotPlugin;

public class TpGuiClickListener implements Listener {

    //main plugin ref
    MySpigotPlugin plugin;
    public TpGuiClickListener(MySpigotPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onGuiClick(InventoryClickEvent e) {
        //if the inventory is not the tp gui returns
        if (!e.getView().getTitle().contentEquals("Teleport to?"))
            return;
        //Add a condition if null causes issues
        //if they click on an incorrect item we return
        if (e.getCurrentItem().getItemMeta().getCustomModelData() != 4242)
            return;

        //if the code is here, it means the item is correct
        //gets the player who clicked
        Player player = (Player) e.getWhoClicked();
        //gets the target chosen by player
        String targetName = e.getCurrentItem().getItemMeta().getDisplayName();
        Player target = Bukkit.getPlayer(targetName);
        //checks if the target has left the game
        if(target == null) {
            player.sendMessage(ChatColor.RED + targetName + "is no longer available");
            return;
        }
        //teleports the player
        player.teleport(target.getLocation());
        //sends message
        player.sendMessage(ChatColor.YELLOW + "Teleported" + player.getName() + "to" + target.getName());
        //cancels event
        e.setCancelled(true);
        //maybe do a accept command if the peeps ask for it

    }

}
