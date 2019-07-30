package modules.shaders;

import core.model.Material;
import core.scene.GameObject;
import core.shaders.Shader;
import core.utils.ResourceLoader;

public class DefaultModelShader extends Shader {

	private static DefaultModelShader instance;

	public static DefaultModelShader getInstance() {
		if (instance == null) {
			instance = new DefaultModelShader();
		}
		return instance;
	}

	public DefaultModelShader() {
		addVertexShader(ResourceLoader.loadShader("shaders/defaultVS.glsl"));
		addFragmentShader(ResourceLoader.loadShader("shaders/defaultFS.glsl"));
		compileShader();

		addUniform("modelViewProjectionMatrix");
		addUniform("alpha");
		addUniform("color");
		addUniform("specularFactor");
		// addUniform("tex");
	}

	@Override public void updateUniforms(GameObject object) {
		setUniform("modelViewProjectionMatrix", object.getWorldTransform().getModelViewProjectionMatrix());
		Material m = new Material();
		setUniformf("alpha", m.getAlpha());
		setUniform("color", m.getColor());
		setUniformf("specularFactor", m.getShininess());

		// setUniformi("tex", 0);
	}

}
