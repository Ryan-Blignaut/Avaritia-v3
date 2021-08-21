package thesilverecho.avaritia.common.item;

import net.minecraft.core.NonNullList;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantments;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.init.ModGroup;
import thesilverecho.avaritia.common.init.ModTiers;

import javax.annotation.Nonnull;

public class InfinitySword extends SwordItem
{

	public InfinitySword()
	{
		super(ModTiers.INFINITY_SWORD, 10001, 1.4f, new Properties().tab(ModGroup.AVARITIA).rarity(Avaritia.COSMIC));
	}

	@Override
	public boolean hurtEnemy(@Nonnull ItemStack stack, @Nonnull LivingEntity target, LivingEntity attacker)
	{
		if (attacker.level.isClientSide)
			return true;
		target.hurtTime = 60;
//		target.getCombatTracker().recordDamage(new InfinitySwordDamageSource(attacker), target.getHealth(), target.getHealth());
		target.setHealth(0);
		target.die(new EntityDamageSource("infinity", attacker));
		return super.hurtEnemy(stack, target, attacker);
	}

	@Override
	public void setDamage(ItemStack stack, int damage)
	{
		super.setDamage(stack, 0);
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items)
	{
		if (group == this.category)
		{
			ItemStack pick = new ItemStack(this);
			pick.enchant(Enchantments.MOB_LOOTING, 10);
			items.add(pick);
		}
		super.fillItemCategory(group, items);
	}

}
