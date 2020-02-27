package com.github.kyazuki.linkedinventory;

import org.bukkit.configuration.file.FileConfiguration;

public class PluginConfig {
  private final LinkedInventory plugin;
  private FileConfiguration config = null;
  public int onDropDelay;
  public int onPickDelay;
  public int onPlaceDelay;
  public int onUseDelay;
  public int onItemDamageDelay;
  public int onInteractDelay;
  public int onClickInventoryDelay;
  public int onDragInventoryDelay;
  public int onExchangeHandDelay;
  public int onDeathDelay;


  public PluginConfig(LinkedInventory plugin) {
    this.plugin = plugin;
    load();
  }

  public void load() {
    plugin.saveDefaultConfig();
    if (config != null) {
      plugin.reloadConfig();
    }
    config = plugin.getConfig();

    onDropDelay = config.getInt("onDropDelay", 0);
    onPickDelay = config.getInt("onPickDelay", 2);
    onPlaceDelay = config.getInt("onPlaceDelay", 0);
    onUseDelay = config.getInt("onUseDelay", 0);
    onItemDamageDelay = config.getInt("onItemDamageDelay", 0);
    onInteractDelay = config.getInt("onInteractDelay", 0);
    onClickInventoryDelay = config.getInt("onClickInventoryDelay", 0);
    onDragInventoryDelay = config.getInt("onDragInventoryDelay", 0);
    onExchangeHandDelay = config.getInt("onExchangeHandDelay", 0);
    onDeathDelay = config.getInt("onDeathDelay", 0);
  }
}
