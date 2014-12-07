package com.goudagames.ld31.base;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.goudagames.ld31.audio.SoundSystem;
import com.goudagames.ld31.entity.Entity;
import com.goudagames.ld31.entity.EntityBasicSnowman;
import com.goudagames.ld31.entity.EntityCorpse;
import com.goudagames.ld31.entity.EntityPlayer;
import com.goudagames.ld31.entity.EntitySpeedSnowman;
import com.goudagames.ld31.gui.GuiGameOver;
import com.goudagames.ld31.gui.GuiHealthBar;
import com.goudagames.ld31.gui.GuiMainMenu;
import com.goudagames.ld31.gui.GuiObject;
import com.goudagames.ld31.gui.GuiPause;
import com.goudagames.ld31.gui.GuiScore;
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
	
	private ArrayList<EntityCorpse> corpses;
	
	private ArrayList<GuiObject> guiObjects;
	private ArrayList<GuiObject> guiAdditions;
	private ArrayList<GuiObject> guiRemovals;
	
	public EntityPlayer player;
	
	private float spawnTime = 0f;
	private float spawnInterval = 5f;
	private float time = 0f;
	
	private int carrots = 0;
	
	private boolean gameover = false;
	public boolean paused = false;
	public boolean mainmenu = true;
	
	public Game() {
		
		instance = this;
		
		Time.init();
		
		TextureLibrary.load("textures/tileset.png", "tileset");
		TextureLibrary.load("textures/entities.png", "entities");
		TextureLibrary.load("textures/gui.png", "gui");
		TextureLibrary.load("textures/font.png", "font");
		
		SoundSystem.load("audio/ambient.wav");
		SoundSystem.load("audio/throw.wav");
		SoundSystem.load("audio/carrot_pickup.wav");
		SoundSystem.load("audio/hurt.wav");
		SoundSystem.load("audio/footstep.wav");
		SoundSystem.load("audio/hit.wav");
		SoundSystem.load("audio/scarf_pickup.wav");
		
		SoundSystem.initAL();
		
		level = new Level();
		
		entities = new ArrayList<Entity>();
		additions = new ArrayList<Entity>();
		removals = new ArrayList<Entity>();
		
		corpses = new ArrayList<EntityCorpse>();
		
		guiObjects = new ArrayList<GuiObject>();
		guiAdditions = new ArrayList<GuiObject>();
		guiRemovals = new ArrayList<GuiObject>();
		
		addGuiObject(new GuiHealthBar(46f, Display.getHeight() - 168f));
		addGuiObject(new GuiScore(48f, 120f));
		addGuiObject(new GuiMainMenu());
		
		player = new EntityPlayer(Display.getWidth() / 2f, 16f);
		entities.add(player);
		
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
		SoundSystem.destroy();
	}
	
	public void addCarrots(int carrots) {
		
		setCarrots(getCarrots() + carrots);
	}
	
	public void setCarrots(int carrots) {
		
		this.carrots = carrots;
	}
	
	public int getCarrots() {
		
		return carrots;
	}
	
	public void reset() {
		
		EntityCorpse corpse = new EntityCorpse(player.x, player.y);
		corpses.add(corpse);
		
		player = new EntityPlayer(Display.getWidth() / 2f, 0f);
		
		entities.clear();
		additions.clear();
		removals.clear();
		
		for (EntityCorpse c : corpses) {
			
			addEntity(c);
		}
		
		setCarrots(0);
		
		addEntity(player);
		
		spawnTime = 0f;
		spawnInterval = 5f;
		time = 0f;
		
		gameover = false;
	}
	
	public void addGuiObject(GuiObject o) {
		
		guiAdditions.add(o);
	}
	
	public void removeGuiObject(GuiObject o) {
		
		guiRemovals.add(o);
	}
	
	public void addEntity(Entity e) {
		
		additions.add(e);
	}
	
	public void removeEntity(Entity e) {
		
		removals.add(e);
	}
	
	public float resetTime = 0f;
	
	public void lose() {
		
		for (Entity e : entities) {
			
			e.reset();
		}
		gameover = true;
		resetTime = 0f;
	}
	
	public void update(float delta) {
		
		if (!gameover && !paused && !mainmenu) {
			spawnTime += delta;
			time += delta;
			
			if (spawnInterval > 2f) {
				
				spawnInterval = 5f - 3f * time / 120f;
				
				if (spawnInterval < 2f) spawnInterval = 2f;
			}
			
			if (spawnTime > spawnInterval) {
				
				Random r = new Random();
				
				int amount = 1;
				float f = r.nextFloat();
				
				if (f < (time - 210f) / 60f) {
					
					amount = 3;
				}
				else if (f < (time - 120f) / 120f) {
					
					amount = 2;
				}
				
				for (int i = 0; i < amount; i++) {
					
					int x = r.nextInt(level.getWidth());
					int y = r.nextInt(level.getHeight());
					
					float rand = r.nextFloat();
					float chance = ((time - 30f) / 60f) * 0.2f;
					
					if (chance < 0f) chance = 0f;
					if (chance > 0.2f) chance = 0.2f;
					
					if (rand >= 0.75f - chance) {
						
						addEntity(new EntitySpeedSnowman(x * 48f + 24f, y * 48f + 56f));
					}
					else {
						
						addEntity(new EntityBasicSnowman(x * 48f + 24f, y * 48f + 56f));
					}
				}
				
				spawnTime = 0f;
			}
		}
		
		if (gameover && resetTime < 1f) {
			
			resetTime += delta;
			if (resetTime >= 1f) {
					
				addGuiObject(new GuiGameOver());
			}
		}
		
		if (!paused && !mainmenu) {
			for (int i = 0; i < entities.size(); i++) {
				
				Entity e = entities.get(i);
				
				e.update(delta);
				
				if (!gameover) {
					level.checkTileCollision(e);
					
					if (e.isCollidable()) {
						for (int j = i + 1; j < entities.size(); j++) {
							
							Entity e1 = entities.get(j);
							
							if (e1.isCollidable()) {
								if (e.getBounds().intersects(e1.getBounds())) {
									
									e.collide(e1);
									e1.collide(e);
								}
							}
						}
					}
				}
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
		
		if (!paused && !gameover && !mainmenu) {
			
			if (Input.isKeyPressed(Keyboard.KEY_ESCAPE)) {
				
				paused = true;
				addGuiObject(new GuiPause());
			}
		}
		
		for (GuiObject o : guiObjects) {
			
			o.update(delta);
		}
		
		for (GuiObject o : guiAdditions) {
			
			guiObjects.add(o);
		}
		
		for (GuiObject o : guiRemovals) {
			
			guiObjects.remove(o);
		}
		
		guiAdditions.clear();
		guiRemovals.clear();
	}
	
	public void render() {
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		level.render();
		
		for (Entity e : entities) {
			
			e.render();
		}
		
		level.renderForeground();
		
		for (GuiObject o : guiObjects) {
			
			o.render();
		}
	}
}
