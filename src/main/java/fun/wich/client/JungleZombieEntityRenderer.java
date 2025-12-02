package fun.wich.client;

import fun.wich.JungleZombieEntity;
import fun.wich.JungleZombiesMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieBaseEntityRenderer;
import net.minecraft.client.render.entity.model.DrownedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class JungleZombieEntityRenderer extends ZombieBaseEntityRenderer<JungleZombieEntity, DrownedEntityModel<JungleZombieEntity>> {
	private static final Identifier TEXTURE = Identifier.of(JungleZombiesMod.MOD_ID, "textures/entity/zombie/jungle.png");
	public JungleZombieEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new DrownedEntityModel<>(context.getPart(EntityModelLayers.DROWNED)), new DrownedEntityModel<>(context.getPart(EntityModelLayers.DROWNED_INNER_ARMOR)), new DrownedEntityModel<>(context.getPart(EntityModelLayers.DROWNED_OUTER_ARMOR)));
		this.addFeature(new JungleZombieOverlayFeatureRenderer(this, context.getModelLoader()));
	}
	public Identifier getTexture(JungleZombieEntity state) { return TEXTURE; }
}