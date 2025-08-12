package ramune314159265.realoni.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ArcherKit extends Kit {
	@Override
	public String getName() {
		return "アーチャーキット";
	}

	@Override
	public void setItems(Player player) {
		Inventory inv = player.getInventory();
		inv.setItem(0, new ItemStack(Material.WOODEN_SWORD));
		inv.setItem(1, new ItemStack(Material.WOODEN_PICKAXE));
		inv.setItem(2, new ItemStack(Material.WOODEN_SHOVEL));
		inv.setItem(4, new ItemStack(Material.WOODEN_AXE));
		inv.setItem(5, new ItemStack(Material.TORCH, 16));
		inv.setItem(6, new ItemStack(Material.COOKIE, 5));
		inv.setItem(7, new ItemStack(Material.IRON_CHESTPLATE));
		inv.setItem(8, new ItemStack(Material.SPECTRAL_ARROW, 10));
	}
}
