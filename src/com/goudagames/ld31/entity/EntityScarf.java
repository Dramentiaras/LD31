package com.goudagames.ld31.entity;

import com.goudagames.ld31.render.RenderEngine;

public class EntityScarf extends Entity {

	public EntityScarf(float x, float y) {
		
		super(x, y, 32f, 16f);
	}
	
	@Override
	public void render() {
		
		RenderEngine.instance.setRGBA(1f, 1f, 1f, 1f);
		RenderEngine.instance.renderTexturedQuad(x, y, width, height, 0f, 0f, 114f, 16f, 8f, true, "entities");
	}
}
