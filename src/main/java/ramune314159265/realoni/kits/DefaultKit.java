package ramune314159265.realoni.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DefaultKit extends Kit {
	@Override
	public String getName() {
		return "デフォルト";
	}

	@Override
	public void setItems(Player player) {
		Inventory inv = player.getInventory();
		inv.setItem(0, new ItemStack(Material.STONE_SWORD));
		inv.setItem(1, new ItemStack(Material.STONE_PICKAXE));
		inv.setItem(2, new ItemStack(Material.STONE_SHOVEL));
		inv.setItem(3, new ItemStack(Material.STONE_AXE));
		inv.setItem(4, new ItemStack(Material.COOKIE, 10));
		inv.setItem(5, new ItemStack(Material.TORCH, 64));
	}
}
