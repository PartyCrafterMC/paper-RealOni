package ramune314159265.realoni.items;

import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import ramune314159265.realoni.Realoni;

public class SuperSpeedPickaxe extends CustomItem{
	@Override
	public String getName() {
		return "爆速ピッケル";
	}

	@Override
	public ItemStack getItemStack() {
		ItemStack item = new ItemStack(Material.IRON_PICKAXE);
		ItemMeta meta = item.getItemMeta();
		meta.itemName(Component.text(getName()));
		meta.setRarity(ItemRarity.UNCOMMON);
		meta.addEnchant(Enchantment.EFFICIENCY, 10, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		return item;
	}

	@Override
	public NamespacedKey getKey() {
		return new NamespacedKey(Realoni.getInstance(), "super_speed_pickaxe");
	}

	@Override
	public void initialize() {
		ShapedRecipe recipe = new ShapedRecipe(
				getKey(),
				this.getItemStack()
		);
		recipe.shape(
				"INI",
				" S ",
				" S "
		);
		recipe.setIngredient('I', Material.IRON_INGOT);
		recipe.setIngredient('N', Material.NETHER_STAR);
		recipe.setIngredient('S', Material.STICK);
		Bukkit.addRecipe(recipe);
	}
}
