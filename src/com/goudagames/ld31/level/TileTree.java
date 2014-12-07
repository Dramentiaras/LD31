package com.goudagames.ld31.level;

import java.util.Random;

import com.goudagames.ld31.render.RenderEngine;

public class TileTree extends Tile {

	public TileTree(int tileID, int index) {
		
		super(tileID, index);
		setForeground(true);
		setObstacle(true);
		setHasVariants(true);
		setVariants(1);
	}
	
	@Override
	public int getVariant(Random rng) {
		
		float f = rng.nextFloat();
		
		if (f > 0.85f) {
			
			System.out.println(1);
			return 1;
		}
		else {
			
			return 0;
		}
	}
	
	@Override
	public void renderForegroundAt(int x, int y, Level level) {
		
		RenderEngine.instance.setRGBA(1f, 1f, 1f, 1f);
		RenderEngine.instance.renderTexturedQuad(x * 48f + 24f, y * 48f + 24f + 49f, 48f, 50f, 0f, level.getVariant(x, y) * 24f, 24f, 24f, 25f, true, TILESET);
	}
}
