package ramune314159265.realoni;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import org.bukkit.*;
import org.bukkit.block.Block;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Ground {
	public static int getY(World world, int x, int z) {
		return world.getHighestBlockAt(x, z, HeightMap.MOTION_BLOCKING_NO_LEAVES).getY();
	}

	public static Location getFromLocation(Location location) {
		return location.getWorld().getHighestBlockAt(location.getBlockX(), location.getBlockZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES).getLocation();
	}
}
