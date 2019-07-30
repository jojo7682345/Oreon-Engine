package modules.objects;

import core.configs.Default;
import core.kernel.Input;
import core.renderer.Renderer;
import core.texturing.Sprite;
import core.utils.Constants;
import core.utils.SceneLoader;
import modules.shaders.SpriteShader;

public class TitleScreen extends SpriteObject{
	
	public void onCreate() {
		Renderer renderer = new Renderer();
		getWorldTransform().setScaling(320, 180, 0);
		addComponent(Constants.RENDERER_COMPONENT, renderer);
	}

	public synchronized void allocate() {
		setSprite(Sprite.load("titleScreen"));
		getSprite().setFrameRate(20);
		scale();
		((Renderer) getComponent(Constants.RENDERER_COMPONENT)).setRenderData(getMesh(), new Default(), SpriteShader.getInstance());;
		
	}
	
	public void update() {
		super.update();
		Input input = Input.getInstance();
		if(input.getPushedKeys().size()>0) {
			SceneLoader.loadScene("stage1",true);
		}
	}
}
