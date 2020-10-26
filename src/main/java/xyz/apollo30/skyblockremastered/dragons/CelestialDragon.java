package xyz.apollo30.skyblockremastered.dragons;

import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.items.Armor;
import xyz.apollo30.skyblockremastered.items.Pets;
import xyz.apollo30.skyblockremastered.items.Stones;
import xyz.apollo30.skyblockremastered.utils.Helper;

public class CelestialDragon extends Dragon {
    private final SkyblockRemastered plugin;
    CelestialDragon(SkyblockRemastered plugin){
        this.plugin = plugin;
        f = "Celestial";

    }
    @Override
    public void getItem() {
        Armor a = new Armor(plugin);
        Pets p = new Pets(plugin);
        Stones s = new Stones(plugin);
        int weight = (int) Math.random();
        int damage = (int) Math.random();
        int eyesPlaced = (int) Math.random();
        double chance = Math.random() * 100;

        if (weight <=0 || damage <= 0) { Masterclass.broadcastWorld(plr, Helper.getRank(plr) + " has 0 weight L");
        } else if (weight >= 550 && chance < 10) {
            Masterclass.broadcastWorld(plr, Helper.getRank(plr) + " &ehas obtained &6Empowered Stone&e!");
            plr.getInventory().addItem(s.EMPOWERED_STONE);
        } else if (weight >= 450) {
            // edrag chance
            if (chance < 0.3) {
                // got leg
                if (chance < 20) {
                    plr.getInventory().addItem(p.ENDER_DRAGON_LEGENDARY);
                    Masterclass.broadcastWorld(plr, Helper.getRank(plr) + " &ehas obtained &7[Lvl 1] &6Ender Dragon&e!");
                }
                // got epic
                else {
                    plr.getInventory().addItem(p.ENDER_DRAGON_EPIC);
                    Masterclass.broadcastWorld(plr, Helper.getRank(plr) + " &ehas obtained &7[Lvl 1] &5Ender Dragon&e!");
                }
            }
            else if (chance < 25) {
                plr.getInventory().addItem(s.DRAGON_CLAW);
                Masterclass.broadcastWorld(plr, Helper.getRank(plr) + " &ehas obtained &9Dragon Claw&e!");
            }
            else if (chance < 50) {
                plr.getInventory().addItem(s.DRAGON_SCALE);
                Masterclass.broadcastWorld(plr, Helper.getRank(plr) + " &ehas obtained &9Dragon Scale&e!");
            }
        }
        else if (weight >= 400 && chance < 50) {
            plr.getInventory().addItem(a.CELESTIAL_DRAGON_CHESTPLATE);
            Masterclass.broadcastPiece(plr, "Chestplate", f);
        }
        else if (weight >= 350) {
            plr.getInventory().addItem(a.CELESTIAL_DRAGON_LEGGINGS);
            Masterclass.broadcastPiece(plr, "Leggings", f);
        }
        else if (weight >= 325) {
            plr.getInventory().addItem(a.CELESTIAL_DRAGON_HELMET);
            Masterclass.broadcastPiece(plr, "Helmet", f);
        }
        else if (weight >= 300) {
            plr.getInventory().addItem(a.CELESTIAL_DRAGON_BOOTS);
            Masterclass.broadcastPiece(plr, "Boots", f);
        }
    }
}
