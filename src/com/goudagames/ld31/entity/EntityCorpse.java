package com.goudagames.ld31.entity;

import com.goudagames.ld31.render.RenderEngine;

public class EntityCorpse extends Entity {

	public EntityCorpse(float x, float y) {
		
		super(x, y, 32f, 32f);
	}
	
	@Override
	public boolean isCollidable() {
		
		return false;
	}
	
	@Override
	public void render() {
		
		RenderEngine.instance.setRGBA(1f, 1f, 1f, 1f);
		RenderEngine.instance.renderTexturedQuad(x, y, width, height, 0f, 96f, 0f, 16f, 16f, true, "entities");
	}
}
