package com.goudagames.ld31.entity;

import java.awt.Rectangle;

import com.goudagames.ld31.level.Level;

public class Entity {

	public float x, y, width, height, xMotion, yMotion;
	private boolean collidable = true;
	
	public Entity(float x, float y, float width, float height) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void reset() {
		
	}
	
	public void update(float delta) {
		
		x += xMotion * delta;
		y += yMotion * delta;
	}
	
	public boolean isCollidable() {
		
		return collidable;
	}
	
	public void setCollidable(boolean collidable) {
		
		this.collidable = collidable;
	}
	
	public void render() {
		
	}
	
	public void collideWithTile(int x, int y, Level level) {
		
	}
	
	public void collide(Entity e) {
		
	}
	
	public Rectangle getBounds() {
		
		return new Rectangle((int)(x - width / 2f), (int)(y - height / 2f), (int)width, (int)height);
	}
}
