package ramune314159265.realoni;

import org.bukkit.Difficulty;
import org.bukkit.GameRule;

public class Game {
	public Game() {
		initialize();
	}

	public void initialize() {
		Realoni.defaultWorld.setDifficulty(Difficulty.NORMAL);
		Realoni.defaultWorld.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
		Realoni.defaultWorld.setGameRule(GameRule.DO_WEATHER_CYCLE, true);

		Supply.placeSupplies(Supply.defaultSupplyCount);
	}
}
