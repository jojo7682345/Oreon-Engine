package modules.objects;

import core.kernel.CoreEngine;
import core.kernel.Input;
import core.texturing.Sprite;
import core.utils.Audio;

public class Test extends SpriteObject {

	public Test() {
		super(Sprite.load("wall"));
	}

	@Override public void onCreate() {
		scale();
		setIndex(2);
		setFramerate(0);
	}

	float delay = .5f;

	public void update() {
		super.update();
		if (!Input.getInstance().getKeysHolding().isEmpty() && delay < 0) {
			Audio.playSound("snd_reload", (int[] pointers) -> {
				Audio.setGain(0.4f, pointers); // Any of these are optional
				Audio.setPitch(1.2f, pointers);
				Audio.setLooping(true, pointers);
			});
			delay = 0.5f;
		}
		delay -= CoreEngine.deltaTime / 1000;

	}

}
