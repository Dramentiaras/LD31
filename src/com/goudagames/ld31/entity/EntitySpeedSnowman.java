package com.goudagames.ld31.entity;

import java.awt.Rectangle;
import java.util.Random;

import com.goudagames.ld31.base.Game;
import com.goudagames.ld31.render.RenderEngine;

public class EntitySpeedSnowman extends EntityBasicSnowman {

	public EntitySpeedSnowman(float x, float y) {
		
		super(x, y, 36f, 36f);
		MAX_SPEED = 280f;
		health = 15f;
	}

	public void render() {
		
		float u = (frame % 8f) * 18f;
		float v = (float)Math.floor(frame / 8) * 18f + 96f;
		
		RenderEngine.instance.setRGBA(1f, 1f, 1f, 1f);
		RenderEngine.instance.renderTexturedQuad(x, y, width, height, 0f, u, v, 18f, 18f, true, "entities");
	}
	
	@Override
	public void die() {
		
		Random r = new Random();
		
		float f = r.nextFloat();
		
		if (f > 0.85f) {
			
			Game.instance.addEntity(new EntityScarf(x, y));
		}
		
		super.die();
	}
	
	@Override
	public Rectangle getBounds() {
		
		return new Rectangle((int)(x - width / 2f), (int)(y - height / 2f), 36, 36);
	}
}
