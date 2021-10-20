package thesilverecho.avaritia.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.init.ModItems;

public class ModLanguageGenerator extends LanguageProvider
{
	public ModLanguageGenerator(DataGenerator gen)
	{
		super(gen, Avaritia.MOD_ID, "en_us");

	}

	@Override
	protected void addTranslations()
	{
		add("itemGroup.avaritia", "Avaritia");
		addItem(ModItems.INFINITY_SWORD, "Sword Of The Cosmos");
		addItem(ModItems.EXTREME_INGOT, "Extreme Ingot");
		addItem(ModItems.NEUTRON_INGOT, "Neutron Ingot");
		addItem(ModItems.NEUTRON_NUGGET, "Neutron Nugget");
		addItem(ModItems.NEUTRON_PILE, "Neutron Pile");

		add("tooltip.neutron_pile.desc", "Try not to think about it.");
		add("tooltip.neutron_nugget.desc", "About 35.6 million metric tons.");
		add("tooltip.neutron_ingot.desc", "The dense heart of a star in convenient ingot form.");
	}
}
