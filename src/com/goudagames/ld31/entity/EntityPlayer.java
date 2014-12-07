package com.goudagames.ld31.entity;

import java.awt.Rectangle;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.goudagames.ld31.audio.SoundSystem;
import com.goudagames.ld31.base.Game;
import com.goudagames.ld31.input.Input;
import com.goudagames.ld31.level.Level;
import com.goudagames.ld31.render.RenderEngine;

public class EntityPlayer extends Entity {

	public static final float MAX_SPEED = 520f;
	
	public static float speed = 260f;
	int bodyFrame = 0;
	int prevBodyFrame = 0;
	int footFrame = 0;
	int prevFootFrame = 0;
	
	private int scarves = 0;
	
	public float temperature = 37.5f;
	
	int direction = 1;
	boolean flip = false;
	boolean fired = false;
	boolean stagger = false;
	private boolean dead = false;
	
	float timeSinceFire = 1f;
	
	float footAnimTime = 0f;
	float bodyAnimTime = 0f;
	
	private float heightHalf;
	private float widthHalf;
	
	public EntityPlayer(float x, float y) {
		
		super(x, y, 32f, 32f);
		heightHalf = height / 2f;
		widthHalf = width / 2f;
	}
	
	public void fire() {
		
		float ym = 0f, xm = 0f;
		float xOffset = 0f, yOffset = 0f;
		
		if (direction == 0) {
			
			ym = -560f;
		}
		if (direction == 1) {
			
			ym = 560f;
			yOffset = 20f;
		}
		if (direction == 2) {
			
			xm = 560f;
			xOffset = 12f;
		}
		if (direction == 3) {
			
			xm = -560f;
			xOffset = -12f;
		}
		
		timeSinceFire = 0f;
		
		Game.instance.addEntity(new EntitySnowball(x + xOffset, y + yOffset, xm, ym));
		SoundSystem.play("throw", 0.5f);
		fired = true;
	}
	
	private float bodyAnimInterval = 1f / 20f;
	private float prevSpeed = 0f;
	private float footAnimInterval;
	
	@Override
	public void update(float delta) {
		
		footAnimTime += delta;
		bodyAnimTime += delta;
		
		if (!stagger && !dead) {
			if (fired) {
				
				if (bodyAnimTime > bodyAnimInterval) {
					
					if (bodyFrame == 0) {
						
						bodyFrame = 3;
					}
					else {
					
						bodyFrame += 1;
					}
					
					if (bodyFrame == 6) {
						
						bodyFrame = 0;
						fired = false;
					}
					
					bodyAnimTime = 0f;
				}
			}
			else {
				
				timeSinceFire += delta;
				bodyFrame = 0;
			}
			
			if (xMotion != 0f || yMotion != 0f) {
	
				if (speed != prevSpeed) {
					
					footAnimInterval = 0.125f / (speed / 300f);
					prevSpeed = speed;
				}
				
				if (footAnimTime > footAnimInterval) {
					
					if (footFrame == 0) {
						
						if (prevFootFrame != 1) {
							
							prevFootFrame = footFrame;
							footFrame = 1;
						}
						else {
							
							prevFootFrame = footFrame;
							footFrame = 2;
						}
					}
					else {
						
						prevFootFrame = footFrame;
						footFrame = 0;
						SoundSystem.play("footstep", 0.15f);
					}
					
					footAnimTime = 0f;
				}
			}
			else {
				
				footFrame = 0;
			}
			
			if (Input.isKeyDown(Keyboard.KEY_W)) {
				
				yMotion = speed;
				
				if (timeSinceFire > 0.15f) {
					
					direction = 1;
				}
			}
			else if (Input.isKeyDown(Keyboard.KEY_S)) {
				
				yMotion = -speed;
				
				if (timeSinceFire > 0.15f) {
					
					direction = 0;
				}
			}
			else {
				
				yMotion = 0f;
			}
			if (Input.isKeyDown(Keyboard.KEY_D)) {
				
				xMotion = speed;
				
				if (timeSinceFire > 0.15f) {
					
					direction = 2;
				}
			}
			else if (Input.isKeyDown(Keyboard.KEY_A)) {
				
				xMotion = -speed;
				
				if (timeSinceFire > 0.15f) {
					
					direction = 3;
				}
			}
			else {
				
				xMotion = 0f;
			}
			
			if (!fired) {
				
				if (Input.isKeyPressed(Keyboard.KEY_UP)) {
					
					direction = 1;
					fire();
				}
				else if (Input.isKeyPressed(Keyboard.KEY_RIGHT)) {
					
					direction = 2;
					fire();
				}
				else if (Input.isKeyPressed(Keyboard.KEY_DOWN)) {
					
					direction = 0;
					fire();
				}
				else if (Input.isKeyPressed(Keyboard.KEY_LEFT)) {
					
					direction = 3;
					fire();
				}
			}
			
			if (xMotion != 0 && yMotion != 0) {
				
				xMotion /= 1.5;
				yMotion /= 1.5;
			}
		}
		else if (!dead) {
			
			float acc = speed * 1.25f * delta;
			
			if (xMotion != 0f) {
				
				if (xMotion < 0f) {
					
					if ((xMotion += acc) >= 0f) {
						
						xMotion = 0f;
					}
				}
				else if (xMotion > 0f) {
					
					if ((xMotion -= acc) <= 0f) {
						
						xMotion = 0f;
					}
				}
			}
			
			if (yMotion != 0f) {
				
				if (yMotion < 0f) {
					
					if ((yMotion += acc) >= 0f) {
						
						yMotion = 0f;
					}
				}
				else if (yMotion > 0f) {
					
					if ((yMotion -= acc) <= 0f) {
						
						yMotion = 0f;
					}
				}
			}
			
			if (xMotion == 0 && yMotion == 0) {
				
				stagger = false;
			}
		}
		else {
			
			yMotion = xMotion = 0f;
		}
		
		if (x < widthHalf) {
			
			x = widthHalf;
			if (xMotion < 0f) {
				xMotion = 0f;
			}
		}
		
		if (x > Display.getWidth() - widthHalf) {
			
			x = Display.getWidth() - widthHalf;
			if (xMotion < 0f) {
				xMotion = 0f;
			}
		}
		
		if (y < heightHalf) {
			
			y = heightHalf;
			if (yMotion < 0f) {
				yMotion = 0f;
			}
		}
		
		if (y > Display.getHeight() - heightHalf) {
			
			y = Display.getHeight() - heightHalf;
			if (yMotion < 0f) {
				yMotion = 0f;
			}
		}
		
		if (!dead) {
			if (xMotion == 0f && yMotion == 0f) {
				
				addTemperature(-0.5f * delta);
			}
			
			if (temperature <= 18f) {
				
				die();
			}
		}
		
		super.update(delta);
	}
	
