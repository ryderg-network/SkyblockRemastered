package xyz.apollo30.skyblockremastered.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ScoreboardTask extends BukkitRunnable {

    private final SkyblockRemastered plugin;
    private String title = "&e&lSKYBLOCK";

    public ScoreboardTask(SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {

        for (Player plr : Bukkit.getOnlinePlayers()) {

            Scoreboard sb = plugin.getServer().getScoreboardManager().getNewScoreboard();
            Objective obj = sb.registerNewObjective("dummy", "dummy");
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            obj.setDisplayName(Utils.chat(title));

            PlayerObject po = plugin.playerManager.playerObjects.get(plr);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            DateTimeFormatter d = DateTimeFormatter.ofPattern("dd");
            LocalDateTime now = LocalDateTime.now();

            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

            String[] names = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

            String coins_gained = po.getCoins_gained() > 0 ? "&e(+" + po.getCoins_gained() + ")" : "";

            List<String> contents = new ArrayList<>();
            contents.add("&7" + dtf.format(now));
            contents.add("&f&7 ");
            contents.add(" &f" + names[dayOfWeek - 1] + " " + d.format(now) + Utils.getDayOfMonthSuffix(Integer.parseInt(d.format(now))));
            contents.add(" &7⋄ " + Utils.getLocation(plr));
            contents.add("&2&8 ");
            contents.add("&fPurse: &6" + String.format("%,.0f", po.getPurse()) + coins_gained);
            contents.add("&fGems: &a" + String.format("%,.0f", po.getGems()));
            contents.add("&2&5");
            contents.add("&eplay.apollo30.xyz");

            int cycle = contents.size();
            for (String list : contents) {
                obj.getScore(Utils.chat(list)).setScore(cycle);
                cycle--;
            }
            plr.setScoreboard(sb);

            po.setCoins_gained(0);

        }


    }

}
