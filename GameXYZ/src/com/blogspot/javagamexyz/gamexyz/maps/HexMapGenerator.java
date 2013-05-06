package com.blogspot.javagamexyz.gamexyz.maps;


public class HexMapGenerator {
	
	public static final int n = 4;
	public static final int wmult = 2;
	public static final int hmult = 2;
	
	public HexMapGenerator() {
	}

	public int[][] getDiamondSquare() {
		MidpointDisplacement md = new MidpointDisplacement();
		return md.getMap(n, wmult, hmult);
	}
	
}