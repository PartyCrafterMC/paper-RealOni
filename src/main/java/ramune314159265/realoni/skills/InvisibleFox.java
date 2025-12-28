package ramune314159265.realoni.skills;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import ramune314159265.realoni.Realoni;
import ramune314159265.realoni.roles.Fox;

import java.util.concurrent.atomic.AtomicInteger;

public class InvisibleFox extends SkillUsable implements Listener {
	@Override
	public String getName() {
		return "透明狐";
	}

	static {
		Realoni.getInstance().getServer().getPluginManager().registerEvents(new InvisibleFox(), Realoni.getInstance());
	}

	@EventHandler
	public void entityCombusted(EntityCombustEvent e) {
		if(!(e.getEntity() instanceof Player player)){
			return;
		}
		if(!(Realoni.processingGame.getPlayerRole(player) instanceof Fox)) {
			return;
		}
		e.setCancelled(true);
	}

	@Override
	public void use(Player player) {
		player.setInvisible(true);
		BukkitScheduler scheduler = Bukkit.getScheduler();
		AtomicInteger tick = new AtomicInteger(-1);
		scheduler.runTaskTimer(Realoni.getInstance(), (BukkitTask task) -> {
			player.getWorld().setType(player.getLocation(), Material.FIRE);
			player.setFireTicks(-1);
			if (200 <= tick.addAndGet(1)) {
				player.setInvisible(false);
				task.cancel();
			}
		}, 0, 1);
	}
}

