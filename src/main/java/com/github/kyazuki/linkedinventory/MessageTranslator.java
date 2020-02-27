package com.github.kyazuki.linkedinventory;

import com.google.common.collect.ImmutableSet;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Set;

public class MessageTranslator {
  private final Plugin plugin;
  private final Set<PluginLocale> locales;
  private final PluginLocale en_us;
  private final PluginLocale ja_jp;

  public MessageTranslator(Plugin plugin) {
    this.plugin = plugin;
    locales = ImmutableSet.of(
            en_us = new PluginLocale(plugin, "en_us"),
            ja_jp = new PluginLocale(plugin, "ja_jp")
    );
  }

  public String getTranslatedMessage(String key, CommandSender sender) {
    if (sender instanceof Player)
      return getTranslatedMessage(key, ((Player) sender).getLocale());
    else
      return en_us.getMessage(key);
  }

  public String getTranslatedMessage(String key, String code) {
    for (PluginLocale locale : locales) {
      if (locale.getCode().equals(code))
        return locale.getMessage(key);
    }
    return en_us.getMessage(key);
  }

  public void allLoad() {
    for (PluginLocale locale : locales)
      locale.load();
  }
}
