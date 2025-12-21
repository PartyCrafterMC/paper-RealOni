package ramune314159265.realoni.skills;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ramune314159265.realoni.Realoni;

public class SweetBerryRestrict extends Skill implements Listener {
	@Override
	public String getName() {
		return "スイートベリーの拘束";
	}

	static {
		Realoni.getInstance().getLogger().info("register");
		Realoni.getInstance().getServer().getPluginManager().registerEvents(new SweetBerryRestrict(), Realoni.getInstance());
	}

	@EventHandler
	public void entityDamagedByBlock(EntityDamageByBlockEvent e) {
		Realoni.getInstance().getLogger().info(e.getDamager().getType().toString());
		if(!(e.getEntity() instanceof Player player)){
			return;
		}
		if(!e.getDamager().getType().equals(Material.SWEET_BERRY_BUSH)) {
			return;
		}
		if(!Realoni.processingGame.getPlayerRole(player).isSurvivor()) {
			return;
		}

		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 0, false, false, false));
		player.setFireTicks(60);
	}
}
