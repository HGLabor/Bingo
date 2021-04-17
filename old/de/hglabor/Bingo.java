package de.hglabor;

import de.hglabor.commands.BingoCommand;
import de.hglabor.commands.StartCommand;
import de.hglabor.listener.*;
import de.hglabor.old.listener.*;
import de.hglabor.utils.BingoManager;
import de.hglabor.utils.GameManager;
import de.hglabor.utils.ItemGenerator;
import mooz.noriskbingo.listener.*;
import de.hglabor.utils.Localization;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Bingo extends JavaPlugin {

    private static Bingo instance = null;
    private static GameManager gameMan;
    private static File file = null;

    @Override
    public void onEnable() {
        instance = this;
        File dir = new File("./plugins/de.hglabor.Bingo");
        if(!dir.exists()) {
            dir.mkdirs();
        }
        file = new File(dir, "locale.yml");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setupFile();
        final ItemGenerator itemGenerator = new ItemGenerator();
        final BingoManager bingoManager = new BingoManager();
        gameMan = new GameManager(this);
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new MapInitializeListener(), this);
        pluginManager.registerEvents(new JoinListener(file), this);
        pluginManager.registerEvents(new ClickListener(), this);
        pluginManager.registerEvents(new ChatFormatter(), this);
        pluginManager.registerEvents(new InteractListener(), this);
        getCommand("bingo").setExecutor(new BingoCommand());
        getCommand("start").setExecutor(new StartCommand());

    }

    public static GameManager getManager() {
        return gameMan;
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    private void setupFile() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if(!config.contains("prefix_DE")) {
            config.set("prefix_DE", "PREFIX > §o");
        }
        if(!config.contains("prefix_EN")) {
            config.set("prefix_EN", "PREFIX > §o");
        }
        if(!config.contains("fieldName_DE")) {
            config.set("fieldName_DE", "Change it");
        }
        if(!config.contains("fieldName_EN")) {
            config.set("fieldName_EN", "Change it");
        }
        if(!config.contains("registered_DE")) {
            config.set("registered_DE", "%player% hat %item% registriert. %checked%/%needed% Items verbleibend");
        }
        if(!config.contains("registered_EN")) {
            config.set("registered_EN", "%player% collected %item%. %checked%/%needed% Items remaining");
        }
        if(!config.contains("win_DE")) {
            config.set("win_DE", "%player% hat alle Items gefunden");
        }
        if(!config.contains("win_EN")) {
            config.set("win_EN", "%player% collected all items");
        }
        if(!config.contains("starting_DE")) {
            config.set("starting_DE", "Das Spiel startet in %value% Sekunde(n)");
        }
        if(!config.contains("starting_EN")) {
            config.set("starting_EN", "The Game starts in %value% second(s)");
        }
        if(!config.contains("restarting_DE")) {
            config.set("restarting_DE", "Das Spiel startet in %value% Sekunde(n)");
        }
        if(!config.contains("restarting_EN")) {
            config.set("restarting_EN", "The Game starts in %value% second(s)");
        }
        if(!config.contains("event")) {
            config.set("event", false);
        }
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Localization getLocalization() {
        return new Localization(instance, file);
    }
}
