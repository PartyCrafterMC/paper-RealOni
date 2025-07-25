package ramune314159265.realoni;

import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Realoni extends JavaPlugin {
	public static World defaultWorld;
	private static Realoni instance;

	public static Realoni getInstance() {
		return instance;
	}

	public static void worldInitialize() {
		InitialRoom.place();
	}

	public static void playerInitialize(Player player) {
		player.teleport(InitialRoom.getSpawnLocation());
		player.setGameMode(GameMode.ADVENTURE);
	}

	@Override
	public void onEnable() {
		instance = this;
		defaultWorld = getServer().getWorlds().get(0);

		this.getServer().getPluginManager().registerEvents(new PluginListener(), this);
		worldInitialize();
	}

	@Override
	public void onDisable() {
	}
}
