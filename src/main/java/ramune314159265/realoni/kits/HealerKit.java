package ramune314159265.realoni.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

public class HealerKit extends Kit {
	@Override
	public String getName() {
		return "ヒーラーキット";
	}

	@Override
	public void setItems(Player player) {
		Inventory inv = player.getInventory();
		inv.setItem(0, new ItemStack(Material.WOODEN_SWORD));
		inv.setItem(1, new ItemStack(Material.WOODEN_PICKAXE));
		inv.setItem(2, new ItemStack(Material.WOODEN_SHOVEL));
		inv.setItem(3, new ItemStack(Material.WOODEN_AXE));
		inv.setItem(4, new ItemStack(Material.TORCH, 16));
		inv.setItem(5, new ItemStack(Material.COOKIE, 5));
		inv.setItem(6, new ItemStack(Material.GOLDEN_APPLE));
		ItemStack portion = new ItemStack(Material.SPLASH_POTION);
		PotionMeta potionMeta = (PotionMeta) portion.getItemMeta();
		potionMeta.setBasePotionType(PotionType.STRONG_REGENERATION);
		portion.setItemMeta(potionMeta);
		inv.setItem(7, portion);
		inv.setItem(8, portion);
	}
}
