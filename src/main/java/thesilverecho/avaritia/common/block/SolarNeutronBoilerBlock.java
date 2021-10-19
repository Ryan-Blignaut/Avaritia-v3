package thesilverecho.avaritia.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import thesilverecho.avaritia.common.tile.ExtremeCraftingTableTile;

public class SolarNeutronBoilerBlock extends BaseEntityBlock
{
	public SolarNeutronBoilerBlock()
	{
		super(Properties.of(Material.METAL).sound(SoundType.METAL).strength(2.0f));
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
	{
		if (world.isClientSide)
			return InteractionResult.SUCCESS;
		else
		{
			final BlockEntity blockentity = world.getBlockEntity(pos);
			if (blockentity instanceof ExtremeCraftingTableTile)
				player.openMenu((ExtremeCraftingTableTile) blockentity);

			return InteractionResult.CONSUME;
		}
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		return new ExtremeCraftingTableTile(blockPos, blockState);
	}
}
