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

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.google.common.base.Joiner;

import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import ru.evatablepvp.echeck.Main;

/**
 * Класс, отвечающий за обработку команды /echeck
 * 
 * @author iEatMeat
 */
@RequiredArgsConstructor
public class CommandECheck implements CommandExecutor {
    private final Main main;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1 && args[0].equalsIgnoreCase("reload") && sender.hasPermission("echeck.reload")) {
            main.loadConfig();
            sender.sendMessage(ChatColor.AQUA + "Конфиг перезагружен!");
            return true;
        }
        String n = main.getDescription().getName();
        String v = main.getDescription().getVersion();
        String a = Joiner.on(", ").join(main.getDescription().getAuthors());
        sender.sendMessage(ChatColor.RED + n + ChatColor.AQUA + " версии " + ChatColor.RED + v + ChatColor.AQUA
                + ". Создатели: " + ChatColor.RED + a);
        if (sender.hasPermission("echeck.reload")) {
            sender.sendMessage(ChatColor.AQUA + "Перезагрузить конфиг: " + ChatColor.RED + "/echeck reload");
        }
        return true;
    }
}
