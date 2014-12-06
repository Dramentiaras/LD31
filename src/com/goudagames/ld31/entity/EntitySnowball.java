package com.goudagames.ld31.entity;

import com.goudagames.ld31.base.Game;
import com.goudagames.ld31.render.RenderEngine;

public class EntitySnowball extends Entity {

	private float sx, sy;
	
	public EntitySnowball(float x, float y, float xMotion, float yMotion) {
		
		super(x, y, 8f, 8f);
		this.xMotion = xMotion;
		this.yMotion = yMotion;
		this.sx = x;
		this.sy = y;
	}
	
	@Override
	public void update(float delta) {
		
		float dx = x - sx;
		float dy = y - sy;
		
		float dist = (float)Math.sqrt(dx * dx + dy * dy);
		
		if (dist > 380f) {
			
			Game.instance.removeEntity(this);
		}
		
		super.update(delta);
	}
	
	@Override
	public void render() {
		
		RenderEngine.instance.setRGBA(1f, 1f, 1f, 1f);
		
		RenderEngine.instance.renderTexturedQuad(x, y, width, height, 0f, 96f, 0f, 4f, 4f, true, "entities");
	}
}
