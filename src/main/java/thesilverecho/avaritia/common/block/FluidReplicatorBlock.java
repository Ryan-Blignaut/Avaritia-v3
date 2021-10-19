package thesilverecho.avaritia.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nullable;

public class FluidReplicatorBlock extends BaseEntityBlock
{


	@Override
	public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos)
	{
		final int lightEmission = super.getLightEmission(state, world, pos);
		if (lightEmission >= 15)
			return lightEmission;


//		world.getBlockEntity(pos).getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).ifPresent(iFluidHandler -> {iFluidHandler.});

		return lightEmission;
	}

	protected FluidReplicatorBlock(Properties p_49224_)
	{
		super(p_49224_);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState)
	{
		return null;
	}
}
