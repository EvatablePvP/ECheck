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

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Maps;

import lombok.Getter;
import ru.evatablepvp.echeck.command.CommandECheck;
import ru.evatablepvp.echeck.command.CommandCheck;

/**
 * Основной класс плагина ECheck
 * 
 * @author iEatMeat
 */
@Getter
public class Main extends JavaPlugin {
    private final Map<Player, Check> checkMap = Maps.newHashMap();
    private String banCommand, admireCommand, timeoutCommand, quitCommand;
    private int time, notifyTime;
    private boolean preventKick, spectator, encapsulate;
    private Material encapsulateMaterial;
    private String callTitle, callSubtitle, callActionbar;
    private int callFadeIn, callStay, callFadeOut, callRepeat;
    private String waitTitle, waitSubtitle, waitActionbar;
    private int waitFadeIn, waitStay, waitFadeOut, waitRepeat;
    private String releaseTitle, releaseSubtitle, releaseActionbar;
    private int releaseFadeIn, releaseStay, releaseFadeOut;
    private String reminder, left, leftPlayer, timeout, chat, commandInvalid, commandPlayer, commandHelp;
    private String commandCallUsage, commandCallOffline, commandCallSelf, commandCallExempt, commandCallAlreadyYou,
            commandCallAlreadyPlayer, commandCallSuccess, commandCallSuccessPlayer;
    private String commandStoptimerNotChecking, commandStoptimerAlready, commandStoptimerSuccess,
            commandStoptimerSuccessPlayer;
    private String commandReleaseNotChecking, commandReleaseSuccess, commandReleaseSuccessPlayer;
    private String commandBanNotChecking, commandBanSuccess;
    private String commandAdmireNotChecking, commandAdmireSuccess;

    @Override
    public void onEnable() {
        loadConfig();
        getCommand("echeck").setExecutor(new CommandECheck(this));
        getCommand("check").setExecutor(new CommandCheck(this));
        getServer().getPluginManager().registerEvents(new CheckListener(this), this);
    }

    @Override
    public void onDisable() {
        for (Check c : checkMap.values()) {
            c.end();
        }
        checkMap.clear();
    }

    public void loadConfig() {
        for (Check c : checkMap.values()) {
            c.end();
        }
        checkMap.clear();
        saveDefaultConfig();
        reloadConfig();
        banCommand = getConfig().getString("command.ban");
        admireCommand = getConfig().getString("command.admire");
        timeoutCommand = getConfig().getString("command.timeout");
        quitCommand = getConfig().getString("command.quit");
        time = getConfig().getInt("time");
        notifyTime = getConfig().getInt("notifyTime");
        preventKick = getConfig().getBoolean("preventKick");
        spectator = getConfig().getBoolean("spectator");
        encapsulate = getConfig().getBoolean("encapsulate");
        encapsulateMaterial = Material.matchMaterial(getConfig().getString("encapsulateMaterial"));
        callTitle = getConfig().getString("title.call.title").replace('&', '\u00a7');
        callSubtitle = getConfig().getString("title.call.subtitle").replace('&', '\u00a7');
        callActionbar = getConfig().getString("title.call.actionbar").replace('&', '\u00a7');
        callFadeIn = getConfig().getInt("title.call.fadeIn");
        callStay = getConfig().getInt("title.call.stay");
        callFadeOut = getConfig().getInt("title.call.fadeOut");
        callRepeat = getConfig().getInt("title.call.repeat");
        waitTitle = getConfig().getString("title.wait.title").replace('&', '\u00a7');
        waitSubtitle = getConfig().getString("title.wait.subtitle").replace('&', '\u00a7');
        waitActionbar = getConfig().getString("title.wait.actionbar").replace('&', '\u00a7');
        waitFadeIn = getConfig().getInt("title.wait.fadeIn");
        waitStay = getConfig().getInt("title.wait.stay");
        waitFadeOut = getConfig().getInt("title.wait.fadeOut");
        waitRepeat = getConfig().getInt("title.wait.repeat");
        releaseTitle = getConfig().getString("title.release.title").replace('&', '\u00a7');
        releaseSubtitle = getConfig().getString("title.release.subtitle").replace('&', '\u00a7');
        releaseActionbar = getConfig().getString("title.release.actionbar").replace('&', '\u00a7');
        releaseFadeIn = getConfig().getInt("title.release.fadeIn");
        releaseStay = getConfig().getInt("title.release.stay");
        releaseFadeOut = getConfig().getInt("title.release.fadeOut");
        reminder = getConfig().getString("message.reminder").replace('&', '\u00a7');
        left = getConfig().getString("message.left").replace('&', '\u00a7');
        leftPlayer = getConfig().getString("message.leftPlayer").replace('&', '\u00a7');
        timeout = getConfig().getString("message.timeout").replace('&', '\u00a7');
        chat = getConfig().getString("message.chat").replace('&', '\u00a7');
        commandInvalid = getConfig().getString("message.command.invalid").replace('&', '\u00a7');
        commandPlayer = getConfig().getString("message.command.player").replace('&', '\u00a7');
        commandHelp = getConfig().getString("message.command.help").replace('&', '\u00a7');
        commandCallUsage = getConfig().getString("message.command.call.usage").replace('&', '\u00a7');
        commandCallOffline = getConfig().getString("message.command.call.offline").replace('&', '\u00a7');
        commandCallSelf = getConfig().getString("message.command.call.self").replace('&', '\u00a7');
        commandCallExempt = getConfig().getString("message.command.call.exempt").replace('&', '\u00a7');
        commandCallAlreadyYou = getConfig().getString("message.command.call.alreadyYou").replace('&', '\u00a7');
        commandCallAlreadyPlayer = getConfig().getString("message.command.call.alreadyPlayer").replace('&', '\u00a7');
        commandCallSuccess = getConfig().getString("message.command.call.success").replace('&', '\u00a7');
        commandCallSuccessPlayer = getConfig().getString("message.command.call.successPlayer").replace('&', '\u00a7');
        commandStoptimerNotChecking = getConfig().getString("message.command.stoptimer.notChecking").replace('&',
                '\u00a7');
        commandStoptimerAlready = getConfig().getString("message.command.stoptimer.already").replace('&', '\u00a7');
        commandStoptimerSuccess = getConfig().getString("message.command.stoptimer.success").replace('&', '\u00a7');
        commandStoptimerSuccessPlayer = getConfig().getString("message.command.stoptimer.successPlayer").replace('&',
                '\u00a7');
        commandReleaseNotChecking = getConfig().getString("message.command.release.notChecking").replace('&', '\u00a7');
        commandReleaseSuccess = getConfig().getString("message.command.release.success").replace('&', '\u00a7');
        commandReleaseSuccessPlayer = getConfig().getString("message.command.release.successPlayer").replace('&',
                '\u00a7');
        commandBanNotChecking = getConfig().getString("message.command.ban.notChecking").replace('&', '\u00a7');
        commandBanSuccess = getConfig().getString("message.command.ban.success").replace('&', '\u00a7');
        commandAdmireNotChecking = getConfig().getString("message.command.admire.notChecking").replace('&', '\u00a7');
        commandAdmireSuccess = getConfig().getString("message.command.admire.success").replace('&', '\u00a7');
    }
}
