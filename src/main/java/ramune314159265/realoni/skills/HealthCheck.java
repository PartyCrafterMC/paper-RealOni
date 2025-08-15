package ramune314159265.realoni.skills;

import net.kyori.adventure.text.Component;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import ramune314159265.realoni.Realoni;

import java.util.Comparator;

public class HealthCheck extends Skill {
	@Override
	public String getName() {
		return "体力チェック";
	}

	@Override
	public void use(Player player) {
		Realoni.processingGame.playerRoles.entrySet().stream()
				.filter(entry -> entry.getValue().isSurvivor())
				.sorted(Comparator.comparing(entry -> entry.getKey().getLocation().distance(player.getLocation()), Comparator.reverseOrder()))
				.forEach(entry -> player.sendMessage(Component.text()
						.append(Component.text(String.format("%16s", entry.getKey().getName())))
						.appendSpace()
						.append(Component.text((Math.round(entry.getKey().getHealth() * 10)) / 10))
						.append(Component.text(" / "))
						.append(Component.text(entry.getKey().getAttribute(Attribute.MAX_HEALTH).getValue()))
				));
	}
}
