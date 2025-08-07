package ramune314159265.realoni;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Ground {
	private static final Set<Material> IGNORED_BLOCKS = Sets.newHashSet(Iterables.concat(
			Tag.LEAVES.getValues(),
			new HashSet<>(List.of(
					Material.GLASS
			))));

	public static int getY(World world, int x, int z) {
		for (int y = world.getMaxHeight() - 1; y >= 0; y--) {
			Block block = world.getBlockAt(x, y, z);

			if (!block.isPassable() && !IGNORED_BLOCKS.contains(block.getType())) {
				return y;
			}
		}
		return 0;
	}
}
