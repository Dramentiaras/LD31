package com.goudagames.ld31.entity;

import java.awt.Rectangle;

import com.goudagames.ld31.audio.SoundSystem;
import com.goudagames.ld31.base.Game;
import com.goudagames.ld31.render.RenderEngine;

public class EntityCarrot extends Entity {
 
	private float landY;
	private float rotation;
	
	boolean landed = false;
	
	public EntityCarrot(float x, float y, float height) {
		
		super(x, y, 24f, 8f);
		landY = y - height;
		
		yMotion = 240f;
	}

	@Override
	public void update(float delta) {
		
		float acc = 640f * delta;
		
		if (y > landY) {
			if ((yMotion -= acc) < -360f) {
				
				yMotion = -360f;
			}
			
			rotation += Math.PI * delta;
			
			if (y - landY < 12f) {
				
				landed = true;
			}
		}
		else {
			
			landed = true;
			yMotion = 0f;
			y = landY;
		}
		
		super.update(delta);
	}
	
	@Override
	public boolean isCollidable() {
		
		return landed;
	}
	
	@Override
	public void collide(Entity e) {
		
		if (e instanceof EntityPlayer) {
				
			Game.instance.removeEntity(this);
			Game.instance.addCarrots(1);
			SoundSystem.play("carrot_pickup", 0.5f);
		}
	}
	
	@Override
	public void render() {
		
		RenderEngine.instance.setRGBA(1f, 1f, 1f, 1f);
		RenderEngine.instance.renderTexturedQuad(x, y, width, height, rotation, 116f, 0f, 12f, 4f, true, "entities");
	}
	
	@Override
	public Rectangle getBounds() {
		
		return new Rectangle((int)(x - width), (int) (y - height), (int)(width * 2), (int)(height * 2));
	}
}
