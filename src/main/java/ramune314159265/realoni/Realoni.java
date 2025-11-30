package ramune314159265.realoni;

import dev.iiahmed.disguise.DisguiseManager;
import dev.iiahmed.disguise.DisguiseProvider;
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
import ramune314159265.realoni.items.CustomItem;
import ramune314159265.realoni.items.Items;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Realoni extends JavaPlugin {
	public static final int worldSize = 700;
	public static final DisguiseProvider disguiseProvider = DisguiseManager.getProvider();
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
		defaultWorld.setGameRule(GameRule.SPAWN_RADIUS, 0);
		defaultWorld.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
		defaultWorld.setGameRule(GameRule.FALL_DAMAGE, false);
		defaultWorld.setGameRule(GameRule.LOCATOR_BAR, false);
		defaultWorld.getWorldBorder().setDamageAmount(0);
		defaultWorld.getWorldBorder().setCenter(0, 0);
		defaultWorld.getWorldBorder().setSize(worldSize);
		InitialRoom.place();
		Items.initialize();

		Realoni.chunky.startTask(defaultWorld.getName(), "square", 0, 0, (double) worldSize / 2, (double) worldSize / 2, "concentric");
		Realoni.chunky.onGenerationComplete(event -> broadcast(
				Component.text().content("チャンクの事前読み込みが完了しました").color(NamedTextColor.GREEN).build()
		));
	}

	public static void playerInitialize(Player player) {
		if (Objects.isNull(Realoni.processingGame)) {
			player.teleport(InitialRoom.getSpawnLocation());
			player.setGameMode(GameMode.ADVENTURE);
		}
		for (CustomItem i : Items.customItems) {
			if(!Objects.isNull(i.getKey())) {
				player.discoverRecipe(i.getKey());
			}
		}
	}

	public static Game newGame() {
		Game game = new Game();
		processingGame = game;
		return game;
	}

	@Override
	public void onEnable() {
		instance = this;
		defaultWorld = getServer().getWorlds().getFirst();
		chunky = getServer().getServicesManager().load(ChunkyAPI.class);
		DisguiseManager.initialize(this, true);
		disguiseProvider.allowOverrideChat(false);
		disguiseProvider.setNamePattern(Pattern.compile("(.+?)"));

		this.getServer().getPluginManager().registerEvents(new PluginListener(), this);
		this.getCommand("realoni").setExecutor(new RealOniCommand());

		worldInitialize();
	}

	@Override
	public void onDisable() {
	}
}
