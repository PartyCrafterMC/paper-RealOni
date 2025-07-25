package ramune314159265.realoni;

import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Realoni extends JavaPlugin {
	public static final int worldSize = 600;
	public static World defaultWorld;
	private static Realoni instance;

	public static Realoni getInstance() {
		return instance;
	}

	public static void worldInitialize() {
		defaultWorld.setTime(1000);
		defaultWorld.setDifficulty(Difficulty.PEACEFUL);
		defaultWorld.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
		defaultWorld.setGameRule(GameRule.DO_DAYLIGHT_CYCLE,false);
		defaultWorld.setGameRule(GameRule.DO_WEATHER_CYCLE,false);
		defaultWorld.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT,false);
		defaultWorld.setGameRule(GameRule.KEEP_INVENTORY,true);
		defaultWorld.setGameRule(GameRule.SPAWN_RADIUS,0);
		defaultWorld.getWorldBorder().setDamageAmount(0);
		defaultWorld.getWorldBorder().setCenter(0,0);
		defaultWorld.getWorldBorder().setSize(worldSize);
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
