package com.github.kyazuki.linkedinventory;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public final class LinkedInventory extends JavaPlugin {
  public boolean enableShare = false;
  public boolean teammode = false;

  @Override
  public void onEnable() {
    getLogger().info("Linked Inventory Loaded.");
    PluginConfig pluginConfig = new PluginConfig(this);
    new PluginEventListener(this, pluginConfig);
    MessageTranslator translator = new MessageTranslator(this);
    new PluginCommands(this, pluginConfig, translator);
  }

  @Override
  public void onDisable() {
    getLogger().info("Linked Inventory Unloaded.");
    HandlerList.unregisterAll(this);
  }
}
