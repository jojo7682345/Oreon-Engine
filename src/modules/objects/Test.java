package modules.objects;

import core.texturing.Sprite;

public class Test extends SpriteObject{

	public Test() {
		super(Sprite.load("titleScreen"));
	}

	@Override public void onCreate() {
		scale();
		setFramerate(20);
	}

}
