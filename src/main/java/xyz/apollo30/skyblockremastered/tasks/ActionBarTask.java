package xyz.apollo30.skyblockremastered.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.apollo30.skyblockremastered.guis.GUIs;
import xyz.apollo30.skyblockremastered.managers.PlayerManager;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.PacketUtils;
import xyz.apollo30.skyblockremastered.utils.Utils;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

public class ActionBarTask extends BukkitRunnable {

    private final SkyblockRemastered plugin;

    public ActionBarTask(SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player plr : Bukkit.getOnlinePlayers()) {

            PlayerObject po = PlayerManager.playerObjects.get(plr);
            if (po == null) return;

            String hp = Integer.toString(po.getHealth() + po.getExtraHealth() + po.getAbsorptionHealth());
            String maxhp = Integer.toString(po.getMaxHealth());

            String defense = Integer.toString(po.getDefense());

            String intell = Integer.toString(po.getIntelligence());
            String maxIntell = Integer.toString(po.getMaxIntelligence());

            String actionbar = (po.getAbsorptionHealth() > 0 ? "&e" : "&c") + hp + "/" + maxhp + GUIs.getUnicode("heart") + " HP" + "     &a" + defense + GUIs.getUnicode("defense") + " Defense" + "     &b" + intell + "/" + maxIntell + GUIs.getUnicode("intel") + " Intelligence";
            PacketUtils.sendMessage(plr, Utils.chat(actionbar));
        }
    }

}
