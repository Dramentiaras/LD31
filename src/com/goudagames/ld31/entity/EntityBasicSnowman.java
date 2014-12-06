package com.goudagames.ld31.entity;

import com.goudagames.ld31.base.Game;
import com.goudagames.ld31.render.RenderEngine;

public class EntityBasicSnowman extends Entity {
	
	public static final float MAX_SPEED = 200f;
	int frame = 3;

	public EntityBasicSnowman(float x, float y) {
		
		super(x, y, 32f, 64f);
	}
	
	@Override
	public void update(float delta) {
		
		float xDist = Game.instance.player.x - x;
		float yDist = Game.instance.player.y - y;
		
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
		
		super.update(delta);
	}
	
	@Override
	public void render() {
		
		float u = (frame % 8f) * 32f;
		float v = (float)Math.floor(frame / 8.0) * 32f + 64f;
		
		RenderEngine.instance.setRGBA(1f, 1f, 1f, 1f);
		RenderEngine.instance.renderTexturedQuad(x, y, 2f * width, height, 0f, u, v, 32f, 32f, true, "entities");
	}
}
