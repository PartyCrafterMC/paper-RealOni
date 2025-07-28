package ramune314159265.realoni;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Ground {
	private static final Set<Material> IGNORED_BLOCKS = Sets.newHashSet(Iterables.concat(
			Tag.FLOWER_POTS.getValues(),
			Tag.LEAVES.getValues(),
			Tag.REPLACEABLE.getValues(),
			new HashSet<>(Arrays.asList(
					Material.SUGAR_CANE,
					Material.GLASS
			))));


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
