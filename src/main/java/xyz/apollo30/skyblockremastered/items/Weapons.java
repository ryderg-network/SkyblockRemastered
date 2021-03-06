package xyz.apollo30.skyblockremastered.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.guis.GUIHelper;
import xyz.apollo30.skyblockremastered.guis.GUIs;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.NMSUtil;

import java.util.UUID;

public class Weapons {

    private final SkyblockRemastered plugin;

    public Weapons(SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    public static ItemStack ASPECT_OF_THE_END = GUIHelper.addLore(new ItemStack(Material.DIAMOND_SWORD), "&9Aspect of The End", "&7Damage: &c+110", "&7Strength: &c+112", "&7Crit Chance: &c+9%", "&7Crit Damage: &c+15%", "&7Bonus Attack Speed: &c+5%", " ", "&7Intelligence: &a+12", " ", "&6Item Ability: Instant Transmission &a&lRIGHT CLICK", "&7Teleport &a8 blocks&7 ahead of", "&7you and again &a+50 &f" + GUIs.getUnicode("speed") + " Speed", "&7for &a3 seconds&7.", "&8Mana Cost: &b50", " ", "&9&lRARE");
    public static ItemStack ASPECT_OF_THE_DRAGONS = GUIHelper.addLore(new ItemStack(Material.DIAMOND_SWORD), "&6&6Aspect of the Dragons", "&7Damage: &c+225", "&7Strength: &c+100", " ", "&6Item Ability: Dragon Rage &e&lRIGHT CLICK", "&7All Monsters in front of you", "&7take &a1,050 &7damage. Hit", "&7monsters take large knockback.", "&8Mana Cost: &3100", "&8Cooldown: &a5s", " ", "&8This item can be reforged!", "&6&lLEGENDARY SWORD");
    public static ItemStack EDIBLE_MACE = NMSUtil.addString(GUIHelper.addLore(new ItemStack(Material.MUTTON), "&9Edible Mace", "&7Damage: &c+125", "&7Strength: &c+25", " ", "&6Item Ability: ME SMASH HEAD &e&lRIGHT CLICK", "&7Your next attack deals &cdouble", "&cdamage &7and weakens animals,", "&7making them deal&c-35% &7damage", "&7for &a30 &7seconds.", "&8Debuff doesn't stack.", "&8Mana Cost: &3100", " ", "&4☠ &cRequires &5Wolf Slayer 5", "&8This item can be reforged!", "&9&lRARE SWORD"), "uuid", UUID.randomUUID().toString());
    public static ItemStack POOCH_SWORD = GUIHelper.addLore(new ItemStack(Material.GOLD_SWORD), "&6Pooch Sword", "&7Damage: &c+120", "&7Strength: &c+20", "&7Crit Damage: &c+50%", " ", "&7Speed: &a+5", " ", "&7Deal &c+1 Damage &7per &c50 max ❤&7.", "&7Receive &a-20% &7damage from wolves.", "&7Gain &c+150❁ Strength &7against wolves.", " ", "&8This item can be reforged!", "&4☠ &cRequires &5Wolf Slayer 6", "&6&lLEGENDARY SWORD");
    public static ItemStack SHAMAN_SWORD = GUIHelper.addLore(new ItemStack(Material.IRON_SWORD), "&5Shaman Sword", "&7Damage: &c+100", "&7Strength: &c+20", " ", "&7Speed: &a+5", " ", "&7Deal &c+1 Damage &7per &c50 max ❤&7.", "&7Receive &a-20% &7damage from wolves.", " ", "&8This item can be reforged!", "&4☠ &cRequires &5Wolf Slayer 3", "&5&lEPIC SWORD");
    public static ItemStack ROGUE_SWORD = GUIHelper.addLore(new ItemStack(Material.GOLD_SWORD), "&fRogue Sword", "&7Damage: &c+20", "&6Item Ability: Speed Boost &e&lRIGHT CLICK", "&7Increases your movement &f✦", "&fSpeed&7 by &a+20&7 for &a30", "&7seconds.", "&8Mana Cost: &350", "&8Half speed if already active.", " ", "&8This item can be reforged!", "&f&lCOMMON SWORD");
    public static ItemStack LIGHTNING_ROD = GUIHelper.addLore(new ItemStack(Material.BLAZE_ROD), "&6Lightning Rod", "&7Damage: &c+170", "&7Strength: &c+100", "&7Crit Damage: &c+120%", " ", "&6Item Ability: Strike 'em! &e&lRIGHT CLICK", "&7Strikes up to &a5 &7nearby enemies", "&7for double your final damage!", "&8Cooldown: &a60s", " ", "&6&lLEGENDARY SWORD");
    public static ItemStack MAGE_BEAM = GUIHelper.addLore(new ItemStack(Material.IRON_BARDING), "&6Mage Beam", "&7Damage: &c+50", "&7Strength: &c+250", "&7Bonus Attack Speed: &c+100%", " ", "&7Intelligence: &a+250", " ", "&6Item Ability: Beam &e&lRIGHT CLICK", "&7All your melee weapon attacks become ranged.", "&7Those attacks deal &c40% &7of their melee damage", "&7also increased by &a0.1 * intel", " ", "&8Mana Cost: &325", "&8Cooldown: &a0.5s", " ", "&8This item can be reforged!", "&6&lLEGENDARY");
    public static ItemStack WALKING_CANE = GUIHelper.addLore(new ItemStack(Material.STICK), "&5Walking Cane", "&7Damage: &c+200", "&7Strength: &c+40", "&7Crit Damage: &c+35", "&7Crit Chance: &c+2", " ", "", "&6Item Ability: Oldie but Goodie! &eRIGHT CLICK", "&7Takes &a1/4 &7of your speed", "&7for &a30 seconds &7and gives ", "&7you &a1/4 &7of your &c❤ Health&7 back.", " ", "&8This item can be reforged!", "&5&lEPIC SWORD");

}
