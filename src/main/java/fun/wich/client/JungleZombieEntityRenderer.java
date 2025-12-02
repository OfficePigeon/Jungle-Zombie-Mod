package fun.wich.client;

import fun.wich.JungleZombieEntity;
import fun.wich.JungleZombiesMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieBaseEntityRenderer;
import net.minecraft.client.render.entity.model.DrownedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EquipmentModelData;
import net.minecraft.client.render.entity.state.ZombieEntityRenderState;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class JungleZombieEntityRenderer extends ZombieBaseEntityRenderer<JungleZombieEntity, ZombieEntityRenderState, DrownedEntityModel> {
	private static final Identifier TEXTURE = Identifier.of(JungleZombiesMod.MOD_ID, "textures/entity/zombie/jungle.png");
	public JungleZombieEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new DrownedEntityModel(context.getPart(EntityModelLayers.DROWNED)), new DrownedEntityModel(context.getPart(EntityModelLayers.DROWNED_BABY)), EquipmentModelData.mapToEntityModel(EntityModelLayers.DROWNED_EQUIPMENT, context.getEntityModels(), DrownedEntityModel::new), EquipmentModelData.mapToEntityModel(EntityModelLayers.DROWNED_BABY_EQUIPMENT, context.getEntityModels(), DrownedEntityModel::new));
		this.addFeature(new JungleZombieOverlayFeatureRenderer(this, context.getEntityModels()));
	}
	@Override public ZombieEntityRenderState createRenderState() { return new ZombieEntityRenderState(); }
	@Override public Identifier getTexture(ZombieEntityRenderState zombieEntityRenderState) { return TEXTURE; }
}