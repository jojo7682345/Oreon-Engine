package modules.objects;

import core.scene.GameObject;
import core.texturing.Texture2D;

public class TexturedObject extends GameObject{

	private Texture2D texture;

	public Texture2D getTexture() {
		return texture;
	}

	public void setTexture(Texture2D texture) {
		this.texture = texture;
	}
	
}
