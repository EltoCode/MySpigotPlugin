package org.elton.myspigotplugin.listeners;

import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.elton.myspigotplugin.MySpigotPlugin;

public class EndrprlTpListener implements Listener {

    private final MySpigotPlugin plugin;

    public EndrprlTpListener(MySpigotPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEnderPearlTp(PlayerTeleportEvent event) {
        //if tped by anything not a pearl, returns
        if(event.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) return;

        //gets tje player
        Player player = event.getPlayer();
        //gets the flair of the player
        String flair = plugin.enderpearlFlair.get(player.getName());
        //if no flair is set, returns
        if(flair == null) return;
        //playes particle at players location
        event.getPlayer().spawnParticle(Particle.valueOf(flair), event.getTo(),
                50, 0.5,0.5,0.5, 0.5);
    }
}
