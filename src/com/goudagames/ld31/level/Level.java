package com.goudagames.ld31.level;

import org.lwjgl.opengl.Display;

public class Level {

	private int[][] level;
	
	public Level() {
		
		level = new int[Display.getWidth() / 48][Display.getHeight() / 48];
		
		fill(0, 0, getWidth() - 1, getHeight() - 1, Tile.snow.tileID);
		fill(0, 7, 12, 7, Tile.pathHorizontal.tileID);
		setTileAt(6, 7, Tile.pathHorizontalUp.tileID);
		fill(6, 8, 6, 9, Tile.pathVertical.tileID);
		setTileAt(6, 10, Tile.pathCornerRightDown.tileID);
		setTileAt(7, 10, Tile.pathEndLeft.tileID);
		setTileAt(13, 7, Tile.pathCornerLeftDown.tileID);
		fill(13, 0, 13, 6, Tile.pathVertical.tileID);
	}
	
	public int getWidth() {
		
		return level.length;
	}
	
	public int getHeight() {
		
		return level[0].length;
	}
	
	public void setTileAt(int x, int y, int id) {
		
		level[x][y] = id;
	}
	
	public void fill(int x0, int y0, int x1, int y1, int id) {
		
		for (int x = x0; x <= x1; x++) {
			for (int y = y0; y <= y1; y++) {
				
				setTileAt(x, y, id);
			}
		}
	}
	
	public void render() {
		
		for (int x = 0; x < level.length; x++) {
			for (int y = 0; y < level[x].length; y++) {
				
				Tile tile = Tile.tiles[level[x][y]];
				
				if (tile.shouldRender()) {
					
					tile.renderAt(x, y);
				}
			}
		}
	}
}
