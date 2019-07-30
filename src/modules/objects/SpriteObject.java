package modules.objects;

import core.model.Mesh;
import core.scene.GameObject;
import core.texturing.Sprite;

public class SpriteObject extends GameObject{

	private Sprite sprite;
	
	public void update() {
		sprite.update();
		super.update();
	}

	public Mesh getMesh() {
		return Sprite.getMesh();
	}
	
	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void scale() {
		getWorldTransform().setScaling(sprite.getTexture().getWidth(),sprite.getTexture().getHeight(),0);
	}
	
}
