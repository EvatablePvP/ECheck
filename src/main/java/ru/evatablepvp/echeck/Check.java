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

import org.bukkit.GameMode;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * Инстанция проверки
 * 
 * @author iEatMeat
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Check {
    final Player player;
    final Player target;
    final BlockState below;
    final BlockState feet;
    final BlockState head;
    final GameMode previousGamemode;
    int time;

    /**
     * Начинает проверку, устанавливая {@link #time} на время проверки и запуская
     * таск
     * 
     * @param m Главный класс плагина
     */
    public void start(Main m) {
        setTime(m.getTime());
        new BukkitRunnable() {
            private int seconds;

            @Override
            public void run() {
                if (getTime() < 0) {
                    cancel();
                    return;
                }
                if (getTime() == 0) {
                    if (seconds % m.getWaitRepeat() == 0) {
                        getTarget().sendTitle(String.format(m.getWaitTitle(), getPlayer().getName()),
                                String.format(m.getWaitSubtitle(), getPlayer().getName()), m.getWaitFadeIn(),
                                m.getWaitStay(), m.getWaitFadeOut());
                        getTarget().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent
                                .fromLegacyText(String.format(m.getWaitActionbar(), getPlayer().getName())));
                    }
                    seconds++;
                    return;
                }
                setTime(getTime() - 1);
                if (getTime() <= 0) {
                    cancel();
                    end();
                    getPlayer().sendMessage(String.format(m.getTimeout(), getTarget().getName()));
                    m.getCheckMap().remove(getPlayer());
                    m.getCheckMap().remove(getTarget());
                    m.getServer().dispatchCommand(m.getServer().getConsoleSender(),
                            String.format(m.getTimeoutCommand(), getTarget().getName(), getPlayer().getName()));
                    return;
                }
                if (seconds % m.getCallRepeat() == 0) {
                    getTarget().sendTitle(String.format(m.getCallTitle(), getPlayer().getName(), getTime()),
                            String.format(m.getCallSubtitle(), getPlayer().getName(), getTime()), m.getCallFadeIn(),
                            m.getCallStay(), m.getCallFadeOut());
                    getTarget().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent
                            .fromLegacyText(String.format(m.getCallActionbar(), getPlayer().getName(), getTime())));
                }
                if (seconds % m.getNotifyTime() == 0) {
                    getTarget().sendMessage(String.format(m.getReminder(), getPlayer().getName(), getTime()));
                }
                seconds++;
            }
        }.runTaskTimer(m, 20, 20);
    }

    /**
     * Заканчивает проверку, возвращая все назад
     */
    public void end() {
        if (getTime() >= 0) {
            setTime(-1);
            if (getBelow() != null) {
                getBelow().update(true, false);
            }
            if (getFeet() != null) {
                getFeet().update(true, false);
            }
            if (getHead() != null) {
                getHead().update(true, false);
            }
            if (getPreviousGamemode() != null) {
                getTarget().setGameMode(getPreviousGamemode());
            }
        }
    }
}
