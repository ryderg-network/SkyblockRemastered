package xyz.apollo30.skyblockremastered.guis.ProfileViewer;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.apollo30.skyblockremastered.guis.constructors.Menu;
import xyz.apollo30.skyblockremastered.guis.constructors.MenuUtility;
import xyz.apollo30.skyblockremastered.managers.PlayerManager;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class EnderChestViewer extends Menu {

    private final Player plr;

    public EnderChestViewer(MenuUtility menuUtility, Player plr) {
        super(menuUtility);
        this.plr = plr;
    }

    @Override
    public String getMenuName() {
        return plr.getName() + "'s Enderchest";
    }

    @Override
    public int getSlots() {
        PlayerObject po = PlayerManager.playerObjects.get(plr);
        Inventory enderChest = Helper.stringToInventory(po.getEnderChest());
        if (enderChest == null) return 27;
        else return enderChest.getSize();
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

    }

    @Override
    public void setMenuItems() {

        PlayerObject po = PlayerManager.playerObjects.get(plr);
        Inventory enderChest = plr.getEnderChest(); //Helper.stringToInventory(po.getEnderChest());
        if (enderChest == null) {
            for (int i = 0; i < 27; i++) {
                ItemStack glass1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
                ItemMeta meta = glass1.getItemMeta();
                meta.setDisplayName(Utils.chat("&cERROR"));
                glass1.setItemMeta(meta);

                ItemStack glass2 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 1);
                ItemMeta meta1 = glass2.getItemMeta();
                meta1.setDisplayName(Utils.chat("&cERROR"));
                glass2.setItemMeta(meta1);
                if (i % 2 == 0)
                    inv.setItem(i, glass1);
                else
                    inv.setItem(i, glass2);
            }
        } else {
            for (int i = 0; i < enderChest.getSize(); i++) {
                ItemStack item = enderChest.getItem(i);
                if (item == null) break;
                else inv.setItem(i, item);
            }
        }

    }
}
