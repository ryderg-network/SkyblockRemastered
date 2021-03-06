package xyz.apollo30.skyblockremastered.commands;

import org.apache.commons.lang.WordUtils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.guis.Bank.BankMenu;
import xyz.apollo30.skyblockremastered.guis.GUIs;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Utils;
import xyz.apollo30.skyblockremastered.utils.ResponsesUtils;

import java.nio.channels.SelectionKey;

public class SkyblockRemastered implements CommandExecutor {

    private final xyz.apollo30.skyblockremastered.SkyblockRemastered plugin;

    public SkyblockRemastered(xyz.apollo30.skyblockremastered.SkyblockRemastered plugin) {
        this.plugin = plugin;
        plugin.getCommand("skyblockremastered").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {

            if (args[0].equalsIgnoreCase("announce")) {
                StringBuilder announcement = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    announcement.append(args[i]).append(" ");
                }
                Utils.broadCast(announcement.toString());
            } else if (args[0].equalsIgnoreCase("plrannounce")) {
                String uuid = Utils.getUuid(args[1]);
                Player plr = Bukkit.getPlayer(uuid);
                if (plr == null) return false;
                StringBuilder announcement = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    announcement.append(args[i]).append(" ");
                }
                plr.sendMessage(Utils.chat(announcement.toString()));
            }
            return false;
        }

        Player plr = (Player) sender;

        PlayerObject po = xyz.apollo30.skyblockremastered.SkyblockRemastered.playerManager.getPlayerData(plr);
        if (!plr.isOp()) return false;
        String prefix = "&6Skyblock &8» &7";

        if (args[0].equalsIgnoreCase("set")) {
            if (args[1].equalsIgnoreCase("speed")) {

                if (!plr.isOp() && Integer.parseInt(args[2]) > 500) {
                    plr.sendMessage(Utils.chat("You cannot go over 500"));
                    return true;
                }
                po.setBaseSpeed(Integer.parseInt(args[2]));
                plr.sendMessage(Utils.chat(prefix + "Speed set to " + args[2]));

            } else if (args[1].equalsIgnoreCase("health") || args[1].equalsIgnoreCase("hp")) {

                if (!plr.isOp() && Integer.parseInt(args[2]) > 10000) {
                    plr.sendMessage(Utils.chat("You cannot go over 10000"));
                    return true;
                }
                po.setBaseHealth(Integer.parseInt(args[2]));
                po.resetHealth();
                plr.sendMessage(Utils.chat(prefix + "Health set to " + args[2]));

            } else if (args[1].equalsIgnoreCase("defense") || args[1].equalsIgnoreCase("def")) {

                if (!plr.isOp() && Integer.parseInt(args[2]) > 10000) {
                    plr.sendMessage(Utils.chat("You cannot go over 10000"));
                    return true;
                }
                po.setBaseDefense(Integer.parseInt(args[2]));
                plr.sendMessage(Utils.chat(prefix + "Defense set to " + args[2]));

            } else if (args[1].equalsIgnoreCase("cd") || args[1].equalsIgnoreCase("critdamage")) {

                if (!plr.isOp() && Integer.parseInt(args[2]) > 10000) {
                    plr.sendMessage(Utils.chat("You cannot go over 10000"));
                    return true;
                }
                po.setBaseCritDamage(Integer.parseInt(args[2]));
                plr.sendMessage(Utils.chat(prefix + "Crit Damage set to " + args[2]));

            } else if (args[1].equalsIgnoreCase("cc") || args[1].equalsIgnoreCase("critchance")) {

                if (!plr.isOp() && Integer.parseInt(args[2]) > 100) {
                    plr.sendMessage(Utils.chat("You cannot go over 100"));
                    return true;
                }
                po.setBaseCritChance(Integer.parseInt(args[2]));
                plr.sendMessage(Utils.chat(prefix + "Crit Chance set to " + args[2]));

            } else if (args[1].equalsIgnoreCase("intel") || args[1].equalsIgnoreCase("intelligence") || args[1].equalsIgnoreCase("mana")) {

                if (!plr.isOp() && Integer.parseInt(args[2]) > 10000) {
                    plr.sendMessage(Utils.chat("You cannot go over 10000"));
                    return true;
                }
                po.setBaseIntelligence(Integer.parseInt(args[2]));
                po.resetIntelligence();
                plr.sendMessage(Utils.chat(prefix + "Intelligence set to " + args[2]));

            } else if (args[1].equalsIgnoreCase("mf") || args[1].equalsIgnoreCase("magicfind")) {

                if (!plr.isOp() && Integer.parseInt(args[2]) > 200) {
                    plr.sendMessage(Utils.chat("You cannot go over 200"));
                    return true;
                }
                po.setBaseMagicFind(Integer.parseInt(args[2]));
                plr.sendMessage(Utils.chat(prefix + "Magic Find set to " + args[2]));

            } else if (args[1].equalsIgnoreCase("str") || args[1].equalsIgnoreCase("strength")) {

                if (!plr.isOp() && Integer.parseInt(args[2]) > 10000) {
                    plr.sendMessage(Utils.chat("You cannot go over 10000"));
                    return true;
                }
                po.setBaseStrength(Integer.parseInt(args[2]));
                plr.sendMessage(Utils.chat(prefix + "Strength set to " + args[2]));

            } else if (args[1].equalsIgnoreCase("absorption") || args[1].equalsIgnoreCase("abs")) {

                if (!plr.isOp() && Integer.parseInt(args[2]) > 10000) {
                    plr.sendMessage(Utils.chat("You cannot go over 10000"));
                    return true;
                }
                po.setAbsorptionHealth(Integer.parseInt(args[2]));
                plr.sendMessage(Utils.chat(prefix + "Absorption set to " + args[2]));

            } else if (args[1].equalsIgnoreCase("purse")) {
                if (!plr.isOp()) return true;
                po.setPurse(Double.parseDouble(args[2]));
                plr.sendMessage(Utils.chat(prefix + "Purse set to " + args[2]));
            } else if (args[1].equalsIgnoreCase("bank")) {
                if (!plr.isOp()) return true;
                po.setPurse(Double.parseDouble(args[2]));
                plr.sendMessage(Utils.chat(prefix + "Bank set to " + args[2]));
            } else if (args[1].equalsIgnoreCase("bits")) {
                if (!plr.isOp()) return true;
                po.setBits(Double.parseDouble(args[2]));
                plr.sendMessage(Utils.chat(prefix + "Bits set to " + args[2]));
            } else if (args[1].equalsIgnoreCase("addcoins")) {
                if (!plr.isOp()) return true;
                po.setCoins_gained(Double.parseDouble(args[2]));
                plr.sendMessage(Utils.chat(prefix + "Added " + args[2] + " coins."));
            } else if (args[1].equalsIgnoreCase("dragon")) {
                if (!plr.isOp()) return true;
                xyz.apollo30.skyblockremastered.SkyblockRemastered.so.setRiggedDragon(args[2].toUpperCase());
                plr.sendMessage(Utils.chat("&aRigged the next dragon to spawn as a &d" + WordUtils.capitalizeFully(args[2]) + "&a dragon!"));
            }
        } else if (args[0].equalsIgnoreCase("pvp")) {
            if (!plr.isOp()) return false;
            xyz.apollo30.skyblockremastered.SkyblockRemastered.so.setPvp(!xyz.apollo30.skyblockremastered.SkyblockRemastered.so.isPvp());
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 100F, 1F);
                player.sendMessage(Utils.chat("&7PVP has been " + (xyz.apollo30.skyblockremastered.SkyblockRemastered.so.isPvp() ? "&aEnabled&7!" : "&cDisabled&7!")));
            }
        } else if (args[0].equalsIgnoreCase("ip")) {
            if (!plr.isOp()) return false;
            xyz.apollo30.skyblockremastered.SkyblockRemastered.so.setHypixelip(!xyz.apollo30.skyblockremastered.SkyblockRemastered.so.isHypixelip());
            plr.sendMessage(Utils.chat("IP has been changed!"));
        } else if (args[0].equalsIgnoreCase("bank")) {
            new BankMenu(xyz.apollo30.skyblockremastered.SkyblockRemastered.getMenuUtility(plr), plr).open();
        } else if (args[0].equalsIgnoreCase("dialogs")) {
            if (args[1].equalsIgnoreCase("clerk")) {
                ResponsesUtils.villagerDialog(plr, plugin, "&e[NPC] Clerk Seraphine:", "&fOh hello! You're here for the mayor elections?", "&fWell, this server ain't pay to win, &c&lGET OUTTA HERE!");
            } else if (args[1].equalsIgnoreCase("hub")) {
                ResponsesUtils.witherDialog(plr, plugin, "&e[NPC] Apollo30:", "&fWelcome to the server! In here this server is just a remake of Hypixel Skyblock!", "&fAs of now, this server is still in beta mode.", "&fWhen the remake is done, most things in the server will be changed.", "&fThis is so I do not get sued.");
            }
        } else if (args[0].equalsIgnoreCase("template-island")) {
            if (!plr.isOp()) return false;
            World island = Bukkit.getServer().createWorld(new WorldCreator("private_island_template"));
            Location loc = new Location(island, island.getSpawnLocation().getX(), island.getSpawnLocation().getY(), island.getSpawnLocation().getZ());
            plr.teleport(loc);
        } else if (args[0].equalsIgnoreCase("hub2")) {
            if (!plr.isOp()) return false;
            World island = Bukkit.getServer().createWorld(new WorldCreator("hub2"));
            Location loc = new Location(island, island.getSpawnLocation().getX(), island.getSpawnLocation().getY(), island.getSpawnLocation().getZ());
            plr.teleport(loc);
        }

        return false;
    }
}
