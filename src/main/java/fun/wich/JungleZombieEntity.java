package fun.wich;

import net.minecraft.entity.*;
import net.minecraft.entity.conversion.EntityConversionContext;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class JungleZombieEntity extends ZombieEntity implements Shearable {
	public JungleZombieEntity(EntityType<? extends ZombieEntity> entityType, World world) { super(entityType, world); }
	@Override
	public boolean tryAttack(ServerWorld world, Entity target) {
		boolean bl = super.tryAttack(world, target);
		if (bl && this.getMainHandStack().isEmpty() && target instanceof LivingEntity living) {
			float f = this.getEntityWorld().getLocalDifficulty(this.getBlockPos()).getLocalDifficulty();
			living.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 140 * (int)f), this);
		}
		return bl;
	}
	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(Items.SHEARS) && this.isShearable()) {
			if (this.getEntityWorld() instanceof ServerWorld serverWorld) {
				this.sheared(serverWorld, SoundCategory.PLAYERS, itemStack);
				this.emitGameEvent(GameEvent.SHEAR, player);
				itemStack.damage(1, player, hand.getEquipmentSlot());
			}
			return ActionResult.SUCCESS;
		}
		else return super.interactMob(player, hand);
	}
	@Override
	public void sheared(ServerWorld world, SoundCategory shearedSoundCategory, ItemStack shears) {
		world.playSoundFromEntity(null, this, JungleZombiesMod.ENTITY_JUNGLE_ZOMBIE_SHEAR, shearedSoundCategory, 1, 1);
		this.convertTo(EntityType.ZOMBIE, EntityConversionContext.create(this, false, false), (zombie) -> {
			this.forEachShearedItem(world, JungleZombiesMod.JUNGLE_ZOMBIE_SHEARING, shears, (worldx, stack) -> {
				for (int i = 0; i < stack.getCount(); ++i) {
					worldx.spawnEntity(new ItemEntity(this.getEntityWorld(), this.getX(), this.getBodyY(1), this.getZ(), stack.copyWithCount(1)));
				}
			});
		});
	}
	@Override public boolean isShearable() { return this.isAlive() && !this.isBaby(); }
	@Override protected SoundEvent getAmbientSound() { return JungleZombiesMod.ENTITY_JUNGLE_ZOMBIE_AMBIENT; }
	@Override protected SoundEvent getHurtSound(DamageSource source) { return JungleZombiesMod.ENTITY_JUNGLE_ZOMBIE_HURT; }
	@Override protected SoundEvent getDeathSound() { return JungleZombiesMod.ENTITY_JUNGLE_ZOMBIE_DEATH; }
	@Override protected SoundEvent getStepSound() { return JungleZombiesMod.ENTITY_JUNGLE_ZOMBIE_STEP; }
	@Override protected boolean canConvertInWater() { return false; }
}
