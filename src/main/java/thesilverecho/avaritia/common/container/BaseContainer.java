package thesilverecho.avaritia.common.container;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;
import java.util.function.Function;

public abstract class BaseContainer extends AbstractContainerMenu
{
	protected Function<Player, Boolean> isUsableByPlayer;

	public BaseContainer(@Nullable MenuType<?> menuType, int id, Function<Player, Boolean> isUsableByPlayer)
	{
		super(menuType, id);
		this.isUsableByPlayer = isUsableByPlayer;
	}


	public BaseContainer(@Nullable MenuType<?> menuType, int id)
	{
		this(menuType, id, p -> false);
	}

	@Override
	public boolean stillValid(Player playerIn)
	{
		return isUsableByPlayer.apply(playerIn);
	}


	protected int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx)
	{
		for (int i = 0; i < amount; i++)
		{
			addSlot(new SlotItemHandler(handler, index, x, y));
			x += dx;
			index++;
		}
		return index;
	}

	protected void addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy)
	{
		for (int j = 0; j < verAmount; j++)
		{
			index = addSlotRange(handler, index, x, y, horAmount, dx);
			y += dy;
		}
	}

	protected void layoutPlayerInventorySlots(IItemHandler handler, int leftCol, int topRow)
	{
		addSlotBox(handler, 9, leftCol, topRow, 9, 18, 3, 18);
		topRow += 58;
		addSlotRange(handler, 0, leftCol, topRow, 9, 18);
	}

	@Override
	public ItemStack quickMoveStack(Player playerIn, int index)
	{
		return ItemStack.EMPTY;
	}
}
