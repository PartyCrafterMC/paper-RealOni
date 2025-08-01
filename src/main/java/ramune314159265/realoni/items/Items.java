package ramune314159265.realoni.items;

public class Items {
	public static final CustomItem[] customItems = {
			new TeleportBall(),
			new LeafChestplate(),
			new SuperSpeedPickaxe(),
			new GlassSword(),
			new Grenade(),
			new KingSword()
	};

	public static void initialize() {
		for (CustomItem i : customItems) {
			i.initialize();
		}
	}
}
