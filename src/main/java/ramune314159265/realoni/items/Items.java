package ramune314159265.realoni.items;

public class Items {
	public static CustomItem[] customItems = {
			new TeleportBall(),
			new LeafChestplate()
	};

	public static void initialize() {
		for (CustomItem i : customItems) {
			i.initialize();
		}
	}
}
