package com.goudagames.ld31.texture;

import org.lwjgl.opengl.GL11;

public class Texture {

	private int id, width, height;
	
	public Texture(int id, int width, int height) {
		
		this.id = id;
		this.width = width;
		this.height = height;
	}
	
	public int getID() {
		
		return id;
	}
	
	public int getWidth() {
		
		return width;
	}
	
	public int getHeight() {
		
		return height;
	}
	
	public void bind() {
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}
}
