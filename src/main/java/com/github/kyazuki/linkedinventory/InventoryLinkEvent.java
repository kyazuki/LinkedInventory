package com.github.kyazuki.linkedinventory;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class InventoryLinkEvent extends Event {
  private static final HandlerList handlers = new HandlerList();
  protected Player player;

  public InventoryLinkEvent(Player player) {
    this.player = player;
  }

  public Player getPlayer() {
    return player;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }
}
