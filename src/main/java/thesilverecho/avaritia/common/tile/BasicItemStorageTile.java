package thesilverecho.avaritia.common.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class BasicItemStorageTile extends BlockEntity
{
	//	Capabilities-> Items, Energy, Fluids, Generic.
//	ArrayList<LazyOptional<? extends INBTSerializable>> optionals = new ArrayList<>();
	protected final LazyOptional<IItemHandler> inventory = LazyOptional.of(this::getInventory);

	@Nonnull
	public abstract ItemStackHandler getInventory();

	public BasicItemStorageTile(BlockEntityType<?> type, BlockPos pos, BlockState state)
	{
		super(type, pos, state);
	}

	protected boolean isWithinUsableDistance(Player player)
	{
		return player.distanceToSqr(worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5) <= 64;
	}

	@Override
	public CompoundTag save(CompoundTag tag)
	{
		final CompoundTag save = super.save(tag);
//		optionals.forEach(lazyOptional ->
//		{
//			save.put("test", lazyOptional.resolve().get().serializeNBT());
//		});
		save.put("storage", getInventory().serializeNBT());
		return save;
	}

	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
		if (tag.contains("storage"))
			getInventory().deserializeNBT(tag.getCompound("storage"));
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
	{
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, this.inventory);

		return super.getCapability(cap, side);
	}

	@Override
	public void setRemoved()
	{
		super.setRemoved();
//		optionals.forEach(LazyOptional::invalidate);
		inventory.invalidate();
	}
}
