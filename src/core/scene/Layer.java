package core.scene;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import core.buffers.MeshVBO;
import core.configs.Default;
import core.kernel.ModelLoader;
import core.kernel.Window;
import core.math.Vec3f;
import modules.shaders.LayerShader;

public class Layer {

	public final static int	WIDTH	= Window.getInstance().viewPortWidth;
	public final static int	HEIGHT	= Window.getInstance().viewPortHeight;

	private static List<Layer> layers = new ArrayList<>();

	private ByteBuffer		buffer;
	int						textureID;
	public final float		depth;
	private static MeshVBO	vbo	= new MeshVBO();
	static {
		vbo.allocate(ModelLoader.get("quad", 0).getMesh());
	}

	public Layer(float depth) {
		this.depth = -depth;
		textureID = GL11.glGenTextures();

		buffer = BufferUtils.createByteBuffer(WIDTH * HEIGHT * 4);
		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			buffer.put((byte) 0);
			buffer.put((byte) 0);
			buffer.put((byte) 0);
			buffer.put((byte) 0);
		}
		buffer.flip();

		glBindTexture(GL_TEXTURE_2D, textureID);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, WIDTH, HEIGHT, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		noFilter();
		unbind();
		layers.add(this);
	}

	public void draw(Consumer<Layer> run) {
		run.accept(this);
	}

	private void render() {
		glBindTexture(GL_TEXTURE_2D, textureID);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, WIDTH, HEIGHT, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		noFilter();
		unbind();
		Default.getInstance().enable();
		LayerShader.getInstance().bind();
		LayerShader.getInstance().updateUniforms(this);
		vbo.draw();
		Default.getInstance().disable();
		clear();
	}

	public static void renderAll() {
		for (Layer layer : layers) {
			layer.render();
		}
	}

	public void bind() {
		glBindTexture(GL_TEXTURE_2D, textureID);
	}

	public void delete() {
		glDeleteTextures(textureID);
	}

	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	private void noFilter() {
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	}

	private float	alpha	= 1f;
	private Vec3f	color	= new Vec3f(1, 1, 1);

	public void clear() {
		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			buffer.put((byte) 0);
			buffer.put((byte) 0);
			buffer.put((byte) 0);
			buffer.put((byte) 0);
		}
		buffer.flip();
	}
	
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public void setColor(Vec3f color) {
		this.color = color;
	}

	public void setColor(float r, float g, float b) {
		setColor(new Vec3f(r, g, b));
	}

	public void set(float r, float g, float b, float a) {
		setColor(r, g, b);
		setAlpha(a);
	}

	public void set(Vec3f color, float a) {
		setColor(color);
		setAlpha(a);
	}

	public void drawRectangle(int x, int y, int width, int height) {
		x = Math.max(Math.min(x, WIDTH), 0);
		y = Math.max(Math.min(y, HEIGHT), 0);
		width = Math.max(Math.min(WIDTH - x, x + width), 0)-x;
		height = Math.max(Math.min(HEIGHT - y, y + height), 0)-y;

		for (int xx = x; xx < x + width; xx++) {
			for (int yy = y; yy < y + height; yy++) {
				int i = yy * WIDTH + xx;
				buffer.put(i * 4, (byte) (color.getX() * 255));
				buffer.put(i * 4 + 1, (byte) (color.getY() * 255));
				buffer.put(i * 4 + 2, (byte) (color.getZ() * 255));
				buffer.put(i * 4 + 3, (byte) (alpha * 255));
			}

		}
	}

}
