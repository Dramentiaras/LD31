package com.goudagames.ld31.level;

import java.util.Random;

public class TileSnow extends Tile {

	public TileSnow(int tileID, int index) {
		
		super(tileID, index);
		setHasVariants(true);
		setVariants(2);
	}
	
	@Override
	public int getVariant(Random rng) {
		
		float f = rng.nextFloat();
		
		if (f > 0.9f) {
			
			return 1;
		}
		else if (f > 0.88f) {
			
			return 2;
		}
		else {
			
			return 0;
		}
	}
}
