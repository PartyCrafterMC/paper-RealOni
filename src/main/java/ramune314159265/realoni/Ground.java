package ramune314159265.realoni;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ground {
	private static final Set<Material> IGNORED_BLOCKS = Stream.of(
					Tag.FLOWER_POTS.getValues(),
					Tag.LEAVES.getValues(),
					new HashSet<>(Arrays.asList(
							Material.SHORT_GRASS,
							Material.TALL_GRASS,
							Material.FERN,
							Material.LARGE_FERN,
							Material.VINE,
							Material.SNOW,
							Material.DEAD_BUSH,
							Material.SUGAR_CANE,
							Material.GLASS
					))
			)
			.flatMap(Collection::stream)
			.collect(Collectors.toSet());


	public static double getY(World world, int x, int z) {
		for (int y = world.getMaxHeight() - 1; y >= 0; y--) {
			Block block = world.getBlockAt(x, y, z);
			Material type = block.getType();

			if (type.isSolid() && !IGNORED_BLOCKS.contains(type)) {
				return y;
			}
		}
		return 0;
	}
}
