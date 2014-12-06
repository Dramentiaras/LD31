package com.goudagames.ld31.entity;

public class Entity {

	public float x, y, width, height, xMotion, yMotion;
	
	public Entity(float x, float y, float width, float height) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void update(float delta) {
		
		x += xMotion * delta;
		y += yMotion * delta;
	}
	
	public void render() {
		
	}
}
