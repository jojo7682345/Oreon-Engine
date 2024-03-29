package core.kernel;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL40;
import org.lwjgl.opengl.GL43;

import core.scene.Scene;
import core.utils.Audio;
import core.utils.Constants;

/**
 * 
 * @author oreon3D CoreEngine implements the game loop and manages window close
 *         requests. On close request the CoreEngine ensures a clean shutdown of
 *         the RenderingEngine and modules.
 *
 */
public class CoreEngine {

	private static float framerate = 120;
	private static float frameTime = 1.0f/framerate;
	private boolean			isRunning;
	private RenderingEngine	renderingEngine;

	public static float deltaTime = 0;

	@SuppressWarnings("unused") private GLFWErrorCallback errorCallback;
	

	public void createWindow(int width, int height, int viewPortWidth, int viewPortHeight, boolean is3D) {
		glfwInit();

		glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

		Window.getInstance().create(width, height, viewPortWidth, viewPortHeight, is3D);

		renderingEngine = new RenderingEngine();

		getDeviceProperties();
	}

	public void init() {
		core.configs.Default.init();
		renderingEngine.init();
		Audio.init();
		Scene.init(renderingEngine);
		
	}

	public void start() {
		if (isRunning)
			return;

		run();
	}

public void run() {
		
		this.isRunning = true;
		
		int frames = 0;
		long frameCounter = 0;
		
		long lastTime = System.nanoTime();
		double unprocessedTime = 0;
		
		// Rendering Loop
		while(isRunning)
		{
			boolean render = false;
			
			long startTime = System.nanoTime();
			long passedTime = startTime - lastTime;
			lastTime = startTime;
			
			unprocessedTime += passedTime / (double) Constants.NANOSECOND;
			frameCounter += passedTime;
		
			deltaTime = Math.max(passedTime/1000f, frameTime*1000000000)/1000000;
			while(unprocessedTime > frameTime)
			{

				render = true;
				unprocessedTime -= frameTime;
				
				if(Window.getInstance().isCloseRequested())
					stop();
				
				update();
				
				if(frameCounter >= Constants.NANOSECOND)
				{
					System.out.println(frames);
					frames = 0;
					frameCounter = 0;
					
				}
			}
			if(render)
			{
				render();
				
				frames++;
			}
			else
			{
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}		
		}
		
		cleanUp();	
	}

	

	public void stop() {
		if (!isRunning)
			return;

		isRunning = false;
	}

	private void render() {
		renderingEngine.render();
	}

	private void update() {
		Input.getInstance().update();
		Camera.getInstance().update();
		Audio.update();
		renderingEngine.update();
	}

	private void cleanUp() {
		renderingEngine.clearRenderingQueue();
		Audio.destroy();
		Window.getInstance().dispose();
		glfwTerminate();
		System.exit(0);
	}

	private void getDeviceProperties() {
		System.out.println("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION) + " bytes");
		System.out.println("Max Geometry Uniform Blocks: " + GL31.GL_MAX_GEOMETRY_UNIFORM_BLOCKS + " bytes");
		System.out.println("Max Geometry Shader Invocations: " + GL40.GL_MAX_GEOMETRY_SHADER_INVOCATIONS + " bytes");
		System.out.println("Max Uniform Buffer Bindings: " + GL31.GL_MAX_UNIFORM_BUFFER_BINDINGS + " bytes");
		System.out.println("Max Uniform Block Size: " + GL31.GL_MAX_UNIFORM_BLOCK_SIZE + " bytes");
		System.out.println("Max SSBO Block Size: " + GL43.GL_MAX_SHADER_STORAGE_BLOCK_SIZE + " bytes");
	}
	
	public RenderingEngine getRenderingEngine() {
		return renderingEngine;
	}
	
}
