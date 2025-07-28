package ramune314159265.realoni;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.popcraft.chunky.api.ChunkyAPI;
import ramune314159265.realoni.commands.RealOniCommand;

public final class Realoni extends JavaPlugin {
	public static final int worldSize = 600;
	public static World defaultWorld;
	public static ChunkyAPI chunky;
	public static Game processingGame;
	private static Realoni instance;

	public static Realoni getInstance() {
		return instance;
	}

	public static void broadcast(Component message) {
		for (Player p : Realoni.getInstance().getServer().getOnlinePlayers()) {
			p.sendMessage(message);
		}
	}

	public static void worldInitialize() {
		defaultWorld.setTime(1000);
		defaultWorld.setDifficulty(Difficulty.PEACEFUL);
		defaultWorld.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
		defaultWorld.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
		defaultWorld.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
		defaultWorld.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, false);
		defaultWorld.setGameRule(GameRule.KEEP_INVENTORY, true);
		defaultWorld.setGameRule(GameRule.SPAWN_RADIUS, 0);
		defaultWorld.getWorldBorder().setDamageAmount(0);
		defaultWorld.getWorldBorder().setCenter(0, 0);
		defaultWorld.getWorldBorder().setSize(worldSize);
		InitialRoom.place();

		Realoni.chunky.startTask(defaultWorld.getName(), "square", 0, 0, (double) worldSize / 2, (double) worldSize / 2, "concentric");
		Realoni.chunky.onGenerationComplete(event -> broadcast(
				Component.text().content("チャンクの事前読み込みが完了しました").color(NamedTextColor.GREEN).build()
		));
	}

	public static void playerInitialize(Player player) {
		player.teleport(InitialRoom.getSpawnLocation());
		player.setGameMode(GameMode.ADVENTURE);
	}

	public static Game startGame() {
		Game game = new Game();
		processingGame = game;
		return game;
	}

	@Override
	public void onEnable() {
		instance = this;
		defaultWorld = getServer().getWorlds().getFirst();
		chunky = getServer().getServicesManager().load(ChunkyAPI.class);

		this.getServer().getPluginManager().registerEvents(new PluginListener(), this);
		this.getCommand("realoni").setExecutor(new RealOniCommand());

		worldInitialize();
	}

	@Override
	public void onDisable() {
	}
}
