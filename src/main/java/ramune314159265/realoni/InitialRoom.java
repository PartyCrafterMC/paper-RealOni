package ramune314159265.realoni;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class InitialRoom {
	private static final Material wallMaterial = Material.GLASS;
	private static final Location center = new Location(
			Realoni.defaultWorld,
			0,
			Ground.getY(Realoni.defaultWorld, 0, 0),
			0
	);
	private static final int altitude = 40;
	private static final int width = 30;
	private static final int height = 10;

	public static void fillHollow(World world, int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
		for (int x = x1; x <= x2; x++) {
			for (int y = y1; y <= y2; y++) {
				for (int z = z1; z <= z2; z++) {
					if (x == x1 || x == x2 ||
							y == y1 || y == y2 ||
							z == z1 || z == z2) {
						world.getBlockAt(x, y, z).setType(material, false);
					}
				}
			}
		}
	}

	public static void place() {
		fillHollow(
				center.getWorld(),
				center.getBlockX() - width / 2, center.getBlockX() + width / 2,
				center.getBlockY() + altitude - height / 2, center.getBlockY() + altitude + height / 2,
				center.getBlockZ() - width / 2, center.getBlockZ() + width / 2,
				wallMaterial
		);
	}

	public static Location getSpawnLocation() {
		return new Location(
				center.getWorld(),
				0,
				(center.getBlockY() + altitude - (double) height / 2) + 1,
				0
		);
	}
}
