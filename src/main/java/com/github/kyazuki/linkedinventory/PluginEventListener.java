package com.github.kyazuki.linkedinventory;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.*;
import org.bukkit.scoreboard.Team;

public class PluginEventListener implements Listener {
  private final LinkedInventory plugin;
  private final PluginConfig config;

  public PluginEventListener(LinkedInventory plugin, PluginConfig config) {
    this.plugin = plugin;
    this.config = config;
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }

  public void delay(Runnable run, long sprints) {
    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, run, sprints);
  }

  public void shareInventory(Player updatePlayer, long delay_time) {
    if (plugin.enableShare) {
      if (!plugin.teammode) {
        this.delay(() -> {
          for (Player player : Bukkit.getOnlinePlayers()) {
            if (player != updatePlayer) {
              synchronized (this) {
                player.getInventory().setArmorContents(updatePlayer.getInventory().getArmorContents());
                player.getInventory().setContents(updatePlayer.getInventory().getContents());
                player.updateInventory();
              }
            }
          }
        }, delay_time);
      } else {
        Team team = updatePlayer.getScoreboard().getPlayerTeam(updatePlayer);
        if (team != null) {
          this.delay(() -> {
            for (OfflinePlayer player : team.getPlayers()) {
              if (player != updatePlayer && player.isOnline()) {
                synchronized (this) {
                  ((Player) player).getInventory().setArmorContents(updatePlayer.getInventory().getArmorContents());
                  ((Player) player).getInventory().setContents(updatePlayer.getInventory().getContents());
                  ((Player) player).updateInventory();
                }
              }
            }
          }, delay_time);
        }
      }
    }
  }

  @EventHandler
  public void onLogin(PlayerJoinEvent event) {
    if (Bukkit.getOnlinePlayers().size() > 1) {
      for (Player existPlayer : Bukkit.getOnlinePlayers()) {
        synchronized (this) {
          event.getPlayer().getInventory().setArmorContents(existPlayer.getInventory().getArmorContents());
          event.getPlayer().getInventory().setContents(existPlayer.getInventory().getContents());
          event.getPlayer().updateInventory();
          break;
        }
      }
    }
  }

  @EventHandler
  public void onLink(InventoryLinkEvent event) {
    this.shareInventory(event.getPlayer(), 0);
  }

  @EventHandler
  public void onDrop(PlayerDropItemEvent event) {
    this.shareInventory(event.getPlayer(), config.onDropDelay);
  }

  @EventHandler
  public void onPick(PlayerPickupItemEvent event) {
    this.shareInventory(event.getPlayer(), config.onPickDelay);
  }

  @EventHandler
  public void onPlace(BlockPlaceEvent event) {
    this.shareInventory(event.getPlayer(), config.onPlaceDelay);
  }

  @EventHandler
  public void onUse(PlayerItemConsumeEvent event) {
    this.shareInventory(event.getPlayer(), config.onUseDelay);
  }

  @EventHandler
  public void onItemDamage(PlayerItemDamageEvent event) {
    this.shareInventory(event.getPlayer(), config.onItemDamageDelay);
  }

  @EventHandler
  public void onInteract(PlayerInteractEvent event) {
    this.shareInventory(event.getPlayer(), config.onInteractDelay);
  }

  @EventHandler
  public void onClickInventory(InventoryClickEvent event) {
    this.shareInventory((Player) event.getWhoClicked(), config.onClickInventoryDelay);
  }

  @EventHandler
  public void onDragInventory(InventoryDragEvent event) {
    this.shareInventory((Player) event.getWhoClicked(), config.onDragInventoryDelay);
  }

  @EventHandler
  public void onExchangeHand(PlayerSwapHandItemsEvent event) {
    this.shareInventory(event.getPlayer(), config.onExchangeHandDelay);
  }

  @EventHandler
  public void onDeath(PlayerDeathEvent event) {
    this.shareInventory(event.getEntity(), config.onDeathDelay);
  }
}
