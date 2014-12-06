package com.goudagames.ld31.entity;

import org.lwjgl.input.Keyboard;

import com.goudagames.ld31.base.Game;
import com.goudagames.ld31.input.Input;
import com.goudagames.ld31.render.RenderEngine;

public class EntityPlayer extends Entity {

	public static final float MAX_SPEED = 300f;
	int bodyFrame = 0;
	int prevBodyFrame = 0;
	int footFrame = 0;
	int prevFootFrame = 0;
	
	int direction = 0;
	boolean flip = false;
	boolean fired = false;
	
	float timeSinceFire = 1f;
	
	float footAnimTime = 0f;
	float bodyAnimTime = 0f;
	
	public EntityPlayer(float x, float y) {
		
		super(x, y, 32f, 32f);
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
		
		Game.instance.addEntity(new EntitySnowball(x + xOffset, y + yOffset, xm, ym));
		fired = true;
	}
	
	@Override
	public void update(float delta) {
		
		footAnimTime += delta;
		bodyAnimTime += delta;
		
		if (fired) {
			
			if (bodyAnimTime > 1f / 20f) {
				
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
			
			bodyFrame = 0;
		}
		
		if (xMotion != 0f || yMotion != 0f) {

			if (footAnimTime > 0.125f) {
				
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
				}
				
				footAnimTime = 0f;
			}
		}
		else {
			
			footFrame = 0;
		}
		
		if (Input.isKeyDown(Keyboard.KEY_W)) {
			
			yMotion = MAX_SPEED;
		}
		else if (Input.isKeyDown(Keyboard.KEY_S)) {
			
			yMotion = -MAX_SPEED;
		}
		else {
			
			yMotion = 0f;
		}
		if (Input.isKeyDown(Keyboard.KEY_D)) {
			
			xMotion = MAX_SPEED;
		}
		else if (Input.isKeyDown(Keyboard.KEY_A)) {
			
			xMotion = -MAX_SPEED;
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
		
		super.update(delta);
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
		RenderEngine.instance.renderTexturedQuad(x, y - height / 2f + 2f, width * (flip ? -1f:1f), 4f, 0f, fu, fv, 16f, 2f, true, "entities");
	}
}
