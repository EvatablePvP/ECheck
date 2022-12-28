/*
 * Copyright 2022 EvatablePvP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.evatablepvp.echeck;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.StructureGrowEvent;

import lombok.RequiredArgsConstructor;

/**
 * Основной слушатель плагина ECheck
 * 
 * @author iEatMeat
 */
@RequiredArgsConstructor
public class CheckListener implements Listener {
    private final Main main;

    @EventHandler
    public void blockPlaceEvent(BlockPlaceEvent e) {
        if (main.getCheckMap().containsKey(e.getPlayer())
                && main.getCheckMap().get(e.getPlayer()).getTarget().equals(e.getPlayer())) {
            e.setCancelled(true);
        } else if (main.isEncapsulate() && !main.getCheckMap().isEmpty()) {
            Location l = e.getBlock().getLocation();
            for (Check c : main.getCheckMap().values()) {
                if (c.getBelow() != null && c.getBelow().getLocation().equals(l)) {
                    e.setCancelled(true);
                    break;
                } else if (c.getFeet() != null && c.getFeet().getLocation().equals(l)) {
                    e.setCancelled(true);
                    break;
                } else if (c.getHead() != null && c.getHead().getLocation().equals(l)) {
                    e.setCancelled(true);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent e) {
        if (main.getCheckMap().containsKey(e.getPlayer())
                && main.getCheckMap().get(e.getPlayer()).getTarget().equals(e.getPlayer())) {
            e.setCancelled(true);
        } else if (main.isEncapsulate() && !main.getCheckMap().isEmpty()) {
            Location l = e.getBlock().getLocation();
            for (Check c : main.getCheckMap().values()) {
                if (c.getBelow() != null && c.getBelow().getLocation().equals(l)) {
                    e.setCancelled(true);
                    break;
                } else if (c.getFeet() != null && c.getFeet().getLocation().equals(l)) {
                    e.setCancelled(true);
                    break;
                } else if (c.getHead() != null && c.getHead().getLocation().equals(l)) {
                    e.setCancelled(true);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent e) {
        if (main.getCheckMap().containsKey(e.getPlayer())
                && main.getCheckMap().get(e.getPlayer()).getTarget().equals(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void entityDamageEvent(EntityDamageEvent e) {
        if (e.getEntityType() == EntityType.PLAYER && main.getCheckMap().containsKey(e.getEntity())
                && main.getCheckMap().get(e.getEntity()).getTarget().equals(e.getEntity())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void entityDamageByEntityEvent(EntityDamageByEntityEvent e) {
        if (e.getDamager().getType() == EntityType.PLAYER && main.getCheckMap().containsKey(e.getDamager())
                && main.getCheckMap().get(e.getEntity()).getTarget().equals(e.getDamager())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void foodLevelChangeEvent(FoodLevelChangeEvent e) {
        if (main.getCheckMap().containsKey(e.getEntity())
                && main.getCheckMap().get(e.getEntity()).getTarget().equals(e.getEntity())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void playerDropItemEvent(PlayerDropItemEvent e) {
        if (main.getCheckMap().containsKey(e.getPlayer())
                && main.getCheckMap().get(e.getPlayer()).getTarget().equals(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void playerInteractEntityEvent(PlayerInteractEntityEvent e) {
        if (main.getCheckMap().containsKey(e.getPlayer())
                && main.getCheckMap().get(e.getPlayer()).getTarget().equals(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void playerInteractAtEntityEvent(PlayerInteractAtEntityEvent e) {
        if (main.getCheckMap().containsKey(e.getPlayer())
                && main.getCheckMap().get(e.getPlayer()).getTarget().equals(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void playerMoveEvent(PlayerMoveEvent e) {
        if (main.getCheckMap().containsKey(e.getPlayer())
                && main.getCheckMap().get(e.getPlayer()).getTarget().equals(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void playerTeleportEvent(PlayerTeleportEvent e) {
        if (main.getCheckMap().containsKey(e.getPlayer())
                && main.getCheckMap().get(e.getPlayer()).getTarget().equals(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent e) {
        if (main.getCheckMap().containsKey(e.getWhoClicked())
                && main.getCheckMap().get(e.getWhoClicked()).getTarget().equals(e.getWhoClicked())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void asyncPlayerChatEvent(AsyncPlayerChatEvent e) {
        if (main.getCheckMap().containsKey(e.getPlayer())) {
            Check c = main.getCheckMap().get(e.getPlayer());
            if (c.getTarget().equals(e.getPlayer())) {
                e.setCancelled(true);
                c.getTarget().sendMessage(String.format(main.getChat(), c.getTarget().getName(),
                        c.getPlayer().getName(), e.getMessage()));
                c.getPlayer().sendMessage(String.format(main.getChat(), c.getTarget().getName(),
                        c.getPlayer().getName(), e.getMessage()));
            } else if (c.getPlayer().equals(e.getPlayer())) {
                e.setCancelled(true);
                c.getPlayer().sendMessage(String.format(main.getChat(), c.getPlayer().getName(),
                        c.getTarget().getName(), e.getMessage()));
                c.getTarget().sendMessage(String.format(main.getChat(), c.getPlayer().getName(),
                        c.getTarget().getName(), e.getMessage()));
            }
        }
    }

    @EventHandler
    public void playerKickEvent(PlayerKickEvent e) {
        if (main.isPreventKick() && main.getCheckMap().containsKey(e.getPlayer())
                && main.getCheckMap().get(e.getPlayer()).getTarget().equals(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent e) {
        if (main.getCheckMap().containsKey(e.getPlayer())) {
            Check c = main.getCheckMap().get(e.getPlayer());
            if (c.getTarget().equals(e.getPlayer())) {
                c.end();
                main.getCheckMap().remove(e.getPlayer());
                c.getPlayer().sendMessage(String.format(main.getLeft(), c.getTarget().getName()));
                main.getServer().dispatchCommand(main.getServer().getConsoleSender(),
                        String.format(main.getQuitCommand(), c.getTarget().getName(), c.getPlayer().getName()));
            } else if (c.getPlayer().equals(e.getPlayer())) {
                c.end();
                main.getCheckMap().remove(e.getPlayer());
                c.getTarget().sendMessage(String.format(main.getLeftPlayer(), c.getPlayer().getName()));
            }
        }
    }

    @EventHandler
    public void blockFromToEvent(BlockFromToEvent e) {
        if (main.isEncapsulate() && !main.getCheckMap().isEmpty()) {
            Location l = e.getBlock().getLocation();
            Location tl = e.getToBlock().getLocation();
            for (Check c : main.getCheckMap().values()) {
                if (c.getBelow() != null
                        && (c.getBelow().getLocation().equals(l) || c.getBelow().getLocation().equals(tl))) {
                    e.setCancelled(true);
                    break;
                } else if (c.getFeet() != null
                        && (c.getFeet().getLocation().equals(l) || c.getFeet().getLocation().equals(tl))) {
                    e.setCancelled(true);
                    break;
                } else if (c.getHead() != null
                        && (c.getHead().getLocation().equals(l) || c.getHead().getLocation().equals(tl))) {
                    e.setCancelled(true);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void blockPistonExtendEvent(BlockPistonExtendEvent e) {
        if (main.isEncapsulate() && !main.getCheckMap().isEmpty()) {
            for (Block b : e.getBlocks()) {
                Location l = b.getLocation();
                Location rl = b.getRelative(e.getDirection()).getLocation();
                for (Check c : main.getCheckMap().values()) {
                    if (c.getBelow() != null
                            && (c.getBelow().getLocation().equals(l) || c.getBelow().getLocation().equals(rl))) {
                        e.setCancelled(true);
                        break;
                    } else if (c.getFeet() != null
                            && (c.getFeet().getLocation().equals(l) || c.getFeet().getLocation().equals(rl))) {
                        e.setCancelled(true);
                        break;
                    } else if (c.getHead() != null
                            && (c.getHead().getLocation().equals(l) || c.getHead().getLocation().equals(rl))) {
                        e.setCancelled(true);
                        break;
                    }
                }
            }
        }
    }

    @EventHandler
    public void blockPistonRetractEvent(BlockPistonRetractEvent e) {
        if (main.isEncapsulate() && !main.getCheckMap().isEmpty()) {
            for (Block b : e.getBlocks()) {
                Location l = b.getLocation();
                Location rl = b.getRelative(e.getDirection()).getLocation();
                for (Check c : main.getCheckMap().values()) {
                    if (c.getBelow() != null
                            && (c.getBelow().getLocation().equals(l) || c.getBelow().getLocation().equals(rl))) {
                        e.setCancelled(true);
                        break;
                    } else if (c.getFeet() != null
                            && (c.getFeet().getLocation().equals(l) || c.getFeet().getLocation().equals(rl))) {
                        e.setCancelled(true);
                        break;
                    } else if (c.getHead() != null
                            && (c.getHead().getLocation().equals(l) || c.getHead().getLocation().equals(rl))) {
                        e.setCancelled(true);
                        break;
                    }
                }
            }
        }
    }

    @EventHandler
    public void blockExplodeEvent(BlockExplodeEvent e) {
        if (main.isEncapsulate() && !main.getCheckMap().isEmpty()) {
            for (Block b : e.blockList()) {
                Location l = b.getLocation();
                for (Check c : main.getCheckMap().values()) {
                    if (c.getBelow() != null && c.getBelow().getLocation().equals(l)) {
                        e.setCancelled(true);
                        break;
                    } else if (c.getFeet() != null && c.getFeet().getLocation().equals(l)) {
                        e.setCancelled(true);
                        break;
                    } else if (c.getHead() != null && c.getHead().getLocation().equals(l)) {
                        e.setCancelled(true);
                        break;
                    }
                }
            }
        }
    }

    @EventHandler
    public void entityExplodeEvent(EntityExplodeEvent e) {
        if (main.isEncapsulate() && !main.getCheckMap().isEmpty()) {
            for (Block b : e.blockList()) {
                Location l = b.getLocation();
                for (Check c : main.getCheckMap().values()) {
                    if (c.getBelow() != null && c.getBelow().getLocation().equals(l)) {
                        e.setCancelled(true);
                        break;
                    } else if (c.getFeet() != null && c.getFeet().getLocation().equals(l)) {
                        e.setCancelled(true);
                        break;
                    } else if (c.getHead() != null && c.getHead().getLocation().equals(l)) {
                        e.setCancelled(true);
                        break;
                    }
                }
            }
        }
    }

    @EventHandler
    public void structureGrowEvent(StructureGrowEvent e) {
        if (main.isEncapsulate() && !main.getCheckMap().isEmpty()) {
            for (BlockState bs : e.getBlocks()) {
                Location l = bs.getLocation();
                for (Check c : main.getCheckMap().values()) {
                    if (c.getBelow() != null && c.getBelow().getLocation().equals(l)) {
                        e.setCancelled(true);
                        break;
                    } else if (c.getFeet() != null && c.getFeet().getLocation().equals(l)) {
                        e.setCancelled(true);
                        break;
                    } else if (c.getHead() != null && c.getHead().getLocation().equals(l)) {
                        e.setCancelled(true);
                        break;
                    }
                }
            }
        }
    }

    @EventHandler
    public void entityChangeBlockEvent(EntityChangeBlockEvent e) {
        if (main.isEncapsulate() && !main.getCheckMap().isEmpty()) {
            Location l = e.getBlock().getLocation();
            for (Check c : main.getCheckMap().values()) {
                if (c.getBelow() != null && c.getBelow().getLocation().equals(l)) {
                    e.setCancelled(true);
                    break;
                } else if (c.getFeet() != null && c.getFeet().getLocation().equals(l)) {
                    e.setCancelled(true);
                    break;
                } else if (c.getHead() != null && c.getHead().getLocation().equals(l)) {
                    e.setCancelled(true);
                    break;
                }
            }
        }
    }
}
