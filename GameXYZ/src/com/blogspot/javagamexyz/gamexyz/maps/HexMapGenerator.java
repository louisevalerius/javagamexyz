package com.blogspot.javagamexyz.gamexyz.maps;


public class HexMapGenerator {
	
	public static final int n = 4;
	public static final int wmult = 2;
	public static final int hmult = 2;
	
	public float deepWaterThreshold, 
	 shallowWaterThreshold,
	 desertThreshold,
	 plainsThreshold,
	 grasslandThreshold,
	 forestThreshold,
	 hillsThreshold,
	 mountainsThreshold;
	
	public HexMapGenerator() {
		
		deepWaterThreshold = 0.5f;
		shallowWaterThreshold = 0.55f;
		desertThreshold = 0.58f;
		plainsThreshold = 0.62f;
		grasslandThreshold = 0.7f;
		forestThreshold = 0.8f;
		hillsThreshold = 0.88f;
		mountainsThreshold = 0.95f;
		
	}

	public int[][] getDiamondSquare() {
		MidpointDisplacement md = new MidpointDisplacement();
		
		float[][] map = md.getMap2(n, wmult, hmult);
		int[][] returnMap = new int[map.length][map[0].length];
		// Use the thresholds to fill in the return map
		for(int row = 0; row < map.length; row++){
			for(int col = 0; col < map[row].length; col++){
				if (map[row][col] < deepWaterThreshold) returnMap[row][col] = 0;
				else if (map[row][col] < shallowWaterThreshold) returnMap[row][col] = 1;
				else if (map[row][col] < desertThreshold) returnMap[row][col] = 2;
				else if (map[row][col] < plainsThreshold) returnMap[row][col] = 3;
				else if (map[row][col] < grasslandThreshold) returnMap[row][col] = 4;
				else if (map[row][col] < forestThreshold) returnMap[row][col] = 5;
				else if (map[row][col] < hillsThreshold) returnMap[row][col] = 6;
				else if (map[row][col] < mountainsThreshold) returnMap[row][col] = 7;
				else returnMap[row][col] = 8;
			}
		}
		
		
		return returnMap;
	}
	
}