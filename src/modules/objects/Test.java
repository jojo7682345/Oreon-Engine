package modules.objects;

import core.kernel.CoreEngine;
import core.kernel.Input;
import core.kernel.Window;
import core.scene.Layer;
import core.texturing.Sprite;
import core.utils.Audio;

public class Test extends SpriteObject {

	private Layer layer;

	public Test() {
		super(Sprite.load("wall"));
	}

	@Override public void onCreate() {
		scale();
		setIndex(2);
		setFramerate(0);
		layer = new Layer(0.5f);
	}

	float delay = 0f;

	public void update() {
		super.update();
		if (!Input.getInstance().getKeysHolding().isEmpty() && delay < 0) {
			Audio.playSound("snd_reload", (int[] pointers) -> {
				Audio.setGain(1f, pointers); // Any of these are optional
				Audio.setPitch(1.2f, pointers);
				Audio.setLooping(false, pointers);
			});
			delay = 0.2f;
		}
		delay -= CoreEngine.deltaTime / 1000;
		layer.draw((Layer layer) -> {
			layer.setColor(1, 1, 1);
			layer.drawRectangle(0, 0, (int) (Window.getInstance().viewPortWidth * (delay / 0.2f)), (int) (Window.getInstance().viewPortHeight * (delay / 0.2f)));
		});

	}

}
