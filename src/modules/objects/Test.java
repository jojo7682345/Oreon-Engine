package modules.objects;

import core.configs.Default;
import core.renderer.Renderer;
import core.texturing.Sprite;
import core.utils.Constants;
import modules.shaders.SpriteShader;

public class Test extends SpriteObject {

	// Can be on another thread;
	public void onCreate() {
		Renderer renderer = new Renderer();
		addComponent(Constants.MODEL_COMPONENT, renderer);
	}

	public synchronized void allocate() {
		setSprite(Sprite.load("ground"));
		scale();
		((Renderer) getComponent(Constants.MODEL_COMPONENT)).setRenderData(getMesh(), new Default(), SpriteShader.getInstance());
		
	}

}
