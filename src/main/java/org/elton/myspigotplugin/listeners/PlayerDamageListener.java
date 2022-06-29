package org.elton.myspigotplugin.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.elton.myspigotplugin.MySpigotPlugin;


public class PlayerDamageListener implements Listener {

    //main plugin ref
    MySpigotPlugin plugin;
    public PlayerDamageListener(MySpigotPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        //if the entity is not player returns
        if(e.getEntityType() != EntityType.PLAYER)
            return;
        //Puts player in player object
        Player player = (Player) e.getEntity();
        //if the damage taken is less than or greater the max health, returns
        if((e.getFinalDamage() < player.getHealth()))
            return;
        //if the player does not have save-death set to true we don't care
        boolean isSave = plugin.saveFromDeath.get(player.getName());
        if(!isSave)
            return;
        //if reached here it means the entity is a player who isDeath Save who is on the killing blow
        try { player.setHealth(player.getHealth() + e.getFinalDamage() + 1); }
        catch (IllegalArgumentException ignored) {}

        //tosses the player around
        new BukkitRunnable() {
            @Override
            public void run() {
                player.setVelocity(new Vector(0, 5, 0));
            }
        }.runTaskLater(plugin, 5);
    }
}
