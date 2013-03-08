package com.blogspot.javagamexyz.gamexyz.utils;


public class HexMapGenerator {
	
	public static final int n = 5;
	public static final int wmult = 5;
	public static final int hmult = 3;
	
	public HexMapGenerator() {
	}

	public int[][] getDiamondSquare() {
		MidpointDisplacement md = new MidpointDisplacement();
		return md.getMap(n, wmult, hmult);
	}
	
}

