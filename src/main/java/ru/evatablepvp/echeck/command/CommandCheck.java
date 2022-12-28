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

package ru.evatablepvp.echeck.command;

import java.util.Arrays;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.base.Joiner;

import lombok.RequiredArgsConstructor;
import ru.evatablepvp.echeck.Check;
import ru.evatablepvp.echeck.Main;

/**
 * Класс, отвечающий за обработку команды /check
 * 
 * @author iEatMeat
 */
@RequiredArgsConstructor
public class CommandCheck implements CommandExecutor {
    private final Main main;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(main.getCommandPlayer());
            return true;
        }
        Player p = (Player) sender;
        if (args.length == 0) {
            p.sendMessage(main.getCommandInvalid());
            return true;
        }
        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "help": {
                p.sendMessage(main.getCommandHelp());
                return true;
            }
            case "call": {
                if (args.length == 1) {
                    p.sendMessage(main.getCommandCallUsage());
                    return true;
                }
                Player t = Bukkit.getPlayer(args[1]);
                if (t == null) {
                    p.sendMessage(main.getCommandCallOffline());
                    return true;
                }
                if (p.equals(t)) {
                    p.sendMessage(main.getCommandCallSelf());
                    return true;
                }
                if (t.hasPermission("echeck.exempt")) {
                    p.sendMessage(String.format(main.getCommandCallExempt(), t.getName()));
                    return true;
                }
                if (main.getCheckMap().containsKey(p)) {
                    Check c = main.getCheckMap().get(p);
                    p.sendMessage(String.format(main.getCommandCallAlreadyYou(), c.getTarget().getName()));
                    return true;
                }
                if (main.getCheckMap().containsKey(t)) {
                    Check c = main.getCheckMap().get(t);
                    p.sendMessage(String.format(main.getCommandCallAlreadyPlayer(), c.getTarget().getName(),
                            c.getPlayer().getName()));
                    return true;
                }
                Check c;
                if (main.isEncapsulate()) {
                    Block f = t.getLocation().getBlock();
                    Block b = f.getRelative(BlockFace.DOWN);
                    Block h = f.getRelative(BlockFace.UP);
                    if (main.isSpectator()) {
                        c = new Check(p, t, b.getState(), f.getState(), h.getState(), t.getGameMode());
                        t.setGameMode(GameMode.SPECTATOR);
                    } else {
                        c = new Check(p, t, b.getState(), f.getState(), h.getState(), null);
                    }
                    b.setType(main.getEncapsulateMaterial(), false);
                    f.setType(Material.AIR, false);
                    h.setType(Material.AIR, false);
                } else if (main.isSpectator()) {
                    c = new Check(p, t, null, null, null, t.getGameMode());
                    t.setGameMode(GameMode.SPECTATOR);
                } else {
                    c = new Check(p, t, null, null, null, null);
                }
                p.sendMessage(String.format(main.getCommandCallSuccess(), t.getName()));
                t.sendMessage(String.format(main.getCommandCallSuccessPlayer(), p.getName()));
                c.start(main);
                main.getCheckMap().put(p, c);
                main.getCheckMap().put(t, c);
                return true;
            }
            case "stoptimer": {
                if (!main.getCheckMap().containsKey(p)) {
                    p.sendMessage(main.getCommandStoptimerNotChecking());
                    return true;
                }
                Check c = main.getCheckMap().get(p);
                if (c.getTime() <= 0) {
                    p.sendMessage(String.format(main.getCommandStoptimerAlready(), c.getTarget().getName()));
                    return true;
                }
                c.setTime(0);
                c.getPlayer().sendMessage(String.format(main.getCommandStoptimerSuccess(), c.getTarget().getName()));
                c.getTarget()
                        .sendMessage(String.format(main.getCommandStoptimerSuccessPlayer(), c.getPlayer().getName()));
                return true;
            }
            case "release": {
                if (!main.getCheckMap().containsKey(p)) {
                    p.sendMessage(main.getCommandReleaseNotChecking());
                    return true;
                }
                Check c = main.getCheckMap().get(p);
                c.end();
                c.getPlayer().sendMessage(String.format(main.getCommandReleaseSuccess(), c.getTarget().getName()));
                c.getTarget()
                        .sendMessage(String.format(main.getCommandReleaseSuccessPlayer(), c.getPlayer().getName()));
                main.getCheckMap().remove(c.getPlayer());
                main.getCheckMap().remove(c.getTarget());
                return true;
            }
            case "ban": {
                if (!main.getCheckMap().containsKey(p)) {
                    p.sendMessage(main.getCommandBanNotChecking());
                    return true;
                }
                String co = Joiner.on(' ').join(Arrays.copyOfRange(args, 1, args.length));
                Check c = main.getCheckMap().get(p);
                c.end();
                c.getPlayer().sendMessage(String.format(main.getCommandBanSuccess(), c.getTarget().getName()));
                main.getCheckMap().remove(c.getPlayer());
                main.getCheckMap().remove(c.getTarget());
                main.getServer().dispatchCommand(main.getServer().getConsoleSender(),
                        String.format(main.getBanCommand(), c.getTarget().getName(), c.getPlayer().getName(), co));
                return true;
            }
            case "admire": {
                if (!main.getCheckMap().containsKey(p)) {
                    p.sendMessage(main.getCommandAdmireNotChecking());
                    return true;
                }
                String co = Joiner.on(' ').join(Arrays.copyOfRange(args, 1, args.length));
                Check c = main.getCheckMap().get(p);
                c.end();
                c.getPlayer().sendMessage(String.format(main.getCommandAdmireSuccess(), c.getTarget().getName()));
                main.getCheckMap().remove(c.getPlayer());
                main.getCheckMap().remove(c.getTarget());
                main.getServer().dispatchCommand(main.getServer().getConsoleSender(),
                        String.format(main.getAdmireCommand(), c.getTarget().getName(), c.getPlayer().getName(), co));
                return true;
            }
            default: {
                p.sendMessage(main.getCommandInvalid());
                return true;
            }
        }
    }
}
