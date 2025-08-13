package ramune314159265.realoni.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ArmorKit extends Kit {
	@Override
	public String getName() {
		return "アーマーキット";
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
		ItemStack chain = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
		ItemMeta chainMeta = chain.getItemMeta();
		chainMeta.addEnchant(Enchantment.PROTECTION, 5, true);
		chain.setItemMeta(chainMeta);
		inv.setItem(6, chain);
		inv.setItem(7, new ItemStack(Material.SHIELD));
	}
}
