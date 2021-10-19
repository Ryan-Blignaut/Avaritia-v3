package thesilverecho.avaritia.common.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import thesilverecho.avaritia.common.container.ExtremeCraftingTableContainer;
import thesilverecho.avaritia.common.init.ModTiles;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ExtremeCraftingTableTile extends BasicItemStorageTile implements MenuProvider
{
	private final ItemStackHandler handler = new ItemStackHandler(81)
	{
		@Override
		protected void onContentsChanged(int slot)
		{
			setChanged();
		}
	};


	public ExtremeCraftingTableTile(BlockPos blockPos, BlockState blockState)
	{
		super(ModTiles.EXTREME_CRAFTING_TABLE_TILE.get(), blockPos, blockState);
	}

	@Nonnull
	@Override
	public ItemStackHandler getInventory()
	{
		return handler;
	}

	@Nonnull
	@Override
	public Component getDisplayName()
	{
		return new TextComponent("test");
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player)
	{
		return new ExtremeCraftingTableContainer(id, inventory, this::isWithinUsableDistance, getInventory());
	}

}
