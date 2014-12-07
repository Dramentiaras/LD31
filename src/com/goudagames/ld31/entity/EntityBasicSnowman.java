package com.goudagames.ld31.entity;

import java.awt.Rectangle;

import com.goudagames.ld31.audio.SoundSystem;
import com.goudagames.ld31.base.Game;
import com.goudagames.ld31.level.Level;
import com.goudagames.ld31.render.RenderEngine;

public class EntityBasicSnowman extends Entity {
	
	public float MAX_SPEED;
	
	int frame = 4;
	
	public float health = 20f;
	
	boolean halted = false;
	float timeHalted = 0f;
	float animTime = 0f;
	
	boolean spawning = true;
	boolean reseting = false;
	
	private float heightHalf, widthHalf;
	
	public EntityBasicSnowman(float x, float y) {
		
		super(x, y, 32f, 64f);
		MAX_SPEED = 200f;
		widthHalf = width / 2f;
		heightHalf = height / 2f;
	}
	
	public EntityBasicSnowman(float x, float y, float width, float height) {
		
		super(x, y, width, height);
	}

	@Override
	public void reset() {
		
		frame = 7;
		xMotion = yMotion = 0f;
		reseting = true;
		spawning = false;
		animTime = 0f;
	}
	
	public int getFrame() {
		
		return frame;
	}
	
	@Override
	public boolean isCollidable() {
		
		return !spawning;
	}
	
	@Override
	public void update(float delta) {
		
		if (spawning) {
			
			animTime += delta;
			
			if (animTime > 0.175f) {
				
				frame += 1;
				
				if (frame == 8) {
					
					spawning = false;
					frame = 0;
				}
				
				animTime = 0f;
			}
		}
		
		if (reseting) {
			
			animTime += delta;
			
			if (animTime > 0.175f) {
				
				frame -= 1;
				
				if (frame == 3) {
					
					reseting = false;
					Game.instance.removeEntity(this);
				}
				
				animTime = 0f;
			}
		}
		
		if (!halted && !spawning && !reseting) {
			float xDist = Game.instance.player.x - x;
			float yDist = (Game.instance.player.y + 16f) - y;
			
			float length = (float)Math.sqrt(xDist * xDist + yDist * yDist);
			
			xDist /= length;
			yDist /= length;
			
			xMotion = xDist * MAX_SPEED;
			yMotion = yDist * MAX_SPEED;
			
			float angle = (float)Math.atan2(yMotion, xMotion);
			
			if (angle >= 0f) {
				
				if (angle <= Math.PI / 4f) {
					
					frame = 0;
				}
				else if (angle > Math.PI / 4f && angle <= Math.PI / 4f * 3f) {
					
					frame = 3;
				}
				else if (angle > Math.PI / 4f * 3f) {
					
					frame = 2;
				}
			}
			else {
				
				angle *= -1f;
				
				if (angle <= Math.PI / 4f) {
					
					frame = 0;
				}
				else if (angle > Math.PI / 4f && angle <= Math.PI / 4f * 3f) {
					
					frame = 1;
				}
				else if (angle > Math.PI / 4f * 3f) {
					
					frame = 2;
				}
			}
		}
		else if (!spawning && !reseting) {
			
			float acc = MAX_SPEED * 1.25f * delta;
			
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
			
			timeHalted += delta;
			
			if (timeHalted > 1f) {
				
				halted = false;
				timeHalted = 0f;
			}
		}
		
		if (health <= 0f) {
			
			die();
		}
		
		super.update(delta);
	}
	
	public void die() {
		
		Game.instance.removeEntity(this);
		Game.instance.addEntity(new EntityCarrot(x + widthHalf, y + heightHalf - 8f, height));
	}
	
	@Override
	public void collide(Entity e) {
		
		if (e instanceof EntityPlayer) {
			
			halted = true;
			xMotion *= -0.5f;
			yMotion *= -0.5f;
		}
		
		if (e instanceof EntitySnowball) {
			
			health -= 5f;
			Game.instance.removeEntity(e);
			SoundSystem.play("hit");
		}
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
		
		if (yMotion > 0f && this.y - height / 8f < wy - 24f) {
			
			yMotion = 0f;
			this.y = wy - 24f;
		}
		else if (yMotion < 0f && this.y - height / 4f > wy + 24f) {
			
			yMotion = 0f;
			this.y = wy + 24f + heightHalf;
		}
	}
	
	@Override
	public void render() {
		
		float u = (frame % 8f) * 32f;
		float v = (float)Math.floor(frame / 8.0) * 32f + 64f;
		
		RenderEngine.instance.setRGBA(1f, 1f, 1f, 1f);
		RenderEngine.instance.renderTexturedQuad(x, y, 2f * width, height, 0f, u, v, 32f, 32f, true, "entities");
	}
	
	@Override
	public Rectangle getBounds() {
		
		return new Rectangle((int)(x - widthHalf), (int)(y - heightHalf), 37, 48);
	}
}
