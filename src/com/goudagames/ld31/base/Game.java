package com.goudagames.ld31.base;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.goudagames.ld31.entity.Entity;
import com.goudagames.ld31.entity.EntityBasicSnowman;
import com.goudagames.ld31.entity.EntityPlayer;
import com.goudagames.ld31.input.Input;
import com.goudagames.ld31.level.Level;
import com.goudagames.ld31.texture.TextureLibrary;
import com.goudagames.ld31.util.Time;

public class Game {

	public static Game instance;
	
	private boolean running = false;
	public Level level;
	
	private ArrayList<Entity> entities;
	private ArrayList<Entity> additions;
	private ArrayList<Entity> removals;
	
	public EntityPlayer player;
	
	public Game() {
		
		instance = this;
		
		Time.init();
		
		TextureLibrary.load("textures/tileset.png", "tileset");
		TextureLibrary.load("textures/entities.png", "entities");
		
		level = new Level();
		
		entities = new ArrayList<Entity>();
		additions = new ArrayList<Entity>();
		removals = new ArrayList<Entity>();
		
		player = new EntityPlayer(Display.getWidth() / 2f, Display.getHeight() / 2f);
		addEntity(player);
		addEntity(new EntityBasicSnowman(16f, Display.getHeight() / 2f));
		
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
			
			Input.update();
			
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
	
	public void addEntity(Entity e) {
		
		additions.add(e);
	}
	
	public void removeEntity(Entity e) {
		
		removals.add(e);
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
		
		additions.clear();
		removals.clear();
	}
	
	public void render() {
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		level.render();
		
		for (Entity e : entities) {
			
			e.render();
		}
	}
}
