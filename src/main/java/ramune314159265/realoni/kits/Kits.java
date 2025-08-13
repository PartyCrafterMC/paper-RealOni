package ramune314159265.realoni.kits;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class Kits {
	public static final Kit[] kitList = {
			new ArcherKit(),
			new ArmorKit(),
			new HealerKit()
	};
	public static final Kit defaultKit = new DefaultKit();
	public static final HashMap<Player ,Kit> kitPlayerHashMap = new HashMap<>();

	public static void setPlayerKit(Player player, Kit kit) {
		if(kitPlayerHashMap.containsValue(kit)) {
			removeKit(kit);
		}
		kitPlayerHashMap.put(player, kit);
	}

	public static void removeKit(Kit kit) {
		Player player = kitPlayerHashMap.entrySet().stream()
				.filter(e -> e.getValue() == kit)
				.toList().getFirst().getKey();
		kitPlayerHashMap.remove(player);
	}

	public static void removePlayer(Player player) {
		kitPlayerHashMap.remove(player);
	}

	public static Kit getPlayerKit(Player player) {
		if(kitPlayerHashMap.containsKey(player)) {
			return kitPlayerHashMap.get(player);
		}
		return defaultKit;
	}
}
