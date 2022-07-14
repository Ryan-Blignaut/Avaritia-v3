package thesilverecho.avaritia.common.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;
import thesilverecho.avaritia.common.tile.ExtremeCraftingTableTile;

public class ModTiles
{
	public static final RegistryObject<BlockEntityType<ExtremeCraftingTableTile>> EXTREME_CRAFTING_TABLE_TILE = register("extreme_crafting_table", ExtremeCraftingTableTile::new, ModBlocks.EXTREME_CRAFTING_TABLE_BLOCK);

	public static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, RegistryObject<Block> block)
	{
		return ModRegistry.TILES.register(name, () -> BlockEntityType.Builder.of(factory, block.get()).build(null));
	}

	static void register()
	{
	}

}
