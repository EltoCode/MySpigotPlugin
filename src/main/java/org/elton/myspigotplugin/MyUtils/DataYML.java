package org.elton.myspigotplugin.MyUtils;

import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;

public class DataYML {

    //create a yml instance
    private static final Yaml YAML_LOADER = new Yaml();



    //function to load a hashmap from file
    public static HashMap<?, ?> load(JavaPlugin plugin, String loadYML) {

        //gets the file path
        File file = new File(plugin.getDataFolder(), loadYML);

        //if the file does not exist, creates it
        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException ignored){}
        }

        //reads the hash map from the provided file
        Object obj = null;
        try {
            obj = YAML_LOADER.load(new FileInputStream(file));
        } catch (FileNotFoundException ignored) {}

        //if it gets a hashmap, returns it
        if (obj instanceof HashMap)
            return (HashMap<Object, Object>) obj;
        //if no hashmap is found, returns new
        return new HashMap<>();
    }


    //function to save a hashmap to file
    public static void save(HashMap<?, ?> map, JavaPlugin plugin, String saveYML) {

        //gets the file path
        File file = new File(plugin.getDataFolder(), saveYML);



        //if the file does not exist, creates it
        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException ignored){}
        }

        //save the hashmap to the file
        try {
            YAML_LOADER.dump(map, new FileWriter(file));
        } catch (IOException ignored) {}
    }
}
