package com.goudagames.ld31.base;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.goudagames.ld31.entity.Entity;
import com.goudagames.ld31.level.Level;
import com.goudagames.ld31.texture.TextureLibrary;
import com.goudagames.ld31.util.Time;

public class Game {

	private boolean running = false;
	public Level level;
	private ArrayList<Entity> entities;
	private ArrayList<Entity> additions;
	private ArrayList<Entity> removals;
	
	public Game() {
		
		Time.init();
		
		TextureLibrary.load("textures/tileset.png", "tileset", GL11.GL_NEAREST, GL11.GL_NEAREST);
		level = new Level();
		
		entities = new ArrayList<Entity>();
		additions = new ArrayList<Entity>();
		removals = new ArrayList<Entity>();
		
		start();
	}
	
	public void start() {
		
		running = true;
		loop();
	}
	
	public void loop() {
		
		while (running) {
			
			update(Time.getDelta());
			render();
			
			Display.update();
			Time.update();
			
			if (Display.isCloseRequested()) {
				
				stop();
			}
			
			Display.sync(60);
		}
	}
	
	public void stop() {
		
		running = false;
	}
	
	public void update(float delta) {
		
		for (int i = 0; i < entities.size(); i++) {
			
			Entity e = entities.get(i);
			
			e.update(delta);
		}
		
		for (Entity e : additions) {
			
			entities.add(e);
		}
		
		for (Entity e : removals) {
			
			entities.remove(e);
		}
	}
	
	public void render() {
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		level.render();
		
		for (Entity e : entities) {
			
			e.render();
		}
	}
}