	public void addTemperature(float temp) {
		
		this.temperature += temp;
		if (temperature < 18f) temperature = 18f;
		if (temperature > 37.5f) temperature = 37.5f;
	}
	
	public void die() {
		
		Game.instance.lose();
		dead = true;
		direction = 0;
		bodyFrame = 6;
		footFrame = 6;
	}
	
	@Override
	public void collideWithTile(int x, int y, Level level) {
		
		float wx = x * 48f + 24f;
		float wy = y * 48f + 24f;
		
		if (xMotion > 0f && this.x < wx - 24f) {
			
			xMotion = 0f;
			this.x = wx - 24f - 10f;
		}
		else if (xMotion < 0f && this.x > wx + 24f) {
			
			xMotion = 0f;
			this.x = wx + 24f + 10f;
		}
		
		if (yMotion > 0f && this.y - heightHalf < wy - 24f) {
			
			this.y = wy - 24f + heightHalf - 12f;
		}
		else if (yMotion < 0f && this.y > wy + 24f) {
			
			yMotion = 0f;
			this.y = wy + 24f + heightHalf;
		}
	}
	
	@Override
	public void collide(Entity e) {
		
		if (e instanceof EntityBasicSnowman && !stagger) {
			
			SoundSystem.play("hurt");
			
			EntityBasicSnowman snowman = (EntityBasicSnowman)e;
			stagger = true;
			
			addTemperature(-2f);
			
			xMotion = 0f;
			yMotion = 0f;
			
			switch (snowman.getFrame()) {
			
				case 0: {
					xMotion = speed / 1.5f;
					break;
				}
				case 1: {
					yMotion = -speed / 1.5f;
					break;
				}
				case 2: {
					xMotion = -speed / 1.5f;
					break;
				}
				case 3: {
					yMotion = speed / 1.5f;
					break;
				}
			}
		}
		
		if (e instanceof EntityScarf) {
			
			addScarves(1);
			SoundSystem.play("scarf_pickup", 0.25f);
			Game.instance.removeEntity(e);
		}
	}
	
	@Override
	public void render() {
		
		int bf = direction * 16 + bodyFrame;
		int ff = direction * 16 + footFrame;
		
		RenderEngine.instance.setRGBA(1f, 1f, 1f, 1f);
		
		float bu = (bf % 16) * 16f;
		float bv = (float)Math.floor(bf / 16.0) * 16f;
		
		float fu = (ff % 16) * 16f;
		float fv = (float)Math.floor(ff / 16.0) * 16f + 14f;
		
		RenderEngine.instance.renderTexturedQuad(x, y + 2f, width * (flip ? -1f:1f), height - 4f, 0f, bu, bv, 16f, 14f, true, "entities");
		RenderEngine.instance.renderTexturedQuad(x, y - heightHalf + 2f, width * (flip ? -1f:1f), 4f, 0f, fu, fv, 16f, 2f, true, "entities");
	}
	
	public int getScarves() {
		
		return scarves;
	}
	
	public void addScarves(int scarf) {
		
		this.scarves += scarf;
		if (scarves < 0) scarves = 0;
		speed = 260f + 20f * scarves;
		
		if (speed > MAX_SPEED) {
			
			speed = MAX_SPEED;
		}
	}
	
	@Override
	public Rectangle getBounds() {
		
		return new Rectangle((int)(x - widthHalf + 6f), (int)(y - heightHalf), 20, (int)(heightHalf));
	}
}
