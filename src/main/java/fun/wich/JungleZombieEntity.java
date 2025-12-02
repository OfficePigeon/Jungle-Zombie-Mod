package fun.wich;

import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
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
	public boolean tryAttack(Entity target) {
		boolean bl = super.tryAttack(target);
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
			this.sheared(SoundCategory.PLAYERS);
			this.emitGameEvent(GameEvent.SHEAR, player);
			if (!this.getWorld().isClient) itemStack.damage(1, player, getSlotForHand(hand));
			return ActionResult.SUCCESS;
		}
		else return super.interactMob(player, hand);
	}
	@Override
	public void sheared(SoundCategory shearedSoundCategory) {
		World world = this.getEntityWorld();
		world.playSoundFromEntity(null, this, JungleZombiesMod.ENTITY_JUNGLE_ZOMBIE_SHEAR, shearedSoundCategory, 1, 1);
		this.convertTo(EntityType.ZOMBIE, true);
		if (world instanceof ServerWorld serverWorld) {
			LootTable lootTable = serverWorld.getServer().getReloadableRegistries().getLootTable(JungleZombiesMod.JUNGLE_ZOMBIE_SHEARING);
			LootContextParameterSet lootContextParameterSet = new LootContextParameterSet.Builder(serverWorld)
					.add(LootContextParameters.ORIGIN, this.getPos())
					.add(LootContextParameters.THIS_ENTITY, this)
					.build(LootContextTypes.SHEARING);
			for (ItemStack itemStack : lootTable.generateLoot(lootContextParameterSet)) {
				this.dropStack(itemStack, this.getHeight());
			}
		}
	}
	@Override public boolean isShearable() { return this.isAlive() && !this.isBaby(); }
	@Override protected SoundEvent getAmbientSound() { return JungleZombiesMod.ENTITY_JUNGLE_ZOMBIE_AMBIENT; }
	@Override protected SoundEvent getHurtSound(DamageSource source) { return JungleZombiesMod.ENTITY_JUNGLE_ZOMBIE_HURT; }
	@Override protected SoundEvent getDeathSound() { return JungleZombiesMod.ENTITY_JUNGLE_ZOMBIE_DEATH; }
	@Override protected SoundEvent getStepSound() { return JungleZombiesMod.ENTITY_JUNGLE_ZOMBIE_STEP; }
	protected boolean canConvertInWater() { return false; }
}
