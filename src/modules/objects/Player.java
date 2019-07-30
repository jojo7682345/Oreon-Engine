package modules.objects;

import core.configs.Default;
import core.renderer.Renderer;
import core.texturing.Sprite;
import core.utils.Constants;
import modules.components.CameraFollower;
import modules.components.Controller2D;
import modules.shaders.SpriteShader;

public class Player extends SpriteObject {

	public void onCreate() {
		Renderer renderer = new Renderer();
		addComponent(Constants.RENDERER_COMPONENT, renderer);
		addComponent("Movement", new Controller2D(32));
		addComponent("Camera", new CameraFollower());
	}
	
	public void allocate() {
		setSprite(Sprite.load("player"));
		scale();
		((Renderer)getComponent(Constants.RENDERER_COMPONENT)).setRenderData(getMesh(), Default.getInstance(), SpriteShader.getInstance());	
	}
	
}
