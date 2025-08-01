package ramune314159265.realoni.commands.subcommands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ramune314159265.realoni.items.CustomItem;
import ramune314159265.realoni.items.Items;

import java.util.Arrays;
import java.util.List;

public class GetItemSubCommand extends SubCommand {
	@Override
	public String getName() {
		return "getitem";
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
			ItemStack item = Arrays.stream(Items.customItems)
					.filter(i -> i.getName().equals(args.get(1)))
					.toList().getFirst().getItemStack();

			player.getInventory().addItem(item.asOne());
		} catch (Exception ignored) {
			sender.sendMessage(Component.text("アイテムが存在しません").color(NamedTextColor.RED));
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, List<String> args) {
		return Arrays.stream(Items.customItems)
				.map(CustomItem::getName)
				.toList();
	}

	@Override
	public boolean isAvailable() {
		return true;
	}
}
