package thesilverecho.avaritia.common;

import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thesilverecho.avaritia.client.ClientSetup;
import thesilverecho.avaritia.common.init.ModRegistry;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("avaritia")
public class Avaritia
{

	public static final Rarity COSMIC = Rarity.create("cosmic", ChatFormatting.RED);
	public static final String MOD_ID = "avaritia";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	public Avaritia()
	{
		ModRegistry.init();
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(this::setup);
		modEventBus.addListener(this::registerCustomColours);
		MinecraftForge.EVENT_BUS.register(this);
//		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CommonManager.CLIENT_SPEC);

	}

	private void setup(final FMLCommonSetupEvent event)
	{
	}

	public void registerCustomColours(FMLLoadCompleteEvent event)
	{
//		ColourHandler.addBlock(ModBlocks.UPGRADE_BENCH_BLOCK.get());
//		ColourHandler.addBlock(ModBlocks.MODIFIER_BLOCK.get());
//		ColourHandler.init();
	}

}

