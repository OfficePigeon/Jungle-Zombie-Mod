package fun.wich.client;

import fun.wich.JungleZombieEntity;
import fun.wich.JungleZombiesMod;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.DrownedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class JungleZombieOverlayFeatureRenderer extends FeatureRenderer<JungleZombieEntity, DrownedEntityModel<JungleZombieEntity>> {
	private static final Identifier SKIN = Identifier.of(JungleZombiesMod.MOD_ID, "textures/entity/zombie/jungle_outer_layer.png");
	private final DrownedEntityModel<JungleZombieEntity> model;
	public JungleZombieOverlayFeatureRenderer(FeatureRendererContext<JungleZombieEntity, DrownedEntityModel<JungleZombieEntity>> context, EntityModelLoader loader) {
		super(context);
		this.model = new DrownedEntityModel<>(loader.getModelPart(EntityModelLayers.DROWNED_OUTER));
	}
	@Override
	public void render(MatrixStack matrixStack, VertexConsumerProvider orderedRenderCommandQueue, int light, JungleZombieEntity state, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		render(this.getContextModel(), this.model, SKIN, matrixStack, orderedRenderCommandQueue, light, state, limbAngle, limbDistance, animationProgress, headYaw, headPitch, tickDelta, -1);
	}
}
