package modules.shaders;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import core.scene.GameObject;
import core.shaders.Shader;
import core.utils.ResourceLoader;
import modules.objects.TexturedObject;

public class TextureShader extends Shader {

	private static TextureShader instance;

	public static TextureShader getInstance() {
		if (instance == null) {
			instance = new TextureShader();
		}
		return instance;
	}
	
	public TextureShader() {
		addVertexShader(ResourceLoader.loadShader("shaders/textureVertex.glsl"));
		addFragmentShader(ResourceLoader.loadShader("shaders/textureFragment.glsl"));
		compileShader();
		
		addUniform("modelViewProjectionMatrix");
		addUniform("tex");
	}
	
	@Override public void updateUniforms(GameObject object) {
		TexturedObject t = (TexturedObject) object;
		setUniform("modelViewProjectionMatrix",t.getWorldTransform().getModelViewProjectionMatrix());
		glActiveTexture(GL_TEXTURE0);
		t.getTexture().bind();
		setUniformi("tex",0);
	}

}
