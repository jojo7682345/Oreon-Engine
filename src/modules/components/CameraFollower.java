package modules.components;

import core.kernel.Camera;
import core.math.Vec3f;
import core.scene.Component;

public class CameraFollower extends Component {

	public void update() {
		Vec3f position = getWorldTransform().getTranslation();
		Vec3f camPosition = Camera.getInstance().getPosition();;
		float x = (position.getX() - camPosition.getX()) * 0.1f;
		float y = (position.getY() - camPosition.getY()) * 0.1f;
		Camera.getInstance().setPosition(camPosition.add(x, y, 0));
	}

}
