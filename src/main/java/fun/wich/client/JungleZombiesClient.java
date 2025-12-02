package fun.wich.client;

import fun.wich.JungleZombiesMod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class JungleZombiesClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(JungleZombiesMod.JUNGLE_ZOMBIE, JungleZombieEntityRenderer::new);
	}
}
