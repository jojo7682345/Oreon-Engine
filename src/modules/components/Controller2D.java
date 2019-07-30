package modules.components;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import core.kernel.CoreEngine;
import core.kernel.Input;
import core.scene.Component;

public class Controller2D extends Component {

	private float speed = 0;

	public Controller2D() {};
	public Controller2D(float speed) {
		setSpeed(speed);
	}
	
	public void update() {
		Input input = Input.getInstance();
		float xAxis = 0;
		float yAxis = 0;
		xAxis += (input.isKeyHold(GLFW_KEY_A)?-1:0);
		xAxis += (input.isKeyHold(GLFW_KEY_D)?1:0);
		yAxis += (input.isKeyHold(GLFW_KEY_W)?1:0);
		yAxis += (input.isKeyHold(GLFW_KEY_S)?-1:0);
		if(xAxis*xAxis+yAxis*yAxis==2) {
			xAxis = (float) (Math.cos(45)*xAxis);
			yAxis = (float) (Math.sin(45)*yAxis);	
		}
		xAxis *= (CoreEngine.deltaTime/1000)*speed;
		yAxis *= (CoreEngine.deltaTime/1000)*speed;
//		System.out.println(xAxisddd);
		getWorldTransform().setTranslation(getWorldTransform().getTranslation().add(xAxis,yAxis,0));
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

}
