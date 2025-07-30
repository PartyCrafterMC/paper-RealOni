package ramune314159265.realoni;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class Cage {
	private static final Location center = new Location(
			Realoni.defaultWorld,
			0,
			Ground.getY(Realoni.defaultWorld, 0, 0) + 2,
			0
	);
	private static final Material wallMaterial = Material.REINFORCED_DEEPSLATE;
	private static final Material cageMaterial = Material.IRON_BARS;

	public static void fill(World world, int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
		for (int x = x1; x <= x2; x++) {
			for (int y = y1; y <= y2; y++) {
				for (int z = z1; z <= z2; z++) {
					world.getBlockAt(x, y, z).setType(material, true);
				}
			}
		}
	}

	public static void place() {
		// 床
		fill(
				center.getWorld(),
				center.getBlockX() - 1, center.getBlockX() + 1,
				center.getBlockY() - 1, center.getBlockY() - 1,
				center.getBlockZ() - 1, center.getBlockZ() + 1,
				wallMaterial
		);
		// 天井
		fill(
				center.getWorld(),
				center.getBlockX() - 1, center.getBlockX() + 1,
				center.getBlockY() + 2, center.getBlockY() + 2,
				center.getBlockZ() - 1, center.getBlockZ() + 1,
				wallMaterial
		);
		// 横
		fill(
				center.getWorld(),
				center.getBlockX() - 1, center.getBlockX() + 1,
				center.getBlockY(), center.getBlockY() + 1,
				center.getBlockZ() - 1, center.getBlockZ() + 1,
				cageMaterial
		);
		fill(
				center.getWorld(),
				center.getBlockX(), center.getBlockX(),
				center.getBlockY(), center.getBlockY() + 1,
				center.getBlockZ(), center.getBlockZ(),
				Material.AIR
		);
	}

	public static void release() {
		fill(
				center.getWorld(),
				center.getBlockX(), center.getBlockX(),
				center.getBlockY(), center.getBlockY() + 1,
				center.getBlockZ() - 1, center.getBlockZ() + 1,
				Material.AIR
		);
		fill(
				center.getWorld(),
				center.getBlockX() - 1, center.getBlockX() + 1,
				center.getBlockY(), center.getBlockY() + 1,
				center.getBlockZ(), center.getBlockZ(),
				Material.AIR
		);
	}

	public static Location getSpawnLocation() {
		return center;
	}
}
