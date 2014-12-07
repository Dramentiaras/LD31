package com.goudagames.ld31.level;

import java.util.Random;

import com.goudagames.ld31.render.RenderEngine;

public class Tile {

	public static Tile[] tiles = new Tile[256];
	
	public static String TILESET = "tileset";
	
	public int tileID;
	public int index;
	
	private boolean render = true;
	private boolean variants = false;
	private boolean foreground = false;
	private boolean obstacle = false;
	
	private int maxVariants = 0;
	
	public static final Tile air = new Tile(0, 0).setShouldRender(false);
	public static final Tile snow = new TileSnow(1, 18);
	public static final Tile pathHorizontal = new Tile(2, 1);
	public static final Tile pathVertical = new Tile(3, 2);
	public static final Tile pathCornerLeftTop = new Tile(4, 3);
	public static final Tile pathCornerRightTop = new Tile(5, 4);
	public static final Tile pathCornerRightDown = new Tile(6, 5);
	public static final Tile pathCornerLeftDown = new Tile(7, 6);
	public static final Tile pathVerticalLeft = new Tile(8, 7);
	public static final Tile pathHorizontalUp = new Tile(9, 8);
	public static final Tile pathVerticalRight = new Tile(10, 9);
	public static final Tile pathHorizontalDown = new Tile(11, 10);
	public static final Tile pathEndLeft = new Tile(12, 11);
	public static final Tile pathEndRight = new Tile(13, 12);
	public static final Tile pathEndDown = new Tile(14, 13);
	public static final Tile pathEndUp = new Tile(15, 14);
	public static final Tile tree = new TileTree(16, 34);
	
	public Tile(int tileID, int index) {
		
		this.tileID = tileID;
		this.index = index;
		
		if (tiles[tileID] != null) {
			
			System.out.println("Tile with ID: " + tileID + " already exists, overwriting...");
		}
		
		tiles[tileID] = this;
	}
	
	public boolean isObstacle() {
		
		return obstacle;
	}
	
	public Tile setObstacle(boolean obstacle) {
		
		this.obstacle = obstacle;
		return this;
	}
	
	public boolean isForeground() {
		
		return foreground;
	}
	
	public Tile setForeground(boolean foreground) {
		
		this.foreground = foreground;
		return this;
	}
	
	public int getVariants() {
		
		return maxVariants;
	}
	
	public Tile setVariants(int variants) {
		
		this.maxVariants = variants;
		return this;
	}
	
	public boolean hasVariants() {
		
		return variants;
	}
	
	public Tile setHasVariants(boolean variants) {
		
		this.variants = variants;
		return this;
	}
	
	public boolean shouldRender() {
		
		return render;
	}
	
	public Tile setShouldRender(boolean render) {
		
		this.render = render;
		return this;
	}
	
	public int getVariant(Random rng) {
		
		return rng.nextInt(maxVariants + 1);
	}
	
	public void renderForegroundAt(int x, int y, Level level) {
		
	}
	
	public void renderAt(int x, int y, Level level) {
		
		RenderEngine.instance.setRGBA(1f, 1f, 1f, 1f);
		
		int i = index;
		
		if (variants) {
			
			i += level.getVariant(x, y);
		}
		
		float u = (i % 16) * 24f;
		float v = (float)Math.floor(i / 16.0) * 24f;
		
		RenderEngine.instance.renderTexturedQuad(x * 48f + 24f, y * 48f + 24f, 48f, 48f, 0f, u, v, 24f, 24f, true, TILESET);
	}
}
