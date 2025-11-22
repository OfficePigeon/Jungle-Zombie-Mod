package fun.wich.client;

import fun.wich.JungleZombiesMod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactories;

@Environment(EnvType.CLIENT)
public class JungleZombiesClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererFactories.register(JungleZombiesMod.JUNGLE_ZOMBIE, JungleZombieEntityRenderer::new);
	}
}
