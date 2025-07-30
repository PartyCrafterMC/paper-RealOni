package ramune314159265.realoni.items;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

abstract public class CustomItem {
	public abstract String getName();

	public abstract ItemStack getItemStack();

	public abstract NamespacedKey getKey();

	public abstract void initialize();
}
