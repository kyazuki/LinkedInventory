package com.github.kyazuki.linkedinventory;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PluginCommands implements TabExecutor {
  private final LinkedInventory plugin;
  private final PluginConfig config;
  private final MessageTranslator translator;
  private final String PermissionToggle = "linkedinventory.toggle";
  private final String PermissionModeToggle = "linkedinventory.mode_toggle";
  private final String PermissionReload = "linkedinventory.reload";

  public PluginCommands(LinkedInventory plugin, PluginConfig config, MessageTranslator translator) {
    this.plugin = plugin;
    this.config = config;
    this.translator = translator;
    plugin.getCommand("linkedinventory").setExecutor(this);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (args.length == 1) {
      if (args[0].equalsIgnoreCase("toggle")) {
        if (sender.hasPermission(PermissionToggle)) {
          if (plugin.enableShare) {
            sender.sendMessage(translator.getTranslatedMessage("TurnOffMessage", sender));
            plugin.enableShare = false;
          } else {
            if (sender instanceof Player) {
              sender.sendMessage(translator.getTranslatedMessage("TurnOnMessage", sender));
              plugin.enableShare = true;
              InventoryLinkEvent event = new InventoryLinkEvent((Player) sender);
              plugin.getServer().getPluginManager().callEvent(event);
            } else {
              sender.sendMessage(translator.getTranslatedMessage("NotPlayerError", sender));
            }
          }
        } else {
          sender.sendMessage(translator.getTranslatedMessage("NoPermError", sender));
        }
        return true;
      } else if (args[0].equalsIgnoreCase("mode-toggle")) {
        if (sender.hasPermission(PermissionModeToggle)) {
          if (plugin.teammode) {
            sender.sendMessage(translator.getTranslatedMessage("ModeEveryoneMessage", sender));
            plugin.teammode = false;
          } else {
            sender.sendMessage(translator.getTranslatedMessage("ModeTeamMessage", sender));
            plugin.teammode = true;
          }
        } else {
          sender.sendMessage(translator.getTranslatedMessage("NoPermError", sender));
        }
        return true;
      } else if (args[0].equalsIgnoreCase("reload")) {
        if (sender.hasPermission(PermissionReload)) {
          sender.sendMessage(translator.getTranslatedMessage("ReloadMessage", sender));
          config.load();
          translator.allLoad();
        } else {
          sender.sendMessage(translator.getTranslatedMessage("NoPermError", sender));
        }
        return true;
      }
    } else if (args.length == 2) {
      if (args[0].equalsIgnoreCase("toggle")) {
        Player player = Bukkit.getPlayer(args[1]);
        if (player != null) {
          if (player.isOnline()) {
            if (!plugin.enableShare) {
              sender.sendMessage(translator.getTranslatedMessage("TurnOnMessage", sender));
              plugin.enableShare = true;
              InventoryLinkEvent event = new InventoryLinkEvent((Player) player);
              plugin.getServer().getPluginManager().callEvent(event);
            } else {
              sender.sendMessage(translator.getTranslatedMessage("AlreadySharingError", sender));
            }
          } else {
            sender.sendMessage(translator.getTranslatedMessage("NotOnlineError", sender));
          }
        } else {
          sender.sendMessage(translator.getTranslatedMessage("NotMatchPlayerError", sender));
        }
        return true;
      }
    }
    return false;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    List<String> list = new ArrayList<>();
    if (args.length == 1) {
      if (sender.hasPermission(PermissionToggle))
        list.add("toggle");
      if (sender.hasPermission(PermissionModeToggle))
        list.add("mode-toggle");
      if (sender.hasPermission(PermissionReload))
        list.add("reload");
      return list;
    } else if (args.length == 2) {
      if (args[0].equalsIgnoreCase("toggle")) {
        if (sender.hasPermission(PermissionModeToggle)) {
          for (Player player : Bukkit.getOnlinePlayers())
            list.add(player.getName());
        }
      }
      return list;
    } else
      return null;
  }
}
