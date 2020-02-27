package com.github.kyazuki.linkedinventory;

import com.google.common.base.Charsets;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PluginLocale {
  private FileConfiguration localeConfig = null;
  private final File localeFile;
  private final String code;
  private final String fileName;
  private final Plugin plugin;

  public String TurnOnMessage;
  public String TurnOffMessage;
  public String ModeEveryoneMessage;
  public String ModeTeamMessasge;
  public String ReloadMessage;

  public String NotPlayerError;
  public String NoPermError;
  public String AlreadySharingError;
  public String NotOnlineError;
  public String NotMatchPlayerError;

  public String PleaseReportError;

  public PluginLocale(Plugin plugin, String code) {
    this.plugin = plugin;
    this.code = code;
    this.fileName = code + ".yml";
    localeFile = new File(plugin.getDataFolder(), fileName);
    load();
  }

  public String getCode() {
    return code;
  }

  public String getMessage(String key) {
    switch (key) {
      case "TurnOnMessage":
        return TurnOnMessage;
      case "TurnOffMessage":
        return TurnOffMessage;
      case "ModeEveryoneMessage":
        return ModeEveryoneMessage;
      case "ModeTeamMessage":
        return ModeTeamMessasge;
      case "ReloadMessage":
        return ReloadMessage;
      case "NotPlayerError":
        return ChatColor.RED + NotPlayerError;
      case "NoPermError":
        return ChatColor.RED + NoPermError;
      case "AlreadySharingError":
        return ChatColor.RED + AlreadySharingError;
      case "NotOnlineError":
        return ChatColor.RED + NotOnlineError;
      case "NotMatchPlayerError":
        return ChatColor.RED + NotMatchPlayerError;
      default:
        return ChatColor.YELLOW + PleaseReportError;
    }
  }

  private void saveDefaultLocale() {
    if (!localeFile.exists()) {
      plugin.saveResource(fileName, false);
    }
  }

  private void reloadLocale() {
    localeConfig = YamlConfiguration.loadConfiguration(localeFile);

    final InputStream defConfigStream = plugin.getResource(fileName);
    if (defConfigStream == null) {
      return;
    }

    localeConfig.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
  }

  private FileConfiguration getLocale() {
    if (localeConfig == null) {
      reloadLocale();
    }
    return localeConfig;
  }

  public void load() {
    this.saveDefaultLocale();
    if (localeConfig != null) {
      this.reloadLocale();
    }
    localeConfig = this.getLocale();

    TurnOnMessage = localeConfig.getString("TurnOnMessage", "Inventory is linked!");
    TurnOffMessage = localeConfig.getString("TurnOffMessage", "Inventory is unlinked!");
    ModeEveryoneMessage = localeConfig.getString("ModeEveryoneMessage", "Set to everyone mode!");
    ModeTeamMessasge = localeConfig.getString("ModeTeamMessasge", "Set to team mode!");
    ReloadMessage = localeConfig.getString("ReloadMessage", "Link Inventory Config reloaded!");

    NotPlayerError = localeConfig.getString("NotPlayerError", "This command can be executed only by player.");
    NoPermError = localeConfig.getString("NoPermError", "You don't have the permission!");
    AlreadySharingError = localeConfig.getString("AlreadySharingError", "Inventory is already linked!");
    NotOnlineError = localeConfig.getString("NotOnlineError", "Player is not online.");
    NotMatchPlayerError = localeConfig.getString("NotMatchPlayerError", "Player is not found.");

    PleaseReportError = localeConfig.getString("PleaseReportError", "Author forgot to translate. Please report!");
  }
}
