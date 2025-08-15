package ramune314159265.realoni.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import ramune314159265.realoni.Realoni;

import java.util.List;
import java.util.Objects;

public class KingSword extends CustomItem implements Listener {
	@Override
	public String getName() {
		return "王の剣";
	}

	@Override
	public ItemStack getItemStack() {
		ItemStack item = new ItemStack(Material.GOLDEN_SWORD);
		ItemMeta meta = item.getItemMeta();
		meta.itemName(Component.text(getName()).color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD));
		meta.lore(List.of(
				Component.text("鬼特攻 X").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)
		));
		meta.setRarity(ItemRarity.UNCOMMON);
		meta.setUnbreakable(true);
		meta.setEnchantmentGlintOverride(true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		item.setItemMeta(meta);
		return item;
	}

	@Override
	public NamespacedKey getKey() {
		return new NamespacedKey(Realoni.getInstance(), "kind_sword");
	}

	@Override
	public void initialize() {
		ShapedRecipe recipe = new ShapedRecipe(
				getKey(),
				this.getItemStack()
		);
		recipe.shape(
				"  N",
				" N ",
				"D  "
		);
		recipe.setIngredient('N', Material.NETHER_STAR);
		recipe.setIngredient('D', Material.DIAMOND_PICKAXE);
		Bukkit.addRecipe(recipe);

		Realoni.getInstance().getServer().getPluginManager().registerEvents(this, Realoni.getInstance());
	}

	@EventHandler
	public void onEntityDamaged(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player victim)) return;
		if (!(e.getDamager() instanceof Player attacker)) return;

		if (Objects.isNull(Realoni.processingGame)) {
			return;
		}
		if (Realoni.processingGame.getPlayerRole(victim).isSurvivor()) {
			return;
		}
		String name = ((TextComponent) attacker.getInventory().getItemInMainHand().getItemMeta().itemName()).content();
		if (!name.equals(getName())) {
			return;
		}

		victim.damage(8);

		Vector direction = victim.getLocation().toVector().subtract(attacker.getLocation().toVector()).normalize();
		double knockbackStrength = 5;

		victim.setVelocity(direction.multiply(knockbackStrength));

		victim.getWorld().spawnParticle(
				Particle.END_ROD,
				victim.getLocation().add(0, 1, 0),
				8, 0.1, 0.1, 0.1, 0.1
		);
	}
}
