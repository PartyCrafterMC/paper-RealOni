package ramune314159265.realoni.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import ramune314159265.realoni.Realoni;

import java.util.Comparator;
import java.util.Objects;

public class TeleportBall extends CustomItem implements Listener {
	@Override
	public String getName() {
		return "テレポート玉";
	}

	@Override
	public ItemStack getItemStack() {
		ItemStack item = new ItemStack(Material.ENDER_EYE);
		ItemMeta meta = item.getItemMeta();
		meta.itemName(Component.text(getName()));
		meta.setRarity(ItemRarity.UNCOMMON);
		meta.setEnchantmentGlintOverride(true);
		item.setItemMeta(meta);
		return item;
	}

	@Override
	public NamespacedKey getKey() {
		return new NamespacedKey(Realoni.getInstance(), "teleport_ball");
	}

	@Override
	public void initialize() {
		ShapelessRecipe recipe = new ShapelessRecipe(
				getKey(),
				this.getItemStack()
		);
		recipe.addIngredient(Material.ENDER_PEARL);
		recipe.addIngredient(Material.NETHER_STAR);
		Bukkit.addRecipe(recipe);

		Realoni.getInstance().getServer().getPluginManager().registerEvents(this, Realoni.getInstance());
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) || !e.hasItem()) {
			return;
		}
		if (e.getItem().getType() != Material.ENDER_EYE) {
			return;
		}
		String name = ((TextComponent) getItemStack().getItemMeta().itemName()).content();
		if (!name.equals(getName())) {
			return;
		}
		e.setCancelled(true);
		if (Objects.isNull(Realoni.processingGame)) {
			return;
		}

		try {
			Player nearestSurvivor = Realoni.getInstance().getServer().getOnlinePlayers()
					.stream()
					.filter(p -> !p.equals(e.getPlayer()))
					.filter(p -> Realoni.processingGame.getPlayerRole(p).isSurvivor())
					.sorted(Comparator.comparing(p -> p.getLocation().distance(e.getPlayer().getLocation()), Comparator.naturalOrder()))
					.toList().getFirst();
			e.getPlayer().teleport(nearestSurvivor);
			e.getItem().setAmount(e.getItem().getAmount() - 1);

			e.getPlayer().playSound(e.getPlayer(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.7f, 1f);
		} catch (Exception ignored) {
		}
	}
}
