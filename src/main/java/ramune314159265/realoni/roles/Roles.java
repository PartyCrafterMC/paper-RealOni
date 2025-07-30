package ramune314159265.realoni.roles;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class Roles {
	public static final List<RoleEntry> roleList = List.of(
			new RoleEntry(Survivor.class, "プレイヤー", Material.IRON_SWORD, NamedTextColor.YELLOW),
			new RoleEntry(Cameleon.class, "11の鬼", Material.NETHERITE_AXE, NamedTextColor.GREEN)
	);
	public static HashMap<Player, RoleEntry> playerRoleEntry = new HashMap<>();

	public static RoleEntry getPlayerRoleEntry(Player player) {
		if (!playerRoleEntry.containsKey(player)) {
			return roleList.getFirst();
		}
		return playerRoleEntry.get(player);
	}

	public static void setPlayerRoleEntry(Player player, RoleEntry roleEntry) {
		playerRoleEntry.put(player, roleEntry);
	}

	public record RoleEntry(Class<? extends RoleAbstract> cls, String name, Material icon, TextColor color) {
	}
}
