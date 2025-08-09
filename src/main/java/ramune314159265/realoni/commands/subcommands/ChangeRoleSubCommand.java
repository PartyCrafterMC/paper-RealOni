package ramune314159265.realoni.commands.subcommands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ramune314159265.realoni.Realoni;
import ramune314159265.realoni.roles.RoleAbstract;
import ramune314159265.realoni.roles.Roles;

import java.util.List;
import java.util.Objects;

public class ChangeRoleSubCommand extends SubCommand{
	@Override
	public String getName() {
		return "changerole";
	}

	@Override
	public void onCommand(CommandSender sender, List<String> args) {
		if (args.size() != 2) {
			return;
		}
		if (!(sender instanceof Player player)) {
			return;
		}

		Roles.RoleEntry roleEntry = Roles.roleList.stream()
				.filter(r -> Objects.equals(r.name(), args.get(1)))
				.findFirst()
				.orElse(Roles.roleList.getFirst());
		Roles.setPlayerRoleEntry(player, roleEntry);
		try {
			RoleAbstract role = Roles.getPlayerRoleEntry(player).cls().getDeclaredConstructor(Player.class).newInstance(player);
			Realoni.processingGame.getPlayerRole(player).exit();
			Realoni.processingGame.playerRoles.put(player, role);
			sender.sendMessage(Component.text(roleEntry.name() + " に変更しました").color(NamedTextColor.GREEN));
		} catch (Exception ignored) {
			sender.sendMessage(Component.text("変更に失敗しました").color(NamedTextColor.RED));
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, List<String> args) {
		return Roles.roleList.stream().map(Roles.RoleEntry::name).toList();
	}

	@Override
	public boolean isAvailable() {
		return !Objects.isNull(Realoni.processingGame);
	}
}
