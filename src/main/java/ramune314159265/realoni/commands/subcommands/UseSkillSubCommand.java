package ramune314159265.realoni.commands.subcommands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ramune314159265.realoni.Realoni;
import ramune314159265.realoni.skills.SkillUsable;

import java.util.List;
import java.util.Objects;

public class UseSkillSubCommand extends SubCommand {
	@Override
	public String getName() {
		return "useskill";
	}

	@Override
	public void onCommand(CommandSender sender, List<String> args) {
		if (args.size() != 2) {
			return;
		}
		if (!(sender instanceof Player player)) {
			return;
		}

		try {
			SkillUsable skill = Realoni.processingGame.getPlayerRole(player).getSkills()
					.stream()
					.filter(SkillUsable.class::isInstance)
					.map(SkillUsable.class::cast)
					.filter(i -> i.getName().equals(args.get(1)))
					.toList().getFirst();

			skill.use(player);
		} catch (Exception e) {
			sender.sendMessage(Component.text("技が存在しません").color(NamedTextColor.RED));
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, List<String> args) {
		if (!(sender instanceof Player player)) {
			return List.of();
		}
		return Realoni.processingGame.getPlayerRole(player).getSkills()
				.stream()
				.filter(SkillUsable.class::isInstance)
				.map(SkillUsable.class::cast)
				.map(SkillUsable::getName).toList();
	}

	@Override
	public boolean isAvailable() {
		return !Objects.isNull(Realoni.processingGame);
	}
}
