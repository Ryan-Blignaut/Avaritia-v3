package thesilverecho.avaritia.common.container;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import thesilverecho.avaritia.common.init.ModContainers;

import java.util.function.Function;

public class ExtremeCraftingTableContainer extends BaseContainer
{

	private final ItemStackHandler result;
	private final Level world;


	public ExtremeCraftingTableContainer(int id, Inventory playerInventory, Function<Player, Boolean> isUsableByPlayer, ItemStackHandler inv)
	{
		super(ModContainers.EXTREME_CRAFTING_TABLE_CONTAINER.get(), id);
		this.isUsableByPlayer = isUsableByPlayer;
		this.result = new ItemStackHandler();
		this.world = playerInventory.player.level;
//		Inventory matrix = new ExtremeCraftingTableInventory(this, inv);
//		addSlot(new ExtremeCraftingTableSlot(result, 0, 206, 89, this, matrix));
//		for (int i = 0; i < 9; i++)
//			for (int j = 0; j < 9; j++)
//				this.addSlot(new Slot(matrix, j + i * 9, 8 + j * 18, 18 + i * 18));
//		layoutPlayerInventorySlots(new InvWrapper(playerInventory), 39, 196);
//		this.slotsChanged(matrix);

	}

	public ExtremeCraftingTableContainer(int windowId, Inventory inventory)
	{
		this(windowId, inventory, p -> false, new ItemStackHandler(81));
	}

	@Override
	public ItemStack quickMoveStack(Player playerIn, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot.hasItem())
		{
			ItemStack slotStack = slot.getItem();
			itemstack = slotStack.copy();

//			output slot
			if (index == 0)
			{
				if (!moveItemStackTo(itemstack, 82/*output slot + 81 crafting slots*/, 118/*82 + inventory slots*/, true))
					return ItemStack.EMPTY;
				slot.onQuickCraft(slotStack, itemstack);
			} else if (index >= 82 && index < 118)
			{
				if (!this.moveItemStackTo(slotStack, 1, 82, false))
					if (index < 109)
					{
						if (!this.moveItemStackTo(slotStack, 109, 118, false))
							return ItemStack.EMPTY;
					} else if (!this.moveItemStackTo(slotStack, 82, 109, false))
						return ItemStack.EMPTY;
			} else if (!this.moveItemStackTo(slotStack, 82, 118, false))
			{
				return ItemStack.EMPTY;
			}

			if (slotStack.isEmpty()) slot.set(ItemStack.EMPTY);
			else slot.setChanged();

			if (slotStack.getCount() == itemstack.getCount()) return ItemStack.EMPTY;

			slot.onTake(playerIn, slotStack);
		}

		return itemstack;
	}


	@Override
	public void slotsChanged(Container matrix)
	{
//		Optional<ExtremeRecipe> recipe = this.world.getRecipeManager().getRecipeFor(ModRecipes.EXTREME_TYPE, matrix, this.world);
//		if (recipe.isPresent())
//		{
//			ItemStack result = recipe.get().assemble(matrix);
//			this.result.setStackInSlot(0, result);
//		} else
//			this.result.setStackInSlot(0, ItemStack.EMPTY);
//
		super.slotsChanged(matrix);

	}

}
