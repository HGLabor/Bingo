package de.hglabor.utils;

import de.hglabor.Bingo;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Locale;

public class Localization {

    public Localization(Bingo bingo, File configFile) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        prefixG = config.getString("prefix_DE");
        prefixE = config.getString("prefix_EN");

        fieldG = config.getString("fieldName_DE");
        fieldE = config.getString("fieldName_EN");

        registeredG = config.getString("registered_DE");
        registeredE = config.getString("registered_EN");

        winG = config.getString("win_DE");
        winE = config.getString("win_EN");

        startingG = config.getString("starting_DE");
        startingE = config.getString("starting_EN");

        restartingG = config.getString("restarting_DE");
        restartingE = config.getString("restarting_EN");
    }

    private static String prefixG;
    private static String prefixE;

    private static String fieldG;
    private static String fieldE;

    private static String registeredG;
    private static String registeredE;

    private static String winG;
    private static String winE;

    private static String startingG;
    private static String startingE;

    private static String restartingG;
    private static String restartingE;

    public String getPrefix(Locale locale) {
        if(locale == Locale.GERMAN) {
            return prefixG;
        } else {
            return prefixE;
        }
    }

    public String getFieldName(Locale locale) {
        if(locale == Locale.GERMAN) {
            return getPrefix(locale) + fieldG;
        } else {
            return getPrefix(locale) + fieldE;
        }
    }

    public String getRegisteringMessage(Locale locale) {
        if(locale == Locale.GERMAN) {
            return getPrefix(locale) + registeredG;
        } else {
            return getPrefix(locale) + registeredE;
        }
    }

    public String getWinMessage(Locale locale) {
        if(locale == Locale.GERMAN) {
            return getPrefix(locale) + winG;
        } else {
            return getPrefix(locale) + winE;
        }
    }

    public String getStartingMessage(Locale locale) {
        if(locale == Locale.GERMAN) {
            return getPrefix(locale) + startingG;
        } else {
            return getPrefix(locale) + startingE;
        }
    }

    public String getRestartingMessage(Locale locale) {
        if(locale == Locale.GERMAN) {
            return getPrefix(locale) + restartingG;
        } else {
            return getPrefix(locale) + restartingE;
        }
    }

}
