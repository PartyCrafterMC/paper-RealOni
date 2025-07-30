package ramune314159265.realoni;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ramune314159265.realoni.roles.RoleAbstract;
import ramune314159265.realoni.roles.Roles;
import ramune314159265.realoni.roles.Survivor;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
	public static final long releaseSecond = 3 * 60;
	public static final int miniumWorldSize = 50;
	public static final double worldShrinkPerSecond = 1f / 20f;
	public static final long worldShrinkTime = (long) Math.floor((Realoni.worldSize - miniumWorldSize) / worldShrinkPerSecond);
	public LocalDateTime startAt;
	public LocalDateTime releastAt;
	public HashMap<Player, RoleAbstract> playerRoles = new HashMap<>();

	public Game() {
		initialize();
	}

	public void initialize() {
		this.startAt = LocalDateTime.now();
		this.releastAt = this.startAt.plusSeconds(releaseSecond);

		Realoni.defaultWorld.setDifficulty(Difficulty.NORMAL);
		Realoni.defaultWorld.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
		Realoni.defaultWorld.setGameRule(GameRule.DO_WEATHER_CYCLE, true);
		Realoni.defaultWorld.setGameRule(GameRule.REDUCED_DEBUG_INFO, true);

		InitialRoom.remove();
		Cage.place();
		Supply.placeSupplies(Supply.defaultSupplyCount);

		for (Player p : Realoni.getInstance().getServer().getOnlinePlayers()) {
			try {
				playerRoles.put(p, Roles.getPlayerRoleEntry(p).cls().getDeclaredConstructor(Player.class).newInstance(p));
			} catch (Exception ignored) {}

			RoleAbstract role = getPlayerRole(p);
			Location cageLocation = Cage.getSpawnLocation();
			if(!role.isSurvivor()){
				p.teleport(cageLocation);
			} else {
				p.teleport(new Location(
						cageLocation.getWorld(),
						cageLocation.getBlockX() + 5,
						Ground.getY(cageLocation.getWorld(), cageLocation.getBlockX() + 5, cageLocation.getBlockZ()) + 1,
						cageLocation.getBlockZ()
				));
			}
			role.initialize();
		}

		TimerTask task = new TimerTask() {
			public void run() {
				Duration duration = Duration.between(LocalDateTime.now(), releastAt);
				for (Player p : Realoni.getInstance().getServer().getOnlinePlayers()) {
					p.sendActionBar(
							Component.text()
									.append(Component.text("鬼開放まで ").color(NamedTextColor.RED))
									.append(Component.text(String.format("%02d", duration.toMinutesPart()) + ":" + String.format("%02d", duration.toSecondsPart())))
									.build()
					);
				}
				if (duration.toSeconds() <= 0) {
					Bukkit.getScheduler().runTask(Realoni.getInstance(), Game.this::oniRelease);
					cancel();
				}
			}
		};

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, 1000);
	}

	public RoleAbstract getPlayerRole(Player player){
		if(!playerRoles.containsKey(player)){
			Survivor defaultRole = new Survivor(player);
			playerRoles.put(player, defaultRole);
		}
		return playerRoles.get(player);
	}

	public void oniRelease() {
		Cage.release();
		for (Player p : Realoni.getInstance().getServer().getOnlinePlayers()) {
			Realoni.defaultWorld.strikeLightningEffect(p.getLocation().add(0, 50 ,0));
		}
		Realoni.defaultWorld.getWorldBorder().setSize(miniumWorldSize, worldShrinkTime);
	}
}
