package core.kernel;

import core.scene.GameObject;

public class Game {
	
	protected CoreEngine engine;
	
	public Game(){
		engine = new CoreEngine();
	}
	
	public void launch(){
		engine.start();
	}
	
	public void init(){
		engine.init();
	}
	
	public CoreEngine getEngine() {
		return engine;
	}
	public void setEngine(CoreEngine engine) {
		this.engine = engine;
	}
	
	public void addObject(GameObject object) {
		engine.addObject(object);
	}
	
	public void removeObject(GameObject object) {
		engine.removeObject(object);
	}

	public void clearScene() {
		engine.clearObjects();
	}
}
