package org.elton.myspigotplugin;

import org.bukkit.Particle;
import org.elton.myspigotplugin.MyUtils.DataYML;
import org.elton.myspigotplugin.commands.*;
import org.elton.myspigotplugin.listeners.*;
//import commands.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class MySpigotPlugin extends JavaPlugin {

    //Various HashMaps
    public HashMap<String, Boolean> isMarioMap;
    public HashMap<String, String> enderpearlFlair;
    public HashMap<String, Boolean> saveFromDeath;
    //public HashMap<String, String> tpTargets;

    //file names

    private final String ENDERPEARL_FLAIR_FILE ="enderpearl_flair.yml";
    private final String SAVE_FROM_DEATH_FILE ="save_from_death.yml";

    @Override
    public void onEnable() {
        //TODO - loop this
        //constructs the maps
        enderpearlFlair = new HashMap<String, String>();
        saveFromDeath = new HashMap<String, Boolean>();
        //tpTargets = new HashMap<String, String>();
        //loads the maps from files
        enderpearlFlair = (HashMap<String, String>) DataYML.load(this, ENDERPEARL_FLAIR_FILE);
        saveFromDeath = (HashMap<String, Boolean>) DataYML.load(this, SAVE_FROM_DEATH_FILE);


        Bukkit.getLogger().info(ChatColor.GREEN + "Enabled Yay" + this.getName());

        //config
        setupConfig();
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        //commands
        registerCommands();

        //event listeners
        registerListeners();
    }

    //Code to be run when plugin is disabled
    @Override
    public void onDisable() {
        Bukkit.getLogger().info(ChatColor.RED + "Disabled " + this.getName());

        //saves Hashmaps
        DataYML.save(enderpearlFlair, this, ENDERPEARL_FLAIR_FILE);
        DataYML.save(saveFromDeath, this, SAVE_FROM_DEATH_FILE);
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new MyFirstListener(), this);
        getServer().getPluginManager().registerEvents(new EndrprlTpListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDamageListener(this), this);
        getServer().getPluginManager().registerEvents(new TpGuiClickListener(this), this);
    }

    private void registerCommands() {
        Objects.requireNonNull(this.getCommand("whoami")).setExecutor(new CommandWhoami());
        Objects.requireNonNull(this.getCommand("fly")).setExecutor(new CommandFly(this));
        Objects.requireNonNull(this.getCommand("enderpearlflair")).setExecutor(
                new CommandEnderPearlFlair(this));
        Objects.requireNonNull(this.getCommand("deathsave")).setExecutor(new CommandDeathSave(this));
        Objects.requireNonNull(this.getCommand("tpplayer")).setExecutor(new CommandTpPlayer(this));
    }

    public void setupConfig() {
        getConfig().addDefault("fly-enable-msg", "Flying Enabled");
        getConfig().addDefault("fly-disable-msg", "Flying Disabled");
        getConfig().addDefault("tp-player-require-perm", "true");
    }

}
