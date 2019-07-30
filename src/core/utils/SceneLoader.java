package core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import core.kernel.CoreEngine;
import core.math.Transform;
import core.scene.GameObject;

public class SceneLoader {
	private static List<GameObject>	loadedObjects	= new ArrayList<>();
	private static List<GameObject>	preLoadedObjects	= new ArrayList<>();
	private static boolean			finished;
	private static boolean			newScene		= false;

	public static void loadScene(String string, boolean newScene) {
		loadScene(new File("./res/scenes/" + string + ".scene"),newScene);

	}

	public static void addObject(GameObject o) {
		Thread thread = new Thread(() -> {

			preLoadedObjects.add(o);
			o.onCreate();
			finished = true;
		});
		thread.start();
	}

	public static void loadScene(File file, boolean newScene) {
		SceneLoader.newScene = newScene;
		Thread thread = new Thread(() -> {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line;
				while ((line = reader.readLine()) != null) {
					if (line.startsWith("#")) {
						continue;
					}
					String[] data = line.split("/");
					GameObject object = (GameObject) (Class.forName(data[0]).newInstance());
					object.setWorldTransform(Transform.fromString(data[1]));
					object.setLocalTransform(Transform.fromString(data[2]));
					object.setName(data[3]);
					preLoadedObjects.add(object);
					object.onCreate();
				}

				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {

				e.printStackTrace();
			} catch (IllegalAccessException e) {

				e.printStackTrace();
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			}
			finished = true;
		});
		thread.start();
	}

	public static void update(CoreEngine game) {
		if (finished) {
			if (newScene) {
				game.clearObjects();
				newScene = false;
				
			}
			loadedObjects.addAll(preLoadedObjects);
			preLoadedObjects.clear();
			for (GameObject o : loadedObjects) {
				game.addObject(o);
			}
			loadedObjects.clear();
			finished = false;
		}
	}

	public static void addAllObject(List<GameObject> addList) {
		for(GameObject object : addList) {
			object.onCreate();
		}
		preLoadedObjects.addAll(addList);
		finished = true;
	}

}
