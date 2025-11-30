package ramune314159265.realoni;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Supply {
	public static final Material material = Material.BEEHIVE;
	public static final int defaultSupplyCount = 70;
	private static final List<DropEntry> dropTable = List.of(
			new DropEntry(0.10, new ItemStack(Material.IRON_BLOCK)),
			new DropEntry(0.10, new ItemStack(Material.BUCKET)),
			new DropEntry(0.07, new ItemStack(Material.NETHER_STAR)),
			new DropEntry(0.05, new ItemStack(Material.OAK_LOG, 5)),
			new DropEntry(0.10, new ItemStack(Material.SHEARS)),
			new DropEntry(0.10, new ItemStack(Material.SHIELD)),
			new DropEntry(0.10, new ItemStack(Material.ARROW, 10)),
			new DropEntry(0.10, new ItemStack(Material.BREAD, 10)),
			new DropEntry(0.10, new ItemStack(Material.GOLDEN_CARROT, 5)),
			new DropEntry(0.10, new ItemStack(Material.BOW)),
			new DropEntry(0.05, new ItemStack(Material.TOTEM_OF_UNDYING)),
			new DropEntry(0.03, new ItemStack(Material.ENCHANTED_GOLDEN_APPLE))
	);
	public static List<Block> blocks = new ArrayList<>();

	public static void placeSupplies(int count) {
		List<Block> randomBlocks = new ArrayList<>();
		Random random = new Random();
		for (int i = 0; i < count; i++) {
			int randomX = random.nextInt(Realoni.worldSize) - Realoni.worldSize / 2;
			int randomZ = random.nextInt(Realoni.worldSize) - Realoni.worldSize / 2;
			randomBlocks.add(Realoni.defaultWorld.getBlockAt(
					randomX,
					Ground.getY(Realoni.defaultWorld, randomX, randomZ) + 1,
					randomZ
			));
		}

		randomBlocks.forEach(block -> {
			Realoni.defaultWorld.getChunkAtAsync(block).thenAccept(chunk -> {
				blocks.add(block);
				Bukkit.getScheduler().runTask(Realoni.getInstance(), () -> block.setType(material));
			});
		});
	}

	public static void brokenHandle(BlockBreakEvent event) {
		Block block = event.getBlock();
		if (block.getType() != material) {
			return;
		}
		event.setDropItems(false);

		double totalWeight = dropTable.stream().map(d -> d.weight).reduce(0d, Double::sum);
		double r = Math.random() * totalWeight;
		double cumulative = 0.0;
		for (DropEntry entry : dropTable) {
			cumulative += entry.weight;
			if (r <= cumulative) {
				block.getWorld().dropItemNaturally(block.getLocation().add(0.5, 0.5, 0.5), entry.item);
				break;
			}
		}
	}

	private record DropEntry(double weight, ItemStack item) {
	}
}
