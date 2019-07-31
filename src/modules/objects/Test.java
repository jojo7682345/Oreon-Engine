package modules.objects;

import core.kernel.Input;
import core.texturing.Sprite;
import core.utils.Audio;

public class Test extends SpriteObject{

	public Test() {
		super(Sprite.load("wall"));
	}

	@Override public void onCreate() {
		scale();
		setIndex(2);
		setFramerate(0);
	}
	
	public void update() {
		super.update();
		if(!Input.getInstance().getKeysHolding().isEmpty()) {
			Audio.playSound("snd_reload");
		}
	}

}
