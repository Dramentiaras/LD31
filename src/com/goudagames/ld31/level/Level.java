package com.goudagames.ld31.level;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import com.goudagames.ld31.entity.Entity;

public class Level {

	private int[][] level;
	private int[][] variants;
	private Random random;
	
	public Level() {
		
		level = new int[Display.getWidth() / 48][Display.getHeight() / 48];
		variants = new int[Display.getWidth() / 48][Display.getHeight() / 48];
		random = new Random();
		
		fill(0, 0, getWidth() - 1, getHeight() - 1, Tile.snow.tileID);
		fill(0, 7, 12, 7, Tile.pathHorizontal.tileID);
		setTileAt(6, 7, Tile.pathHorizontalUp.tileID);
		fill(6, 8, 6, 9, Tile.pathVertical.tileID);
		setTileAt(6, 10, Tile.pathCornerRightDown.tileID);
		setTileAt(7, 10, Tile.pathEndLeft.tileID);
		setTileAt(13, 7, Tile.pathCornerLeftDown.tileID);
		fill(13, 0, 13, 6, Tile.pathVertical.tileID);
		
		setTileAt(22, 9, Tile.tree.tileID);
		setTileAt(23, 11, Tile.tree.tileID);
		setTileAt(19, 8, Tile.tree.tileID);
		setTileAt(18, 12, Tile.tree.tileID);
		setTileAt(15, 10, Tile.tree.tileID);
		setTileAt(23, 8, Tile.tree.tileID);
		setTileAt(17, 6, Tile.tree.tileID);
		setTileAt(21, 7, Tile.tree.tileID);
		setTileAt(20, 11, Tile.tree.tileID);
		
		setTileAt(4, 3, Tile.tree.tileID);
		setTileAt(6, 5, Tile.tree.tileID);
		setTileAt(3, 1, Tile.tree.tileID);
		setTileAt(5, 0, Tile.tree.tileID);
		setTileAt(6, 2, Tile.tree.tileID);
		
		fill(22, 0, 22, 7, Tile.pathVertical.tileID);
		setTileAt(22, 8, Tile.pathCornerLeftDown.tileID);
		setTileAt(21, 8, Tile.pathCornerRightTop.tileID);
		setTileAt(21, 9, Tile.pathVertical.tileID);
		setTileAt(21, 10, Tile.pathVerticalRight.tileID);
		setTileAt(21, 11, Tile.pathEndDown.tileID);
		setTileAt(22, 10, Tile.pathHorizontal.tileID);
		setTileAt(23, 10, Tile.pathEndLeft.tileID);
		
		setTileAt(22, 4, Tile.pathVerticalLeft.tileID);
		fill(20, 4, 21, 4, Tile.pathHorizontal.tileID);
		setTileAt(19, 4, Tile.pathVerticalRight.tileID);
		setTileAt(19, 5, Tile.pathCornerLeftDown.tileID);
		setTileAt(19, 3, Tile.pathCornerLeftTop.tileID);
		setTileAt(18, 5, Tile.pathHorizontal.tileID);
		setTileAt(18, 4, Tile.tree.tileID);
		setTileAt(18, 3, Tile.pathHorizontal.tileID);
		setTileAt(17, 5, Tile.pathCornerRightDown.tileID);
		setTileAt(17, 4, Tile.pathVertical.tileID);
		setTileAt(17, 3, Tile.pathCornerRightTop.tileID);
	}
	
	public int getWidth() {
		
		return level.length;
	}
	
	public int getHeight() {
		
		return level[0].length;
	}
	
	public int getVariant(int x, int y) {
		
		return variants[x][y];
	}
	
	public int getTileAt(int x, int y) {
		
		return level[x][y];
	}
	
	public void setTileAt(int x, int y, int id) {
		
		level[x][y] = id;
		
		if (Tile.tiles[id].hasVariants()) {
			
			variants[x][y] = Tile.tiles[id].getVariant(random);
		}
		else {
			
			variants[x][y] = 0;
		}
	}
	
	public void fill(int x0, int y0, int x1, int y1, int id) {
		
		for (int x = x0; x <= x1; x++) {
			for (int y = y0; y <= y1; y++) {
				
				setTileAt(x, y, id);
			}
		}
	}
	
	public void checkTileCollision(Entity e) {
		
		Rectangle bounds = e.getBounds();
		
		int x = (int)(e.x - (e.x % 48f));
		x /= 48f;
		int y = (int)(e.y - (e.y % 48f));
		y /= 48f;
		
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				
				if (i < 0 || i >= getWidth() || j < 0 || j >= getHeight()) {
					
					continue;
				}
				
				if (Tile.tiles[level[i][j]].isObstacle()) {
					
					Rectangle tBounds = new Rectangle(i * 48, j * 48, 48, 48);
					
					if (bounds.intersects(tBounds)) {
						
						e.collideWithTile(i, j, this);
					}
				}
			}
		}
	}
	
	private ArrayList<Vector2f> foreground = new ArrayList<Vector2f>();
	
	public void render() {
		
		for (int x = 0; x < level.length; x++) {
			for (int y = level[x].length - 1; y >= 0; y--) {
				
				Tile tile = Tile.tiles[level[x][y]];
				
				if (tile.isForeground()) {
					
					foreground.add(new Vector2f(x, y));
				}
				
				if (tile.shouldRender()) {
					
					tile.renderAt(x, y, this);
				}
			}
		}
	}
	
	public void renderForeground() {
		
		for (Vector2f v : foreground) {
			
			int x = (int)v.x;
			int y = (int)v.y;
			Tile tile = Tile.tiles[level[x][y]];
			
			tile.renderForegroundAt(x, y, this);
		}
		
		foreground.clear();
	}
}
