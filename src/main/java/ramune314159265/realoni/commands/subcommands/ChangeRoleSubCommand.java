package ramune314159265.realoni.commands.subcommands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ramune314159265.realoni.Realoni;
import ramune314159265.realoni.roles.Role;
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
		if (args.size() <= 1) {
			sender.sendMessage(Component.text("引数が不足しています").color(NamedTextColor.RED));
			return;
		}
		Player target;
		if (args.size() == 2) {
			if(!(sender instanceof Player player)) {
				sender.sendMessage(Component.text("プレイヤー以外では実行できません").color(NamedTextColor.RED));
				return;
			}
			target = player;
		} else {
			Player specifiedPlayer = Bukkit.getPlayer(args.get(2));
			if (Objects.isNull(specifiedPlayer)) {
				sender.sendMessage(Component.text("指定されたプレイヤーは存在しません").color(NamedTextColor.RED));
				return;
			}
			target = specifiedPlayer;
		}

		Roles.RoleEntry roleEntry = Roles.roleList.stream()
				.filter(r -> Objects.equals(r.name(), args.get(1)))
				.findFirst()
				.orElse(Roles.roleList.getFirst());
		Roles.setPlayerRoleEntry(target, roleEntry);
		try {
			Role role = Roles.getPlayerRoleEntry(target).cls().getDeclaredConstructor(Player.class).newInstance(target);
			Realoni.processingGame.getPlayerRole(target).exit();
			Realoni.processingGame.playerRoles.put(target, role);
			sender.sendMessage(Component.text(target.getName() + "のロールを" + roleEntry.name() + " に変更しました").color(NamedTextColor.GREEN));
		} catch (Exception ignored) {
			sender.sendMessage(Component.text("変更に失敗しました").color(NamedTextColor.RED));
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, List<String> args) {
		return switch (args.size()) {
			case 2 -> Roles.roleList.stream().map(Roles.RoleEntry::name).toList();
			case 3 -> Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
			default -> List.of();
		};
	}

	@Override
	public boolean isAvailable() {
		return !Objects.isNull(Realoni.processingGame);
	}
}
