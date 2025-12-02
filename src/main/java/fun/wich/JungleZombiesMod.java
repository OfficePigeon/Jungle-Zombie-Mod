package fun.wich;

import fun.wich.mixin.JungleZombies_LootTablesMixin;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;

import java.util.function.Function;

public class JungleZombiesMod implements ModInitializer {
	public static final String MOD_ID = "wich";
	public static final SoundEvent ENTITY_JUNGLE_ZOMBIE_AMBIENT = register("entity.jungle_zombie.ambient");
	public static final SoundEvent ENTITY_JUNGLE_ZOMBIE_DEATH = register("entity.jungle_zombie.death");
	public static final SoundEvent ENTITY_JUNGLE_ZOMBIE_HURT = register("entity.jungle_zombie.hurt");
	public static final SoundEvent ENTITY_JUNGLE_ZOMBIE_STEP = register("entity.jungle_zombie.step");
	public static final SoundEvent ENTITY_PARROT_IMITATE_JUNGLE_ZOMBIE = register("entity.parrot.imitate.jungle_zombie");
	public static final SoundEvent ENTITY_JUNGLE_ZOMBIE_SHEAR = register("entity.jungle_zombie.shear");
	private static SoundEvent register(String path) {
		Identifier id = Identifier.of(MOD_ID, path);
		return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
	}
	public static final TagKey<Biome> TAG_SPAWNS_JUNGLE_ZOMBIES = TagKey.of(RegistryKeys.BIOME, Identifier.of(MOD_ID, "spawns_jungle_zombies"));
	public static final EntityType<JungleZombieEntity> JUNGLE_ZOMBIE = register(
			"jungle_zombie",
			EntityType.Builder.create(JungleZombieEntity::new, SpawnGroup.MONSTER)
					.dimensions(0.6F, 1.99F)
					.eyeHeight(1.74F)
					.vehicleAttachment(-0.7F)
					.maxTrackingRange(8)
					.notAllowedInPeaceful()
	);
	private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> type) {
		RegistryKey<EntityType<?>> key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(MOD_ID, name));
		return Registry.register(Registries.ENTITY_TYPE, key, type.build(key));
	}
	public static final Item JUNGLE_ZOMBIE_SPAWN_EGG = register("jungle_zombie_spawn_egg", SpawnEggItem::new, new Item.Settings().spawnEgg(JUNGLE_ZOMBIE));
	public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
		RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, name));
		return Registry.register(Registries.ITEM, key, itemFactory.apply(settings.registryKey(key)));
	}
	public static final RegistryKey<LootTable> JUNGLE_ZOMBIE_SHEARING = JungleZombies_LootTablesMixin.registerLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(MOD_ID, "shearing/jungle_zombie")));
	@Override
	public void onInitialize() {
		//Attributes
		FabricDefaultAttributeRegistry.register(JUNGLE_ZOMBIE, JungleZombieEntity.createZombieAttributes());
		//Spawning
		SpawnRestriction.register(JUNGLE_ZOMBIE, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, JungleZombieEntity::canSpawnInDark);
		BiomeModifications.addSpawn(BiomeSelectors.tag(TAG_SPAWNS_JUNGLE_ZOMBIES),
				SpawnGroup.MONSTER, JUNGLE_ZOMBIE, 40, 1, 4);
		//Items
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(itemGroup -> itemGroup.add(JUNGLE_ZOMBIE_SPAWN_EGG));
	}
}